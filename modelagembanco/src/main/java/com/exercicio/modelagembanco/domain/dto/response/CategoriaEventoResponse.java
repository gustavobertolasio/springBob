package com.exercicio.modelagembanco.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaEventoResponse {

    private int IdCategoriaEvento;
    private String NomeCategoria;

}