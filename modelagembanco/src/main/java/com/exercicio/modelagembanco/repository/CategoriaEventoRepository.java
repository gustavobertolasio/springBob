package com.exercicio.modelagembanco.repository;

import org.springframework.stereotype.Repository;

import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * EventoRepository
 */
@Repository(value = "CategoriaEvento")
public interface CategoriaEventoRepository extends JpaRepository<CategoriaEvento, Integer> {

    
}