package com.exercicio.modelagembanco.domain.dto.response;

import java.util.Date;

import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.StatusEvento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoResponse {

    private StatusEvento StatusEvento;
    private CategoriaEvento CategoriaEvento;
    private String Nome;
    private Date DataHoraInicio;
    private Date DataHoraFim;
    private String Local;
    private String Descricao;
    private Integer LimiteVagas;
}