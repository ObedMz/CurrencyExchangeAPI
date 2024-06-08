package obed.mz.projects.application.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase que maneja las excepciones globales de la aplicación.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción CurrencyExchangeException y devuelve una respuesta de error 400.
     *
     * @param e Excepción de tipo CurrencyExchangeException.
     * @return ResponseEntity con el mensaje de error y el código de estado 400.
     * @see CurrencyExchangeException
     */
    @ExceptionHandler(CurrencyExchangeException.class)
    public ResponseEntity<String> handleCurrencyExchangeException(CurrencyExchangeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    /**
     * Maneja la excepción FeignClientException y devuelve una respuesta de error 503.
     *
     * @param e Excepción de tipo FeignClientException.
     * @return ResponseEntity con el mensaje de error y el código de estado 503.
     */
    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<String> handleFeignClientException(FeignClientException e) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Servicio no disponible: " + e.getMessage());
    }

    /**
     * Maneja la excepción ConstraintViolationException y devuelve una respuesta de error 400.
     *
     * @param e Excepción de ConstraintViolationException.
     * @return ResponseEntity con el mensaje de error y el código de estado 400.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    /**
     * Maneja la excepción HttpMessageNotReadableException y devuelve una respuesta de error 400.
     *
     * @param ex La excepción HttpMessageNotReadableException.
     * @return ResponseEntity con el mensaje de error y el código de estado 400.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleBadRequest(HttpMessageNotReadableException ex) {
        String errorMessage = "Hubo un error en la solicitud: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja cualquier otra excepción no manejada y devuelve una respuesta de error 500.
     *
     * @param e La excepción no manejada.
     * @return ResponseEntity con el mensaje de error y el código de estado 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error: " + e.getMessage());
    }
}
