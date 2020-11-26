package br.com.exemplo.dataingestion.adapters.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

@Configuration
public class MicrometerConfig {

    @Bean
    public SimpleMeterRegistry getMeter()
    {
        return new SimpleMeterRegistry();
    }
}
