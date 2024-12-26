package com.example.security_demo.application.service;

import com.example.security_demo.application.dto.request.role.SoftDeleteRoleRequest;
import com.example.security_demo.application.dto.response.role.RolesResponse;
import com.example.security_demo.infrastructure.persistance.Role;
import com.example.security_demo.infrastructure.repository.IRoleRepositoryJpa;
import com.example.security_demo.infrastructure.repository.IUserRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final IUserRepositoryJpa userRepository;
    private final IRoleRepositoryJpa roleRepository;
    public Role addRole(Role role){
        if(roleRepository.findByCode(role.getCode()).isPresent()){
            throw new IllegalArgumentException("code existed");
        }
        return roleRepository.save(role);
    }
    public String softDelete(String code, SoftDeleteRoleRequest request){
        Role role = roleRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Not found role"));
        role.setDeleted(request.isStatus());
        roleRepository.save(role);
        return "role updated";
    }
    public void deleteRoleById(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Cant find role" + id);
        }
        roleRepository.deleteById(id);
    }
    public RolesResponse<Role> getRoles(Pageable pageable){
        Pageable pages = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<Role> roles = roleRepository.findAll(pages);
        return new RolesResponse<>(roles.getContent(), roles.getTotalPages());
    }
}
