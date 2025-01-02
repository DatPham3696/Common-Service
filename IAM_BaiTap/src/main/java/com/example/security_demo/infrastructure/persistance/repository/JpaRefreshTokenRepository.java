package com.example.security_demo.infrastructure.persistance.repository;

import com.example.security_demo.infrastructure.persistance.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  Optional<RefreshTokenEntity> findByRefreshToken(String token);

  void deleteByUserId(String userId);
}
