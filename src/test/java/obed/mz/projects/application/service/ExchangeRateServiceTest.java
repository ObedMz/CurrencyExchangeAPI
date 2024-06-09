package obed.mz.projects.application.service;

import obed.mz.projects.application.exceptions.CurrencyExchangeException;
import obed.mz.projects.application.exceptions.FeignClientException;
import obed.mz.projects.domain.model.ExchangeRate;
import obed.mz.projects.domain.model.ExchangeResponse;
import obed.mz.projects.domain.repositories.ExchangeRateRepository;
import obed.mz.projects.infrastructure.external.ExchangeRateClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ExchangeRateServiceTest {
    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Captor
    private ArgumentCaptor<ExchangeRate> exchangeRateCaptor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba el método convertCurrencyTo cuando la conversión es exitosa.
     */
    @Test
    void testConvertCurrencyTo_Success() {
        double amount = 100.0;
        String currencyFrom = "USD";
        String currencyTo = "EUR";

        ExchangeResponse mockResponse = new ExchangeResponse("success",
                Map.of("USD", 1.0, currencyTo, 0.923621));

        when(exchangeRateClient.getExchangeRate(currencyFrom)).thenReturn(mockResponse);

        ExchangeRate savedExchangeRate = new ExchangeRate();
        savedExchangeRate.setAmount(amount);
        savedExchangeRate.setConvertedAmount(amount * mockResponse.rates().get(currencyTo));
        savedExchangeRate.setCurrencyFrom(currencyFrom);
        savedExchangeRate.setCurrencyTo(currencyTo);
        savedExchangeRate.setRate(mockResponse.rates().get(currencyTo));

        when(exchangeRateRepository.save(any(ExchangeRate.class))).thenReturn(Mono.just(savedExchangeRate));

        Mono<ExchangeRate> resultMono = exchangeRateService.convertCurrencyTo(amount, currencyFrom, currencyTo);

        StepVerifier.create(resultMono)
                .assertNext(result -> {
                    assertEquals(amount, result.getAmount());
                    assertEquals(amount * mockResponse.rates().get(currencyTo), result.getConvertedAmount());
                    assertEquals(currencyFrom, result.getCurrencyFrom());
                    assertEquals(currencyTo, result.getCurrencyTo());
                    assertEquals(mockResponse.rates().get(currencyTo), result.getRate());
                })
                .verifyComplete();
    }

    /**
     * Prueba el método convertCurrencyTo cuando el monto es negativo.
     */
    @Test
    void testConvertCurrencyTo_NegativeAmount() {
        double amount = -100.0;
        String currencyFrom = "USD";
        String currencyTo = "EUR";

        Mono<ExchangeRate> resultMono = exchangeRateService.convertCurrencyTo(amount, currencyFrom, currencyTo);

        StepVerifier.create(resultMono)
                .expectError(CurrencyExchangeException.class)
                .verify();
    }

    /**
     * Prueba el método convertCurrencyTo cuando la tasa de cambio no está disponible.
     */
    @Test
    void testConvertCurrencyTo_ExchangeRateNotAvailable() {
        double amount = 100.0;
        String currencyFrom = "USD";
        String currencyTo = "XYZ";
        ExchangeResponse mockResponse = new ExchangeResponse("success"
        ,Map.of("EUR", 0.923621));

        when(exchangeRateClient.getExchangeRate(currencyFrom)).thenReturn(mockResponse);

        Mono<ExchangeRate> resultMono = exchangeRateService.convertCurrencyTo(amount, currencyFrom, currencyTo);

        StepVerifier.create(resultMono)
                .expectError(CurrencyExchangeException.class)
                .verify();
    }

    /**
     * Prueba el método convertCurrencyTo cuando hay un error en el cliente Feign.
     */
    @Test
    void testConvertCurrencyTo_FeignClientError() {
        double amount = 100.0;
        String currencyFrom = "USD";
        String currencyTo = "EUR";

        when(exchangeRateClient.getExchangeRate(currencyFrom)).thenThrow(new RuntimeException("API error"));

        Mono<ExchangeRate> resultMono = exchangeRateService.convertCurrencyTo(amount, currencyFrom, currencyTo);

        StepVerifier.create(resultMono)
                .expectError(FeignClientException.class)
                .verify();
    }
}
