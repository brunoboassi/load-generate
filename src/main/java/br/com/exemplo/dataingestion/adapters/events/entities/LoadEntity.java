package br.com.exemplo.dataingestion.adapters.events.entities;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoadEntity {
    private UUID idConta;
    private int quantidadeDias;
    private LocalDate dataFim;
}
