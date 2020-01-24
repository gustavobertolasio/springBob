package com.exercicio.modelagembanco.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.StatusEvento;
import com.exercicio.modelagembanco.repository.CategoriaEventoRepository;
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
public class CategoriaEventoControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CategoriaEventoRepository repository;

    @Test
    public void should_getEmptyList_whenGetEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoriaevento")) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isOk()) // faz a validação.
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_getList_whenThingsInDB() throws Exception {
        repository.save(new CategoriaEvento(1, "Backend"));
        repository.save(new CategoriaEvento(2, "Frontend"));

        mockMvc.perform(MockMvcRequestBuilders.get("/categoriaevento")) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isOk()) // faz a validação.
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_get404_whenGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categoriaevento/1")) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isNotFound()); // faz a validação.
    }

    @Test
    public void should_getReturn_whenGetById() throws Exception {
        repository.save(new CategoriaEvento(1, "Backend"));

        mockMvc.perform(MockMvcRequestBuilders.get("/categoriaevento/1")) // Executa
                .andDo(MockMvcResultHandlers.print()) // pega resultado
                .andExpect(MockMvcResultMatchers.status().isOk()) // faz a validação.
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


}