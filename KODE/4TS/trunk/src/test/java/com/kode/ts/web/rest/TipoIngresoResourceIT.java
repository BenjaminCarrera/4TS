package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.TipoIngreso;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.TipoIngresoRepository;
import com.kode.ts.service.TipoIngresoService;
import com.kode.ts.service.dto.TipoIngresoDTO;
import com.kode.ts.service.mapper.TipoIngresoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.TipoIngresoCriteria;
import com.kode.ts.service.TipoIngresoQueryService;

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
import java.util.List;

import static com.kode.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TipoIngresoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class TipoIngresoResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private TipoIngresoRepository tipoIngresoRepository;

    @Autowired
    private TipoIngresoMapper tipoIngresoMapper;

    @Autowired
    private TipoIngresoService tipoIngresoService;

    @Autowired
    private TipoIngresoQueryService tipoIngresoQueryService;

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

    private MockMvc restTipoIngresoMockMvc;

    private TipoIngreso tipoIngreso;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoIngresoResource tipoIngresoResource = new TipoIngresoResource(tipoIngresoService, tipoIngresoQueryService);
        this.restTipoIngresoMockMvc = MockMvcBuilders.standaloneSetup(tipoIngresoResource)
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
    public static TipoIngreso createEntity(EntityManager em) {
        TipoIngreso tipoIngreso = new TipoIngreso()
            .tipo(DEFAULT_TIPO);
        return tipoIngreso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoIngreso createUpdatedEntity(EntityManager em) {
        TipoIngreso tipoIngreso = new TipoIngreso()
            .tipo(UPDATED_TIPO);
        return tipoIngreso;
    }

    @BeforeEach
    public void initTest() {
        tipoIngreso = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoIngreso() throws Exception {
        int databaseSizeBeforeCreate = tipoIngresoRepository.findAll().size();

        // Create the TipoIngreso
        TipoIngresoDTO tipoIngresoDTO = tipoIngresoMapper.toDto(tipoIngreso);
        restTipoIngresoMockMvc.perform(post("/api/tipo-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoIngresoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoIngreso in the database
        List<TipoIngreso> tipoIngresoList = tipoIngresoRepository.findAll();
        assertThat(tipoIngresoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoIngreso testTipoIngreso = tipoIngresoList.get(tipoIngresoList.size() - 1);
        assertThat(testTipoIngreso.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createTipoIngresoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoIngresoRepository.findAll().size();

        // Create the TipoIngreso with an existing ID
        tipoIngreso.setId(1L);
        TipoIngresoDTO tipoIngresoDTO = tipoIngresoMapper.toDto(tipoIngreso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoIngresoMockMvc.perform(post("/api/tipo-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoIngresoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoIngreso in the database
        List<TipoIngreso> tipoIngresoList = tipoIngresoRepository.findAll();
        assertThat(tipoIngresoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoIngresos() throws Exception {
        // Initialize the database
        tipoIngresoRepository.saveAndFlush(tipoIngreso);

        // Get all the tipoIngresoList
        restTipoIngresoMockMvc.perform(get("/api/tipo-ingresos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoIngreso.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoIngreso() throws Exception {
        // Initialize the database
        tipoIngresoRepository.saveAndFlush(tipoIngreso);

        // Get the tipoIngreso
        restTipoIngresoMockMvc.perform(get("/api/tipo-ingresos/{id}", tipoIngreso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoIngreso.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoIngresosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoIngresoRepository.saveAndFlush(tipoIngreso);

        // Get all the tipoIngresoList where tipo equals to DEFAULT_TIPO
        defaultTipoIngresoShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the tipoIngresoList where tipo equals to UPDATED_TIPO
        defaultTipoIngresoShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTipoIngresosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoIngresoRepository.saveAndFlush(tipoIngreso);

        // Get all the tipoIngresoList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultTipoIngresoShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the tipoIngresoList where tipo equals to UPDATED_TIPO
        defaultTipoIngresoShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTipoIngresosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoIngresoRepository.saveAndFlush(tipoIngreso);

        // Get all the tipoIngresoList where tipo is not null
        defaultTipoIngresoShouldBeFound("tipo.specified=true");

        // Get all the tipoIngresoList where tipo is null
        defaultTipoIngresoShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoIngresosByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        tipoIngreso.addRequerimiento(requerimiento);
        tipoIngresoRepository.saveAndFlush(tipoIngreso);
        Long requerimientoId = requerimiento.getId();

        // Get all the tipoIngresoList where requerimiento equals to requerimientoId
        defaultTipoIngresoShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the tipoIngresoList where requerimiento equals to requerimientoId + 1
        defaultTipoIngresoShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoIngresoShouldBeFound(String filter) throws Exception {
        restTipoIngresoMockMvc.perform(get("/api/tipo-ingresos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoIngreso.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));

        // Check, that the count call also returns 1
        restTipoIngresoMockMvc.perform(get("/api/tipo-ingresos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoIngresoShouldNotBeFound(String filter) throws Exception {
        restTipoIngresoMockMvc.perform(get("/api/tipo-ingresos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoIngresoMockMvc.perform(get("/api/tipo-ingresos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoIngreso() throws Exception {
        // Get the tipoIngreso
        restTipoIngresoMockMvc.perform(get("/api/tipo-ingresos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoIngreso() throws Exception {
        // Initialize the database
        tipoIngresoRepository.saveAndFlush(tipoIngreso);

        int databaseSizeBeforeUpdate = tipoIngresoRepository.findAll().size();

        // Update the tipoIngreso
        TipoIngreso updatedTipoIngreso = tipoIngresoRepository.findById(tipoIngreso.getId()).get();
        // Disconnect from session so that the updates on updatedTipoIngreso are not directly saved in db
        em.detach(updatedTipoIngreso);
        updatedTipoIngreso
            .tipo(UPDATED_TIPO);
        TipoIngresoDTO tipoIngresoDTO = tipoIngresoMapper.toDto(updatedTipoIngreso);

        restTipoIngresoMockMvc.perform(put("/api/tipo-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoIngresoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoIngreso in the database
        List<TipoIngreso> tipoIngresoList = tipoIngresoRepository.findAll();
        assertThat(tipoIngresoList).hasSize(databaseSizeBeforeUpdate);
        TipoIngreso testTipoIngreso = tipoIngresoList.get(tipoIngresoList.size() - 1);
        assertThat(testTipoIngreso.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoIngreso() throws Exception {
        int databaseSizeBeforeUpdate = tipoIngresoRepository.findAll().size();

        // Create the TipoIngreso
        TipoIngresoDTO tipoIngresoDTO = tipoIngresoMapper.toDto(tipoIngreso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoIngresoMockMvc.perform(put("/api/tipo-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoIngresoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoIngreso in the database
        List<TipoIngreso> tipoIngresoList = tipoIngresoRepository.findAll();
        assertThat(tipoIngresoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoIngreso() throws Exception {
        // Initialize the database
        tipoIngresoRepository.saveAndFlush(tipoIngreso);

        int databaseSizeBeforeDelete = tipoIngresoRepository.findAll().size();

        // Delete the tipoIngreso
        restTipoIngresoMockMvc.perform(delete("/api/tipo-ingresos/{id}", tipoIngreso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TipoIngreso> tipoIngresoList = tipoIngresoRepository.findAll();
        assertThat(tipoIngresoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoIngreso.class);
        TipoIngreso tipoIngreso1 = new TipoIngreso();
        tipoIngreso1.setId(1L);
        TipoIngreso tipoIngreso2 = new TipoIngreso();
        tipoIngreso2.setId(tipoIngreso1.getId());
        assertThat(tipoIngreso1).isEqualTo(tipoIngreso2);
        tipoIngreso2.setId(2L);
        assertThat(tipoIngreso1).isNotEqualTo(tipoIngreso2);
        tipoIngreso1.setId(null);
        assertThat(tipoIngreso1).isNotEqualTo(tipoIngreso2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoIngresoDTO.class);
        TipoIngresoDTO tipoIngresoDTO1 = new TipoIngresoDTO();
        tipoIngresoDTO1.setId(1L);
        TipoIngresoDTO tipoIngresoDTO2 = new TipoIngresoDTO();
        assertThat(tipoIngresoDTO1).isNotEqualTo(tipoIngresoDTO2);
        tipoIngresoDTO2.setId(tipoIngresoDTO1.getId());
        assertThat(tipoIngresoDTO1).isEqualTo(tipoIngresoDTO2);
        tipoIngresoDTO2.setId(2L);
        assertThat(tipoIngresoDTO1).isNotEqualTo(tipoIngresoDTO2);
        tipoIngresoDTO1.setId(null);
        assertThat(tipoIngresoDTO1).isNotEqualTo(tipoIngresoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoIngresoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoIngresoMapper.fromId(null)).isNull();
    }
}
