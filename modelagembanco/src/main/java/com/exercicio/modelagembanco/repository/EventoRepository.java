package com.exercicio.modelagembanco.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.Evento;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EventoRepository
 */
@Repository(value = "Evento")
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    List<Evento> findBycategoriaEvento(CategoriaEvento categoria);

}