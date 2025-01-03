package com.example.security_demo.infrastructure.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles_users")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class RoleUserEntity extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "role_id")
  private Long roleId;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "deleted")
  private boolean deleted;
}
