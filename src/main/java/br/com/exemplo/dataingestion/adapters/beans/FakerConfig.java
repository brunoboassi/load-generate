package br.com.exemplo.dataingestion.adapters.beans;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FakerConfig {

    @Bean
    Faker getFaker()
    {
        return new Faker();
    }
}
