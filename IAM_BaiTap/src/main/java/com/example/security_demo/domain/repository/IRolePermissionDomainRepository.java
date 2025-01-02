package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.entity.RolePermissionEntity;
import java.util.List;

public interface IRolePermissionDomainRepository {

  List<RolePermissionEntity> findAllByRoleId(Long roleId);

  boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
