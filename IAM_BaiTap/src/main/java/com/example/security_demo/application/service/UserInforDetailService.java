package com.example.security_demo.application.service;

import com.example.security_demo.infrastructure.persistance.Role;
import com.example.security_demo.infrastructure.persistance.RolePermission;
import com.example.security_demo.infrastructure.persistance.RoleUser;
import com.example.security_demo.infrastructure.persistance.User;
import com.example.security_demo.domain.enums.EnumRole;
import com.example.security_demo.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor
public class UserInforDetailService implements UserDetailsService {
    private final IUserRepositoryJpa userRepository;
    private final IRoleRepositoryJpa roleRepository;
    private final IRoleUserRepositoryJpa roleUserRepository;
    private final IRolePermissionRepositoryJpa rolePermissionRepository;
    private final IPermissionRepositoryJpa permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));
        RoleUser roleUser = roleUserRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(roleUser.getRoleId()).orElseThrow(() -> new RuntimeException("role not found"));
        String roleName = roleRepository.findById(roleUser.getRoleId()).map(Role::getCode).orElseThrow(() -> new RuntimeException("role not found"));
        List<String> permissions = rolePermissionRepository.findAllByRoleId(roleUser.getRoleId()).stream()
                .map(RolePermission::getPermissionId)
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .map(permission -> permission.getResourceCode() + "_" + permission.getScope())
                        .orElse("Unknow permission"))
                .toList();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String permission : permissions){
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        if(role.isAdmin()){
            authorities.add(new SimpleGrantedAuthority(EnumRole.ADMIN.name()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
    public UserDetails loadUserByUserUserId(String keyClkId) throws UsernameNotFoundException {
        User user = userRepository.findByKeyclUserId(keyClkId);
        RoleUser roleUser = roleUserRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(roleUser.getRoleId()).orElseThrow(() -> new RuntimeException("role not found"));
        String roleName = roleRepository.findById(roleUser.getRoleId()).map(Role::getCode).orElseThrow(() -> new RuntimeException("role not found"));
        List<String> permissions = rolePermissionRepository.findAllByRoleId(roleUser.getRoleId()).stream()
                .map(RolePermission::getPermissionId)
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .map(permission -> permission.getResourceCode() + "_" + permission.getScope())
                        .orElse("Unknow permission"))
                .toList();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String permission : permissions){
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        if(role.isAdmin()){
            authorities.add(new SimpleGrantedAuthority(EnumRole.ADMIN.name()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
