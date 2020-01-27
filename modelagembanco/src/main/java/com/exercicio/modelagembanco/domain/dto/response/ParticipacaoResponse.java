package com.exercicio.modelagembanco.domain.dto.response;

import com.exercicio.modelagembanco.domain.entity.Evento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipacaoResponse {

    private Integer idParticipacao;
    private Evento evento;
    private String loginParticipante;
    private Boolean flagPresente;
    private Integer nota;
    private String comentario;
}