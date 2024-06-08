package obed.mz.projects.application.controller;

import obed.mz.projects.domain.model.AuthResponse;
import obed.mz.projects.infrastructure.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
/**
 * Controlador REST encargado de manejar las solicitudes de autenticación.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Endpoint para generar un JWT.
     *
     * @return ResponseEntity que contiene el JWT.
     */
    @GetMapping("/token")
    public Mono<ResponseEntity<AuthResponse>> generateToken() {
        String currentDateTime = Instant.now().toString();
        String token = jwtUtil.generateToken(currentDateTime);
        return Mono.just(ResponseEntity.ok(new AuthResponse(token)));
    }
}
