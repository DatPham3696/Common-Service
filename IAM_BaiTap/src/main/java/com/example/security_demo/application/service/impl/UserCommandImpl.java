package com.example.security_demo.application.service.impl;

import com.example.security_demo.application.config.AuthenticationUtils;
import com.example.security_demo.application.dto.request.user.RegisterRequest;
import com.example.security_demo.application.dto.request.user.RoleUserRequest;
import com.example.security_demo.application.dto.request.user.UpdateInforRequest;
import com.example.security_demo.application.mapper.RoleCommandMapper;
import com.example.security_demo.application.mapper.UserCommandMapper;
import com.example.security_demo.application.service.RoleService;
import com.example.security_demo.application.service.UserCommandService;
import com.example.security_demo.application.service.UserKeycloakService;
import com.example.security_demo.domain.command.RegisterCmd;
import com.example.security_demo.domain.command.RoleUserCmd;
import com.example.security_demo.domain.command.UpdateInforCmd;
import com.example.security_demo.domain.domainEntity.RoleUser;
import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.domain.exception.UserExistedException;
import com.example.security_demo.domain.repository.IRoleUserDomainRepository;
import com.example.security_demo.domain.repository.IUserDomainRepository;
import com.example.security_demo.infrastructure.persistance.repository.repoImpl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserCommandImpl implements UserCommandService {

  private final UserCommandMapper userCommandMapper;
  private final IUserDomainRepository userRepository;
  private final UserRepositoryImpl userRepositoryImpl;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;
  private final UserKeycloakService userKeycloakService;
  private final IRoleUserDomainRepository roleUserRepository;
  private final RoleCommandMapper roleCommandMapper;

  @Override
  public String createUser(RegisterRequest registerRequest) throws UserExistedException {
    RegisterCmd registerCommand = userCommandMapper.fromRegisterDto(registerRequest);

    if (userRepositoryImpl.existsByEmail(registerCommand.getEmail())) {
      throw new UserExistedException("Email already exists");
    }
    if (registerCommand.getRolesId() == null) {
      throw new RuntimeException("Role not found " + registerCommand.getRolesId());
    }
//        List<Long> roleExists = roleService.listRoleExist(registerCommand.getRolesId());
    String verificationCode = UUID.randomUUID().toString().substring(0, 6);
    User userDomain = new User(registerCommand,
        passwordEncoder,
        verificationCode,
        () -> userKeycloakService.getKeycloakUserId(userCommandMapper.from(registerCommand)));
    userRepositoryImpl.save(userDomain);
    return "Create account successful";
  }

  @Override
  public String updateUser(UpdateInforRequest updateInforRequest) throws UserExistedException {
    UpdateInforCmd updateInforCommand = userCommandMapper.fromUpdateInforDto(updateInforRequest);
    String email = AuthenticationUtils.getEmail();
    User updateUser = userRepository.findUserByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
    updateUser.update(updateInforCommand, passwordEncoder);
    userRepositoryImpl.save(updateUser);
    return "Update user successful";
  }

  @Override
  public String softDeleted(String userId) {
    User user = userRepository.findUserById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    user.softDelete();
    userRepositoryImpl.save(user);
    return "Soft delete user successful";
  }

  @Override
  public String updateRoleUser(RoleUserRequest roleUserRequest) {
    User user = userRepository
        .findUserById(roleUserRequest.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));
    RoleUserCmd roleUserCmd = roleCommandMapper.fromUpdateRequest(roleUserRequest);
    List<RoleUser> roleUserExist = roleUserRepository.findAllByUserId(roleUserRequest.getUserId());
    user.updateRoleUser(roleUserExist, roleUserCmd);
    userRepository.save(user);
    return "update user role success";
  }


}
