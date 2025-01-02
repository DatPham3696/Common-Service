package com.example.security_demo.application.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuthenticationUtils {

  public static String getEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof User) {
        return ((User) principal).getUsername();
      }
    }
    return null;
  }
}
