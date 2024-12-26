package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.infrastructure.persistance.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleUserRepositoryJpa extends JpaRepository<RoleUser, Long> {
    RoleUser findByUserId(String userId);
    List<RoleUser> findAllByUserId(String userId);
//    Optional<String> find
}