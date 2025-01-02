package com.example.security_demo.application.dto.response.role;

import com.example.security_demo.application.dto.response.permission.PermissionResponse;
import com.example.security_demo.infrastructure.persistance.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

  private String roleName;
  private Set<PermissionResponse> permission;

  public static RoleResponse fromRole(RoleEntity role) {
    return RoleResponse.builder()
        .roleName(role.getCode())
//                .permission(role.getPermissions().stream().map(PermissionResponseDTO::fromPermission).collect(Collectors.toSet()))
        .build();
  }
}
