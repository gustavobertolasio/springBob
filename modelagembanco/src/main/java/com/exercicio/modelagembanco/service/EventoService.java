package com.exercicio.modelagembanco.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.Optional;

import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.exception.DataNotFoundException;
import com.exercicio.modelagembanco.exception.DateEndException;
import com.exercicio.modelagembanco.exception.EventoDayException;
import com.exercicio.modelagembanco.exception.EventoNotExcludedException;
import com.exercicio.modelagembanco.exception.EventoNotUpdatedException;
import com.exercicio.modelagembanco.repository.EventoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;
    private final CategoriaEventoService categoriaEventoService;
    private final StatusEventoService statusEventoService;
    // private final ParticipacaoService participacaoService;
    Date diaAtual = new Date();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    public EventoService(EventoRepository eventoRepository, StatusEventoService statusEventoService,
            CategoriaEventoService categoriaEventoService) {
        this.eventoRepository = eventoRepository;
        this.statusEventoService = statusEventoService;
        this.categoriaEventoService = categoriaEventoService;

        // this.participacaoService = participacaoService;
    }

    public List<Evento> getList() {
        return eventoRepository.findAll();
    }

    public Evento findEvento(Integer id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.orElseThrow(() -> new DataNotFoundException("Evento not found"));
    }

    public List<Evento> findEventosByCategoria(Integer id) {
        return eventoRepository.findBycategoriaEvento(categoriaEventoService.findCategoriaEvento(id));
    }

    public List<Evento> findByDate(String data) throws ParseException {
        String dataEmString = data.substring(0, 2) + "/" + data.substring(2, 4) + "/" + data.substring(4);
        return eventoRepository.findByDate(dataEmString);
    }

    public Evento saveEvento(Evento evento, Integer idCategoriaEvento) {

        Calendar dataHoraInicio = Calendar.getInstance();
        dataHoraInicio.setTime(evento.getDataHoraInicio());

        Calendar dataHoraFim = Calendar.getInstance();
        dataHoraFim.setTime(evento.getDataHoraFim());

        if (evento.getDataHoraInicio().getTime() > evento.getDataHoraFim().getTime())
            throw new DateEndException("Data fim menor que data início");

        if (!(df.format(dataHoraFim.getTime()).equals(df.format(dataHoraInicio.getTime()))))
            throw new DateEndException("O evento precisa terminar no mesmo dia");

        evento.setStatusEvento(statusEventoService.findStatusEvento(1));
        evento.setCategoriaEvento(categoriaEventoService.findCategoriaEvento(idCategoriaEvento));

        return eventoRepository.save(evento);
    }

    public Evento updateEvento(Evento evento, Integer id, Integer idStatusEvento) {
        Evento auxEvento = findEvento(id);

        if (!(df.format(diaAtual).equals(df.format(auxEvento.getDataHoraInicio()))) && idStatusEvento == 2) {
            throw new EventoDayException("Não é possível iniciar o evento fora do dia de início");
        }
        if ((df.format(diaAtual).equals(df.format(auxEvento.getDataHoraInicio()))) && idStatusEvento == 4) {
            throw new EventoDayException("Não é possível cancelar o evento no dia de início");
        }
        if ((auxEvento.getStatusEvento().getIdEventoStatus() != 2) && idStatusEvento == 3) {
            throw new EventoNotUpdatedException("Só é possível concluir o evento quando ele está em andamento");
        }

        auxEvento.setDescricao(evento.getDescricao());
        auxEvento.setLocal(evento.getLocal());
        auxEvento.setLimiteVagas(evento.getLimiteVagas());
        auxEvento.setStatusEvento(statusEventoService.findStatusEvento(idStatusEvento));
        auxEvento.setNome(evento.getNome());

        return eventoRepository.save(auxEvento);
    }

    public Evento deleteEvento(Integer id) {

        Evento evento = findEvento(id);
        Integer participacoes = eventoRepository.countParticipacoesInEvento(id);

        if (participacoes > 0)
            throw new EventoNotExcludedException("Evento já com participação");

        evento.setStatusEvento(statusEventoService.findStatusEvento(4));
        return eventoRepository.save(evento);
    }

}