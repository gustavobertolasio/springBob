package com.exercicio.modelagembanco.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoUpdateRequest  {
 
    private Integer idStatusEvento;

    private String nome;

    private String local;

    private String descricao;

    private Integer limiteVagas;
}