package com.exercicio.modelagembanco.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.exercicio.modelagembanco.domain.dto.request.EventoCreateRequest;
import com.exercicio.modelagembanco.domain.dto.request.EventoUpdateRequest;
import com.exercicio.modelagembanco.domain.dto.response.EventoResponse;
import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.domain.mapper.EventoMapper;
import com.exercicio.modelagembanco.service.EventoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * EventoController
 */
@RestController
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;
    private final EventoMapper mapper;

    @Autowired
    public EventoController(EventoService eventoService, EventoMapper mapper) {
        this.eventoService = eventoService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponse>> listEventos() {
        return ResponseEntity
                .ok(eventoService.getList().stream().map(x -> mapper.toDto(x)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<EventoResponse>> listEventoByCategoria(@Valid @RequestParam Integer id) {
        return ResponseEntity.ok((eventoService.findEventosByCategoria(id).stream().map(x -> mapper.toDto(x))
                .collect(Collectors.toList())));
    }

    @PostMapping()
    public ResponseEntity<EventoResponse> postEvento(@Valid @RequestBody EventoCreateRequest entity) {
        Evento evento = mapper.fromDto(entity);
        return ResponseEntity.ok(mapper.toDto(eventoService.saveEvento(evento, entity.getIdCategoriaEvento())));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EventoResponse> putEvento(@Valid @PathVariable Integer id,
            @Valid @RequestBody EventoUpdateRequest entity) {
        Evento evento = mapper.fromDtoUpdate(entity);
        return ResponseEntity.ok(mapper.toDto(eventoService.updateEvento(evento, id, entity.getIdStatusEvento())));
    }

    @DeleteMapping(value = "/{id}")
    public String deleteEvento(@Valid @PathVariable Integer id) {
        eventoService.deleteEvento(id);
        return "Deletado com sucesso";
    }
}