package com.example.security_demo.application.dto.response.permission;


import com.example.security_demo.infrastructure.entity.PermissionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponseDTO {
    private String description;
    public static PermissionResponseDTO fromPermission(PermissionEntity permission){
        return PermissionResponseDTO.builder()
                .description(permission.getScope())
                .build();
    }
}
