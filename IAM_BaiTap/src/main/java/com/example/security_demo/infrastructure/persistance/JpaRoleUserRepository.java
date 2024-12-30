package com.example.security_demo.infrastructure.persistance;

import com.example.security_demo.infrastructure.entity.RoleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRoleUserRepository extends JpaRepository<RoleUserEntity, Long> {
    RoleUserEntity findByUserId(String userId);
    List<RoleUserEntity> findAllByUserId(String userId);
//    Optional<String> find
}
