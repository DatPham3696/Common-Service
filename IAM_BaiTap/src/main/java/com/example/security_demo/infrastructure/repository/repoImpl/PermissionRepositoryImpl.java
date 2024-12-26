package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.persistance.Permission;
import com.example.security_demo.domain.repository.IPermissionRepository;
import com.example.security_demo.infrastructure.repository.IPermissionRepositoryJpa;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PermissionRepositoryImpl implements IPermissionRepository {
    private final IPermissionRepositoryJpa permissionRepositoryJpa;

    public PermissionRepositoryImpl(IPermissionRepositoryJpa permissionRepositoryJpa) {
        this.permissionRepositoryJpa = permissionRepositoryJpa;
    }

    @Override
    public Optional<Permission> findByScope(String scope) {
        return permissionRepositoryJpa.findByScope(scope);
    }

    @Override
    public Optional<Permission> findByResourceCode(String resourceCode) {
        return permissionRepositoryJpa.findByResourceCode(resourceCode);
    }

    @Override
    public Optional<Permission> findByResourceCodeAndScope(String resourceCode, String scope) {
        return permissionRepositoryJpa.findByResourceCodeAndScope(resourceCode, scope);
    }

    @Override
    public Optional<Long> findPermissionIdByUserAndResourceCodeAndScope(String userID, String resourceCode, String scope) {
        return permissionRepositoryJpa.findPermissionIdByUserAndResourceCodeAndScope(userID, resourceCode, scope);
    }

    @Override
    public boolean existsByScopeAndResourceCode(String scope, String resourceCode) {
        return permissionRepositoryJpa.existsByScopeAndResourceCode(scope, resourceCode);
    }

    @Override
    public Optional<Permission> findById(Long permissionId) {
        return permissionRepositoryJpa.findById(permissionId);
    }
}
