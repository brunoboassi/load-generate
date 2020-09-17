package br.com.exemplo.dataingestion.domain.repositories;

import br.com.exemplo.dataingestion.domain.entities.Lancamento;

import java.util.List;
import java.util.UUID;

public interface LancamentoRepository {
    public Lancamento save(Lancamento lancamento);
    public Lancamento getLancamento(UUID id);
    public List<Lancamento> getLancamentosByConta(UUID idConta);

}