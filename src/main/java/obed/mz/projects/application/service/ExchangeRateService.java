package obed.mz.projects.application.service;

import obed.mz.projects.domain.model.ExchangeRate;
import obed.mz.projects.domain.model.ExchangeResponse;
import obed.mz.projects.domain.repositories.ExchangeRateRepository;
import obed.mz.projects.application.exceptions.CurrencyExchangeException;
import obed.mz.projects.application.exceptions.FeignClientException;
import obed.mz.projects.infrastructure.external.ExchangeRateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Servicio para manejar las operaciones con tasas de cambio de monedas.
 */
@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateClient exchangeRateClient;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    /**
     * Convierte una cantidad de una moneda a otra.
     *
     * @param amount      La cantidad a convertir.
     * @param currencyFrom La moneda de origen.
     * @param currencyTo   La moneda de destino.
     * @return Un Mono que emite el objeto ExchangeRate resultante de la conversión.
     */
    public Mono<ExchangeRate> convertCurrencyTo(double amount, String currencyFrom, String currencyTo) {
        if (amount <= 0)
            return Mono.error(new CurrencyExchangeException("El monto debe ser mayor que cero."));

        return getExchangeRate(currencyFrom)
                .flatMap(response -> processExchangeRate(response, amount, currencyFrom, currencyTo));
    }

    /**
     * Obtiene la tasa de cambio de una moneda desde el servicio Feign.
     *
     * @param currency La moneda para la que se desea obtener la tasa de cambio.
     * @return Un Mono que emite la respuesta de la API de tasas de cambio.
     */
    private Mono<ExchangeResponse> getExchangeRate(String currency) {
        return Mono.fromCallable(() -> exchangeRateClient.getExchangeRate(currency))
                .onErrorMap(e -> new FeignClientException("Error al obtener la tasa de cambio: ", e));
    }

    /**
     * Procesa la respuesta de la API de tasas de cambio y realiza la conversión de moneda.
     *
     * @param response    La respuesta de la API de tasas de cambio.
     * @param amount      La cantidad a convertir.
     * @param currencyFrom La moneda de origen.
     * @param currencyTo   La moneda de destino.
     * @return Un Mono que emite el objeto ExchangeRate resultante de la conversión.
     */
    private Mono<ExchangeRate> processExchangeRate(ExchangeResponse response, double amount, String currencyFrom, String currencyTo) {
        if (response.rates() == null || !response.rates().containsKey(currencyTo)) {
            String errorMessage = response.rates() == null ?
                    "Tipo de cambio no disponible para: " + currencyFrom :
                    "Conversión no disponible para " + currencyFrom + " a " + currencyTo;
            return Mono.error(new CurrencyExchangeException(errorMessage));
        }

        double rate = response.rates().get(currencyTo);
        double convertedAmount = amount * rate;
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setAmount(amount);
        exchangeRate.setConvertedAmount(convertedAmount);
        exchangeRate.setCurrencyFrom(currencyFrom);
        exchangeRate.setCurrencyTo(currencyTo);
        exchangeRate.setRate(rate);
        return exchangeRateRepository.save(exchangeRate);
    }

    public Mono<ExchangeRate> findById(Long id) {
        return exchangeRateRepository.findById(id);
    }

    public Flux<ExchangeRate> findAll(int page, int size) {
        long offset = page * size;
        return exchangeRateRepository.findAllPaged(offset, size);
    }

    public Flux<ExchangeRate> findByCurrency(String from, String to, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return exchangeRateRepository.findByCurrencyFromAndCurrencyTo(from, to, pageable);
    }
}

