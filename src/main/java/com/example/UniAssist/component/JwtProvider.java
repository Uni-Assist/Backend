package com.example.UniAssist.component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final SecretKey jwtSecretKey;

    public JwtProvider(@Value("${jwt.secret}") String jwtSecretKey) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    public String generateToken(UUID userId) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant expirationInstant = now.plusHours(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date expiration = Date.from(expirationInstant);
        return Jwts.builder()
                .claims()
                .subject(userId.toString())
                .expiration(expiration)
                .and()
                .signWith(jwtSecretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token);
        return true;
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}