package com.example.security_demo.application.service;

import com.example.security_demo.application.dto.request.permission.SoftDeletePermissionRequest;
import com.example.security_demo.application.dto.response.permission.PermissionsResponse;
import com.example.security_demo.domain.entity.Permission;
import com.example.security_demo.domain.repository.IPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final IPermissionRepository permissionRepository;
    public Permission addPermission(Permission permission){
        if(permissionRepository.existsByScopeAndResourceCode(permission.getScope(), permission.getResourceCode())){
            throw new IllegalArgumentException("data existed");
        }
        return permissionRepository.save(permission);
    }
    public String softDelete(Long permissionId, SoftDeletePermissionRequest request){
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new RuntimeException("Not found permission"));
        permission.setDeleted(request.isStatus());
        permissionRepository.save(permission);
        return "permission updated";
    }
    public PermissionsResponse<Permission> getPermissions(Pageable pageable){
        Pageable pages = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Permission> permissions = permissionRepository.findAll(pages);
        return new PermissionsResponse<>(permissions.getContent(), permissions.getTotalPages());
    }
}
