package br.com.exemplo.dataingestion.util;

import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class CreateLancamentoTest {

    @SneakyThrows
    @Test
    void create() {
        CreateLancamento createLancamento= new CreateLancamento(new Faker());
        Lancamento lancamento = createLancamento.create();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(lancamento));

    }
}