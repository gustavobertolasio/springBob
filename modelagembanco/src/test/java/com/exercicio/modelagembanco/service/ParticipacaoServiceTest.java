package com.exercicio.modelagembanco.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.domain.entity.Participacao;
import com.exercicio.modelagembanco.domain.entity.StatusEvento;
import com.exercicio.modelagembanco.exception.ComentarioNotUpdatedException;
import com.exercicio.modelagembanco.exception.DataNotFoundException;
import com.exercicio.modelagembanco.exception.ParticipacaoNotCreatedException;
import com.exercicio.modelagembanco.repository.EventoRepository;
import com.exercicio.modelagembanco.repository.ParticipacaoRepository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * ClientServiceTest
 */
@RunWith(MockitoJUnitRunner.class)
public class ParticipacaoServiceTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Mock
    private ParticipacaoRepository repositoryMock;

    @Mock
    private EventoRepository eventoRepositoryMock;

    @Mock
    private EventoService eventoServiceMock;

    @InjectMocks
    private ParticipacaoService service;

    StatusEvento status = new StatusEvento(2, "Some string");
    CategoriaEvento cateEvento = new CategoriaEvento(1, "Some string");
    Evento evento = new Evento(1, status, cateEvento, "Oi", new Date(), new Date(), "oi", "oi", 1);
    Participacao participacao = new Participacao(1, evento, "", false, null, null);

    @Test
    public void should_getList() {
        List<Participacao> partList = new ArrayList<>();
        partList.add(participacao);

        when(repositoryMock.findAll()).thenReturn(partList);

        List<Participacao> list = service.getList();

        verify(repositoryMock, times(1)).findAll();
        assertNotNull(list);
        assertEquals("Array deve ser de tamanho 1", 1, list.size());
    }

    @Test
    public void should_findParticipacao_WhenFindingById() {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(participacao));

        Participacao part = service.findParticipacao(anyInt());

        verify(repositoryMock, times(1)).findById(anyInt());
        assertNotNull(part);
    }

    @Test
    public void should_ThrowDataNotFound_WhenFindingById() {

        expected.expect(DataNotFoundException.class);
        expected.expectMessage("Participacao not found");

        service.findParticipacao(anyInt());
    }

    @Test
    public void should_DeleteParticipacao_WhenDeleting() {
        service.deleteParticipacao(anyInt());
        verify(repositoryMock, times(1)).deleteById(anyInt());
    }

    @Test
    public void should_ThrowDataNotFoundException_WhenDeleting() {
        doThrow(new DataNotFoundException("Participacao not found")).when(repositoryMock).deleteById(anyInt());

        expected.expect(DataNotFoundException.class);
        expected.expectMessage("Participacao not found");

        service.deleteParticipacao(anyInt());
    }

    @Test
    public void should_ThrowDataNotFoundException_WhenSavingParticipacaoInANonExistingEvento() {
        doThrow(new DataNotFoundException("Evento not found")).when(eventoServiceMock).findEvento(anyInt());

        expected.expect(DataNotFoundException.class);
        expected.expectMessage("Evento not found");

        service.saveParticipacao(participacao, anyInt());
    }

    @Test
    public void should_ThrowParticipacaoNotCreatedException_WhenSavingParticipacaoWithExceededLimiteVagas() {

        when(eventoServiceMock.findEvento(anyInt())).thenReturn(evento);
        when(repositoryMock.countByEvento(evento)).thenReturn(1);

        expected.expect(ParticipacaoNotCreatedException.class);
        expected.expectMessage("Não há mais vagas nesse evento");

        service.saveParticipacao(participacao, anyInt());
    }

    @Test
    public void should_SaveParticipacao_WhenSavingParticipacao() {
        // List<Participacao> partList = new ArrayList<>();
        when(repositoryMock.countByEvento(evento)).thenReturn(0);
        when(eventoServiceMock.findEvento(anyInt())).thenReturn(evento);
        when(repositoryMock.save(participacao)).thenReturn(participacao);

        Participacao part = service.saveParticipacao(participacao, anyInt());

        verify(repositoryMock, times(1)).save(participacao);
        assertNotNull(part);
    }
    @Test
    public void should_FindParticipacoesByIdEvento() {
         List<Participacao> partList = new ArrayList<>();
         partList.add(participacao);
        when(eventoServiceMock.findEvento(anyInt())).thenReturn(evento);
        when(repositoryMock.findByEvento(evento)).thenReturn(partList);

        List<Participacao> part = service.findParticipacoesByEventoId(anyInt());

        verify(repositoryMock, times(1)).findByEvento(evento);
        assertNotNull(part);
        assertEquals(1, part.size());
    }

  

    @Test
    public void should_throwComentarioNotUpdatedExceptionLastTime_WhenFlagPresenteNotTrue() {
        Participacao part = new Participacao(1, new Evento(), "loginParticipante", false, 8, null);
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(part));

        expected.expect(ComentarioNotUpdatedException.class);
        expected.expectMessage("Só é possível comentar ou dar nota após o administrador marcar sua presença");

        service.updateParticipacao(part, 1);
    }

    @Test
    public void should_UpdateFlagParticipacao_WhenFlagPresenteNotTrue() {
        Participacao part = new Participacao(1, new Evento(), "loginParticipante", false, null, null);
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(part));
        when(repositoryMock.save(part)).thenReturn(part);

        Participacao aux = service.flagParticipacao(anyInt());
        assertNotNull(aux);
        assertTrue(aux.getFlagPresente());
        verify(repositoryMock, times(1)).save(aux);
    }

    @Test
    public void should_UpdateParticipacao_WhenFlagPresenteIsTrue() {
        // participacao.setFlagPresente(true);
        Participacao part = new Participacao(1, new Evento(), "loginParticipante", true, 9, "boa");
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(part));
        when(repositoryMock.save(part)).thenReturn(part);

        Participacao aux = service.updateParticipacao(part, 1);
        assertNotNull(aux);
        verify(repositoryMock, times(1)).save(aux);
    }

    @Test
    public void should_ReturnCountOfParticipacoes() {
        when(repositoryMock.countByEvento(evento)).thenReturn(anyInt());

        Integer count = service.countParticipacoes(evento);

        assertNotNull(count);
        verify(repositoryMock, times(1)).countByEvento(evento);

    }
}