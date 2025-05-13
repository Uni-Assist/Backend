package com.example.UniAssist.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
    private final long expirationHours;

    public JwtProvider(
            @Value("${jwt.secret}") String jwtSecretKey,
            @Value("${jwt.expiration-hours}") long expirationHours
    ) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
        this.expirationHours = expirationHours;
    }

    public String generateToken(UUID userId) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant expirationInstant = now
                .plusHours(expirationHours)
                .atZone(ZoneId.systemDefault())
                .toInstant();
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
        parseToken(token);
        return true;
    }

    public String getSubject(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token);
    }
}
