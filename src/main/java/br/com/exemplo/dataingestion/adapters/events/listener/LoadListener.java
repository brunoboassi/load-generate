package br.com.exemplo.dataingestion.adapters.events.listener;

import br.com.exemplo.dataingestion.adapters.events.entities.LoadEntity;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import br.com.exemplo.dataingestion.util.CreateLancamento;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j

public class LoadListener {

    private final CreateLancamento createLancamento;
    private final ApplicationContext applicationContext;

    private final MeterRegistry simpleMeterRegistry;
    private ProducerService producerService;


    @KafkaHandler
    public void geraEvento(LoadEntity loadEntity, Acknowledgment acknowledgment)
    {
        for (int i=0;i<loadEntity.getQuantidadeDias();i++)
        {
            producerService.produce(createLancamento.createByContaAndData(loadEntity.getIdConta(),i));
        }
        acknowledgment.acknowledge();

    }
}
