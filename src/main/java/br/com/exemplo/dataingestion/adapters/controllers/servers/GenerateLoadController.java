package br.com.exemplo.dataingestion.adapters.controllers.servers;

import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import br.com.exemplo.dataingestion.util.CreateLancamento;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    @SneakyThrows
    public void geraEvento(int qtdConta, int qtdReg, int qtdDias)
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
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }
}
