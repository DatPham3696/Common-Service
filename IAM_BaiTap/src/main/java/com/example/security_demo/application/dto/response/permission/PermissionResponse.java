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
public class PermissionResponse {
    private String description;
    public static PermissionResponse fromPermission(PermissionEntity permission){
        return PermissionResponse.builder()
                .description(permission.getScope())
                .build();
    }
}
