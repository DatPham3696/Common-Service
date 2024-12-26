package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.persistance.Role;
import com.example.security_demo.domain.repository.IRoleRepository;
import com.example.security_demo.infrastructure.repository.IRoleRepositoryJpa;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleRepositoryImpl implements IRoleRepository {
    private final IRoleRepositoryJpa roleRepositoryJpa;

    public RoleRepositoryImpl(IRoleRepositoryJpa roleRepositoryJpa) {
        this.roleRepositoryJpa = roleRepositoryJpa;
    }

    @Override
    public Optional<Role> findByCode(String code) {
        return roleRepositoryJpa.findByCode(code);
    }
}
