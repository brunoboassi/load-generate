package br.com.exemplo.dataingestion.application.usecases;

import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.repositories.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetLancamentoUseCase implements UseCase<UUID,Lancamento>{

    private final LancamentoRepository lancamentoRepository;

    @Override
    public Lancamento execute(UUID id) {
        return lancamentoRepository.getLancamento(id);
    }
}
