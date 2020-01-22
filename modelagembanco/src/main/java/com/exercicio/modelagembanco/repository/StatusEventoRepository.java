package com.exercicio.modelagembanco.repository;

import org.springframework.stereotype.Repository;

import com.exercicio.modelagembanco.domain.entity.StatusEvento;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EventoRepository
 */
@Repository(value = "StatusEvento")
public interface StatusEventoRepository extends JpaRepository<StatusEvento, Integer> {

}