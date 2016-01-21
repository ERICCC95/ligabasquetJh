package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Jugador;
import com.mycompany.myapp.repository.JugadorRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the JugadorResource REST controller.
 *
 * @see JugadorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JugadorResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    private static final String DEFAULT_POSICIONCAMPO = "SAMPLE_TEXT";
    private static final String UPDATED_POSICIONCAMPO = "UPDATED_TEXT";

    private static final Integer DEFAULT_CANASTAS_TOTALES = 1;
    private static final Integer UPDATED_CANASTAS_TOTALES = 2;

    private static final Integer DEFAULT_ASISTENCIAS_TOTALES = 1;
    private static final Integer UPDATED_ASISTENCIAS_TOTALES = 2;

    private static final Integer DEFAULT_REBOTES_TOTALES = 1;
    private static final Integer UPDATED_REBOTES_TOTALES = 2;

    private static final DateTime DEFAULT_FECHA_NACIMIENTO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_FECHA_NACIMIENTO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_FECHA_NACIMIENTO_STR = dateTimeFormatter.print(DEFAULT_FECHA_NACIMIENTO);

    @Inject
    private JugadorRepository jugadorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restJugadorMockMvc;

    private Jugador jugador;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JugadorResource jugadorResource = new JugadorResource();
        ReflectionTestUtils.setField(jugadorResource, "jugadorRepository", jugadorRepository);
        this.restJugadorMockMvc = MockMvcBuilders.standaloneSetup(jugadorResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jugador = new Jugador();
        jugador.setNombre(DEFAULT_NOMBRE);
        jugador.setPosicioncampo(DEFAULT_POSICIONCAMPO);
        jugador.setCanastasTotales(DEFAULT_CANASTAS_TOTALES);
        jugador.setAsistenciasTotales(DEFAULT_ASISTENCIAS_TOTALES);
        jugador.setRebotesTotales(DEFAULT_REBOTES_TOTALES);
        jugador.setFechaNacimiento(DEFAULT_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void createJugador() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeCreate + 1);
        Jugador testJugador = jugadors.get(jugadors.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJugador.getPosicioncampo()).isEqualTo(DEFAULT_POSICIONCAMPO);
        assertThat(testJugador.getCanastasTotales()).isEqualTo(DEFAULT_CANASTAS_TOTALES);
        assertThat(testJugador.getAsistenciasTotales()).isEqualTo(DEFAULT_ASISTENCIAS_TOTALES);
        assertThat(testJugador.getRebotesTotales()).isEqualTo(DEFAULT_REBOTES_TOTALES);
        assertThat(testJugador.getFechaNacimiento().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllJugadors() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get all the jugadors
        restJugadorMockMvc.perform(get("/api/jugadors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jugador.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].posicioncampo").value(hasItem(DEFAULT_POSICIONCAMPO.toString())))
                .andExpect(jsonPath("$.[*].canastasTotales").value(hasItem(DEFAULT_CANASTAS_TOTALES)))
                .andExpect(jsonPath("$.[*].asistenciasTotales").value(hasItem(DEFAULT_ASISTENCIAS_TOTALES)))
                .andExpect(jsonPath("$.[*].rebotesTotales").value(hasItem(DEFAULT_REBOTES_TOTALES)))
                .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO_STR)));
    }

    @Test
    @Transactional
    public void getJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", jugador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jugador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.posicioncampo").value(DEFAULT_POSICIONCAMPO.toString()))
            .andExpect(jsonPath("$.canastasTotales").value(DEFAULT_CANASTAS_TOTALES))
            .andExpect(jsonPath("$.asistenciasTotales").value(DEFAULT_ASISTENCIAS_TOTALES))
            .andExpect(jsonPath("$.rebotesTotales").value(DEFAULT_REBOTES_TOTALES))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO_STR));
    }

    @Test
    @Transactional
    public void getNonExistingJugador() throws Exception {
        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

		int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Update the jugador
        jugador.setNombre(UPDATED_NOMBRE);
        jugador.setPosicioncampo(UPDATED_POSICIONCAMPO);
        jugador.setCanastasTotales(UPDATED_CANASTAS_TOTALES);
        jugador.setAsistenciasTotales(UPDATED_ASISTENCIAS_TOTALES);
        jugador.setRebotesTotales(UPDATED_REBOTES_TOTALES);
        jugador.setFechaNacimiento(UPDATED_FECHA_NACIMIENTO);
        

        restJugadorMockMvc.perform(put("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isOk());

        // Validate the Jugador in the database
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeUpdate);
        Jugador testJugador = jugadors.get(jugadors.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJugador.getPosicioncampo()).isEqualTo(UPDATED_POSICIONCAMPO);
        assertThat(testJugador.getCanastasTotales()).isEqualTo(UPDATED_CANASTAS_TOTALES);
        assertThat(testJugador.getAsistenciasTotales()).isEqualTo(UPDATED_ASISTENCIAS_TOTALES);
        assertThat(testJugador.getRebotesTotales()).isEqualTo(UPDATED_REBOTES_TOTALES);
        assertThat(testJugador.getFechaNacimiento().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void deleteJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

		int databaseSizeBeforeDelete = jugadorRepository.findAll().size();

        // Get the jugador
        restJugadorMockMvc.perform(delete("/api/jugadors/{id}", jugador.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
