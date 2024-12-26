package com.example.security_demo.application.config;

import com.evo.common.webapp.config.CommonService;
import com.example.security_demo.infrastructure.persistance.RoleUser;
import com.example.security_demo.infrastructure.persistance.User;
import com.example.security_demo.infrastructure.repository.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtTokenUtils {
    private final IRoleRepositoryJpa roleRepository;
    private final IPermissionRepositoryJpa permissionRepository;
    private final IRolePermissionRepositoryJpa rolePermissionRepository;
    private final IInvalidTokenRepositoryJpa invalidTokenRepository;
    private final IRoleUserRepositoryJpa roleUserRepository;
    private final CommonService commonService;

    @Autowired
    private TokenProvider tokenProvider;
    @Value("${spring.security.authentication.jwt.jwt_refresh_expiration}")
    private Long refreshTokenDuration;

    public JwtTokenUtils(IRoleRepositoryJpa roleRepository, IPermissionRepositoryJpa permissionRepository,
                         IRolePermissionRepositoryJpa rolePermissionRepository, IInvalidTokenRepositoryJpa invalidTokenRepository,
                         IRoleUserRepositoryJpa roleUserRepository, CommonService commonService) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.invalidTokenRepository = invalidTokenRepository;
        this.roleUserRepository = roleUserRepository;
        this.commonService = commonService;
    }

    public String generateToken(User user) {
        RoleUser roleUser = roleUserRepository.findByUserId(user.getId());
        String roleName = roleRepository.findById(roleUser.getRoleId()).get().getCode();
        long currentTimeMillis = System.currentTimeMillis();
        Date expirationDate = new Date(currentTimeMillis + 30 * 60 * 1000);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("sub", user.getEmail());
        claims.put("exp", expirationDate);
        claims.put("scope", roleName);
        claims.put("jti", UUID.randomUUID().toString());
        String JWT = Jwts.builder().claims(claims)
                .signWith(tokenProvider.getKeyPair().getPrivate(), Jwts.SIG.RS256)
                .compact();
        return JWT;
    }
    public String generaRefreshToken(User user) {
        RoleUser roleUser = roleUserRepository.findByUserId(user.getId());
        String roleName = roleRepository.findById(roleUser.getRoleId()).get().getCode();
        long currentTimeMillis = System.currentTimeMillis();
        Date expirationDate = new Date(currentTimeMillis + refreshTokenDuration);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("sub", user.getEmail());
        claims.put("exp", expirationDate);
        claims.put("scope", roleName);
        claims.put("jti", UUID.randomUUID().toString());
        String JWT = Jwts.builder().claims(claims)
                .signWith(tokenProvider.getKeyPair().getPrivate(), Jwts.SIG.RS256)
                .compact();
        return JWT;
    }

    public String generateClientToken(String clientId){
        return Jwts.builder()
                .claim("client-id", clientId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .signWith(tokenProvider.getKeyPair().getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }


    public Claims getAllClaimFromToken(String token) {
        return Jwts.parser()
                .verifyWith(tokenProvider.getKeyPair().getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getSubFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationTimeFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getJtiFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationTimeFromToken(token);
        return expirationDate.before(new Date());
    }

    public boolean isTokenValid(String token) {
        String jti = getJtiFromToken(token);
        if(commonService.isTokenExist(jti)){
            if(isTokenExpired(token)){
                commonService.deleteFromRedis(jti);
            }
            return true;
        }
        return false;
    }
}
