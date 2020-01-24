package com.exercicio.modelagembanco.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.domain.entity.Participacao;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EventoRepository
 */
@Repository(value = "Participacao")
public interface ParticipacaoRepository extends JpaRepository<Participacao, Integer> {

    List<Participacao> findByEvento(Evento evento); 

    Integer countByEvento(Evento evento);
}