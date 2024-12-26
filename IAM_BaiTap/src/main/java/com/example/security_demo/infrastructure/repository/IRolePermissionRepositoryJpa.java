package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.infrastructure.persistance.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRolePermissionRepositoryJpa extends JpaRepository<RolePermission, Long> {
    List<RolePermission> findAllByRoleId(Long roleId);
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
