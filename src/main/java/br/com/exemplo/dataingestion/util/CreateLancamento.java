package br.com.exemplo.dataingestion.util;

import br.com.exemplo.dataingestion.adapters.events.entities.ContaEvent;
import br.com.exemplo.dataingestion.adapters.events.entities.LancamentoEvent;
import br.com.exemplo.dataingestion.domain.entities.Conta;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CreateLancamento {
    public  Lancamento create()
    {
        return Lancamento.builder()
                .codigoMoedaTransacao("986")
                .codigoMotivoLancamento("123456")
                .codigoTipoOperacao("TEF_CC_CC")
                .conta(
                        Conta.builder()
                                .codigoSufixoConta("100")
                                .numeroUnicoConta(UUID.fromString("94c92f41-d081-4f2c-b2ed-86766461aed5"))
                                .build()
                )
                .dataContabilLancamento(OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .dataLancamento(OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .indicadorLancamentoCompulsorioOcorrencia("N")
                .metadados(new ArrayList<>())
                .numeroIdentificacaoLancamentoConta(UUID.randomUUID())
                .siglaSistemaOrigem("X0")
                .textoComplementoLancamento("Complemento")
                .valorLancamento(BigDecimal.valueOf(1000.00).toString())
                .build();
    }
    public Lancamento createWithParameter(UUID numeroConta, double valor)
    {
        return Lancamento.builder()
                .codigoMoedaTransacao("986")
                .codigoMotivoLancamento("123456")
                .codigoTipoOperacao("TEF_CC_CC")
                .conta(
                        Conta.builder()
                                .codigoSufixoConta("100")
                                .numeroUnicoConta(numeroConta)
                                .build()
                )
                .dataContabilLancamento(OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .dataLancamento(OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .indicadorLancamentoCompulsorioOcorrencia("N")
                .metadados(new ArrayList<>())
                .numeroIdentificacaoLancamentoConta(UUID.randomUUID())
                .siglaSistemaOrigem("X0")
                .textoComplementoLancamento("Complemento")
                .valorLancamento(BigDecimal.valueOf(valor).toString())
                .build();
    }
    public List<Lancamento> createList(int quantidadeRegistros, int quantidadeContas)
    {
        List<ContaMassa> listConta = new ArrayList<>();
        List<Lancamento> listLancamento = new ArrayList<>();
        Random random = new Random();
        ContaMassa contaMassa = new ContaMassa();
        for (int i=0;i<quantidadeContas;i++) {
            contaMassa = new ContaMassa();
            contaMassa.setAgencia(random.nextInt(9999));
            contaMassa.setConta(random.nextInt(99999));
            contaMassa.setDac(random.nextInt(9));
            contaMassa.geraIdConta();
            listConta.add(contaMassa);
        }

        for (int j=0;j<quantidadeRegistros;j++) {
           listLancamento.add(this.createWithParameter(listConta.get(random.nextInt(quantidadeContas)).getIdConta(),random.nextDouble()));
        }

        return listLancamento;
    }

}
