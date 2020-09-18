package br.com.exemplo.dataingestion.util;

import br.com.exemplo.dataingestion.adapters.events.entities.ContaEvent;
import br.com.exemplo.dataingestion.adapters.events.entities.LancamentoEvent;
import br.com.exemplo.dataingestion.domain.entities.Conta;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CreateLancamento {

    private final Faker faker;

    public  Lancamento create()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("nome",faker.name().fullName());
        map.put("estabelecimento",faker.company().name());
        map.put("longitude",faker.address().longitude());
        map.put("latitude",faker.address().latitude());
        return Lancamento.builder()
                .codigoMoedaTransacao("986")
                .codigoMotivoLancamento(faker.number().digits(6))
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
                .metadados(map)
                .numeroIdentificacaoLancamentoConta(UUID.randomUUID())
                .siglaSistemaOrigem("X0")
                .textoComplementoLancamento(faker.commerce().productName())
                .valorLancamento(BigDecimal.valueOf(1000.00).toString())
                .build();
    }
    public Lancamento createWithParameter(UUID numeroConta, double valor)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("nome",faker.name().fullName());
        map.put("estabelecimento",faker.company().name());
        map.put("longitude",faker.address().longitude());
        map.put("latitude",faker.address().latitude());
        map.put("categoriaEstabelecimento",faker.commerce().department());
        return Lancamento.builder()
                .codigoMoedaTransacao("986")
                .codigoMotivoLancamento(faker.number().digits(6))
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
                .metadados(map)
                .numeroIdentificacaoLancamentoConta(UUID.randomUUID())
                .siglaSistemaOrigem("X0")
                .textoComplementoLancamento(faker.commerce().productName())
                .valorLancamento(BigDecimal.valueOf(valor).toString())
                .build();
    }
    public void createList(int quantidadeRegistros, int quantidadeContas, ProducerService producerService)
    {
        Random random = new Random();
        for (int j=0;j<quantidadeRegistros;j++) {
            producerService.produce(this.createWithParameter(getIdConta(quantidadeContas),random.nextDouble()));
        }
    }
    private UUID getIdConta(int quantidadeContas)
    {
        Random random = new Random();
        return UUID.nameUUIDFromBytes(new StringBuilder(
                StringUtils.leftPad(
                        String.valueOf(random.nextInt(quantidadeContas<10000?quantidadeContas:9999)),4, '0')
                )
                .append(
                        StringUtils.leftPad(String.valueOf(random.nextInt(quantidadeContas<100000?quantidadeContas:99999)),7, '0')
                )
                .append(
                        random.nextInt(9)
                )
                .toString()
                .getBytes()
        );
    }

}
