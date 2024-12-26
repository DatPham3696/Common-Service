package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.infrastructure.persistance.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRefreshTokenRepositoryJpa extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByRefreshToken(String token);
    void deleteByUserId(String userId);
}
