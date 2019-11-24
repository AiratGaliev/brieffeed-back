package com.brieffeed.back.security;

import com.brieffeed.back.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.brieffeed.back.security.SecurityContains.EXPIRATION_TIME;
import static com.brieffeed.back.security.SecurityContains.SECRET;

@Component
public class JwtTokenProvider {
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        String userId = Long.toString(user.getUserId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getUserId())));
        claims.put("username", user.getUsername());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }
}
