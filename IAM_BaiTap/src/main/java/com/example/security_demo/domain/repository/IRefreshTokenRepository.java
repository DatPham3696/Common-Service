package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.entity.RefreshTokenEntity;

import java.util.Optional;

public interface IRefreshTokenRepository  {
    Optional<RefreshTokenEntity> findByRefreshToken(String token);
    void deleteByUserId(String userId);
}
