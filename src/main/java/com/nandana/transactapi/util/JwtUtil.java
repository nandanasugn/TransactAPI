package com.nandana.transactapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.token.expiration}")
    private Long TOKEN_EXPIRATION;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return createToken(claims, email, TOKEN_EXPIRATION, getSigningKey());
    }

    private String createToken(Map<String, Object> claims, String email, Long expiration, SecretKey key) {
        return Jwts.builder().claims(claims).subject(email).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + expiration)).signWith(key, Jwts.SIG.HS256).compact();
    }

    public Boolean isTokenValid(String token, String email) {
        String tokenEmail = extractEmail(token);

        return tokenEmail.equals(email) && !isTokenExpired(token);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = getSigningKey();

        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
