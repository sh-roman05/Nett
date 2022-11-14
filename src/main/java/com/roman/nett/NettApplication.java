package com.roman.nett;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class NettApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettApplication.class, args);
	}

}
