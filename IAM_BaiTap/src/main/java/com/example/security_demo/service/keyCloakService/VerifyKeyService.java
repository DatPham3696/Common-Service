package com.example.security_demo.service.keyCloakService;

import com.example.security_demo.config.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
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
        return jwtTokenUtils.generateClientToken(clientId);
    }
}
