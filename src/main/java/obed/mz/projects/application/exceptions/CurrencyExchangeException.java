package obed.mz.projects.application.exceptions;

/**
 * Excepción personalizada para representar errores relacionados con el intercambio de moneda.
 */
public class CurrencyExchangeException extends Exception {
    /**
     * Constructor que crea una nueva instancia de CurrencyExchangeException con el mensaje especificado.
     *
     * @param message El mensaje que describe la excepción.
     */
    public CurrencyExchangeException(String message) {
        super(message);
    }
}
