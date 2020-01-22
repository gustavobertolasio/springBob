package com.exercicio.modelagembanco.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipacaoUpdateRequest {
    private Integer Nota;

    private Boolean FlagPresente;

    private String Comentario;
}