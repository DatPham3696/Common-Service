package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.entity.UserProfile;

import java.util.Optional;

public interface IUserProfileDomainRepository {

  Optional<UserProfile> findByUsername(String username);
}
