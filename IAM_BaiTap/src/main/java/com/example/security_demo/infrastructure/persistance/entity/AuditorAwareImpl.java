package com.example.security_demo.infrastructure.persistance.entity;

import com.example.security_demo.infrastructure.persistance.repository.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Autowired
  private JpaUserRepository userRepository;

  @Override
  public Optional<String> getCurrentAuditor() {
    String name = "system";
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof AbstractAuthenticationToken) {
      Object principal = ((AbstractAuthenticationToken) authentication).getPrincipal();
      if (principal instanceof Jwt jwt) {
        name = jwt.getClaim("sub");
      } else {
        name = authentication.getName();
      }
    }
    return Optional.ofNullable(name);
  }
}
