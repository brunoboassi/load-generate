package br.com.exemplo.dataingestion.adapters.controllers.servers;

import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import br.com.exemplo.dataingestion.util.CreateLancamento;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.applet.Applet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

@Component
@RequiredArgsConstructor
@Slf4j
public class GenerateLoadController {

    private final CreateLancamento createLancamento;
    private final ApplicationContext applicationContext;

    private ExecutorService executorService;
    private final List<ProducerService> producerServiceList;
    private final MeterRegistry simpleMeterRegistry;

    @Value("${processamento.threads.producao:10}")
    private int numeroThreadsProducao;

    @PostConstruct
    public void constroiProducer()
    {
        this.executorService = Executors.newFixedThreadPool(numeroThreadsProducao);
        log.debug("Inicializando produtores");
        for(int i=0;i<numeroThreadsProducao;i++)
        {
            producerServiceList.add(applicationContext.getBean(ProducerService.class));
        }
    }

    public void geraEvento(@PathVariable("qtdConta") int qtdConta,@PathVariable("qtdReg") int qtdReg,@PathVariable("qtdDias") int qtdDias)
    {
        AtomicInteger numeroItensThread = new AtomicInteger(qtdReg/numeroThreadsProducao);

        for(int i =0; i<numeroThreadsProducao;i++)
        {
            ProducerService producerService = producerServiceList.get(i);
            if(i==numeroThreadsProducao-1)
            {
                numeroItensThread.addAndGet(qtdReg%numeroThreadsProducao);
            }
            executorService.execute(() -> {
                log.info("Inicializando thread {} com {} registros",Thread.currentThread().getId(),numeroItensThread.get());
                createLancamento.create(numeroItensThread.get(), qtdConta,qtdDias,producerService);
                log.info("Finalizando Thread thread {} ",Thread.currentThread().getId(),numeroItensThread.get());
            });
        }
    }
}
