package com.example.security_demo.domain.domainEntity;

import com.example.security_demo.domain.command.CreateRoleCmd;
import com.example.security_demo.domain.command.RolePermissionCmd;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends Auditable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private boolean deleted;

  private String code;

  private String description;

  private String name;

  private boolean isAdmin;

  private List<RolePermission> rolePermissions = new ArrayList<>();

  public Role(CreateRoleCmd createRoleCommand) {
    this.deleted = createRoleCommand.isDeleted();
    this.description = createRoleCommand.getDescription();
    this.code = createRoleCommand.getCode();
    this.name = createRoleCommand.getName();
    this.isAdmin = createRoleCommand.isAdmin();
    this.assignPermissionRole(List.of(), createRoleCommand.getPermissionIds());
  }

  public void assignPermissionRole(List<RolePermission> existingRolePermission,
      List<Long> newPermissionId) {
    List<Long> existingPermissionIds = existingRolePermission.stream()
        .map(RolePermission::getPermissionId).toList();

    List<RolePermission> permissionsToAdd = newPermissionId.stream()
        .filter(permissionId -> !existingPermissionIds.contains(permissionId))
        .map(permissionId -> {
          RolePermission rolePermission = new RolePermission();
          rolePermission.setRoleId(this.id);
          rolePermission.setPermissionId(permissionId);
          rolePermission.setDeleted(false);
          return rolePermission;
        })
        .toList();

    List<RolePermission> permissionsToUpdateOrDelete = existingRolePermission.stream()
        .map(existingPermission -> {
          if (newPermissionId.contains(existingPermission.getPermissionId())) {
            existingPermission.setDeleted(false);
          } else {
            existingPermission.setDeleted(true);
          }
          return existingPermission;
        })
        .toList();

    List<RolePermission> result = new ArrayList<>();
    result.addAll(permissionsToAdd);
    result.addAll(permissionsToUpdateOrDelete);
    this.rolePermissions = result;
  }

  public void updateRolePermission(List<RolePermission> rolePermissionsExisted,
      RolePermissionCmd cmd) {
    this.assignPermissionRole(rolePermissionsExisted, cmd.getPermissionIds());
  }

}
