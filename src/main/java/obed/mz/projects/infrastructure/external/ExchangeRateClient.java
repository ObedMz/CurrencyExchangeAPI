package obed.mz.projects.infrastructure.external;

import obed.mz.projects.domain.model.ExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Interfaz para consumir el servicio de tasas de cambio.
 */
@FeignClient(name = "exchangeRateClient", url = "${exchange.rate.api.url}")
public interface ExchangeRateClient {

    /**
     * Obtiene la tasa de cambio para una moneda específica.
     *
     * @param currency El código de la moneda para la que se desea obtener la lista de tasa de cambio.
     * @return Devuelve una lista completa de tasas de cambio para la moneda especificada.
     */
    @GetMapping("/{currency}")
    ExchangeResponse getExchangeRate(@PathVariable(value = "currency") String currency);
}
