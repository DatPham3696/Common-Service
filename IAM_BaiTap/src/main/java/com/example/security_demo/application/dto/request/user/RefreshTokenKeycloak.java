package com.example.security_demo.application.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenKeycloak {

  private String grant_type;
  private String client_id;
  private String client_secret;
  private String refresh_token;
}
