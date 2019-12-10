package com.br.solufix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.br.solufix.config.SolufixApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(SolufixApiProperty.class)
public class SolufixApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolufixApplication.class, args);
	}

}
