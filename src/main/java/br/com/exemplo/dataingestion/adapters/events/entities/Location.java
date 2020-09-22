package br.com.exemplo.dataingestion.adapters.events.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Location {
    private String lat;
    private String lon;
}
