package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.EstadisticasJugadorPartido;
import com.mycompany.myapp.repository.EstadisticasJugadorPartidoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EstadisticasJugadorPartidoResource REST controller.
 *
 * @see EstadisticasJugadorPartidoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EstadisticasJugadorPartidoResourceTest {


    private static final Integer DEFAULT_CANASTAS = 1;
    private static final Integer UPDATED_CANASTAS = 2;

    private static final Integer DEFAULT_ASISTENCIAS = 1;
    private static final Integer UPDATED_ASISTENCIAS = 2;

    private static final Integer DEFAULT_FALTAS = 1;
    private static final Integer UPDATED_FALTAS = 2;

    @Inject
    private EstadisticasJugadorPartidoRepository estadisticasJugadorPartidoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restEstadisticasJugadorPartidoMockMvc;

    private EstadisticasJugadorPartido estadisticasJugadorPartido;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EstadisticasJugadorPartidoResource estadisticasJugadorPartidoResource = new EstadisticasJugadorPartidoResource();
        ReflectionTestUtils.setField(estadisticasJugadorPartidoResource, "estadisticasJugadorPartidoRepository", estadisticasJugadorPartidoRepository);
        this.restEstadisticasJugadorPartidoMockMvc = MockMvcBuilders.standaloneSetup(estadisticasJugadorPartidoResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        estadisticasJugadorPartido = new EstadisticasJugadorPartido();
        estadisticasJugadorPartido.setCanastas(DEFAULT_CANASTAS);
        estadisticasJugadorPartido.setAsistencias(DEFAULT_ASISTENCIAS);
        estadisticasJugadorPartido.setFaltas(DEFAULT_FALTAS);
    }

    @Test
    @Transactional
    public void createEstadisticasJugadorPartido() throws Exception {
        int databaseSizeBeforeCreate = estadisticasJugadorPartidoRepository.findAll().size();

        // Create the EstadisticasJugadorPartido

        restEstadisticasJugadorPartidoMockMvc.perform(post("/api/estadisticasJugadorPartidos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estadisticasJugadorPartido)))
                .andExpect(status().isCreated());

        // Validate the EstadisticasJugadorPartido in the database
        List<EstadisticasJugadorPartido> estadisticasJugadorPartidos = estadisticasJugadorPartidoRepository.findAll();
        assertThat(estadisticasJugadorPartidos).hasSize(databaseSizeBeforeCreate + 1);
        EstadisticasJugadorPartido testEstadisticasJugadorPartido = estadisticasJugadorPartidos.get(estadisticasJugadorPartidos.size() - 1);
        assertThat(testEstadisticasJugadorPartido.getCanastas()).isEqualTo(DEFAULT_CANASTAS);
        assertThat(testEstadisticasJugadorPartido.getAsistencias()).isEqualTo(DEFAULT_ASISTENCIAS);
        assertThat(testEstadisticasJugadorPartido.getFaltas()).isEqualTo(DEFAULT_FALTAS);
    }

    @Test
    @Transactional
    public void getAllEstadisticasJugadorPartidos() throws Exception {
        // Initialize the database
        estadisticasJugadorPartidoRepository.saveAndFlush(estadisticasJugadorPartido);

        // Get all the estadisticasJugadorPartidos
        restEstadisticasJugadorPartidoMockMvc.perform(get("/api/estadisticasJugadorPartidos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(estadisticasJugadorPartido.getId().intValue())))
                .andExpect(jsonPath("$.[*].canastas").value(hasItem(DEFAULT_CANASTAS)))
                .andExpect(jsonPath("$.[*].asistencias").value(hasItem(DEFAULT_ASISTENCIAS)))
                .andExpect(jsonPath("$.[*].faltas").value(hasItem(DEFAULT_FALTAS)));
    }

    @Test
    @Transactional
    public void getEstadisticasJugadorPartido() throws Exception {
        // Initialize the database
        estadisticasJugadorPartidoRepository.saveAndFlush(estadisticasJugadorPartido);

        // Get the estadisticasJugadorPartido
        restEstadisticasJugadorPartidoMockMvc.perform(get("/api/estadisticasJugadorPartidos/{id}", estadisticasJugadorPartido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(estadisticasJugadorPartido.getId().intValue()))
            .andExpect(jsonPath("$.canastas").value(DEFAULT_CANASTAS))
            .andExpect(jsonPath("$.asistencias").value(DEFAULT_ASISTENCIAS))
            .andExpect(jsonPath("$.faltas").value(DEFAULT_FALTAS));
    }

    @Test
    @Transactional
    public void getNonExistingEstadisticasJugadorPartido() throws Exception {
        // Get the estadisticasJugadorPartido
        restEstadisticasJugadorPartidoMockMvc.perform(get("/api/estadisticasJugadorPartidos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadisticasJugadorPartido() throws Exception {
        // Initialize the database
        estadisticasJugadorPartidoRepository.saveAndFlush(estadisticasJugadorPartido);

		int databaseSizeBeforeUpdate = estadisticasJugadorPartidoRepository.findAll().size();

        // Update the estadisticasJugadorPartido
        estadisticasJugadorPartido.setCanastas(UPDATED_CANASTAS);
        estadisticasJugadorPartido.setAsistencias(UPDATED_ASISTENCIAS);
        estadisticasJugadorPartido.setFaltas(UPDATED_FALTAS);
        

        restEstadisticasJugadorPartidoMockMvc.perform(put("/api/estadisticasJugadorPartidos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estadisticasJugadorPartido)))
                .andExpect(status().isOk());

        // Validate the EstadisticasJugadorPartido in the database
        List<EstadisticasJugadorPartido> estadisticasJugadorPartidos = estadisticasJugadorPartidoRepository.findAll();
        assertThat(estadisticasJugadorPartidos).hasSize(databaseSizeBeforeUpdate);
        EstadisticasJugadorPartido testEstadisticasJugadorPartido = estadisticasJugadorPartidos.get(estadisticasJugadorPartidos.size() - 1);
        assertThat(testEstadisticasJugadorPartido.getCanastas()).isEqualTo(UPDATED_CANASTAS);
        assertThat(testEstadisticasJugadorPartido.getAsistencias()).isEqualTo(UPDATED_ASISTENCIAS);
        assertThat(testEstadisticasJugadorPartido.getFaltas()).isEqualTo(UPDATED_FALTAS);
    }

    @Test
    @Transactional
    public void deleteEstadisticasJugadorPartido() throws Exception {
        // Initialize the database
        estadisticasJugadorPartidoRepository.saveAndFlush(estadisticasJugadorPartido);

		int databaseSizeBeforeDelete = estadisticasJugadorPartidoRepository.findAll().size();

        // Get the estadisticasJugadorPartido
        restEstadisticasJugadorPartidoMockMvc.perform(delete("/api/estadisticasJugadorPartidos/{id}", estadisticasJugadorPartido.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EstadisticasJugadorPartido> estadisticasJugadorPartidos = estadisticasJugadorPartidoRepository.findAll();
        assertThat(estadisticasJugadorPartidos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
