package com.eazyBank.cards;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "AuditAwareImp")
@OpenAPIDefinition(
		info = @Info(
				title = "Cards Micro Services REST API's",
				description = "This microservices provides an API's for CURD operations on Cards",
				version = "v1",
				contact = @Contact(
						name = "Arjunagi A Rehman",
						email = "andul123arj@gmail.com"
				),
				license =@License(
						name = "Apache"
				)
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
