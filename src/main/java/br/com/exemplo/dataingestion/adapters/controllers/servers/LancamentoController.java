package br.com.exemplo.dataingestion.adapters.controllers.servers;


import br.com.exemplo.dataingestion.adapters.events.entities.DataLancamentoEvent;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.application.usecases.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@Component
@RequiredArgsConstructor
public class LancamentoController {

    private final UseCase<UUID,Lancamento> getLancamento;
    private final UseCase<UUID,List<Lancamento>> getLancamentoByConta;

    @GetMapping("/lancamentos/{id}")
    public Lancamento getExtratoDetalhado(@PathVariable UUID id)
    {
        log.info("Buscando lançamento com id -> {}",id);
        return getLancamento.execute(id);
    }
    @GetMapping("/lancamentos/conta/{id}")
    public List<Lancamento> getExtratoPorConta(@PathVariable UUID id)
    {
        log.info("Buscando Lista de lançamentos com id -> {}",id);
        return getLancamentoByConta.execute(id);
    }
}
