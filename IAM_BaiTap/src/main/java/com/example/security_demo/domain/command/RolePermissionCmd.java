package com.example.security_demo.domain.command;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionCmd {

  private Long roleId;
  private List<Long> permissionIds;
}
