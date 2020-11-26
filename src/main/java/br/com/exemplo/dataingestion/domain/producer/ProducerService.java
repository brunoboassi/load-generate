package br.com.exemplo.dataingestion.domain.producer;

import br.com.exemplo.dataingestion.domain.entities.Lancamento;

public interface ProducerService {
    public Lancamento produce(Lancamento lancamento);
}
