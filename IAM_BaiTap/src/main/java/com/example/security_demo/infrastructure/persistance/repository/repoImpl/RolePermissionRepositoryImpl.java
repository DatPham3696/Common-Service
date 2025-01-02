package com.example.security_demo.infrastructure.persistance.repository.repoImpl;

import com.example.security_demo.application.mapper.RolePermissionMapper;
import com.example.security_demo.domain.domainEntity.RolePermission;
import com.example.security_demo.infrastructure.persistance.entity.RolePermissionEntity;
import com.example.security_demo.domain.repository.IRolePermissionDomainRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaRolePermissionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolePermissionRepositoryImpl implements IRolePermissionDomainRepository {

  private final JpaRolePermissionRepository rolePermissionRepositoryJpa;
  private final RolePermissionMapper rolePermissionMapper;

  public RolePermissionRepositoryImpl(JpaRolePermissionRepository rolePermissionRepositoryJpa,
      RolePermissionMapper rolePermissionMapper) {
    this.rolePermissionRepositoryJpa = rolePermissionRepositoryJpa;
    this.rolePermissionMapper = rolePermissionMapper;
  }

  @Override
  public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
    return rolePermissionRepositoryJpa.existsByRoleIdAndPermissionId(roleId, permissionId);
  }

  @Override
  public List<RolePermission> findAllByRoleIds(Long roleIds) {
    List<RolePermissionEntity> rolePermissionEntities = rolePermissionRepositoryJpa.findAllByRoleId(
        roleIds);
    return rolePermissionMapper.fromListRolePermissionEntity(rolePermissionEntities);
  }

  @Override
  public boolean saveAll(List<RolePermission> domain) {
    List<RolePermissionEntity> rolePermissionEntities = rolePermissionMapper.fromListRolePermissonDomain(
        domain);
    rolePermissionRepositoryJpa.saveAll(rolePermissionEntities);
    return true;
  }
}
