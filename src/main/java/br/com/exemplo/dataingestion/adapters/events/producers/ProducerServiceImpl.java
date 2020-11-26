package br.com.exemplo.dataingestion.adapters.events.producers;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import br.com.exemplo.dataingestion.adapters.events.mappers.EventMapper;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope(value = "prototype")
public class ProducerServiceImpl implements ProducerService {

    @Value("${POD_NAME}") 
    String podName;

    @Value("${data.ingestion.producer.topic}")
    private String producerTopic;

    @Autowired
    private KafkaTemplate<String, DataLancamentoEvent> kafkaTemplate;

    @Autowired
    private EventMapper<Lancamento,DataLancamentoEvent> lancamentoDataLancamentoEventEventMapper;

    private Counter counter;
    private final AtomicInteger records = new AtomicInteger(0);
    private Timer timer;

    @PostConstruct
    private void init() {
        counter = Metrics.globalRegistry.counter("producer.items", "Type", podName);
        timer = Metrics.globalRegistry.timer("producer.elapsed", "Type", podName);
    }

    @Override
    public Lancamento produce(Lancamento lancamento) {
        Timer.Sample sample = Timer.start(Metrics.globalRegistry);
        kafkaTemplate.send(
            new ProducerRecord<String, DataLancamentoEvent> (producerTopic, lancamentoDataLancamentoEventEventMapper.convert(lancamento))
        );
        counter.increment();
        sample.stop(timer);
        log.debug(
            "Records so far: {}", 
            records.incrementAndGet()
        );
        return lancamento;
    }
}
