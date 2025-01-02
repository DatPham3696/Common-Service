package com.example.security_demo.infrastructure.persistance.repository.repoImpl;

import com.example.security_demo.infrastructure.persistance.entity.PermissionEntity;
import com.example.security_demo.domain.repository.IPermissionDomainRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaPermissionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PermissionRepositoryImpl implements IPermissionDomainRepository {

  private final JpaPermissionRepository permissionRepositoryJpa;

  public PermissionRepositoryImpl(JpaPermissionRepository permissionRepositoryJpa) {
    this.permissionRepositoryJpa = permissionRepositoryJpa;
  }

  @Override
  public Optional<PermissionEntity> findByScope(String scope) {
    return permissionRepositoryJpa.findByScope(scope);
  }

  @Override
  public Optional<PermissionEntity> findByResourceCode(String resourceCode) {
    return permissionRepositoryJpa.findByResourceCode(resourceCode);
  }

  @Override
  public Optional<PermissionEntity> findByResourceCodeAndScope(String resourceCode, String scope) {
    return permissionRepositoryJpa.findByResourceCodeAndScope(resourceCode, scope);
  }

  @Override
  public Optional<Long> findPermissionIdByUserAndResourceCodeAndScope(String userID,
      String resourceCode, String scope) {
    return permissionRepositoryJpa.findPermissionIdByUserAndResourceCodeAndScope(userID,
        resourceCode, scope);
  }

  @Override
  public boolean existsByScopeAndResourceCode(String scope, String resourceCode) {
    return permissionRepositoryJpa.existsByScopeAndResourceCode(scope, resourceCode);
  }

  @Override
  public Optional<PermissionEntity> findById(Long permissionId) {
    return permissionRepositoryJpa.findById(permissionId);
  }
}
