package com.exercicio.modelagembanco.service;

import java.util.List;

import java.util.Optional;
import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.exception.DataNotFoundException;
import com.exercicio.modelagembanco.repository.CategoriaEventoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class CategoriaEventoService {
    private final CategoriaEventoRepository categoriaEventoRepository;

    @Autowired
    public CategoriaEventoService(CategoriaEventoRepository categoriaEventoRepository) {
        this.categoriaEventoRepository = categoriaEventoRepository;
    }

    public List<CategoriaEvento> getList() {
        return categoriaEventoRepository.findAll();
    }

    public CategoriaEvento findCategoriaEvento(Integer id){
    Optional<CategoriaEvento> categoriaEvento = categoriaEventoRepository.findById(id);
        return categoriaEvento.orElseThrow(() -> new DataNotFoundException("Categoria not found"));
    }
}