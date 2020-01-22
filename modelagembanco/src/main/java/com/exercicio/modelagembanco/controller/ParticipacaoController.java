package com.exercicio.modelagembanco.controller;

import javax.validation.Valid;

import com.exercicio.modelagembanco.domain.dto.request.ParticipacaoCreateRequest;
import com.exercicio.modelagembanco.domain.dto.request.ParticipacaoUpdateRequest;
import com.exercicio.modelagembanco.domain.dto.response.ParticipacaoResponse;
import com.exercicio.modelagembanco.domain.entity.Participacao;
import com.exercicio.modelagembanco.domain.mapper.ParticipacaoMapper;
import com.exercicio.modelagembanco.service.ParticipacaoService;

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
@RequestMapping("/participacao")
public class ParticipacaoController {

    private final ParticipacaoService participacaoService;
    private final ParticipacaoMapper mapper;

    @Autowired
    public ParticipacaoController(ParticipacaoService participacaoService, ParticipacaoMapper mapper) {
        this.participacaoService = participacaoService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ParticipacaoResponse> postParticipacao(@Valid @RequestBody ParticipacaoCreateRequest entity) {
        Participacao participacao = mapper.fromDto(entity);
        return ResponseEntity
                .ok(mapper.toDto(participacaoService.saveParticipacao(participacao, entity.getIdEvento())));
    }

    @PutMapping
    public ResponseEntity<ParticipacaoResponse> putParticipacao(@Valid @PathVariable Integer id,
            @Valid @RequestBody ParticipacaoUpdateRequest entity) {
        Participacao participacao = mapper.fromDtoUpdate(entity);
        return ResponseEntity.ok(mapper.toDto(participacaoService.updateParticipacao(participacao, id)));
    }

    @GetMapping
    public ResponseEntity<ParticipacaoResponse> findEventoId(@Valid @RequestParam Integer id) {
        return ResponseEntity.ok(mapper.toDto(participacaoService.findParticipacao(id)));
    }

    @DeleteMapping(value = "/{id}")
    public String deleteEvento(@Valid @PathVariable Integer id) {
        participacaoService.findParticipacao(id);
        participacaoService.deleteParticipacao(id);
        return "Deletado com sucesso";
    }

}