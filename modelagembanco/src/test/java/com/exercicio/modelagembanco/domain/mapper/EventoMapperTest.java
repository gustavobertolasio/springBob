package com.exercicio.modelagembanco.domain.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.exercicio.modelagembanco.configuration.MapperConfig;
import com.exercicio.modelagembanco.domain.dto.request.EventoCreateRequest;
import com.exercicio.modelagembanco.domain.dto.request.EventoUpdateRequest;
import com.exercicio.modelagembanco.domain.dto.request.ParticipacaoCreateRequest;
import com.exercicio.modelagembanco.domain.dto.request.ParticipacaoUpdateRequest;
import com.exercicio.modelagembanco.domain.dto.response.CategoriaEventoResponse;
import com.exercicio.modelagembanco.domain.dto.response.EventoResponse;
import com.exercicio.modelagembanco.domain.dto.response.ParticipacaoResponse;
import com.exercicio.modelagembanco.domain.dto.response.StatusEventoResponse;
import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.domain.entity.Participacao;
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
public class EventoMapperTest {

    @Spy
    private ModelMapper modelMapper = new MapperConfig().getModelMapper();

    @InjectMocks
    private EventoMapper mapper;

    @Test
    public void should_ConvertEventotToEventoResponse() {
        Evento entity = new Evento(1, new StatusEvento(), new CategoriaEvento(), "nome", new Date(), new Date(), "local", "descricao", 5);
        EventoResponse dto = mapper.toDto(entity);

        assertEquals("Unexpected value found!", dto.getCategoriaEvento(), entity.getCategoriaEvento());
        assertEquals("Unexpected value found!", dto.getDataHoraFim(), entity.getDataHoraFim());
        assertEquals("Unexpected value found!", dto.getDataHoraInicio(), entity.getDataHoraInicio());
        assertEquals("Unexpected value found!", dto.getDescricao(), entity.getDescricao());
        assertEquals("Unexpected value found!", dto.getLimiteVagas(), entity.getLimiteVagas());
        assertEquals("Unexpected value found!", dto.getLocal(), entity.getLocal());
        assertEquals("Unexpected value found!", dto.getNome(), entity.getNome());
        assertEquals("Unexpected value found!", dto.getStatusEvento(), entity.getStatusEvento());
    }

    @Test
    public void should_ConvertEventoCreateRequestToEvento() {
        EventoCreateRequest entity = new EventoCreateRequest(4, "nome", new Date(), new Date(), "local", "descricao", 50);
        Evento dto = mapper.fromDto(entity);

      //  assertEquals("Unexpected value found!", dto.getCategoriaEvento().getIdCategoriaEvento()entity.getCategoriaEvento());
        assertEquals("Unexpected value found!", dto.getDataHoraFim(), entity.getDataHoraFim());
        assertEquals("Unexpected value found!", dto.getDataHoraInicio(), entity.getDataHoraInicio());
        assertEquals("Unexpected value found!", dto.getDescricao(), entity.getDescricao());
        assertEquals("Unexpected value found!", dto.getLimiteVagas(), entity.getLimiteVagas());
        assertEquals("Unexpected value found!", dto.getLocal(), entity.getLocal());
        assertEquals("Unexpected value found!", dto.getNome(), entity.getNome());
       // assertEquals("Unexpected value found!", dto.getStatusEvento(), entity.getStatusEvento());
    }
    @Test
    public void should_ConvertParticipacaoUpdateRequestToParticipacao() {
        EventoUpdateRequest entity = new EventoUpdateRequest(1, "nome", "local", "descricao", 5);
        Evento dto = mapper.fromDtoUpdate(entity);

        assertEquals("Unexpected value found!", dto.getDescricao(), entity.getDescricao());
       // assertEquals("Unexpected value found!", dto.get(), entity.getIdStatusEvento());
        assertEquals("Unexpected value found!", dto.getLimiteVagas(), entity.getLimiteVagas());
        assertEquals("Unexpected value found!", dto.getLocal(), entity.getLocal());
        assertEquals("Unexpected value found!", dto.getNome(), entity.getNome());
    }
}