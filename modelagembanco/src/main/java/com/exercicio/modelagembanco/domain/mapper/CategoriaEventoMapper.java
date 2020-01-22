package com.exercicio.modelagembanco.domain.mapper;

import com.exercicio.modelagembanco.domain.dto.response.CategoriaEventoResponse;
import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaEventoMapper {

    private final ModelMapper mapper;

    @Autowired
    public CategoriaEventoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CategoriaEventoResponse toDto(CategoriaEvento input) {
        return mapper.map(input, CategoriaEventoResponse.class);
    }

    /*public CategoriaEvento fromDto(CategoriaEventoResponse input) {
        return mapper.map(input, CategoriaEvento.class);
    }*/

} 