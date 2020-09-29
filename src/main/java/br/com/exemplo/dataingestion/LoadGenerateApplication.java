package br.com.exemplo.dataingestion;

import br.com.exemplo.dataingestion.adapters.controllers.servers.GenerateLoadController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class LoadGenerateApplication {


	public static void main(String[] args) {
		SpringApplication.run(LoadGenerateApplication.class, args);
	}
}
