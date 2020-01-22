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

    private Integer IdParticipacao;
    private Evento Evento;
    private String LoginParticipante;
    private Boolean FlagPresente;
    private Integer Nota;
    private String Comentario;
}