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

	@Value("${registros.total:1000000}")
	private int registro;

	@Value("${contas.total:1000}")
	private int contas;

	@Value("${dias.total:90}")
	private int dias;

	private final GenerateLoadController generateLoadController;

	public static void main(String[] args) {
		SpringApplication.run(LoadGenerateApplication.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void run() throws Exception {
		log.info("Iniciando a produção de {} com {} contas e com {} dias retroativos",registro,contas,dias);
		generateLoadController.geraEvento(contas,registro,dias);
		log.info("Liberando comando da aplicação");
	}
}
