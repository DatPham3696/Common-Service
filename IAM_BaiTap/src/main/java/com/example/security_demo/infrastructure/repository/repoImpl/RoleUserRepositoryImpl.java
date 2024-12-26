package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.persistance.RoleUser;
import com.example.security_demo.domain.repository.IRoleUserRepository;
import com.example.security_demo.infrastructure.repository.IRoleUserRepositoryJpa;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class RoleUserRepositoryImpl implements IRoleUserRepository {
    private final IRoleUserRepositoryJpa roleUserRepositoryJpa;

    public RoleUserRepositoryImpl(IRoleUserRepositoryJpa roleUserRepositoryJpa) {
        this.roleUserRepositoryJpa = roleUserRepositoryJpa;
    }

    @Override
    public RoleUser findByUserId(String userId) {
        return roleUserRepositoryJpa.findByUserId(userId);
    }

    @Override
    public List<RoleUser> findAllByUserId(String userId) {
        return roleUserRepositoryJpa.findAllByUserId(userId);
    }
}
