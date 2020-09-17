package br.com.exemplo.dataingestion.adapters.datastores;

import br.com.exemplo.dataingestion.adapters.datastores.entities.LancamentoEntity;
import br.com.exemplo.dataingestion.adapters.datastores.mappers.EntityMapper;
import br.com.exemplo.dataingestion.adapters.datastores.repositories.LancamentoEntityRepository;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.repositories.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LancamentoRepositoryImpl implements LancamentoRepository {

    private final EntityMapper<Lancamento, LancamentoEntity> lancamentoLancamentoEntityEntityMapper;
    private final EntityMapper<LancamentoEntity,Lancamento> lancamentoEntityLancamentoEntityMapper;
    private final LancamentoEntityRepository lancamentoEntityRepository;
    @Override
    public Lancamento save(Lancamento lancamento) {
        return lancamentoEntityLancamentoEntityMapper
                .convert(
                        lancamentoEntityRepository
                                .save(
                                        lancamentoLancamentoEntityEntityMapper
                                                .convert(lancamento)
                                )
                );
    }

    @Override
    public Lancamento getLancamento(UUID id) {
        return lancamentoEntityLancamentoEntityMapper
                .convert(
                        lancamentoEntityRepository.findById(id)
                                .orElse(
                                        LancamentoEntity.builder()
                                                .build()
                                )
                );
    }

    @Override
    public List<Lancamento> getLancamentosByConta(UUID idConta) {
        Page<LancamentoEntity> lancamentoEntityList = lancamentoEntityRepository.findByContaNumeroUnicoConta(idConta, PageRequest.of(0, 10));
        return lancamentoEntityList
                .map(lancamentoEntity -> lancamentoEntityLancamentoEntityMapper.convert(lancamentoEntity))
                .toList();
    }
}
