package obed.mz.projects.infrastructure.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Filtro para procesar tokens JWT en las solicitudes entrantes.
 */
@Component
public class JwtFilter implements WebFilter {

    /**
     * Filtra las solicitudes entrantes para verificar la presencia y validez del token JWT.
     *
     * @param exchange El intercambio de la solicitud y respuesta.
     * @param chain    La cadena de filtros web a la que se llama para continuar con el proceso de filtrado.
     * @return Un Mono que indica la finalización del proceso de filtrado.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        if (path.contains("auth")) {
            return chain.filter(exchange);
        }

        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            return handleUnauthorized(exchange);
        }

        String token = auth.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);
        return chain.filter(exchange);
    }

    /**
     * Maneja las solicitudes que no tienen un token JWT válido.
     *
     * @param exchange El intercambio de la solicitud y respuesta.
     * @return Un Mono que indica la finalización del manejo de la respuesta.
     */
    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}
