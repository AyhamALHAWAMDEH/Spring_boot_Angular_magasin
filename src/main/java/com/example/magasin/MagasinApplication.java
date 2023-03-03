package com.example.magasin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MagasinApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagasinApplication.class, args);
	}

}
