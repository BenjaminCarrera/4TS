package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstatusCandidato;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.EstCanInactivo;
import com.kode.ts.repository.EstatusCandidatoRepository;
import com.kode.ts.service.EstatusCandidatoService;
import com.kode.ts.service.dto.EstatusCandidatoDTO;
import com.kode.ts.service.mapper.EstatusCandidatoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstatusCandidatoCriteria;
import com.kode.ts.service.EstatusCandidatoQueryService;

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
 * Integration tests for the {@Link EstatusCandidatoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstatusCandidatoResourceIT {

    private static final String DEFAULT_ESTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ESTATUS = "BBBBBBBBBB";

    @Autowired
    private EstatusCandidatoRepository estatusCandidatoRepository;

    @Autowired
    private EstatusCandidatoMapper estatusCandidatoMapper;

    @Autowired
    private EstatusCandidatoService estatusCandidatoService;

    @Autowired
    private EstatusCandidatoQueryService estatusCandidatoQueryService;

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

    private MockMvc restEstatusCandidatoMockMvc;

    private EstatusCandidato estatusCandidato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusCandidatoResource estatusCandidatoResource = new EstatusCandidatoResource(estatusCandidatoService, estatusCandidatoQueryService);
        this.restEstatusCandidatoMockMvc = MockMvcBuilders.standaloneSetup(estatusCandidatoResource)
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
    public static EstatusCandidato createEntity(EntityManager em) {
        EstatusCandidato estatusCandidato = new EstatusCandidato()
            .estatus(DEFAULT_ESTATUS);
        return estatusCandidato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusCandidato createUpdatedEntity(EntityManager em) {
        EstatusCandidato estatusCandidato = new EstatusCandidato()
            .estatus(UPDATED_ESTATUS);
        return estatusCandidato;
    }

    @BeforeEach
    public void initTest() {
        estatusCandidato = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusCandidato() throws Exception {
        int databaseSizeBeforeCreate = estatusCandidatoRepository.findAll().size();

        // Create the EstatusCandidato
        EstatusCandidatoDTO estatusCandidatoDTO = estatusCandidatoMapper.toDto(estatusCandidato);
        restEstatusCandidatoMockMvc.perform(post("/api/estatus-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusCandidatoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusCandidato in the database
        List<EstatusCandidato> estatusCandidatoList = estatusCandidatoRepository.findAll();
        assertThat(estatusCandidatoList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusCandidato testEstatusCandidato = estatusCandidatoList.get(estatusCandidatoList.size() - 1);
        assertThat(testEstatusCandidato.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createEstatusCandidatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusCandidatoRepository.findAll().size();

        // Create the EstatusCandidato with an existing ID
        estatusCandidato.setId(1L);
        EstatusCandidatoDTO estatusCandidatoDTO = estatusCandidatoMapper.toDto(estatusCandidato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusCandidatoMockMvc.perform(post("/api/estatus-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusCandidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusCandidato in the database
        List<EstatusCandidato> estatusCandidatoList = estatusCandidatoRepository.findAll();
        assertThat(estatusCandidatoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstatusCandidatoes() throws Exception {
        // Initialize the database
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);

        // Get all the estatusCandidatoList
        restEstatusCandidatoMockMvc.perform(get("/api/estatus-candidatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusCandidato.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusCandidato() throws Exception {
        // Initialize the database
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);

        // Get the estatusCandidato
        restEstatusCandidatoMockMvc.perform(get("/api/estatus-candidatoes/{id}", estatusCandidato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusCandidato.getId().intValue()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllEstatusCandidatoesByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);

        // Get all the estatusCandidatoList where estatus equals to DEFAULT_ESTATUS
        defaultEstatusCandidatoShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the estatusCandidatoList where estatus equals to UPDATED_ESTATUS
        defaultEstatusCandidatoShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusCandidatoesByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);

        // Get all the estatusCandidatoList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultEstatusCandidatoShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the estatusCandidatoList where estatus equals to UPDATED_ESTATUS
        defaultEstatusCandidatoShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusCandidatoesByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);

        // Get all the estatusCandidatoList where estatus is not null
        defaultEstatusCandidatoShouldBeFound("estatus.specified=true");

        // Get all the estatusCandidatoList where estatus is null
        defaultEstatusCandidatoShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstatusCandidatoesByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        estatusCandidato.addCandidato(candidato);
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);
        Long candidatoId = candidato.getId();

        // Get all the estatusCandidatoList where candidato equals to candidatoId
        defaultEstatusCandidatoShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the estatusCandidatoList where candidato equals to candidatoId + 1
        defaultEstatusCandidatoShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllEstatusCandidatoesByEstCanInactivoIsEqualToSomething() throws Exception {
        // Initialize the database
        EstCanInactivo estCanInactivo = EstCanInactivoResourceIT.createEntity(em);
        em.persist(estCanInactivo);
        em.flush();
        estatusCandidato.addEstCanInactivo(estCanInactivo);
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);
        Long estCanInactivoId = estCanInactivo.getId();

        // Get all the estatusCandidatoList where estCanInactivo equals to estCanInactivoId
        defaultEstatusCandidatoShouldBeFound("estCanInactivoId.equals=" + estCanInactivoId);

        // Get all the estatusCandidatoList where estCanInactivo equals to estCanInactivoId + 1
        defaultEstatusCandidatoShouldNotBeFound("estCanInactivoId.equals=" + (estCanInactivoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstatusCandidatoShouldBeFound(String filter) throws Exception {
        restEstatusCandidatoMockMvc.perform(get("/api/estatus-candidatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusCandidato.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));

        // Check, that the count call also returns 1
        restEstatusCandidatoMockMvc.perform(get("/api/estatus-candidatoes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstatusCandidatoShouldNotBeFound(String filter) throws Exception {
        restEstatusCandidatoMockMvc.perform(get("/api/estatus-candidatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstatusCandidatoMockMvc.perform(get("/api/estatus-candidatoes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstatusCandidato() throws Exception {
        // Get the estatusCandidato
        restEstatusCandidatoMockMvc.perform(get("/api/estatus-candidatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusCandidato() throws Exception {
        // Initialize the database
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);

        int databaseSizeBeforeUpdate = estatusCandidatoRepository.findAll().size();

        // Update the estatusCandidato
        EstatusCandidato updatedEstatusCandidato = estatusCandidatoRepository.findById(estatusCandidato.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusCandidato are not directly saved in db
        em.detach(updatedEstatusCandidato);
        updatedEstatusCandidato
            .estatus(UPDATED_ESTATUS);
        EstatusCandidatoDTO estatusCandidatoDTO = estatusCandidatoMapper.toDto(updatedEstatusCandidato);

        restEstatusCandidatoMockMvc.perform(put("/api/estatus-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusCandidatoDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusCandidato in the database
        List<EstatusCandidato> estatusCandidatoList = estatusCandidatoRepository.findAll();
        assertThat(estatusCandidatoList).hasSize(databaseSizeBeforeUpdate);
        EstatusCandidato testEstatusCandidato = estatusCandidatoList.get(estatusCandidatoList.size() - 1);
        assertThat(testEstatusCandidato.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusCandidato() throws Exception {
        int databaseSizeBeforeUpdate = estatusCandidatoRepository.findAll().size();

        // Create the EstatusCandidato
        EstatusCandidatoDTO estatusCandidatoDTO = estatusCandidatoMapper.toDto(estatusCandidato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusCandidatoMockMvc.perform(put("/api/estatus-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusCandidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusCandidato in the database
        List<EstatusCandidato> estatusCandidatoList = estatusCandidatoRepository.findAll();
        assertThat(estatusCandidatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstatusCandidato() throws Exception {
        // Initialize the database
        estatusCandidatoRepository.saveAndFlush(estatusCandidato);

        int databaseSizeBeforeDelete = estatusCandidatoRepository.findAll().size();

        // Delete the estatusCandidato
        restEstatusCandidatoMockMvc.perform(delete("/api/estatus-candidatoes/{id}", estatusCandidato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstatusCandidato> estatusCandidatoList = estatusCandidatoRepository.findAll();
        assertThat(estatusCandidatoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusCandidato.class);
        EstatusCandidato estatusCandidato1 = new EstatusCandidato();
        estatusCandidato1.setId(1L);
        EstatusCandidato estatusCandidato2 = new EstatusCandidato();
        estatusCandidato2.setId(estatusCandidato1.getId());
        assertThat(estatusCandidato1).isEqualTo(estatusCandidato2);
        estatusCandidato2.setId(2L);
        assertThat(estatusCandidato1).isNotEqualTo(estatusCandidato2);
        estatusCandidato1.setId(null);
        assertThat(estatusCandidato1).isNotEqualTo(estatusCandidato2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusCandidatoDTO.class);
        EstatusCandidatoDTO estatusCandidatoDTO1 = new EstatusCandidatoDTO();
        estatusCandidatoDTO1.setId(1L);
        EstatusCandidatoDTO estatusCandidatoDTO2 = new EstatusCandidatoDTO();
        assertThat(estatusCandidatoDTO1).isNotEqualTo(estatusCandidatoDTO2);
        estatusCandidatoDTO2.setId(estatusCandidatoDTO1.getId());
        assertThat(estatusCandidatoDTO1).isEqualTo(estatusCandidatoDTO2);
        estatusCandidatoDTO2.setId(2L);
        assertThat(estatusCandidatoDTO1).isNotEqualTo(estatusCandidatoDTO2);
        estatusCandidatoDTO1.setId(null);
        assertThat(estatusCandidatoDTO1).isNotEqualTo(estatusCandidatoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusCandidatoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusCandidatoMapper.fromId(null)).isNull();
    }
}
