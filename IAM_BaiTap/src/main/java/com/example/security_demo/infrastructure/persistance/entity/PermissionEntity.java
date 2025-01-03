package com.example.security_demo.infrastructure.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "permissions")
public class PermissionEntity extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  //    @Column(name = "description")
//    private String description;
  @Column(name = "deleted")
  private boolean deleted;

  @Column(name = "name")
  private String name;

  @Column(name = "resource_code")
  private String resourceCode;

  @Column(name = "scope")
  private String scope;
}
