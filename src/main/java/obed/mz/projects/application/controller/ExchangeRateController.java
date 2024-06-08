package obed.mz.projects.application.controller;

import obed.mz.projects.domain.model.CurrencyConversionRequest;
import obed.mz.projects.domain.model.ExchangeRate;
import obed.mz.projects.application.service.ExchangeRateService;
import obed.mz.projects.application.exceptions.CurrencyExchangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador para manejar las operaciones relacionadas con las tasas de cambio.
 */
@RestController
@RequestMapping("/exchange-rate")
@Validated
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService domainExchangeRateService;

    /**
     * Convierte una cantidad de una moneda a otra.
     *
     * @param request La solicitud de conversión de moneda.
     * @return Un Mono que emite el objeto ExchangeRate resultante de la conversión.
     */
    @PostMapping("/change")
    public Mono<ExchangeRate> currencyConversion(@RequestBody @Validated CurrencyConversionRequest request) {
        return domainExchangeRateService.convertCurrencyTo(request.getAmount(),
                request.getCurrencyFrom(), request.getCurrencyTo())
                .onErrorResume(e -> Mono.error(new CurrencyExchangeException("Failed to convert currency: " + e.getMessage())));
    }

    /**
     * Obtiene una tasa de cambio por su identificador.
     *
     * @param id el identificador de la tasa de cambio.
     * @return un Mono que emite la tasa de cambio encontrada o vacío si no se encuentra ninguna.
     */
    @GetMapping("/{id}")
    public Mono<ExchangeRate> getById(@PathVariable Long id) {
        return domainExchangeRateService.findById(id);
    }

    /**
     * Obtiene todas las tasas de cambio con paginación.
     *
     * @param page el número de página (por defecto: 0).
     * @param size el tamaño de la página (por defecto: 10).
     * @return Un Flux con las tasas de cambio.
     */
    @GetMapping
    public Flux<ExchangeRate> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return domainExchangeRateService.findAll(page, size);
    }

    /**
     * Obtener las tasas de cambio por moneda de origen y moneda de destino.
     *
     * @param from la moneda de origen.
     * @param to   la moneda de destino.
     * @param page el número de página (por defecto: 0).
     * @param size el tamaño de la página (por defecto: 10).
     * @return Un Flux con las tasas de cambio.
     */
    @GetMapping("/search")
    public Flux<ExchangeRate> getByCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return domainExchangeRateService.findByCurrency(from, to, page, size);
    }
}
