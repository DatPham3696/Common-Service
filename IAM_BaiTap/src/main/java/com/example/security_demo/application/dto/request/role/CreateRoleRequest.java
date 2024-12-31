package com.example.security_demo.application.dto.request.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoleRequest {
    private boolean deleted;

    private String code;

    private String description;

    private String name;

    private boolean isAdmin;
}
