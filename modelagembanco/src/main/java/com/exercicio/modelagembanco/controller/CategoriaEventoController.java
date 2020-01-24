package com.exercicio.modelagembanco.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.exercicio.modelagembanco.domain.dto.response.CategoriaEventoResponse;
import com.exercicio.modelagembanco.domain.mapper.CategoriaEventoMapper;
import com.exercicio.modelagembanco.service.CategoriaEventoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * EventoController
 */
@RestController
@RequestMapping("/categoriaevento")
public class CategoriaEventoController {

    private final CategoriaEventoService categoriaEventoService;
    private final CategoriaEventoMapper mapper;

    @Autowired
    public CategoriaEventoController(CategoriaEventoService categoriaEventoService, CategoriaEventoMapper mapper) {
        this.categoriaEventoService = categoriaEventoService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaEventoResponse>> listCategoriaEvento() {
        return ResponseEntity
                .ok(categoriaEventoService.getList().stream().map(x -> mapper.toDto(x)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEventoResponse> findCategoriaEvento(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDto(categoriaEventoService.findCategoriaEvento(id)));
    }

}