package com.exercicio.modelagembanco.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import com.exercicio.modelagembanco.domain.dto.request.EventoCreateRequest;
import com.exercicio.modelagembanco.domain.dto.request.EventoUpdateRequest;
import com.exercicio.modelagembanco.domain.dto.response.EventoResponse;
import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.domain.entity.StatusEvento;
import com.exercicio.modelagembanco.repository.CategoriaEventoRepository;
import com.exercicio.modelagembanco.repository.EventoRepository;
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
public class EventoControllerIntTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper mapper;

        @Autowired
        private EventoRepository repository;

        @Autowired
        private CategoriaEventoRepository categoriaEventoRepository;
        @Autowired
        private StatusEventoRepository statusEventoRepository;

        @Test
        public void should_getEmptyList_whenGetEmpty() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/evento")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isOk()) // faz a validação.
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void should_getList_whenThingsInDB() throws Exception {
                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                new Date(), "local", "descricao", 5));

                mockMvc.perform(MockMvcRequestBuilders.get("/evento")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isOk()) // faz a validação.
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void should_listEvento_whenGetByCategoria() throws Exception {

                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));

                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                new Date(), "local", "descricao", 5));

                mockMvc.perform(MockMvcRequestBuilders.get("/evento/categoria/1")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isOk()); // faz a validação.
        }

        @Test
        public void should_listEvento_whenGetDate() throws Exception {

                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));

                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                new Date(), "local", "descricao", 5));
                Date date = new Date();

                mockMvc.perform(MockMvcRequestBuilders.get("/evento/data").param("d", new Date().toString())
                                .content(mapper.writeValueAsString(date))) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isOk()); // faz a validação.
        }

        @Test
        public void should_get404_whenGetByCategoria() throws Exception {

                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));

                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                new Date(), "local", "descricao", 5));

                mockMvc.perform(MockMvcRequestBuilders.get("/evento/900000")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isNotFound()); // faz a validação.
        }

        @Test
        public void should_createParticipacao() throws Exception {
                statusEventoRepository.save(new StatusEvento(null, "Aberto para inscrições"));
                categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));
                Calendar data = Calendar.getInstance();
                data.setTime(new Date());
                data.set(data.get(Calendar.YEAR), data.get(Calendar.MONTH), data.get(Calendar.DAY_OF_MONTH) + 1);

                EventoCreateRequest part = new EventoCreateRequest(1, "nome", data.getTime(), data.getTime(), "local",
                                "descricao", 50);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/evento").contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(part)))
                                .andDo(MockMvcResultHandlers.print()).andReturn();

                EventoResponse response = mapper.readValue(result.getResponse().getContentAsString(),
                                EventoResponse.class);

                assertNotNull(response.getStatusEvento());
                assertNotNull(response.getCategoriaEvento());
                assertEquals(part.getDataHoraFim(), response.getDataHoraFim());
                assertEquals(part.getDataHoraInicio(), response.getDataHoraInicio());
                assertEquals(part.getDescricao(), response.getDescricao());
                assertEquals(part.getLimiteVagas(), response.getLimiteVagas());
                assertEquals(part.getLocal(), response.getLocal());
                assertEquals(part.getNome(), response.getNome());
                assertEquals(part.getIdCategoriaEvento(), response.getCategoriaEvento().getIdCategoriaEvento());
                assertEquals(1, (int) response.getStatusEvento().getIdEventoStatus());

        }

        @Test
        public void should_put() throws Exception {
                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                statusEventoRepository.save(new StatusEvento(null, "Em andamento"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));

                Calendar data2 = Calendar.getInstance();
                data2.setTime(new Date());
                data2.set(data2.get(Calendar.YEAR), data2.get(Calendar.MONTH), data2.get(Calendar.DAY_OF_MONTH),
                                data2.get(Calendar.HOUR_OF_DAY) + 4, data2.get(Calendar.MINUTE),
                                data2.get(Calendar.SECOND));

                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                data2.getTime(), "local", "descricao", 5));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                data2.getTime(), "local", "descricao", 5));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                data2.getTime(), "local", "descricao", 5));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                data2.getTime(), "local", "descricao", 5));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                data2.getTime(), "local", "descricao", 5));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                data2.getTime(), "local", "descricao", 5));

                EventoUpdateRequest eventoUpdateRequest = new EventoUpdateRequest(2, "nome", "local", "descricao", 20);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.put("/evento/5")
                                                .content(mapper.writeValueAsString(eventoUpdateRequest))
                                                .contentType(MediaType.APPLICATION_JSON)) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn(); // faz a validação.

                EventoResponse response = mapper.readValue(result.getResponse().getContentAsString(),
                                EventoResponse.class);

                assertEquals(eventoUpdateRequest.getDescricao(), response.getDescricao());
                assertEquals(eventoUpdateRequest.getIdStatusEvento(), response.getStatusEvento().getIdEventoStatus());
                assertEquals(eventoUpdateRequest.getLimiteVagas(), response.getLimiteVagas());
                assertEquals(eventoUpdateRequest.getLocal(), response.getLocal());
                assertEquals(eventoUpdateRequest.getNome(), response.getNome());

        }

        @Test
        public void should_delete() throws Exception {
                StatusEvento statusEvento = statusEventoRepository
                                .save(new StatusEvento(null, "Aberto para inscrições"));
                statusEventoRepository.save(new StatusEvento(null, "Em andamento"));
                statusEventoRepository.save(new StatusEvento(null, "Concluído"));
                statusEventoRepository.save(new StatusEvento(null, "Cancelado"));
                CategoriaEvento categoriaEvento = categoriaEventoRepository.save(new CategoriaEvento(null, "Backend"));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                new Date(), "local", "descricao", 5));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                new Date(), "local", "descricao", 5));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                new Date(), "local", "descricao", 5));
                repository.save(new Evento(null, statusEvento, categoriaEvento, "Nome do evento", new Date(),
                                new Date(), "local", "descricao", 5));

                mockMvc.perform(MockMvcRequestBuilders.delete("/evento/1")) // Executa
                                .andDo(MockMvcResultHandlers.print()) // pega resultado
                                .andExpect(MockMvcResultMatchers.status().isOk()); // faz a validação.
        }

}