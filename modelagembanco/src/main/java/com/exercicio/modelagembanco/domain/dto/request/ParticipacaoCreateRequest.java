package com.exercicio.modelagembanco.domain.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipacaoCreateRequest {

    // private Integer IdParticipacao;

    @NotNull(message = "EventoId is required")
    private Integer idEvento;
    @NotEmpty(message = "LoginParticipante is required")
    private String loginParticipante;
   }