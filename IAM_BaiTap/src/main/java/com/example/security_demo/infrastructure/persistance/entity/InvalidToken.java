package com.example.security_demo.infrastructure.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Invalid_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvalidToken {

  @Id
  private String id;

  private Date expiryTime;

  private String refreshTokenId;
}
