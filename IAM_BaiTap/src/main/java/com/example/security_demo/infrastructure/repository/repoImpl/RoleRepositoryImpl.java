package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.entity.RoleEntity;
import com.example.security_demo.domain.repository.IRoleRepository;
import com.example.security_demo.infrastructure.persistance.JpaRoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleRepositoryImpl implements IRoleRepository {
    private final JpaRoleRepository roleRepositoryJpa;

    public RoleRepositoryImpl(JpaRoleRepository roleRepositoryJpa) {
        this.roleRepositoryJpa = roleRepositoryJpa;
    }

    @Override
    public Optional<RoleEntity> findByCode(String code) {
        return roleRepositoryJpa.findByCode(code);
    }

    @Override
    public List<RoleEntity> findAll(List<Long> id) {
        return roleRepositoryJpa.findAllById(id);
    }
}
