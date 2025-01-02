package com.example.security_demo.domain.domainEntity;

import com.example.security_demo.domain.command.CreateRoleCmd;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

  public Role(CreateRoleCmd createRoleCommand) {
    this.deleted = createRoleCommand.isDeleted();
    this.description = createRoleCommand.getDescription();
    this.code = createRoleCommand.getCode();
    this.name = createRoleCommand.getName();
    this.isAdmin = createRoleCommand.isAdmin();
  }
}
