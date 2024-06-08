package obed.mz.projects.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utilidad para la generación y validación de tokens JWT.
 */
@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    /**
     * Genera un token JWT para el nombre de usuario especificado.
     *
     * @param username El nombre de usuario para el cual se generará el token JWT.
     * @return El token JWT generado como una cadena.
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Obtiene los claims (datos) del token JWT especificado.
     *
     * @param token El token JWT del cual se extraerán los claims.
     * @return Los claims extraídos del token JWT.
     */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
