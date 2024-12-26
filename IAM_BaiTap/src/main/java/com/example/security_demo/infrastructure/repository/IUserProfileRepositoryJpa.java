package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.infrastructure.persistance.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserProfileRepositoryJpa extends JpaRepository<UserProfile,String> {
    Optional<UserProfile> findByUsername(String username);
}