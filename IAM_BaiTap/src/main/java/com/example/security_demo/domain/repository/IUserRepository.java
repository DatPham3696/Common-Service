package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserRepository{
    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findById(String userId);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByVerificationCode(String verificationCode);

    UserEntity findByKeyclUserId(String keycloakUserId);

    Page<UserEntity> findByKeyWord(String keyword, Pageable pageable);
    UserEntity save(User user);
}

