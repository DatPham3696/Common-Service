package com.example.security_demo.application.service;

import com.evo.common.webapp.config.CommonService;
import com.evo.common.webapp.config.RedisService;
import com.example.security_demo.application.config.JwtTokenUtils;
import com.example.security_demo.application.dto.request.Page.SearchRequest;
import com.example.security_demo.application.dto.request.user.*;
import com.example.security_demo.application.dto.response.user.*;
import com.example.security_demo.domain.UserDomain;
import com.example.security_demo.domain.enums.LogInfor;
import com.example.security_demo.domain.exception.InvalidPasswordException;
import com.example.security_demo.domain.exception.UserExistedException;
import com.example.security_demo.domain.exception.UserNotFoundException;
import com.example.security_demo.infrastructure.persistance.*;
import com.example.security_demo.infrastructure.repository.*;
import com.example.security_demo.infrastructure.repository.custom.UserCustomRepositoryImpl;
import com.example.security_demo.infrastructure.repository.repoImpl.PermissionRepositoryImpl;
import com.example.security_demo.infrastructure.repository.repoImpl.RoleRepositoryImpl;
import com.example.security_demo.infrastructure.repository.repoImpl.RoleUserRepositoryImpl;
import com.example.security_demo.infrastructure.repository.repoImpl.UserRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService {
    private final IUserRepositoryJpa userRepository;
    private final IRoleRepositoryJpa roleRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final IPermissionRepositoryJpa permissionRepository;
    private final IRolePermissionRepositoryJpa rolePermissionRepository;
    private final EmailService emailService;
    private final IInvalidTokenRepositoryJpa invalidTokenRepository;
    //    private final RedisService redisService;
    private final LogService logService;
    private final IRoleUserRepositoryJpa roleUserRepository;
    private final HttpServletRequest request;
    private final RefreshTokenService refreshTokenService;
    private final UserKeycloakService userKeycloakService;
    private final UserCustomRepositoryImpl userRepositoryCustomImpl;
    private final CommonService commonService;
    private final RedisService redisService;
    private final UserRepositoryImpl userRepositoryImpl;
    private final RoleRepositoryImpl roleRepositoryImpl;
    private final PermissionRepositoryImpl permissionRepositoryImpl;
    private final RoleUserRepositoryImpl roleUserRepositoryImpl;

    public UserResponse register(RegisterDTO registerDTO) throws UserExistedException {
        if (userRepositoryImpl.existsByEmail(registerDTO.getEmail())) {
            throw new UserExistedException("Email already exists");
        }
        Role role = roleRepositoryImpl.findByCode("HR").orElseThrow(() -> new RuntimeException("role not found"));
        if (role == null) {
            throw new RuntimeException("Role not found: USER");
        }
        String verificationCode = UUID.randomUUID().toString().substring(0, 6);
        UserDomain userDomain = new UserDomain().create(registerDTO, passwordEncoder, verificationCode);
        User user = userRepositoryImpl.save(User.builder()
                .userName(userDomain.getUserName())
                .keyclUserId(userKeycloakService.getKeycloakUserId(registerDTO))
                .address(userDomain.getAddress())
                .passWord(userDomain.getPassWord())
                .email(userDomain.getEmail())
                .dateOfBirth(userDomain.getDateOfBirth())
                .verificationCode(userDomain.getVerificationCode())
                .phoneNumber(userDomain.getPhoneNumber())
                .build());

        RoleUser roleUser = roleUserRepository.save(RoleUser.builder()
                .roleId(role.getId())
                .userId(user.getId())
                .build());

        List<String> descriptions = rolePermissionRepository.findAllByRoleId(role.getId()).stream()
                .map(RolePermission::getPermissionId)
                .map(permissionId -> permissionRepositoryImpl.findById(permissionId)
                        .map(Permission::getScope)
                        .orElse("Unknown Permission"))
                .toList();

        String sub = "Confirm register account";
        String text = "Hello " + user.getUsername() + ",\n\n" +
                "Thank you for registering an account. Please click the link below to confirm your account:\n" +
                "http://localhost:8080/confirmemail?code=" + verificationCode;
        emailService.sendEmail(user.getEmail(), sub, text);
        return UserResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .roleName(role.getCode())
                .perDescription(descriptions)
                .build();
    }

    public boolean confirmRegisterCode(String code) {
        User user = userRepositoryImpl.findByVerificationCode(code);
        if (user != null && !user.isEmailVerified()) {
            user.setEmailVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public String login(LoginRequestDTO userDTO) {
        User user = userRepositoryImpl.findByEmail(userDTO.getEmail()).orElseThrow(() -> new RuntimeException("Invalid inforr"));
        if (!passwordEncoder.matches(userDTO.getPassWord(), user.getPassword())) {
            throw new RuntimeException("invalid infor");
        }
        String verificationCode = UUID.randomUUID().toString().substring(0, 6);
        emailService.sendEmail(user.getEmail(), "Verify login code", "Please using this code to complete your login:\n " +
                "http://localhost:8080/confirmemail?code=" + verificationCode);
        redisService.saveStringToRedis(user.getEmail(), verificationCode, 5, TimeUnit.MINUTES);
        return "Please check your verify code in your email ";
    }

    public JwtResponse verifyLoginGenerateToken(String email, String code) {
        String storeCode = redisService.getStringFromRedis(email);
        if (storeCode != null && storeCode.equals(code)) {
            redisService.deleteFromRedis(email);
        User user = userRepositoryImpl.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid inforr"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        securityContextHolder.setAuthentication(authentication);

        logService.saveLog(UserActivityLog.builder()
                .action(LogInfor.LOGIN.getDescription())
                .browserId(request.getRemoteAddr())
                .userId(user.getId())
                .timestamp(LocalDateTime.now())
                .build());
//        refreshTokenService.deleteByUserId(user.getId());
//        commonService.deleteFromRedis();
        String token = jwtTokenUtils.generateToken(user);
        return JwtResponse.builder()
                .accessToken(token)
                .refreshToken(jwtTokenUtils.generaRefreshToken(user))
                .build();
        } else {
            throw new RuntimeException("Invalid code");
        }
    }

    public TokenResponse refreshToken(RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();
        String refreshTokenJti = jwtTokenUtils.getJtiFromToken(refreshToken);
        if (commonService.isTokenExist("invalid-refresh-token:" + refreshTokenJti)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String email = jwtTokenUtils.getSubFromToken(refreshToken);
        User user = userRepositoryImpl.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String newAccessToken = jwtTokenUtils.generateToken(user);

        String newRefreshToken = jwtTokenUtils.generaRefreshToken(user);

        commonService.storeToken("invalid-refresh-token:" + refreshTokenJti, refreshToken, 7 * 24 * 60 * 60); // 7 ngày

        commonService.storeToken("valid-refresh-token:" + jwtTokenUtils.getJtiFromToken(newRefreshToken), newRefreshToken, 7 * 24 * 60 * 60); // 7 ngày

        commonService.storeToken("valid-access-token:" + jwtTokenUtils.getJtiFromToken(newAccessToken), newAccessToken, 60 * 60); // 1 giờ

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public UserResponse getUserById(String userId) {
        User user = userRepositoryImpl.findById(userId).orElseThrow(() -> new RuntimeException("Cant find user"));
        RoleUser roleUser = roleUserRepositoryImpl.findByUserId(user.getId());
        Role role = roleRepository.findById(roleUser.getRoleId()).orElseThrow(() -> new RuntimeException("Cant find roleName"));
        String roleName = role.getCode();
        List<Long> permissionId = rolePermissionRepository.findAllByRoleId(role.getId()).stream().map(RolePermission::getPermissionId).toList();
        List<String> description = permissionRepository.findAllById(permissionId).stream().map(Permission::getScope).toList();
        return UserResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .roleName(roleName)
                .perDescription(description)
                .build();
    }

    public UserResponse updateUserInfo(String userId, UpdateInforRequestDTO updateInforRequestDTO) throws UserNotFoundException,
            UserExistedException {
        User existingUser = userRepositoryImpl.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        existingUser.setUserName(updateInforRequestDTO.getUserName());
        existingUser.setAddress(updateInforRequestDTO.getAddress());
        existingUser.setPhoneNumber(updateInforRequestDTO.getPhoneNumber());
        existingUser.setDateOfBirth(updateInforRequestDTO.getDateOfBirth());
        User updatedUser = userRepositoryImpl.save(existingUser);

        RoleUser roleUser = roleUserRepositoryImpl.findByUserId(updatedUser.getId());
        Role role = roleRepository.findById(roleUser.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        List<String> descriptions = rolePermissionRepository.findAllByRoleId(role.getId()).stream()
                .map(RolePermission::getPermissionId)
                .map(permissionId -> permissionRepositoryImpl.findById(permissionId)
                        .map(Permission::getScope)
                        .orElse("Unknown Permission"))
                .toList();

        String sub = "Modify infor";
        String text = "Hello " + existingUser.getUsername() + ",\n\n" +
                "Modify information successfully";
        emailService.sendEmail(existingUser.getEmail(), sub, text);

        logService.saveLog(UserActivityLog.builder()
                .action(LogInfor.UPDATE.getDescription())
                .browserId(request.getRemoteAddr())
                .userId(existingUser.getId())
                .timestamp(LocalDateTime.now())
                .build());

        return UserResponse.builder()
                .userName(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .address(updatedUser.getAddress())
                .dateOfBirth(updatedUser.getDateOfBirth())
                .roleName(role.getCode())
                .perDescription(descriptions)
                .build();
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public ChangePasswordResponse changePassword(String userId, ChangePasswordRequest changePasswordRequest) throws InvalidPasswordException {
        User user = userRepositoryImpl.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Password not match");
        }
        user.setPassWord(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepositoryImpl.save(user);
        logService.saveLog(UserActivityLog.builder()
                .action(LogInfor.CHANGEPASSWORD.getDescription())
                .browserId(request.getRemoteAddr())
                .userId(user.getId())
                .timestamp(LocalDateTime.now())
                .build());
        return ChangePasswordResponse.builder().email(user.getEmail()).build();
    }

    public String forgotPasswordRequest(String email) {
        User user = userRepositoryImpl.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found user"));
        String token = jwtTokenUtils.generateToken(user);
        emailService.sendPasswordResetToken(email, token);
        return "Open gmail and check for our email to reset your password";
    }

    public String resetPasswordByToken(RetakePasswordByTokenDTO retakePasswordByTokenDTO) {
        String email = jwtTokenUtils.getSubFromToken(retakePasswordByTokenDTO.getToken());
        User user = userRepositoryImpl.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found user"));
        user.setPassWord(passwordEncoder.encode(retakePasswordByTokenDTO.getNewPassword()));
        userRepositoryImpl.save(user);
        logService.saveLog(UserActivityLog.builder()
                .action(LogInfor.RESETPASSWORD.getDescription())
                .browserId(request.getRemoteAddr())
                .userId(user.getId())
                .timestamp(LocalDateTime.now())
                .build());
        return "Change password successfully";
    }

    public String logout(String accessToken, String refreshToken) {
        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7).trim();
        }
//        InvalidToken invalidToken = InvalidToken.builder()
//                .id(jwtTokenUtils.getJtiFromToken(accessToken))
//                .expiryTime(jwtTokenUtils.getExpirationTimeFromToken(accessToken))
//                .refreshTokenId(jwtTokenUtils.getJtiFromToken(refreshToken))
//                .build();
//        invalidTokenRepository.save(invalidToken);
        String accessTokenJti = jwtTokenUtils.getJtiFromToken(accessToken);
        String accessTokenExp = jwtTokenUtils.getExpirationTimeFromToken(accessToken).toString();
        String refreshTokenJti = jwtTokenUtils.getJtiFromToken(refreshToken);
        String refreshTokenExp = jwtTokenUtils.getExpirationTimeFromToken(refreshToken).toString();

        commonService.storeToken("invalid-access-token:" + accessTokenJti , accessTokenExp, 60 * 60);
        commonService.storeToken("invalid-refresh-token:" + refreshTokenJti, refreshTokenExp, 7 * 24 * 60 * 60);
        return "logout success";
    }

    @Transactional
    public String deletedSoft(String userId, SoftDeleteRequest request) {
        User user = userRepositoryImpl.findById(userId).orElseThrow(() -> new RuntimeException("Not find user"));
        boolean status = request.isStatus();
        user.setDeleted(status);
        userRepositoryImpl.save(user);
        return "User updated";
    }

    public String enableUser(String userId, EnableUserRequest request) {
        User user = userRepositoryImpl.findById(userId).orElseThrow(() -> new RuntimeException("Not find user"));
        boolean enabled = request.isEnabled();
        user.setEnabled(enabled);
        userRepositoryImpl.save(user);
        return "User updated";
    }

    public String resetPassword(String userId, ResetPasswordRequest request) {
        User user = userRepositoryImpl.findByKeyclUserId(userId);
        user.setPassWord(passwordEncoder.encode(request.getNewPassword()));
        userRepositoryImpl.save(user);
        return "Reset password successfully";
    }

    public UserResponse getUserInfor(String token) {
        if (token.startsWith("Bearer")) {
            token = token.substring(7).trim();
        }
        User user = userRepositoryImpl.findByEmail(jwtTokenUtils.getSubFromToken(token)).get();
        RoleUser roleUser = roleUserRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(roleUser.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        List<String> descriptions = rolePermissionRepository.findAllByRoleId(role.getId()).stream()
                .map(RolePermission::getPermissionId)
                .map(permissionId -> permissionRepositoryImpl.findById(permissionId)
                        .map(Permission::getScope)
                        .orElse("Unknown Permission"))
                .toList();
        return UserResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .roleName(role.getCode())
                .perDescription(descriptions)
                .build();
    }

    public UsersResponse<UserResponse> getUsers(SearchRequest request) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, request.getAttribute());//

        if ("desc".equalsIgnoreCase(request.getSort())) {
            order = new Sort.Order(Sort.Direction.DESC, request.getAttribute());
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(order));
        Page<User> userPage = (request.getKeyword() == null || request.getKeyword().isBlank()) ? userRepository
                .findAll(sortedPageable) :
                userRepository.findByKeyWord(request.getKeyword().trim(), sortedPageable);
        List<UserResponse> userResponseDTOList = userPage.getContent().stream().map(user -> UserResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .build()).toList();
        return new UsersResponse<>(userResponseDTOList, userPage.getTotalPages());
    }

    public UsersResponse<UserResponse> getUsers(UserSearchRequest request) {
        List<User> users = userRepositoryCustomImpl.searchUser(request);
        List<UserResponse> userResponseList = users.stream().map(user -> UserResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .build()).toList();
        return new UsersResponse<>(userResponseList, request.getPage());
    }
}


























