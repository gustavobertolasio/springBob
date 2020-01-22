package com.exercicio.modelagembanco.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.exercicio.modelagembanco.domain.entity.StatusEvento;
import com.exercicio.modelagembanco.exception.DataNotFoundException;
import com.exercicio.modelagembanco.repository.StatusEventoRepository;

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
public class StatusEventoServiceTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Mock
    private StatusEventoRepository repositoryMock;

    @InjectMocks
    private StatusEventoService service;

    StatusEvento status = new StatusEvento(1, "Some string");

    @Test
    public void should_getList() {
        List<StatusEvento> evList = new ArrayList<>();
        evList.add(status);

        when(repositoryMock.findAll()).thenReturn(evList);

        List<StatusEvento> list = service.getList();

        verify(repositoryMock, times(1)).findAll();
        assertNotNull(list);
        assertEquals("Array deve ser de tamanho 1", 1, list.size());
    }

    @Test
    public void should_findStatusEvento_WhenFindingById() {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(status));

        StatusEvento staEv = service.findStatusEvento(anyInt());

        verify(repositoryMock, times(1)).findById(anyInt());
        assertNotNull(staEv);
    }

    @Test
    public void should_ThrowDataNotFound_WhenFindingById() {

        expected.expect(DataNotFoundException.class);
        expected.expectMessage("StatusEvento not found");

        service.findStatusEvento(anyInt());
    }

}