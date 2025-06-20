package dac.ms.autenticacao.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${jwt.validity-in-milliseconds}")
    private long jwtExpiration;

    private String secretKey;
    private long validityInMilliseconds;

    public String generateToken(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateToken(String email, List<String> roles) {
    return Jwts.builder()
            .subject(email)
            .claim("auth", roles) // Mantém o mesmo claim "auth" para compatibilidade
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(getSigningKey())
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public JwtTokenProvider(
    @Value("${jwt.secret-key}") String jwtSecret,
    @Value("${jwt.validity-in-milliseconds}") long validityInMilliseconds) {
    
    // Validação rigorosa da chave
    try {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        if (keyBytes.length < 32) { // Tamanho mínimo recomendado para HS256
            throw new IllegalArgumentException("Chave JWT muito curta. Mínimo 32 bytes.");
        }
        this.jwtSecret = jwtSecret;
        this.validityInMilliseconds = validityInMilliseconds;
    } catch (Exception e) {
        throw new IllegalArgumentException("Chave JWT inválida. Deve ser Base64 seguro.", e);
    }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        List<GrantedAuthority> authorities = ((List<?>) claims.get("auth"))
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.toString()))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities
        );
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}