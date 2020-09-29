package br.com.exemplo.dataingestion.util;

import br.com.exemplo.dataingestion.adapters.events.entities.ContaEvent;
import br.com.exemplo.dataingestion.adapters.events.entities.LancamentoEvent;
import br.com.exemplo.dataingestion.adapters.events.entities.Location;
import br.com.exemplo.dataingestion.domain.entities.Conta;
import br.com.exemplo.dataingestion.domain.entities.Lancamento;
import br.com.exemplo.dataingestion.domain.producer.ProducerService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
public class CreateLancamento {

    private final Faker faker;

    private ExecutorService executorService;
    private List<String> lista = new ArrayList<>();
    private  Random random = new Random();
    @Value("${processamento.threads.geracao.massa:10}")
    private int numeroThreadsGeracaoMassa;

    @PostConstruct
    public void constroiExecutors() {

        lista.add("Café");
        lista.add("ALmoço");
        lista.add("Árvore");
        lista.add("Boné");
        lista.add("paralelepipedo");
        lista.add("Maçã");
        lista.add("Coração");
        lista.add("Itaú");
        lista.add("paralelepipedo");
        lista.add("McDonalds");
        lista.add("Drogasil");

    }

    public  Lancamento create123()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("nome",faker.name().fullName());
        map.put("estabelecimento",faker.company().name());
        map.put("location", Location.builder().lat(faker.address().latitude()).lon(faker.address().longitude()).build());
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
                .indicadorLancamentoCompulsorioOcorrencia(true)
                .metadados(map)
                .numeroIdentificacaoLancamentoConta(UUID.randomUUID())
                .siglaSistemaOrigem("X0")
                .textoComplementoLancamento(faker.commerce().productName())
                .valorLancamento(BigDecimal.valueOf(1000.00).toString())
                .build();
    }


    public Lancamento createByContaAndData(UUID idConta,int dias) {
        String data = OffsetDateTime.now().minus(dias,ChronoUnit.DAYS).format(DateTimeFormatter.ISO_DATE_TIME);
        Map<String,Object> map = new HashMap<>();
        map.put("nome",faker.name().fullName());
        map.put("estabelecimento",faker.company().name());
        map.put("location", Location.builder().lat(faker.address().latitude()).lon(faker.address().longitude()).build());
        map.put("categoriaEstabelecimento",faker.commerce().department());
        return Lancamento.builder()
                .codigoMoedaTransacao("986")
                .codigoMotivoLancamento(String.valueOf(random.nextInt(999999)))
                .codigoTipoOperacao("TEF_CC_CC")
                .conta(
                        Conta.builder()
                                .codigoSufixoConta("100")
                                .numeroUnicoConta(idConta)
                                .build()
                )
                .dataContabilLancamento(data)
                .dataLancamento(data)
                .indicadorLancamentoCompulsorioOcorrencia(random.nextBoolean())
                .metadados(map)
                .numeroIdentificacaoLancamentoConta(UUID.randomUUID())
                .siglaSistemaOrigem("X0")
                .textoComplementoLancamento(faker.commerce().productName()+" "+lista.get(random.nextInt(lista.size()-1)))
                .valorLancamento(BigDecimal.valueOf(random.nextDouble()).toString())
                .build();
    }
}
