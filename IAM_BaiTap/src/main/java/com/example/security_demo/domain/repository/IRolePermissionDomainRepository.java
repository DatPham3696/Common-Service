package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.domainEntity.Role;
import com.example.security_demo.domain.domainEntity.RolePermission;
import com.example.security_demo.infrastructure.persistance.entity.RolePermissionEntity;
import java.util.List;

public interface IRolePermissionDomainRepository {

  boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

  List<RolePermission> findAllByRoleIds(Long roleIds);

  boolean saveAll(List<RolePermission> domain);
}
