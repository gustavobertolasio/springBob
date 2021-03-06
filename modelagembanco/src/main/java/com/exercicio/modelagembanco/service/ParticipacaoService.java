package com.exercicio.modelagembanco.service;

import java.util.List;

import java.util.Optional;

import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.domain.entity.Participacao;
import com.exercicio.modelagembanco.exception.ComentarioNotUpdatedException;
import com.exercicio.modelagembanco.exception.DataNotFoundException;
import com.exercicio.modelagembanco.exception.ParticipacaoNotCreatedException;
import com.exercicio.modelagembanco.repository.ParticipacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercicio.modelagembanco.service.EventoService;

@Service
public class ParticipacaoService {
    private final ParticipacaoRepository participacaoRepository;
    private final EventoService eventoService;

    @Autowired
    public ParticipacaoService(ParticipacaoRepository participacaoRepository, EventoService eventoService) {
        this.participacaoRepository = participacaoRepository;
        this.eventoService = eventoService;
    }

    public List<Participacao> getList() {
        return participacaoRepository.findAll();
    }

    public Participacao findParticipacao(Integer id) {
        Optional<Participacao> participacao = participacaoRepository.findById(id);
        return participacao.orElseThrow(() -> new DataNotFoundException("Participacao not found"));
    }

    public List<Participacao> findParticipacoesByEventoId(Integer id) {
        return participacaoRepository.findByEvento(eventoService.findEvento(id));
    }

    public Participacao saveParticipacao(Participacao participacao, Integer idEvento) {
        Evento event = eventoService.findEvento(idEvento);
        // List<Participacao> participacoesEvento =
        // participacaoRepository.findByEvento(event);
        Integer participacoesEvento = participacaoRepository.countByEvento(event);

        if (event.getLimiteVagas() <= participacoesEvento)
            throw new ParticipacaoNotCreatedException("Não há mais vagas nesse evento");

        participacao.setEvento(event);
        participacao.setFlagPresente(false);
        return participacaoRepository.save(participacao);
    }

    public void deleteParticipacao(Integer id) {
        try {
            participacaoRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataNotFoundException("Participacao not found");
        }
    }

    public Participacao updateParticipacao(Participacao participacao, Integer idParticipacao) {
        Participacao auxParticipacao = findParticipacao(idParticipacao);

        if (auxParticipacao.getFlagPresente() == false)
                   throw new ComentarioNotUpdatedException("Só é possível comentar ou dar nota após o administrador marcar sua presença");
        

        auxParticipacao.setComentario(participacao.getComentario());
        auxParticipacao.setNota(participacao.getNota());
        return participacaoRepository.save(auxParticipacao);
    }

    public Participacao flagParticipacao(Integer idParticipacao) {
        Participacao auxParticipacao = findParticipacao(idParticipacao);
        auxParticipacao.setFlagPresente(true);
        return participacaoRepository.save(auxParticipacao);
    }

    public Integer countParticipacoes(Evento evento) {
        return participacaoRepository.countByEvento(evento);
    }
}