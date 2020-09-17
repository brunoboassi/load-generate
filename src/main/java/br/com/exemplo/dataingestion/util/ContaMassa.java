package br.com.exemplo.dataingestion.util;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContaMassa {
    private int agencia;
    private int conta;
    private int dac;
    private UUID idConta;

    public UUID geraIdConta()
    {
        idConta = UUID.nameUUIDFromBytes(new StringBuilder(StringUtils.leftPad(String.valueOf(agencia),4, '0')).append(StringUtils.leftPad(String.valueOf(conta),7, '0')).append(dac).toString().getBytes());
        return idConta;
    }
}
