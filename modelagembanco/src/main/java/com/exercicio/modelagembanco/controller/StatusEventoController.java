package com.exercicio.modelagembanco.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.exercicio.modelagembanco.domain.dto.response.StatusEventoResponse;
import com.exercicio.modelagembanco.domain.mapper.StatusEventoMapper;
import com.exercicio.modelagembanco.service.StatusEventoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * EventoController
 */
@RestController
@RequestMapping("/statusevento")
public class StatusEventoController {

    private final StatusEventoService statusEventoService;
    private final StatusEventoMapper mapper;

    @Autowired
    public StatusEventoController(StatusEventoService statusEventoService, StatusEventoMapper mapper) {
        this.statusEventoService = statusEventoService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<StatusEventoResponse>> listStatusEvento() {
        return ResponseEntity
                .ok(statusEventoService.getList().stream().map(x -> mapper.toDto(x)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusEventoResponse> findStatusEvento(@Valid @RequestParam Integer id) {
        return ResponseEntity.ok(mapper.toDto(statusEventoService.findStatusEvento(id)));
    }

}