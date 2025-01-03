package com.example.security_demo.presentation.rest;

import com.evo.common.UserAuthority;
import com.evo.common.webapp.security.AuthorityService;
import com.example.security_demo.application.config.TokenProvider;
import com.example.security_demo.application.dto.request.Page.SearchRequest;
import com.example.security_demo.application.dto.request.user.*;
import com.example.security_demo.application.dto.response.user.UserResponse;
import com.example.security_demo.application.dto.response.user.UsersResponse;
import com.example.security_demo.application.service.impl.UserCommandImpl;
import com.example.security_demo.domain.exception.InvalidPasswordException;
import com.example.security_demo.domain.exception.UserExistedException;
import com.example.security_demo.domain.exception.UserNotFoundException;
import com.example.security_demo.application.service.EmailService;
import com.example.security_demo.application.service.UserKeycloakService;
import com.example.security_demo.application.service.DefaultUserService;
import com.example.security_demo.application.service.UserService;
import com.example.security_demo.application.service.keyCloakService.VerifyKeyService;
import com.example.security_demo.application.service.storageService.AuthorityServiceImplement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

  private final DefaultUserService defaultUserService;
  private final UserService userService;
  private final EmailService emailService;
  private final UserKeycloakService userKeycloakService;
  private final VerifyKeyService verifyKeyService;
  private final TokenProvider tokenProvider;
  private final AuthorityServiceImplement authorityServiceImplement;
  private final AuthorityService authorityService;
  private final UserCommandImpl userCommand;

  //    @PostMapping("/register")
