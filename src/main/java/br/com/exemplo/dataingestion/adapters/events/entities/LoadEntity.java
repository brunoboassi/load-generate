package br.com.exemplo.dataingestion.adapters.events.entities;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoadEntity {
    private UUID idConta;
    private int quantidadeDias;
}
