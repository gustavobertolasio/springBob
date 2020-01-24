package com.exercicio.modelagembanco.domain.mapper;

import static org.junit.Assert.assertEquals;

import com.exercicio.modelagembanco.configuration.MapperConfig;
import com.exercicio.modelagembanco.domain.dto.response.CategoriaEventoResponse;
import com.exercicio.modelagembanco.domain.dto.response.StatusEventoResponse;
import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.StatusEvento;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

/**
 * ClientMapperTest
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoriaEventoMapperTest {

    @Spy
    private ModelMapper modelMapper = new MapperConfig().getModelMapper();

    @InjectMocks
    private CategoriaEventoMapper mapper;

    @Test
    public void shouldConvertClientToClientResponse() {
        CategoriaEvento entity = new CategoriaEvento(1, "Some string");
        CategoriaEventoResponse dto = mapper.toDto(entity);
      
        assertEquals("Unexpected value found!", dto.getNomeCategoria(), entity.getNomeCategoria());
        //assertEquals("Unexpected value found!", dto.getIdCategoriaEvento(), entity.getIdCategoriaEvento());
    }  
} 