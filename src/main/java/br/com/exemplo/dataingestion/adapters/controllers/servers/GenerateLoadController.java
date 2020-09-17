package br.com.exemplo.dataingestion.adapters.controllers.servers;

import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import br.com.exemplo.dataingestion.util.CreateLancamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@RequiredArgsConstructor
public class GenerateLoadController {

    private final CreateLancamento createLancamento;
    private final ProducerService producerService;
    @GetMapping("/geraevento/{qtdConta}/{qtdReg}")
    public ResponseEntity geraEvento(@PathVariable("qtdConta") int qtdConta,@PathVariable("qtdReg") int qtdReg)
    {
        createLancamento.createList(qtdReg,qtdConta).stream()
                .forEach(lancamento -> producerService.produce(lancamento));
        return ResponseEntity.ok().build();
    }
}
