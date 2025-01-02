package com.example.security_demo.application.service.keyCloakService;

import com.example.security_demo.application.config.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class VerifyKeyService {

  @Value("${spring.storage.client-id}")
  private String configClientId;
  @Value("${spring.storage.client-secret}")
  private String configClientSecret;
  private final JwtTokenUtils jwtTokenUtils;

  public String verifyClientKey(String clientId, String clientSecret) {
    if (!configClientId.equals(clientId) && !configClientSecret.equals(clientSecret)) {
      throw new RuntimeException("Invalid credential infor");
    }
//        log.info(jwtTokenUtils.generateClientToken(clientId));
    return jwtTokenUtils.generateClientToken(clientId);
  }
}
