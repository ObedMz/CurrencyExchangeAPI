package obed.mz.projects.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad que representa una tasa de cambio.
 */
@Data
@Table("exchange_rate")
public class ExchangeRate {

    /** Identificador único de la tasa de cambio. */
    @Id
    private Long id;

    /** Cantidad original a ser convertida. */
    @Column("amount")
    private double amount;

    /** Cantidad convertida después de aplicar la tasa de cambio. */
    @Column("converted_amount")
    private double convertedAmount;

    /** Moneda de origen. */
    @Column("currency_from")
    private String currencyFrom;

    /** Moneda de destino. */
    @Column("currency_to")
    private String currencyTo;

    /** Tasa de cambio entre las monedas. */
    @Column("rate")
    private double rate;
}