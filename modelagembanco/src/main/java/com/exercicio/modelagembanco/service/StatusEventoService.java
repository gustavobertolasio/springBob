package com.exercicio.modelagembanco.service;

import java.util.List;

import java.util.Optional;
import com.exercicio.modelagembanco.domain.entity.StatusEvento;
import com.exercicio.modelagembanco.exception.DataNotFoundException;
import com.exercicio.modelagembanco.repository.StatusEventoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class StatusEventoService {
    private final StatusEventoRepository statusEventoRepository;

    @Autowired
    public StatusEventoService(StatusEventoRepository statusEventoRepository) {
        this.statusEventoRepository = statusEventoRepository;
    }

    public List<StatusEvento> getList() {
        return statusEventoRepository.findAll();
    }

    public StatusEvento findStatusEvento(Integer id){
    Optional<StatusEvento> categoriaEvento = statusEventoRepository.findById(id);
        return categoriaEvento.orElseThrow(() -> new DataNotFoundException("StatusEvento not found"));
    }
}