//    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO user) {
//        try {
//            return ResponseEntity.ok(userService.register(user));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest user) {
    try {
      return ResponseEntity.ok(userCommand.createUser(user));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


  @PostMapping("/login")
  public String login(@Valid @RequestBody LoginRequest userDTO, HttpServletRequest request)
      throws Exception {
    try {
      return defaultUserService.login(userDTO);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @GetMapping("/confirm-login-email")
  public ResponseEntity<?> confirmLoginEmail(@RequestParam String email, @RequestBody String code) {
    return ResponseEntity.ok(defaultUserService.verifyLoginGenerateToken(email, code));
  }

  @GetMapping("/{userId}")
  @PreAuthorize("hasPermission('USER','VIEW')")
  public ResponseEntity<?> getUserById(@PathVariable("userId") String userId) {
    return ResponseEntity.ok(defaultUserService.getUserById(userId));
  }

  @PutMapping("update")
  @PreAuthorize("hasPermission('USER','UPDATE')")
  public ResponseEntity<?> updateUserById(
      @Valid @RequestBody UpdateInforRequest updateInforRequest) {
    try {
      //return ResponseEntity.ok(defaultUserService.updateUserInfo(userId, updateInforRequestDTO));
      return ResponseEntity.ok(userCommand.updateUser(updateInforRequest));
    } catch (UserExistedException e) {
      throw new RuntimeException(e);
    }
  }

  @PutMapping("/{userId}/change-password")
  @PreAuthorize("hasPermission('USER','UPDATE')")
  public ResponseEntity<?> changeUserPassword(@PathVariable("userId") String userId,
      @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
    try {
      defaultUserService.changePassword(userId, changePasswordRequest);
      return ResponseEntity.ok("Change password successful");
    } catch (InvalidPasswordException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("/send-email")
  @PreAuthorize("hasPermission('USER','UPDATE')")
  public ResponseEntity<?> sendResetPasswordRequest(@RequestParam String email) {
    return ResponseEntity.ok(defaultUserService.forgotPasswordRequest(email));
  }

  @PostMapping("/reset-password-token")
  @PreAuthorize("hasPermission('USER','UPDATE')")
  public ResponseEntity<?> resetPasswordByToken(
      @Valid @RequestBody RetakePasswordByTokenRequest retakePasswordByTokenDTO) {
    return ResponseEntity.ok(defaultUserService.resetPasswordByToken(retakePasswordByTokenDTO));
  }

  //        @PostMapping("/logoutAccount")
//    public ResponseEntity<?> logout(@RequestParam("authorization") String authorizationHeader,@RequestParam("refresh_token") String refreshToken){
//        defaultUserService.logout(logoutRequest);
//        return ResponseEntity.ok( defaultUserService.logout(logoutRequest));
//    }
  @PostMapping("/logout-account")
  public ResponseEntity<?> logout(@RequestHeader("authorization") String authorizationHeader,
      @RequestParam("refresh_token") String refreshToken) {
    return ResponseEntity.ok(userService.logout(authorizationHeader, refreshToken));
  }

  @GetMapping("/confirm-register-email")
  public String confirmEmail(@RequestParam("code") String code) {
    if (defaultUserService.confirmRegisterCode(code)) {
      return "Confirm register email succesfull";
    } else {
      return "Invalid code";
    }
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(
      @RequestParam("refresh_token") RefreshTokenRequest request) {
    return ResponseEntity.ok(userService.refreshToken(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutKcl(@RequestHeader("authorization") String authorizationHeader,
      @RequestParam("refresh_token") String refreshToken) {
    return ResponseEntity.ok().body(userKeycloakService.logout(authorizationHeader, refreshToken));
  }

  @PostMapping("{userId}/soft-delete")
  @PreAuthorize("hasPermission('USER','UPDATE')")
  public ResponseEntity<?> softDeleted(@PathVariable("userId") String userId) {
    return ResponseEntity.ok().body(userCommand.softDeleted(userId));
  }

  @PostMapping("/enable-user/{userId}")
  public ResponseEntity<?> enableUser(@RequestHeader("authorization") String authorizationHeader,
      @PathVariable("userId") String userId,
      @RequestBody EnableUserRequest request) {
    return ResponseEntity.ok().body(userService.enableUser(authorizationHeader, userId, request));
  }

  @PostMapping("/reset-password-sync/{userId}")
  public ResponseEntity<?> resetPassword(@RequestHeader("authorization") String accessToken,
      @PathVariable("userId") String userId,
      @RequestBody ResetPasswordRequest request) {
    return ResponseEntity.ok().body(userService.resetPassword(accessToken, userId, request));
  }

  @PostMapping("/get-user-infor")
  @PreAuthorize("hasPermission('USER','VIEW')")
  public ResponseEntity<?> getUserInfor(@RequestHeader("authorization") String accessToken) {
    return ResponseEntity.ok().body(defaultUserService.getUserInfor(accessToken));
  }

  @GetMapping("/users-infor")
//    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
  public ResponseEntity<UsersResponse<UserResponse>> getUsers(
      @ModelAttribute SearchRequest request) {
    return ResponseEntity.ok().body(defaultUserService.getUsers(request));
  }

  @GetMapping("/users-search")
//    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
  public ResponseEntity<UsersResponse<UserResponse>> getSearch(
      @ModelAttribute UserSearchRequest request) {
    return ResponseEntity.ok().body(defaultUserService.getUsers(request));
  }

  @GetMapping("/verify-client-key/{clientId}/{clientSecret}")
  public ResponseEntity<?> verifyClientKey(@PathVariable("clientId") String clientId,
      @PathVariable("clientSecret") String clientSecret) {
    return ResponseEntity.ok().body(verifyKeyService.verifyClientKey(clientId, clientSecret));
  }

  @GetMapping("/certificate/.well-known/jwks.json")
  public ResponseEntity<?> getPublicKey() {
    return ResponseEntity.ok(tokenProvider.jwkSet().toJSONObject());
  }

  @GetMapping("/{email}/authorities-by-email")
  ResponseEntity<UserAuthority> getUserAuthority(@PathVariable("email") String email) {
    if (email == null || email.isEmpty()) {
      return ResponseEntity.badRequest().body(null);
    }
    return ResponseEntity.ok().body(authorityServiceImplement.getUserAuthority(email));
  }

  @PostMapping("update-user-role")
  ResponseEntity<?> updateUserRole(@RequestBody RoleUserRequest roleUserRequest) {
    return ResponseEntity.ok().body(userCommand.updateRoleUser(roleUserRequest));
  }
}
