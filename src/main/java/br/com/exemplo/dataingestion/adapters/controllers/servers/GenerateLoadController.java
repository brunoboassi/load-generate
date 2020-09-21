package br.com.exemplo.dataingestion.adapters.controllers.servers;

import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
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
    private final ProducerService producerService;
    private final MeterRegistry simpleMeterRegistry;


    @GetMapping("/geraevento/{qtdConta}/{qtdReg}")
    public ResponseEntity geraEvento(@PathVariable("qtdConta") int qtdConta,@PathVariable("qtdReg") int qtdReg)
    {
        Timer.Sample sample = Timer.start(simpleMeterRegistry);
        List<Lancamento> list = createLancamento.createList(qtdReg, qtdConta);
        list.stream().forEach(lancamento ->
        {
            producerService.produce(lancamento);
        });
        sample.stop(simpleMeterRegistry.timer("kafka.processamento","thread",String.valueOf(Thread.currentThread().getId())));
        return ResponseEntity.ok().build();
    }
}
