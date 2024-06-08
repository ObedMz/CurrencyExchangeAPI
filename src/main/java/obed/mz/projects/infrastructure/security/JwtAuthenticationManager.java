package obed.mz.projects.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Manager de autenticación reactiva para validar tokens JWT.
 */
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Método para autenticar una solicitud utilizando un token JWT.
     *
     * @param authentication La autenticación a procesar.
     * @return Un Mono que emite la autenticación resultante.
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtUtil.getClaims(auth.getCredentials().toString()))
                .onErrorResume(e -> Mono.error(new AuthenticationException("Invalid token", e) {}))
                .map(claims -> {
                    String username = claims.getSubject();
                    return new UsernamePasswordAuthenticationToken(username, null, null);
                });
    }
}
