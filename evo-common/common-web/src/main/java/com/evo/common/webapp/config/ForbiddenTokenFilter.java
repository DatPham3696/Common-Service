package com.evo.common.webapp.config;

import com.evo.common.webapp.security.TokenCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Component
@Slf4j
public class ForbiddenTokenFilter extends OncePerRequestFilter {

    private final CommonService commonService;


    public ForbiddenTokenFilter(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("ForbiddenTokenFilter");
        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = securityContextHolder.getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            Map<String, Object> tokenAtribute = jwtAuthToken.getTokenAttributes();
          if(tokenAtribute.containsKey("jti")){
              String accessToken = jwtAuthToken.getTokenAttributes().get("jti").toString();
              Instant expiration = (Instant) jwtAuthToken.getTokenAttributes().get("exp");
              log.info("Token JTI: {}", accessToken);
              if (expiration != null && expiration.isBefore(Instant.now())) {
                  throw new RuntimeException("Token expired");
              }
              if (commonService.isTokenExist("invalid-access-token:" + accessToken)) {
                  throw new RuntimeException("Invalid access token");
              }
          }else {
              filterChain.doFilter(httpServletRequest,httpServletResponse);
          }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return true;
        }
        if (authentication instanceof JwtAuthenticationToken) {
            return !authentication.isAuthenticated();
        }
        return authentication instanceof AnonymousAuthenticationToken;
    }
}
