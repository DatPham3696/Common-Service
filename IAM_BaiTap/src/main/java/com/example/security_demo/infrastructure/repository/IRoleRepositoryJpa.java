package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.infrastructure.persistance.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepositoryJpa extends JpaRepository<Role,Long> {
    Optional<Role> findByCode(String code);
}
