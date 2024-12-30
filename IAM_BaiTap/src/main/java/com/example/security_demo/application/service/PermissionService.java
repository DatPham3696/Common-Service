package com.example.security_demo.application.service;

import com.example.security_demo.application.dto.request.permission.SoftDeletePermissionRequest;
import com.example.security_demo.application.dto.response.permission.PermissionsResponse;
import com.example.security_demo.infrastructure.entity.PermissionEntity;
import com.example.security_demo.infrastructure.persistance.JpaPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final JpaPermissionRepository permissionRepository;
    public PermissionEntity addPermission(PermissionEntity permission){
        if(permissionRepository.existsByScopeAndResourceCode(permission.getScope(), permission.getResourceCode())){
            throw new IllegalArgumentException("data existed");
        }
        return permissionRepository.save(permission);
    }
    public String softDelete(Long permissionId, SoftDeletePermissionRequest request){
        PermissionEntity permission = permissionRepository.findById(permissionId).orElseThrow(() -> new RuntimeException("Not found permission"));
        permission.setDeleted(request.isStatus());
        permissionRepository.save(permission);
        return "permission updated";
    }
    public PermissionsResponse<PermissionEntity> getPermissions(Pageable pageable){
        Pageable pages = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<PermissionEntity> permissions = permissionRepository.findAll(pages);
        return new PermissionsResponse<>(permissions.getContent(), permissions.getTotalPages());
    }
}
