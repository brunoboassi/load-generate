package br.com.exemplo.dataingestion.adapters.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import br.com.exemplo.dataingestion.adapters.events.entities.LoadEntity;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.listener.concurrency}")
    private int concurrency;


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LoadEntity> kafkaListenerContainerFactory(ConsumerFactory<String, LoadEntity> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, LoadEntity> factory = new ConcurrentKafkaListenerContainerFactory<String, LoadEntity>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        return factory;
    }
}
