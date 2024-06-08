package obed.mz.projects.domain.model;

import lombok.Data;

/**
 * Cuerpo de solicitud de conversión de moneda.
 */
@Data
public class CurrencyConversionRequest {

    /** La cantidad a convertir. */
    private Double amount;

    /** La moneda de origen. */
    private String currencyFrom;

    /** La moneda de destino. */
    private String currencyTo;

}