package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.RefreshToken;

import java.util.Optional;

public interface IRefreshTokenRepository  {
    Optional<RefreshToken> findByRefreshToken(String token);
    void deleteByUserId(String userId);
}
