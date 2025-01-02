package com.example.security_demo.application.dto.request.role;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRolePermissionRequest {

  private Long roleId;
  private List<Long> permissionIds;
}
