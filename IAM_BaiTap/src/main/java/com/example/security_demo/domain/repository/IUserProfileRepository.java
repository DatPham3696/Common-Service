package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.entity.UserProfile;

import java.util.Optional;

public interface IUserProfileRepository {
    Optional<UserProfile> findByUsername(String username);
}
