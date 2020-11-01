package br.com.exemplo.dataingestion.adapters.events.listener;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.exemplo.dataingestion.adapters.events.entities.LoadEntity;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import br.com.exemplo.dataingestion.util.CreateLancamento;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
        topics = "${data.ingestion.consumer.topic}",
        containerFactory = "kafkaListenerContainerFactory")
public class LoadListener {

    public LoadListener(CreateLancamento createLancamento, ProducerService producerService, @Value("${POD_NAME}") String podName) {
        this.createLancamento = createLancamento;
        this.producerService = producerService;
        counter = Metrics.globalRegistry.counter("producer.account.items", "Type", podName);
        timer = Metrics.globalRegistry.timer("producer.account.elapsed", "Type", podName);
    }

    private final CreateLancamento createLancamento;
    private final ProducerService producerService;

    private final Counter counter;
    private final AtomicInteger accounts = new AtomicInteger(0);
    private final Timer timer;

    @KafkaHandler
    public void geraEvento(LoadEntity loadEntity) {
        log.debug("will generate {} records for account {}", loadEntity.getQuantidadeDias(), loadEntity.getIdConta());
        Timer.Sample sample = Timer.start(Metrics.globalRegistry);
        for (int i = 0; i < loadEntity.getQuantidadeDias(); i++) {
            producerService.produce(createLancamento.createByContaAndData(loadEntity.getIdConta(), i));
        }

        counter.increment();
        sample.stop(
            timer
        );
        log.info(
            "Accounts so far: {}", 
            accounts.incrementAndGet()
        );
    }

}
