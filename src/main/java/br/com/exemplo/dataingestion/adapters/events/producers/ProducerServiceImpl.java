package br.com.exemplo.dataingestion.adapters.events.producers;

import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import br.com.exemplo.dataingestion.adapters.events.mappers.EventMapper;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProducerServiceImpl implements ProducerService {

    @Value("${data.ingestion.producer.topic}")
    private String producerTopic;

    private final KafkaTemplate<String, DataLancamentoEvent> kafkaTemplate;
    private final EventMapper<Lancamento,DataLancamentoEvent> lancamentoDataLancamentoEventEventMapper;
    @Override
    public Lancamento produce(Lancamento lancamento) {
        ProducerRecord producerRecord = new ProducerRecord(producerTopic, lancamentoDataLancamentoEventEventMapper.convert(lancamento));
        kafkaTemplate.send(producerRecord);
        return lancamento;
    }
}
