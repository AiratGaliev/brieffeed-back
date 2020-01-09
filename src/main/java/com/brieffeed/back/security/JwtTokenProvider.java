package com.brieffeed.back.security;

import static com.brieffeed.back.security.SecurityContains.EXPIRATION_TIME;
import static com.brieffeed.back.security.SecurityContains.SECRET;

import com.brieffeed.back.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Date now = new Date(System.currentTimeMillis());
    Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
    String userId = Long.toString(user.getId());
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", (Long.toString(user.getId())));
    claims.put("username", user.getUsername());
    claims.put("firstName", user.getFirstName());
    claims.put("lastName", user.getLastName());
    claims.put("role", user.getRole());
    return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now)
        .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      System.out.println("Invalid JWT Signature");
    } catch (MalformedJwtException e) {
      System.out.println("Invalid JWT Token");
    } catch (ExpiredJwtException e) {
      System.out.println("Expired JWT Token");
    } catch (UnsupportedJwtException e) {
      System.out.println("Unsupported JWT Token");
    } catch (IllegalArgumentException e) {
      System.out.println("JWT claims string empty");
    }
    return false;
  }

  public Long getUserIdFormJWT(String token) {
    Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    String id = (String) claims.get("id");
    return Long.parseLong(id);
  }
}
