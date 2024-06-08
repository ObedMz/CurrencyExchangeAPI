package obed.mz.projects.infrastructure.config;

import obed.mz.projects.infrastructure.security.JwtFilter;
import obed.mz.projects.domain.repositories.SecurityContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuración para la seguridad de la aplicación.
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    @Autowired
    private SecurityContextRepository securityContextRepository;

    /**
     * Configura el codificador de contraseñas.
     *
     * @return Un bean que proporciona el codificador de contraseñas BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura los filtros de seguridad de la aplicación.
     *
     * @param http      La configuración de seguridad HTTP del servidor.
     * @param jwtFilter El filtro JWT para la autenticación basada en tokens JWT.
     * @return Filtro de seguridad.
     */
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JwtFilter jwtFilter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec -> exchangeSpec.pathMatchers("/auth/**").permitAll()
                        .anyExchange().authenticated())
                .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
                .securityContextRepository(securityContextRepository)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .build();
    }
}
