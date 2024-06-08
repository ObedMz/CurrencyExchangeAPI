package obed.mz.projects.domain.repositories;

import feign.Param;
import obed.mz.projects.domain.model.ExchangeRate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repositorio para almacenar las operaciones realizadas por el service.
 */
@Repository
public interface ExchangeRateRepository extends R2dbcRepository<ExchangeRate, Long> {

    /**
     * Busca todas las tasas de cambio con paginación.
     *
     * @param offset el índice de inicio de la página.
     * @param size   el tamaño de la página.
     * @return registro de tasas de cambio.
     */
    @Query("SELECT * FROM exchange_rate LIMIT :size OFFSET :offset")
    Flux<ExchangeRate> findAllPaged(@Param("offset") long offset, @Param("size") int size);

    /**
     * Busca las tasas de cambio por moneda origen y moneda destino.
     *
     * @param currencyFrom la moneda de origen.
     * @param currencyTo   la moneda de destino.
     * @param pageable     la información de paginación.
     * @return Lista de tasas de cambio por moneda origen y moneda destino.
     */
    Flux<ExchangeRate> findByCurrencyFromAndCurrencyTo(String currencyFrom, String currencyTo, Pageable pageable);
}
