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

@RestController
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

    @GetMapping("/geraevento/{qtdConta}/{qtdReg}/{qtdDias}")
    public ResponseEntity geraEvento(@PathVariable("qtdConta") int qtdConta,@PathVariable("qtdReg") int qtdReg,@PathVariable("qtdDias") int qtdDias)
    {
        log.info("Iniciando criação de massa");
        AtomicInteger control = new AtomicInteger(0);
        Timer.Sample sample = Timer.start(simpleMeterRegistry);
        List<Lancamento> list = createLancamento.createList(qtdReg, qtdConta,qtdDias);
        log.info("Massa finalizada, iniciando produção paralelizada");
            list.stream().forEach(lancamento -> {
                executorService.execute(() -> {
                    producerServiceList.get(control.get()).produce(lancamento);
                    if(control.get()<=numeroThreadsProducao-1)
                    {
                        control.set(0);
                    }
                    else
                    {
                        control.getAndIncrement();
                    }
                });
            });
        sample.stop(simpleMeterRegistry.timer("kafka.processamento","thread",String.valueOf(Thread.currentThread().getId())));
        log.info("Finalizado");
        return ResponseEntity.ok().build();
    }
}
