package obed.mz.projects.domain.repositories;

import obed.mz.projects.domain.model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataR2dbcTest
public class ExchangeRateRepositoryTest {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @BeforeEach
    public void setup() {
        exchangeRateRepository.deleteAll().block();
    }

    /**
     * Prueba la búsqueda de un objeto ExchangeRate por monedas de origen y destino.
     */
    @Test
    public void testFindByCurrencyFromAndCurrencyTo() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setAmount(100.0);
        exchangeRate.setConvertedAmount(92.3621);
        exchangeRate.setCurrencyFrom("USD");
        exchangeRate.setCurrencyTo("EUR");
        exchangeRate.setRate(0.923621);

        exchangeRateRepository.save(exchangeRate).block();

        Pageable pageable = PageRequest.of(0, 10);
        Flux<ExchangeRate> results = exchangeRateRepository.findByCurrencyFromAndCurrencyTo("USD", "EUR", pageable);

        StepVerifier.create(results)
                .expectNextMatches(rate -> rate.getCurrencyFrom().equals("USD") && rate.getCurrencyTo().equals("EUR"))
                .verifyComplete();
    }
    /**
     * Prueba la búsqueda paginada de todos los objetos ExchangeRate.
     */
    @Test
    public void testFindAllPaged() {
        for (int i = 0; i < 5; i++) {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setAmount(100.0 + i);
            exchangeRate.setConvertedAmount(92.3621 + i);
            exchangeRate.setCurrencyFrom("USD");
            exchangeRate.setCurrencyTo("EUR");
            exchangeRate.setRate(0.923621);

            exchangeRateRepository.save(exchangeRate).block();
        }

        Flux<ExchangeRate> results = exchangeRateRepository.findAllPaged(0, 5);

        StepVerifier.create(results)
                .expectNextCount(5)
                .verifyComplete();
    }
}
