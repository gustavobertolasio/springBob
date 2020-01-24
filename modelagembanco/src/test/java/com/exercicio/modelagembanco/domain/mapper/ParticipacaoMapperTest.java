package com.exercicio.modelagembanco.domain.mapper;

import static org.junit.Assert.assertEquals;

import com.exercicio.modelagembanco.configuration.MapperConfig;
import com.exercicio.modelagembanco.domain.dto.request.ParticipacaoCreateRequest;
import com.exercicio.modelagembanco.domain.dto.request.ParticipacaoUpdateRequest;
import com.exercicio.modelagembanco.domain.dto.response.CategoriaEventoResponse;
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
public class ParticipacaoMapperTest {

    @Spy
    private ModelMapper modelMapper = new MapperConfig().getModelMapper();

    @InjectMocks
    private ParticipacaoMapper mapper;

    @Test
    public void should_ConvertParticipacaotToParticipacaoResponse() {
        Participacao entity = new Participacao(1, new Evento(), "loginParticipante", false, null, null);
        ParticipacaoResponse dto = mapper.toDto(entity);

        assertEquals("Unexpected value found!", dto.getComentario(), entity.getComentario());
        assertEquals("Unexpected value found!", dto.getEvento(), entity.getEvento());
        assertEquals("Unexpected value found!", dto.getFlagPresente(), entity.getFlagPresente());
        assertEquals("Unexpected value found!", dto.getIdParticipacao(), entity.getIdParticipacao());
        assertEquals("Unexpected value found!", dto.getLoginParticipante(), entity.getLoginParticipante());
        assertEquals("Unexpected value found!", dto.getNota(), entity.getNota());
    }

    @Test
    public void should_ConvertParticipacaoCreateRequestToParticipacao() {
        ParticipacaoCreateRequest entity = new ParticipacaoCreateRequest(1, "LoginParticipante");
        Participacao dto = mapper.fromDto(entity);


        assertEquals("Unexpected value found!", dto.getLoginParticipante(), entity.getLoginParticipante());
        
    }
    @Test
    public void should_ConvertParticipacaoUpdateRequestToParticipacao() {
        ParticipacaoUpdateRequest entity = new ParticipacaoUpdateRequest(null,null);
        Participacao dto = mapper.fromDtoUpdate(entity);

        assertEquals("Unexpected value found!", dto.getComentario(), entity.getComentario());
        assertEquals("Unexpected value found!", dto.getNota(), entity.getNota());
    }
}