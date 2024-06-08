package obed.mz.projects.application.exceptions;

/**
 * Excepción personalizada para representar errores relacionados con clientes Feign.
 */
public class FeignClientException extends Exception {
    /**
     * Constructor que crea una nueva instancia de FeignClientException con el mensaje y la causa especificados.
     *
     * @param message El mensaje que describe la excepción.
     * @param cause   La causa de la excepción.
     */
    public FeignClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
