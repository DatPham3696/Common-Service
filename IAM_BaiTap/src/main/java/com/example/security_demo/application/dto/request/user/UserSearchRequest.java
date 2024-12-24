package com.example.security_demo.application.dto.request.user;

import com.example.security_demo.application.dto.request.Page.SearchRequest;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchRequest extends SearchRequest {
    private String userName;
    @Email(message = "Invalid email format")
    private String email;
}
