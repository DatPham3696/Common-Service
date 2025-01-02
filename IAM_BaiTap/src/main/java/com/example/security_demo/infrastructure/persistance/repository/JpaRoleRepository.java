package com.example.security_demo.infrastructure.persistance.repository;

import com.example.security_demo.infrastructure.persistance.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByCode(String code);
}
