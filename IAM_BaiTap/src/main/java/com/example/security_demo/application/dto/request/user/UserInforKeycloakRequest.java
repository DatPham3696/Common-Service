package com.example.security_demo.application.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInforKeycloakRequest {

  private String grant_type;
  private String client_id;
  private String client_secret;
  private String username;
  private String password;
  private String scope;
}
