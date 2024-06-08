package obed.mz.projects.domain.repositories;

import obed.mz.projects.infrastructure.security.JwtAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;

import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Componente encargado de manejar el contexto de seguridad para las solicitudes entrantes.
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private JwtAuthenticationManager jwtAuthenticationManager;

    /**
     * Guarda el contexto de seguridad para una solicitud entrante.
     *
     * @param exchange El intercambio de la solicitud y respuesta.
     * @param context  El contexto de seguridad que se va a guardar.
     * @return Un Mono que se completa vacío.
     *
     * En este caso, no lo estaremos implementando.
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }
    /**
     * Carga el contexto de seguridad para una solicitud entrante.
     *
     * @param exchange El intercambio de la solicitud y respuesta.
     * @return Un Mono que emite el contexto de seguridad cargado.
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getAttribute("token");
        return jwtAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, token))
                .map(SecurityContextImpl::new);
    }
}
