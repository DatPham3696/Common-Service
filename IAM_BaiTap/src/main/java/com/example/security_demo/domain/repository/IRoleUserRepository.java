package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.domainEntity.Role;
import com.example.security_demo.domain.domainEntity.RoleUser;
import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.infrastructure.entity.RoleUserEntity;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface IRoleUserRepository {
    RoleUserEntity findByUserId(String userId);
//    List<RoleUserEntity> findAllByUserId(String userId);
    List<RoleUser> findAllByUserId(String userId);
    boolean saveAll(List<RoleUser> domain);
    RoleUser findByRoleId(Long roleId);

}
