package br.com.exemplo.dataingestion.adapters.controllers.servers;

import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import br.com.exemplo.dataingestion.util.CreateLancamento;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Component
@RequiredArgsConstructor
public class GenerateLoadController {

    private final CreateLancamento createLancamento;
    private final ApplicationContext applicationContext;
    private static final int NUMERO_THREADS=4;
    private final ExecutorService executorService= Executors.newFixedThreadPool(NUMERO_THREADS);
    private final List<ProducerService> producerServiceList;
    private final MeterRegistry simpleMeterRegistry;


    @PostConstruct
    public void constroiProducer()
    {
        for(int i=0;i<NUMERO_THREADS;i++)
        {
            producerServiceList.add(applicationContext.getBean(ProducerService.class));
        }
    }

    @GetMapping("/geraevento/{qtdConta}/{qtdReg}")
    public ResponseEntity geraEvento(@PathVariable("qtdConta") int qtdConta,@PathVariable("qtdReg") int qtdReg)
    {
        AtomicInteger numeroItensThread = new AtomicInteger(qtdReg/NUMERO_THREADS);

        for(int i =0; i<NUMERO_THREADS;i++)
        {
            ProducerService producerService = producerServiceList.get(i);
            if(i==NUMERO_THREADS-1)
            {
                numeroItensThread.addAndGet(qtdReg%NUMERO_THREADS);
            }
            executorService.execute(() -> {
                Timer.Sample sample = Timer.start(simpleMeterRegistry);
                createLancamento.createList(numeroItensThread.get(), qtdConta,producerService);
                sample.stop(simpleMeterRegistry.timer("kafka.processamento","thread",String.valueOf(Thread.currentThread().getId())));
            });
        }




        return ResponseEntity.ok().build();
    }
}
