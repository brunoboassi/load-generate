package br.com.exemplo.dataingestion.adapters.beans;

import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    @Scope(value = "prototype")
    public KafkaTemplate<String, DataLancamentoEvent> kafkaTemplate1(ProducerFactory<String, DataLancamentoEvent> producerFactory){
        Map<String,String> map = new HashMap<>();
        map.put(ProducerConfig.CLIENT_ID_CONFIG,String.valueOf(Thread.currentThread().getId()));
        return new KafkaTemplate(producerFactory,false,map);
    }
}
