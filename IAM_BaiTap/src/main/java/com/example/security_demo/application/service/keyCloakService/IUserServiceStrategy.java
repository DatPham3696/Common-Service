package com.example.security_demo.application.service.keyCloakService;

import com.example.security_demo.application.dto.request.user.EnableUserRequest;
import com.example.security_demo.application.dto.request.user.RefreshTokenRequest;
import com.example.security_demo.application.dto.request.user.RegisterDTO;
import com.example.security_demo.application.dto.request.user.ResetPasswordRequest;
import com.example.security_demo.domain.exception.UserExistedException;
import org.springframework.http.ResponseEntity;

public interface IUserServiceStrategy {
    ResponseEntity<?> register(RegisterDTO registerDTO)  throws UserExistedException;
    ResponseEntity<?> refreshToken(RefreshTokenRequest request);
    ResponseEntity<?> logout(String accessToken, String refreshToken);
    ResponseEntity<?> enableUser(String accessToken,String userId, EnableUserRequest request);
    ResponseEntity<?> resetPassword(String accessToken, String userId, ResetPasswordRequest request);
}
