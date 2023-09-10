package com.ecureuill.rpgbattle.infrastructure.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

  @Value("${api.security.secret}")
  private String secret;

  public String generateToken(UserDetails user){
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create()
      .withIssuer("batterpg")
      .withSubject(user.getUsername())
      .withExpiresAt(expireTime())
      .sign(algorithm);
  }

  private static Instant expireTime() {
    final Integer SESSION_DURATION_IN_MINUTES = 120;
    return LocalDateTime.now().plusMinutes(SESSION_DURATION_IN_MINUTES).toInstant(ZoneOffset.of("-03:00"));
  }

  public Object getSubject(String tokenJWT) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    
    return JWT.require(algorithm)
      .withIssuer("battlerpg")
      .build()
      .verify(tokenJWT)
      .getSubject();
  }

}
