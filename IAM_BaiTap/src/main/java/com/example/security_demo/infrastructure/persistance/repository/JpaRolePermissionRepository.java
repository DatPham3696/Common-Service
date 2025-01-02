package com.example.security_demo.infrastructure.persistance.repository;

import com.example.security_demo.infrastructure.persistance.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {

  List<RolePermissionEntity> findAllByRoleId(Long roleId);

  boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
