package com.example.security_demo.application.service;

import com.example.security_demo.application.dto.request.user.EnableUserRequest;
import com.example.security_demo.application.dto.request.user.RefreshTokenRequest;
import com.example.security_demo.application.dto.request.user.RegisterRequest;
import com.example.security_demo.application.dto.request.user.ResetPasswordRequest;
import com.example.security_demo.application.mapper.UserCommandMapper;
import com.example.security_demo.application.service.impl.UserCommandImpl;
import com.example.security_demo.domain.exception.UserExistedException;
import com.example.security_demo.application.service.keyCloakService.IUserServiceStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserServiceStrategy {

  private final UserKeycloakService userKeycloakService;
  private final DefaultUserService defaultUserService;
  private final UserCommandMapper userCommandMapper;
  @Value("${idp.enabled}")
  private boolean keycloakEnabled;
  private final UserCommandImpl userCommand;

  @Override
  public ResponseEntity<?> register(RegisterRequest registerDTO) throws UserExistedException {
    if (keycloakEnabled) {
      return ResponseEntity.ok().body(userKeycloakService.createKeycloakUser(registerDTO));
    } else {
//            return ResponseEntity.ok().body(defaultUserService.register(userCommandMapper.fromRegisterDto(registerDTO)));
      return ResponseEntity.ok().body(userCommand.createUser(registerDTO));
    }
  }

  @Override
  public ResponseEntity<?> refreshToken(RefreshTokenRequest request) {
    if (keycloakEnabled) {
      return ResponseEntity.ok().body(userKeycloakService.refreshToken(request));
    } else {
      return ResponseEntity.ok().body(defaultUserService.refreshToken(request));
    }
  }

  @Override
  public ResponseEntity<?> logout(String accessToken, String refreshToken) {
    if (keycloakEnabled) {
      return ResponseEntity.ok().body(userKeycloakService.logout(accessToken, refreshToken));
    } else {
      return ResponseEntity.ok().body(defaultUserService.logout(accessToken, refreshToken));
    }
  }

  @Override
  public ResponseEntity<?> enableUser(String accessToken, String userId,
      EnableUserRequest request) {
    if (keycloakEnabled) {
      return ResponseEntity.ok().body(userKeycloakService.enableUser(accessToken, userId, request));
    } else {
      return ResponseEntity.ok().body(defaultUserService.enableUser(userId, request));
    }
  }

  @Override
  public ResponseEntity<?> resetPassword(String accessToken, String userId,
      ResetPasswordRequest request) {
    userKeycloakService.resetPassword(accessToken, userId, request);
    defaultUserService.resetPassword(userId, request);
    return ResponseEntity.ok().body("Change password successful");
  }
}

