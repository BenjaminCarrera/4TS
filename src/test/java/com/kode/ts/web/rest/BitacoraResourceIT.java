package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Bitacora;
import com.kode.ts.domain.User;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.Tarea;
import com.kode.ts.repository.BitacoraRepository;
import com.kode.ts.service.BitacoraService;
import com.kode.ts.service.dto.BitacoraDTO;
import com.kode.ts.service.mapper.BitacoraMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.BitacoraCriteria;
import com.kode.ts.service.BitacoraQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.kode.ts.web.rest.TestUtil.sameInstant;
import static com.kode.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link BitacoraResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class BitacoraResourceIT {

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private BitacoraMapper bitacoraMapper;

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private BitacoraQueryService bitacoraQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBitacoraMockMvc;

    private Bitacora bitacora;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BitacoraResource bitacoraResource = new BitacoraResource(bitacoraService, bitacoraQueryService);
        this.restBitacoraMockMvc = MockMvcBuilders.standaloneSetup(bitacoraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bitacora createEntity(EntityManager em) {
        Bitacora bitacora = new Bitacora()
            .fecha(DEFAULT_FECHA)
            .comentario(DEFAULT_COMENTARIO);
        return bitacora;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bitacora createUpdatedEntity(EntityManager em) {
        Bitacora bitacora = new Bitacora()
            .fecha(UPDATED_FECHA)
            .comentario(UPDATED_COMENTARIO);
        return bitacora;
    }

    @BeforeEach
    public void initTest() {
        bitacora = createEntity(em);
    }

    @Test
    @Transactional
    public void createBitacora() throws Exception {
        int databaseSizeBeforeCreate = bitacoraRepository.findAll().size();

        // Create the Bitacora
        BitacoraDTO bitacoraDTO = bitacoraMapper.toDto(bitacora);
        restBitacoraMockMvc.perform(post("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isCreated());

        // Validate the Bitacora in the database
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeCreate + 1);
        Bitacora testBitacora = bitacoraList.get(bitacoraList.size() - 1);
        assertThat(testBitacora.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testBitacora.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createBitacoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bitacoraRepository.findAll().size();

        // Create the Bitacora with an existing ID
        bitacora.setId(1L);
        BitacoraDTO bitacoraDTO = bitacoraMapper.toDto(bitacora);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBitacoraMockMvc.perform(post("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bitacora in the database
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBitacoras() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList
        restBitacoraMockMvc.perform(get("/api/bitacoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bitacora.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getBitacora() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get the bitacora
        restBitacoraMockMvc.perform(get("/api/bitacoras/{id}", bitacora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bitacora.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getAllBitacorasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList where fecha equals to DEFAULT_FECHA
        defaultBitacoraShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the bitacoraList where fecha equals to UPDATED_FECHA
        defaultBitacoraShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllBitacorasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultBitacoraShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the bitacoraList where fecha equals to UPDATED_FECHA
        defaultBitacoraShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllBitacorasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList where fecha is not null
        defaultBitacoraShouldBeFound("fecha.specified=true");

        // Get all the bitacoraList where fecha is null
        defaultBitacoraShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllBitacorasByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList where fecha greater than or equals to DEFAULT_FECHA
        defaultBitacoraShouldBeFound("fecha.greaterOrEqualThan=" + DEFAULT_FECHA);

        // Get all the bitacoraList where fecha greater than or equals to UPDATED_FECHA
        defaultBitacoraShouldNotBeFound("fecha.greaterOrEqualThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllBitacorasByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList where fecha less than or equals to DEFAULT_FECHA
        defaultBitacoraShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the bitacoraList where fecha less than or equals to UPDATED_FECHA
        defaultBitacoraShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }


    @Test
    @Transactional
    public void getAllBitacorasByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList where comentario equals to DEFAULT_COMENTARIO
        defaultBitacoraShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the bitacoraList where comentario equals to UPDATED_COMENTARIO
        defaultBitacoraShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllBitacorasByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultBitacoraShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the bitacoraList where comentario equals to UPDATED_COMENTARIO
        defaultBitacoraShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllBitacorasByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList where comentario is not null
        defaultBitacoraShouldBeFound("comentario.specified=true");

        // Get all the bitacoraList where comentario is null
        defaultBitacoraShouldNotBeFound("comentario.specified=false");
    }

    @Test
    @Transactional
    public void getAllBitacorasByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        User usuario = UserResourceIT.createEntity(em);
        em.persist(usuario);
        em.flush();
        bitacora.setUsuario(usuario);
        bitacoraRepository.saveAndFlush(bitacora);
        Long usuarioId = usuario.getId();

        // Get all the bitacoraList where usuario equals to usuarioId
        defaultBitacoraShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the bitacoraList where usuario equals to usuarioId + 1
        defaultBitacoraShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllBitacorasByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        bitacora.setRequerimiento(requerimiento);
        bitacoraRepository.saveAndFlush(bitacora);
        Long requerimientoId = requerimiento.getId();

        // Get all the bitacoraList where requerimiento equals to requerimientoId
        defaultBitacoraShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the bitacoraList where requerimiento equals to requerimientoId + 1
        defaultBitacoraShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllBitacorasByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        bitacora.setCandidato(candidato);
        bitacoraRepository.saveAndFlush(bitacora);
        Long candidatoId = candidato.getId();

        // Get all the bitacoraList where candidato equals to candidatoId
        defaultBitacoraShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the bitacoraList where candidato equals to candidatoId + 1
        defaultBitacoraShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllBitacorasByTareaIsEqualToSomething() throws Exception {
        // Initialize the database
        Tarea tarea = TareaResourceIT.createEntity(em);
        em.persist(tarea);
        em.flush();
        bitacora.setTarea(tarea);
        bitacoraRepository.saveAndFlush(bitacora);
        Long tareaId = tarea.getId();

        // Get all the bitacoraList where tarea equals to tareaId
        defaultBitacoraShouldBeFound("tareaId.equals=" + tareaId);

        // Get all the bitacoraList where tarea equals to tareaId + 1
        defaultBitacoraShouldNotBeFound("tareaId.equals=" + (tareaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBitacoraShouldBeFound(String filter) throws Exception {
        restBitacoraMockMvc.perform(get("/api/bitacoras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bitacora.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)));

        // Check, that the count call also returns 1
        restBitacoraMockMvc.perform(get("/api/bitacoras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBitacoraShouldNotBeFound(String filter) throws Exception {
        restBitacoraMockMvc.perform(get("/api/bitacoras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBitacoraMockMvc.perform(get("/api/bitacoras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBitacora() throws Exception {
        // Get the bitacora
        restBitacoraMockMvc.perform(get("/api/bitacoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBitacora() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        int databaseSizeBeforeUpdate = bitacoraRepository.findAll().size();

        // Update the bitacora
        Bitacora updatedBitacora = bitacoraRepository.findById(bitacora.getId()).get();
        // Disconnect from session so that the updates on updatedBitacora are not directly saved in db
        em.detach(updatedBitacora);
        updatedBitacora
            .fecha(UPDATED_FECHA)
            .comentario(UPDATED_COMENTARIO);
        BitacoraDTO bitacoraDTO = bitacoraMapper.toDto(updatedBitacora);

        restBitacoraMockMvc.perform(put("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isOk());

        // Validate the Bitacora in the database
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeUpdate);
        Bitacora testBitacora = bitacoraList.get(bitacoraList.size() - 1);
        assertThat(testBitacora.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testBitacora.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingBitacora() throws Exception {
        int databaseSizeBeforeUpdate = bitacoraRepository.findAll().size();

        // Create the Bitacora
        BitacoraDTO bitacoraDTO = bitacoraMapper.toDto(bitacora);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBitacoraMockMvc.perform(put("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bitacora in the database
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBitacora() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        int databaseSizeBeforeDelete = bitacoraRepository.findAll().size();

        // Delete the bitacora
        restBitacoraMockMvc.perform(delete("/api/bitacoras/{id}", bitacora.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bitacora.class);
        Bitacora bitacora1 = new Bitacora();
        bitacora1.setId(1L);
        Bitacora bitacora2 = new Bitacora();
        bitacora2.setId(bitacora1.getId());
        assertThat(bitacora1).isEqualTo(bitacora2);
        bitacora2.setId(2L);
        assertThat(bitacora1).isNotEqualTo(bitacora2);
        bitacora1.setId(null);
        assertThat(bitacora1).isNotEqualTo(bitacora2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BitacoraDTO.class);
        BitacoraDTO bitacoraDTO1 = new BitacoraDTO();
        bitacoraDTO1.setId(1L);
        BitacoraDTO bitacoraDTO2 = new BitacoraDTO();
        assertThat(bitacoraDTO1).isNotEqualTo(bitacoraDTO2);
        bitacoraDTO2.setId(bitacoraDTO1.getId());
        assertThat(bitacoraDTO1).isEqualTo(bitacoraDTO2);
        bitacoraDTO2.setId(2L);
        assertThat(bitacoraDTO1).isNotEqualTo(bitacoraDTO2);
        bitacoraDTO1.setId(null);
        assertThat(bitacoraDTO1).isNotEqualTo(bitacoraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bitacoraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bitacoraMapper.fromId(null)).isNull();
    }
}
