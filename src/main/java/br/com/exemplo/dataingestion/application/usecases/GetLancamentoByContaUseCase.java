package br.com.exemplo.dataingestion.application.usecases;

import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.repositories.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetLancamentoByContaUseCase implements UseCase<UUID,List<Lancamento>>{

    private final LancamentoRepository lancamentoRepository;

    @Override
    public List<Lancamento> execute(UUID idConta) {
        return lancamentoRepository.getLancamentosByConta(idConta);
    }
}
