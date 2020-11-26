package br.com.exemplo.dataingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class LoadGenerateApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoadGenerateApplication.class, args);
	}
}
