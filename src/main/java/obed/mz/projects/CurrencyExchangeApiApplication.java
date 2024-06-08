package obed.mz.projects;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition
@EnableR2dbcRepositories
public class CurrencyExchangeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApiApplication.class, args);
	}
}
