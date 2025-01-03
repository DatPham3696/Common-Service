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
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class RoleEntity extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  //    @Column(name = "role_name")
//    private String roleName;
  @Column(name = "deleted")
  private boolean deleted;

  @Column(name = "code")
  private String code;

  @Column(name = "description")
  private String description;

  @Column(name = "name")
  private String name;

  @Column(name = "is_admin")
  private boolean isAdmin;
}
