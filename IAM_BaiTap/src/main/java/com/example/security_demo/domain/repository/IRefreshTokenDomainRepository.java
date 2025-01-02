package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.entity.RefreshTokenEntity;

import java.util.Optional;

public interface IRefreshTokenDomainRepository {

  Optional<RefreshTokenEntity> findByRefreshToken(String token);

  void deleteByUserId(String userId);
}
