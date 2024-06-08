package obed.mz.projects.domain.model;

import java.util.Map;

/**
 * Respuesta de la API externa de Feign.
 */
public record ExchangeResponse(String result, Map<String, Double> rates) {
    /**
     * Devuelve el estado de la petición: "success" o "error".
     *
     * @return El estado de la petición.
     */
    public String result() {
        return result;
    }

    /**
     * Obtiene las tasas de cambio de la respuesta.
     *
     * @return Mapa que contiene las tasas de cambio.
     */
    public Map<String, Double> rates() {
        return rates;
    }
}