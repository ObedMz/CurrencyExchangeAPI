package obed.mz.projects.infraestructure.external;

import obed.mz.projects.domain.model.ExchangeResponse;
import obed.mz.projects.infrastructure.external.ExchangeRateClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Import;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
@Import(FeignClientProperties.FeignClientConfiguration.class)
public class ExchangeRateClientTest {

    @Autowired
    private ExchangeRateClient exchangeRateClient;

    @MockBean
    private ExchangeRateClient mockExchangeRateClient;

    /**
     *
     * Prueba la respuesta del método getExchangeRate de ExchangeRateClient.
     */
    @Test
    public void testGetExchangeRateClient() {
        ExchangeResponse response = new ExchangeResponse("success",
                Map.of("USD", 1.0, "EUR", 0.9, "JPY", 110.0));

        when(mockExchangeRateClient.getExchangeRate("USD")).thenReturn(response);
        assertEquals("success", response.result());

    }
}
