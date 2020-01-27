package com.exercicio.modelagembanco.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import com.exercicio.modelagembanco.domain.dto.request.ParticipacaoCreateRequest;
import com.exercicio.modelagembanco.domain.dto.request.ParticipacaoUpdateRequest;
import com.exercicio.modelagembanco.domain.dto.response.ParticipacaoResponse;
import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.domain.entity.Participacao;
import com.exercicio.modelagembanco.domain.entity.StatusEvento;
import com.exercicio.modelagembanco.repository.CategoriaEventoRepository;
import com.exercicio.modelagembanco.repository.EventoRepository;
import com.exercicio.modelagembanco.repository.ParticipacaoRepository;
import com.exercicio.modelagembanco.repository.StatusEventoRepository;
import com.exercicio.modelagembanco.utils.IntegrationTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * StatusEventoControllerIntTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = IntegrationTestConfig.appProperties)
@ActiveProfiles("test")
public class ParticipacaoControllerIntTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper mapper;

        @Autowired
        private ParticipacaoRepository repository;
        
        @Autowired
        private EventoRepository eventoRepository;
        @Autowired
        private CategoriaEventoRepository categoriaEventoRepository;
        @Autowired
        private StatusEventoRepository statusEventoRepository;

        @Test
        public void should_get404_whenGetById() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/participacao/9000")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isNotFound()); // faz a validação.
        }

        @Test
        public void should_get404_whenDeleting() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/participacao/9000")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isNotFound()); // faz a validação.
        }

        @Test
        public void should_delete() throws Exception {
                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));
                Evento evento = eventoRepository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento",
                                new Date(), new Date(), "local", "descricao", 5));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));
                mockMvc.perform(MockMvcRequestBuilders.delete("/participacao/3")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isOk()); // faz a validação.
        }

        @Test
        public void should_getReturn_whenGetById() throws Exception {
                StatusEvento statusEvento = statusEventoRepository.save(new StatusEvento(1, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(1, "Backend"));
                Evento evento = eventoRepository.save(new Evento(1, statusEvento, categoriaEvento, "Nome do evento",
                                new Date(), new Date(), "local", "descricao", 5));
                repository.save(new Participacao(1, evento, "loginParticipante", false, null, null));
                repository.save(new Participacao(2, evento, "loginParticipante", false, null, null));

                mockMvc.perform(MockMvcRequestBuilders.get("/participacao/1")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isOk()) // faz a validação.
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void should_get405_whenUpdateParticipacaoWithFlagFalse() throws Exception {
                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));
                Evento evento = eventoRepository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento",
                                new Date(), new Date(), "local", "descricao", 5));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));
                repository.save(new Participacao(null, evento, "loginParticipante", false, null, null));

                ParticipacaoUpdateRequest updateRequest = new ParticipacaoUpdateRequest(9, "Oi");

                mockMvc.perform(MockMvcRequestBuilders.put("/participacao/comentarioenota/7")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updateRequest))).andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
        }

        @Test
        public void should_updateParticipacao() throws Exception {
                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));
                Evento evento = eventoRepository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento",
                                new Date(), new Date(), "local", "descricao", 5));
                Participacao part = repository
                                .save(new Participacao(null, evento, "loginParticipante", false, null, null));

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.put("/participacao/1")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print()).andReturn();

                ParticipacaoResponse response = mapper.readValue(result.getResponse().getContentAsString(),
                                ParticipacaoResponse.class);

                assertNotNull(response.getIdParticipacao());
                assertNull(response.getNota());
                assertNull(response.getComentario());
                assertTrue(response.getFlagPresente());
                assertEquals(part.getLoginParticipante(), response.getLoginParticipante());
                assertNotNull(response.getEvento());
        }

        @Test
        public void should_updateComentarioENota() throws Exception {
                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));
                Evento evento = eventoRepository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento",
                                new Date(), new Date(), "local", "descricao", 5));
                Participacao part = repository
                                .save(new Participacao(null, evento, "loginParticipante", true, null, null));

                ParticipacaoUpdateRequest updateRequest = new ParticipacaoUpdateRequest(9, "Oi");

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.put("/participacao/comentarioenota/1")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(updateRequest)))
                                .andDo(MockMvcResultHandlers.print()).andReturn();

                System.out.println(result.getResponse().getContentAsString());

                ParticipacaoResponse response = mapper.readValue(result.getResponse().getContentAsString(),
                                ParticipacaoResponse.class);

                assertNotNull(response.getIdParticipacao());
                assertNotNull(response.getNota());
                assertNotNull(response.getComentario());
                assertTrue(response.getFlagPresente());
                assertEquals(part.getLoginParticipante(), response.getLoginParticipante());
                assertNotNull(response.getEvento());
        }

        @Test
        public void should_createParticipacao() throws Exception {
                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));
                Evento evento = eventoRepository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento",
                                new Date(), new Date(), "local", "descricao", 5));
                ParticipacaoCreateRequest part = new ParticipacaoCreateRequest(evento.getIdEvento(),
                                "loginParticipante");

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/participacao")
                                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(part)))
                                .andDo(MockMvcResultHandlers.print()).andReturn();

                ParticipacaoResponse response = mapper.readValue(result.getResponse().getContentAsString(),
                                ParticipacaoResponse.class);
                assertNotNull(response.getIdParticipacao());
                assertNull(response.getNota());
                assertNull(response.getComentario());
                assertFalse(response.getFlagPresente());
                assertEquals(part.getLoginParticipante(), response.getLoginParticipante());
                assertEquals(part.getIdEvento(), response.getEvento().getIdEvento());
        }

}