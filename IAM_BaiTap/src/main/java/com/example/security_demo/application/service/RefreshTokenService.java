package com.example.security_demo.application.service;

import com.evo.common.webapp.config.CommonService;
import com.example.security_demo.application.config.JwtTokenUtils;
import com.example.security_demo.infrastructure.persistance.RefreshToken;
import com.example.security_demo.infrastructure.repository.IRefreshTokenRepositoryJpa;
import com.example.security_demo.infrastructure.repository.IUserRepositoryJpa;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${spring.security.authentication.jwt.jwt_refresh_expiration}")
    private Long refreshTokenDuration;
    private final IRefreshTokenRepositoryJpa refreshTokenRepository;
    private final IUserRepositoryJpa userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final CommonService commonService;
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public RefreshToken createRefreshToken(String userId, String accessTokenId, Date accessTokenExp) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + refreshTokenDuration));
        refreshToken.setRefreshToken(jwtTokenUtils.generaRefreshToken(userRepository.findById(userId).get()));
        refreshToken.setAccessTokenId(accessTokenId);
        refreshToken.setAccessTokenExp(accessTokenExp);
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(RefreshToken token) {
        if (jwtTokenUtils.getExpirationTimeFromToken(token.getRefreshToken()).before(new Date())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Token was expired");
        }
        return token;
    }

    @Transactional
    public void deleteByUserId(String userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}