package com.exercicio.modelagembanco.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.exercicio.modelagembanco.exception.DataNotFoundException;
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
    Evento evento = new Evento(1, status, cateEvento, "Oi", new Date(), new Date(), "oi", "oi", 10);
    Participacao participacao = new Participacao(1, evento, "", false, null, "");

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
    public void should_findStatusEvento_WhenFindingById() {
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
    public void should_DeleteParticipacao_WhenDeleting(){
        service.deleteParticipacao(anyInt());
    }
    @Test
    public void should_ThrowDataNotFoundException_WhenDeleting(){
        doThrow(new DataNotFoundException("Participacao not found")).when(repositoryMock).deleteById(anyInt());

        expected.expect( DataNotFoundException.class);
        expected.expectMessage("Participacao not found");
        
        service.deleteParticipacao(anyInt());
    }
    @Test
    public void should_ThrowDataNotFoundException_WhenSavingParticipacaoInANonExistingEvento(){
      doThrow(new DataNotFoundException("Evento not found")).when(eventoServiceMock).findEvento(anyInt());

        expected.expect(DataNotFoundException.class);
        expected.expectMessage("Evento not found");
        
        service.saveParticipacao(participacao, anyInt());
    }
}