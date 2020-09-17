package br.com.exemplo.dataingestion.domain.entities;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Lancamento {
    private UUID numeroIdentificacaoLancamentoConta;
    private String codigoTipoOperacao;
    private String valorLancamento;
    private String codigoMoedaTransacao;
    private String siglaSistemaOrigem;
    private String codigoMotivoLancamento;
    private String textoComplementoLancamento;
    private String indicadorLancamentoCompulsorioOcorrencia;
    private String dataContabilLancamento;
    private String dataLancamento;
    private Conta conta;
    private List<String> metadados;
}
