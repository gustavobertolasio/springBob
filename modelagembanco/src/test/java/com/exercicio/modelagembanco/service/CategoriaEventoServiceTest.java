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

import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.exception.DataNotFoundException;
import com.exercicio.modelagembanco.repository.CategoriaEventoRepository;

import org.junit.BeforeClass;
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
public class CategoriaEventoServiceTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Mock
    private CategoriaEventoRepository repositoryMock;

    @InjectMocks
    private CategoriaEventoService service;

    static List<CategoriaEvento> listaCategoriaEvento;
    static CategoriaEvento evento;
    @BeforeClass
    public static void setUp(){
    listaCategoriaEvento = new ArrayList<>();
    CategoriaEvento cate = new CategoriaEvento(1, "Aberto para inscrições");
    listaCategoriaEvento.add(cate);
    cate = new CategoriaEvento(2, "Em andamento");
    listaCategoriaEvento.add(cate);
    cate = new CategoriaEvento(3, "Concluído");
    listaCategoriaEvento.add(cate);
    cate = new CategoriaEvento(1, "Cancelado");
    listaCategoriaEvento.add(cate);
    evento = listaCategoriaEvento.get(1);
    }

    @Test
    public void should_getCategoriaEventoList() {

        // given
        when(repositoryMock.findAll()).thenReturn(listaCategoriaEvento);


        // when
       List<CategoriaEvento> list = service.getList();

        // then
        verify(repositoryMock, times(1)).findAll();
        assertEquals(list, listaCategoriaEvento);        
    }

    @Test
    public void should_BeValid_WhenGetCategoriaEventoById(){
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));

        CategoriaEvento cateEvento = service.findCategoriaEvento(anyInt());

        verify(repositoryMock, times(1)).findById(anyInt());
        assertNotNull(cateEvento);    
    }
    @Test
    public void should_ThrowDataNotFoundException_WhenGetCategoriaEventoById(){
        
        expected.expect(DataNotFoundException.class);
        expected.expectMessage("Categoria not found");   

        service.findCategoriaEvento(6);
    }    
}