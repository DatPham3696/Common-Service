package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserProfileRepository extends JpaRepository<UserProfile,String> {
    Optional<UserProfile> findByUsername(String username);
}
