package com.example.security_demo.application.service;

import com.example.security_demo.application.dto.request.role.SoftDeleteRoleRequest;
import com.example.security_demo.application.dto.response.role.RolesResponse;
import com.example.security_demo.domain.repository.IRoleDomainRepository;
import com.example.security_demo.infrastructure.persistance.entity.RoleEntity;
import com.example.security_demo.infrastructure.persistance.repository.JpaRoleRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final JpaUserRepository userRepository;
  private final JpaRoleRepository roleRepositoryJpa;
  private final IRoleDomainRepository roleRepository;

  public RoleEntity addRole(RoleEntity role) {
    if (roleRepositoryJpa.findByCode(role.getCode()).isPresent()) {
      throw new IllegalArgumentException("code existed");
    }
    return roleRepositoryJpa.save(role);
  }

  public String softDelete(String code, SoftDeleteRoleRequest request) {
    RoleEntity role = roleRepositoryJpa.findByCode(code)
        .orElseThrow(() -> new RuntimeException("Not found role"));
    role.setDeleted(request.isStatus());
    roleRepositoryJpa.save(role);
    return "role updated";
  }

  public void deleteRoleById(Long id) {
    if (!roleRepositoryJpa.existsById(id)) {
      throw new IllegalArgumentException("Cant find role" + id);
    }
    roleRepositoryJpa.deleteById(id);
  }

  public RolesResponse<RoleEntity> getRoles(Pageable pageable) {
    Pageable pages = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    Page<RoleEntity> roles = roleRepositoryJpa.findAll(pages);
    return new RolesResponse<>(roles.getContent(), roles.getTotalPages());
  }

  public List<Long> listRoleExist(List<Long> id) {
    return roleRepository.findAll(id).stream().map(RoleEntity::getId).toList();
  }
}
