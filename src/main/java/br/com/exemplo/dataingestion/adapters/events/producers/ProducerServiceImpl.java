package br.com.exemplo.dataingestion.adapters.events.producers;

import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import br.com.exemplo.dataingestion.adapters.events.mappers.EventMapper;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Scope(value = "prototype")
public class ProducerServiceImpl implements ProducerService {

    @Value("${data.ingestion.producer.topic}")
    private String producerTopic;

    private final KafkaTemplate<String, DataLancamentoEvent> kafkaTemplate;
    private final EventMapper<Lancamento,DataLancamentoEvent> lancamentoDataLancamentoEventEventMapper;
    private final MeterRegistry simpleMeterRegistry;
    @Override
    public Lancamento produce(Lancamento lancamento) {
        simpleMeterRegistry.counter("kafka.contador","type","producao","thread",String.valueOf(Thread.currentThread().getId())).increment();
        Timer.Sample sample = Timer.start(simpleMeterRegistry);
        ProducerRecord producerRecord = new ProducerRecord(producerTopic, lancamentoDataLancamentoEventEventMapper.convert(lancamento));
        sample.stop(simpleMeterRegistry.timer("kafka.time","type","producao","thread",String.valueOf(Thread.currentThread().getId())));
        kafkaTemplate.send(producerRecord);
        return lancamento;
    }
}
