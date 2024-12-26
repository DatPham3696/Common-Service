package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserRepository{
    Optional<User> findByUserName(String userName);

    Optional<User> findById(String userId);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User findByVerificationCode(String verificationCode);

    User findByKeyclUserId(String keycloakUserId);

    Page<User> findByKeyWord(String keyword, Pageable pageable);
    User save(User user);
}

