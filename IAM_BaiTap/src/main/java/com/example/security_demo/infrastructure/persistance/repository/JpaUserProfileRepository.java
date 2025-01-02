package com.example.security_demo.infrastructure.persistance.repository;

import com.example.security_demo.infrastructure.persistance.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserProfileRepository extends JpaRepository<UserProfile, String> {

  Optional<UserProfile> findByUsername(String username);
}
