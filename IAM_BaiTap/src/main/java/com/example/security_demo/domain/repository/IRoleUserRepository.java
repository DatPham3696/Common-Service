package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.RoleUser;

import java.util.List;

public interface IRoleUserRepository {
    RoleUser findByUserId(String userId);
    List<RoleUser> findAllByUserId(String userId);
}
