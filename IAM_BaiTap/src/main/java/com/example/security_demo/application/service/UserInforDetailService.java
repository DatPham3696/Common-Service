//package com.example.security_demo.application.service;
//
//import com.example.security_demo.infrastructure.persistance.entity.RoleEntity;
//import com.example.security_demo.infrastructure.persistance.entity.RolePermissionEntity;
//import com.example.security_demo.infrastructure.persistance.entity.RoleUserEntity;
//import com.example.security_demo.infrastructure.persistance.entity.UserEntity;
//import com.example.security_demo.domain.enums.EnumRole;
//import com.example.security_demo.infrastructure.persistance.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
////@AllArgsConstructor
//public class UserInforDetailService implements UserDetailsService {
//
//  private final JpaUserRepository userRepository;
//  private final JpaRoleRepository roleRepository;
//  private final JpaRoleUserRepository roleUserRepository;
//  private final JpaRolePermissionRepository rolePermissionRepository;
//  private final JpaPermissionRepository permissionRepository;
//
//  @Override
//  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    UserEntity user = userRepository.findByEmail(email)
//        .orElseThrow(() -> new RuntimeException("user not found"));
//    RoleUserEntity roleUser = roleUserRepository.findByUserId(user.getId());
//    RoleEntity role = roleRepository.findById(roleUser.getRoleId())
//        .orElseThrow(() -> new RuntimeException("role not found"));
//    String roleName = roleRepository.findById(roleUser.getRoleId()).map(RoleEntity::getCode)
//        .orElseThrow(() -> new RuntimeException("role not found"));
//    List<String> permissions = rolePermissionRepository.findAllByRoleId(roleUser.getRoleId())
//        .stream()
//        .map(RolePermissionEntity::getPermissionId)
//        .map(permissionId -> permissionRepository.findById(permissionId)
//            .map(permission -> permission.getResourceCode() + "_" + permission.getScope())
//            .orElse("Unknow permission"))
//        .toList();
//    List<GrantedAuthority> authorities = new ArrayList<>();
//    for (String permission : permissions) {
//      authorities.add(new SimpleGrantedAuthority(permission));
//    }
//    if (role.isAdmin()) {
//      authorities.add(new SimpleGrantedAuthority(EnumRole.ADMIN.name()));
//    }
//    return new org.springframework.security.core.userdetails.User(
//        user.getEmail(),
//        user.getPassword(),
//        authorities
//    );
//  }
//
//  public UserDetails loadUserByUserUserId(String keyClkId) throws UsernameNotFoundException {
//    UserEntity user = userRepository.findByKeyclUserId(keyClkId);
//    RoleUserEntity roleUser = roleUserRepository.findByUserId(user.getId());
//    RoleEntity role = roleRepository.findById(roleUser.getRoleId())
//        .orElseThrow(() -> new RuntimeException("role not found"));
//    String roleName = roleRepository.findById(roleUser.getRoleId()).map(RoleEntity::getCode)
//        .orElseThrow(() -> new RuntimeException("role not found"));
//    List<String> permissions = rolePermissionRepository.findAllByRoleId(roleUser.getRoleId())
//        .stream()
//        .map(RolePermissionEntity::getPermissionId)
//        .map(permissionId -> permissionRepository.findById(permissionId)
//            .map(permission -> permission.getResourceCode() + "_" + permission.getScope())
//            .orElse("Unknow permission"))
//        .toList();
//    List<GrantedAuthority> authorities = new ArrayList<>();
//    for (String permission : permissions) {
//      authorities.add(new SimpleGrantedAuthority(permission));
//    }
//    if (role.isAdmin()) {
//      authorities.add(new SimpleGrantedAuthority(EnumRole.ADMIN.name()));
//    }
//    return new org.springframework.security.core.userdetails.User(
//        user.getEmail(),
//        user.getPassword(),
//        authorities
//    );
//  }
//}
