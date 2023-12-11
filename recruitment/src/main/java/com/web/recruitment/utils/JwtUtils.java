package com.web.recruitment.utils;

import com.web.recruitment.persistence.dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    private final Long expiration;
    private final SecretKey secretKey;

    public JwtUtils(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration) {
        this.expiration = expiration;
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user){
        try {
            Date now = new Date();
            Date expiredDate = new Date(now.getTime() + expiration);
            JwtBuilder jwtBuilder = Jwts.builder();
            return jwtBuilder.setId(Long.toString(user.getId()))
                    .setSubject(user.getEmail())
                    .setExpiration(expiredDate)
                    .setIssuedAt(now)
                    .claim("created_at", user.getCreateAt())
                    .signWith(secretKey)
                    .compact();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
    public Claims getBody(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (JwtException ex) {
            return null;
        }
    }
    public String getEmail(String token) {
        try {
            var jws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return jws.getBody().getSubject();
        } catch (Exception ex) {
            return null;
        }
    }
    public Date getTime(String token) {
        try {
            var jws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return jws.getBody().getIssuedAt();
        } catch (Exception ex) {
            return null;
        }
    }
    public User getUserInfo(String token) {
        try {
            var jws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            var tokenBody = jws.getBody();

            // get id
            String idStr = tokenBody.getId();
            if (idStr == null) {
                return null;
            }
            int id = Integer.parseInt(idStr);

            // get email
            String email = tokenBody.getSubject();
            if (email == null) {
                return null;
            }

            User user = new User();
            user.setId(id);
            user.setEmail(email);

            return user;
        } catch (Exception ex) {
            return null;
        }
    }
    public Long getExpiration() {
        return this.expiration;
    }
}
