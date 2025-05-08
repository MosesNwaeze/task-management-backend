package com.softnet.task_management.utils;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final JwtProperty jwtProperty;
  private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
  private SecretKey secretKey;
  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(jwtProperty.secret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username) {
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + jwtProperty.expiration()))
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(key).build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  private boolean isTokenExpired(Claims claims) {
    return claims.getExpiration().before(new Date());
  }

  public boolean validateJwtToken(String token) {
    try {
      String username = getUsernameFromToken(token);
      Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return username.equals(claimsJws.getBody().getSubject()) && !isTokenExpired(claimsJws.getBody());
    } catch (SecurityException e) {
      LOGGER.error("Invalid JWT signature: {}", e.getMessage());
      throw new JwtException(e.getMessage());
    } catch (MalformedJwtException e) {
      LOGGER.error("Invalid JWT token: {}", e.getMessage());
      throw new JwtException(e.getMessage());
    } catch (ExpiredJwtException e) {
      LOGGER.error("JWT token is expired: {}", e.getMessage());
      throw new JwtException(e.getMessage());
    } catch (UnsupportedJwtException e) {
      LOGGER.error("JWT token is unsupported:  {}", e.getMessage());
      throw new JwtException(e.getMessage());
    } catch (IllegalArgumentException e) {
      LOGGER.error("JWT claims string is empty: {}", e.getMessage());
      throw new JwtException(e.getMessage());
    }
  }
}
