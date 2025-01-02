package com.example.security_demo.domain.enums;

public enum EnumRole {
  ADMIN("ADMIN");
  private String description;

  EnumRole(String description) {
    this.description = description;
  }
}
