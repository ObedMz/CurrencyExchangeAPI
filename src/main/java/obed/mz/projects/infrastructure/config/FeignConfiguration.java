package obed.mz.projects.infrastructure.config;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración personalizada para Feign Client.
 */
@Configuration
public class FeignConfiguration {

    /**
     * Establecemos un decodificador personalizado para la API usando ObjectFactory.
     *
     * @return El decodificador.
     */
    @Bean
    public Decoder feignDecoder() {
        ObjectFactory<HttpMessageConverters> messageConvertersObjectFactory = () -> {
            HttpMessageConverters converters = new HttpMessageConverters();
            return converters;
        };
        return new SpringDecoder(messageConvertersObjectFactory);
    }
}