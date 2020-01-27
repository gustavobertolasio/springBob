package com.exercicio.modelagembanco.repository;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.Evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * EventoRepository
 */
@Repository(value = "Evento")
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    List<Evento> findBycategoriaEvento(CategoriaEvento categoria);

    @Query(nativeQuery = true, value = "select a.IdEvento, a.IdEventoStatus, a.IdCategoriaEvento,a.Nome, a.DataHoraInicio, a.DataHoraFim, a.Local, a.Descricao, a.LimiteVagas from evento a where convert(varchar, a.DataHoraInicio, 103) = convert(varchar, ?1, 103)")
    List<Evento> findByDate(Date data);

    @Query(nativeQuery = true, value = "select count(*) from participacao p inner join evento e on e.idEvento = p.idEvento where p.IdEvento = ?1")
    Integer countParticipacoesInEvento(Integer idEvento);

}