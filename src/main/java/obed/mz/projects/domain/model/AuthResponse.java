package obed.mz.projects.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Cuerpo de respuesta de autenticación.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    /**
     * Token de autenticación generado.
     */
    private String token;
}