package com.exercicio.modelagembanco.domain.mapper;

import static org.junit.Assert.assertEquals;

import com.exercicio.modelagembanco.configuration.MapperConfig;
import com.exercicio.modelagembanco.domain.dto.response.StatusEventoResponse;
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
public class StatusEventoMapperTest {

    @Spy
    private ModelMapper modelMapper = new MapperConfig().getModelMapper();

    @InjectMocks
    private StatusEventoMapper mapper;

    @Test
    public void shouldConvertClientToClientResponse() {
        StatusEvento entity = new StatusEvento(1, "Some string");
        StatusEventoResponse dto = mapper.toDto(entity);
      
        assertEquals("Unexpected value found!", dto.getNomeStatus(), entity.getNomeStatus());
        //assertEquals("Unexpected value found!", dto.getIdEventoStatus(), entity.getIdEventoStatus());
    }  
} 