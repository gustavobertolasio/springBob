package com.exercicio.modelagembanco.domain.dto.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.exercicio.modelagembanco.domain.validator.DateFuture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoCreateRequest {
    @NotNull(message = "IdCategoriaEvento is required")
    private Integer idCategoriaEvento;

    @NotEmpty(message = "Nome is required")
    private String nome;

    @DateFuture
    @NotNull(message = "DataHoraInicio is required")
    private Date dataHoraInicio;

    @NotNull(message = "DataHoraFim is required")
    private Date dataHoraFim;

    @NotEmpty(message = "Local is required")
    private String local;

    @NotEmpty(message = "Descricao is required")
    private String descricao;
    @NotNull(message = "LimiteVagas is required")
    private Integer limiteVagas;
}