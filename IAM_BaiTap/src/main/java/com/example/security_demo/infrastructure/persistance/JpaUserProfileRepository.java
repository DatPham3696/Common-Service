package com.example.security_demo.infrastructure.persistance;

import com.example.security_demo.infrastructure.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserProfileRepository extends JpaRepository<UserProfile,String> {
    Optional<UserProfile> findByUsername(String username);
}
