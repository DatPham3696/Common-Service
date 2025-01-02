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
public class CreateRoleCmd {

  private boolean deleted;
  private String code;
  private String description;
  private String name;
  private boolean isAdmin;
  private List<Long> permissionIds;
}
