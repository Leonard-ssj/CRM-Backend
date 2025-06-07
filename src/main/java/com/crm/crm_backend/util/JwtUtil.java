package com.crm.crm_backend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${jwt.refreshExpiration}")
    private long jwtRefreshExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Generar token JWT con tiempo de expiración variable
    public String generateToken(String email, int minutes) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (long) minutes * 60 * 1000)) // Conversión correcta
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtener email desde el token
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validar si el token es válido o si ha expirado
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return !isTokenExpired(token); // ❗ Verifica si el token ha expirado
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado: " + e.getMessage()); // Agrega log para depurar
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Verificar si el token ha expirado
    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    // Extraer fecha de expiración del token
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Extraer todas las claims del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtener el username (email) del token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public long getJwtRefreshExpirationMs() {
        return jwtRefreshExpirationMs;
    }

}
