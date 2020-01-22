package com.exercicio.modelagembanco.domain.mapper;

import com.exercicio.modelagembanco.domain.dto.request.EventoCreateRequest;
import com.exercicio.modelagembanco.domain.dto.request.EventoUpdateRequest;
import com.exercicio.modelagembanco.domain.dto.response.EventoResponse;
import com.exercicio.modelagembanco.domain.entity.Evento;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventoMapper {

    private final ModelMapper mapper;

    @Autowired
    public EventoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public EventoResponse toDto(Evento input) {
        return mapper.map(input, EventoResponse.class);
    }

    public Evento fromDto(EventoCreateRequest input) {
        return mapper.map(input, Evento.class);
    }

    public Evento fromDtoUpdate(EventoUpdateRequest input) {
        return mapper.map(input, Evento.class);
    }

} 