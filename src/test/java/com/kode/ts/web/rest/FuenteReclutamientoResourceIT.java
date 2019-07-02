package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.FuenteReclutamiento;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.FuenteReclutamientoRepository;
import com.kode.ts.service.FuenteReclutamientoService;
import com.kode.ts.service.dto.FuenteReclutamientoDTO;
import com.kode.ts.service.mapper.FuenteReclutamientoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.FuenteReclutamientoCriteria;
import com.kode.ts.service.FuenteReclutamientoQueryService;

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
 * Integration tests for the {@Link FuenteReclutamientoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class FuenteReclutamientoResourceIT {

    private static final String DEFAULT_FUENTE = "AAAAAAAAAA";
    private static final String UPDATED_FUENTE = "BBBBBBBBBB";

    @Autowired
    private FuenteReclutamientoRepository fuenteReclutamientoRepository;

    @Autowired
    private FuenteReclutamientoMapper fuenteReclutamientoMapper;

    @Autowired
    private FuenteReclutamientoService fuenteReclutamientoService;

    @Autowired
    private FuenteReclutamientoQueryService fuenteReclutamientoQueryService;

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

    private MockMvc restFuenteReclutamientoMockMvc;

    private FuenteReclutamiento fuenteReclutamiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuenteReclutamientoResource fuenteReclutamientoResource = new FuenteReclutamientoResource(fuenteReclutamientoService, fuenteReclutamientoQueryService);
        this.restFuenteReclutamientoMockMvc = MockMvcBuilders.standaloneSetup(fuenteReclutamientoResource)
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
    public static FuenteReclutamiento createEntity(EntityManager em) {
        FuenteReclutamiento fuenteReclutamiento = new FuenteReclutamiento()
            .fuente(DEFAULT_FUENTE);
        return fuenteReclutamiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuenteReclutamiento createUpdatedEntity(EntityManager em) {
        FuenteReclutamiento fuenteReclutamiento = new FuenteReclutamiento()
            .fuente(UPDATED_FUENTE);
        return fuenteReclutamiento;
    }

    @BeforeEach
    public void initTest() {
        fuenteReclutamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuenteReclutamiento() throws Exception {
        int databaseSizeBeforeCreate = fuenteReclutamientoRepository.findAll().size();

        // Create the FuenteReclutamiento
        FuenteReclutamientoDTO fuenteReclutamientoDTO = fuenteReclutamientoMapper.toDto(fuenteReclutamiento);
        restFuenteReclutamientoMockMvc.perform(post("/api/fuente-reclutamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteReclutamientoDTO)))
            .andExpect(status().isCreated());

        // Validate the FuenteReclutamiento in the database
        List<FuenteReclutamiento> fuenteReclutamientoList = fuenteReclutamientoRepository.findAll();
        assertThat(fuenteReclutamientoList).hasSize(databaseSizeBeforeCreate + 1);
        FuenteReclutamiento testFuenteReclutamiento = fuenteReclutamientoList.get(fuenteReclutamientoList.size() - 1);
        assertThat(testFuenteReclutamiento.getFuente()).isEqualTo(DEFAULT_FUENTE);
    }

    @Test
    @Transactional
    public void createFuenteReclutamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fuenteReclutamientoRepository.findAll().size();

        // Create the FuenteReclutamiento with an existing ID
        fuenteReclutamiento.setId(1L);
        FuenteReclutamientoDTO fuenteReclutamientoDTO = fuenteReclutamientoMapper.toDto(fuenteReclutamiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuenteReclutamientoMockMvc.perform(post("/api/fuente-reclutamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteReclutamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FuenteReclutamiento in the database
        List<FuenteReclutamiento> fuenteReclutamientoList = fuenteReclutamientoRepository.findAll();
        assertThat(fuenteReclutamientoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFuenteReclutamientos() throws Exception {
        // Initialize the database
        fuenteReclutamientoRepository.saveAndFlush(fuenteReclutamiento);

        // Get all the fuenteReclutamientoList
        restFuenteReclutamientoMockMvc.perform(get("/api/fuente-reclutamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuenteReclutamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].fuente").value(hasItem(DEFAULT_FUENTE.toString())));
    }
    
    @Test
    @Transactional
    public void getFuenteReclutamiento() throws Exception {
        // Initialize the database
        fuenteReclutamientoRepository.saveAndFlush(fuenteReclutamiento);

        // Get the fuenteReclutamiento
        restFuenteReclutamientoMockMvc.perform(get("/api/fuente-reclutamientos/{id}", fuenteReclutamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fuenteReclutamiento.getId().intValue()))
            .andExpect(jsonPath("$.fuente").value(DEFAULT_FUENTE.toString()));
    }

    @Test
    @Transactional
    public void getAllFuenteReclutamientosByFuenteIsEqualToSomething() throws Exception {
        // Initialize the database
        fuenteReclutamientoRepository.saveAndFlush(fuenteReclutamiento);

        // Get all the fuenteReclutamientoList where fuente equals to DEFAULT_FUENTE
        defaultFuenteReclutamientoShouldBeFound("fuente.equals=" + DEFAULT_FUENTE);

        // Get all the fuenteReclutamientoList where fuente equals to UPDATED_FUENTE
        defaultFuenteReclutamientoShouldNotBeFound("fuente.equals=" + UPDATED_FUENTE);
    }

    @Test
    @Transactional
    public void getAllFuenteReclutamientosByFuenteIsInShouldWork() throws Exception {
        // Initialize the database
        fuenteReclutamientoRepository.saveAndFlush(fuenteReclutamiento);

        // Get all the fuenteReclutamientoList where fuente in DEFAULT_FUENTE or UPDATED_FUENTE
        defaultFuenteReclutamientoShouldBeFound("fuente.in=" + DEFAULT_FUENTE + "," + UPDATED_FUENTE);

        // Get all the fuenteReclutamientoList where fuente equals to UPDATED_FUENTE
        defaultFuenteReclutamientoShouldNotBeFound("fuente.in=" + UPDATED_FUENTE);
    }

    @Test
    @Transactional
    public void getAllFuenteReclutamientosByFuenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        fuenteReclutamientoRepository.saveAndFlush(fuenteReclutamiento);

        // Get all the fuenteReclutamientoList where fuente is not null
        defaultFuenteReclutamientoShouldBeFound("fuente.specified=true");

        // Get all the fuenteReclutamientoList where fuente is null
        defaultFuenteReclutamientoShouldNotBeFound("fuente.specified=false");
    }

    @Test
    @Transactional
    public void getAllFuenteReclutamientosByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        fuenteReclutamiento.addCandidato(candidato);
        fuenteReclutamientoRepository.saveAndFlush(fuenteReclutamiento);
        Long candidatoId = candidato.getId();

        // Get all the fuenteReclutamientoList where candidato equals to candidatoId
        defaultFuenteReclutamientoShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the fuenteReclutamientoList where candidato equals to candidatoId + 1
        defaultFuenteReclutamientoShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuenteReclutamientoShouldBeFound(String filter) throws Exception {
        restFuenteReclutamientoMockMvc.perform(get("/api/fuente-reclutamientos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuenteReclutamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].fuente").value(hasItem(DEFAULT_FUENTE)));

        // Check, that the count call also returns 1
        restFuenteReclutamientoMockMvc.perform(get("/api/fuente-reclutamientos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuenteReclutamientoShouldNotBeFound(String filter) throws Exception {
        restFuenteReclutamientoMockMvc.perform(get("/api/fuente-reclutamientos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuenteReclutamientoMockMvc.perform(get("/api/fuente-reclutamientos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFuenteReclutamiento() throws Exception {
        // Get the fuenteReclutamiento
        restFuenteReclutamientoMockMvc.perform(get("/api/fuente-reclutamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuenteReclutamiento() throws Exception {
        // Initialize the database
        fuenteReclutamientoRepository.saveAndFlush(fuenteReclutamiento);

        int databaseSizeBeforeUpdate = fuenteReclutamientoRepository.findAll().size();

        // Update the fuenteReclutamiento
        FuenteReclutamiento updatedFuenteReclutamiento = fuenteReclutamientoRepository.findById(fuenteReclutamiento.getId()).get();
        // Disconnect from session so that the updates on updatedFuenteReclutamiento are not directly saved in db
        em.detach(updatedFuenteReclutamiento);
        updatedFuenteReclutamiento
            .fuente(UPDATED_FUENTE);
        FuenteReclutamientoDTO fuenteReclutamientoDTO = fuenteReclutamientoMapper.toDto(updatedFuenteReclutamiento);

        restFuenteReclutamientoMockMvc.perform(put("/api/fuente-reclutamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteReclutamientoDTO)))
            .andExpect(status().isOk());

        // Validate the FuenteReclutamiento in the database
        List<FuenteReclutamiento> fuenteReclutamientoList = fuenteReclutamientoRepository.findAll();
        assertThat(fuenteReclutamientoList).hasSize(databaseSizeBeforeUpdate);
        FuenteReclutamiento testFuenteReclutamiento = fuenteReclutamientoList.get(fuenteReclutamientoList.size() - 1);
        assertThat(testFuenteReclutamiento.getFuente()).isEqualTo(UPDATED_FUENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingFuenteReclutamiento() throws Exception {
        int databaseSizeBeforeUpdate = fuenteReclutamientoRepository.findAll().size();

        // Create the FuenteReclutamiento
        FuenteReclutamientoDTO fuenteReclutamientoDTO = fuenteReclutamientoMapper.toDto(fuenteReclutamiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuenteReclutamientoMockMvc.perform(put("/api/fuente-reclutamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteReclutamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FuenteReclutamiento in the database
        List<FuenteReclutamiento> fuenteReclutamientoList = fuenteReclutamientoRepository.findAll();
        assertThat(fuenteReclutamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFuenteReclutamiento() throws Exception {
        // Initialize the database
        fuenteReclutamientoRepository.saveAndFlush(fuenteReclutamiento);

        int databaseSizeBeforeDelete = fuenteReclutamientoRepository.findAll().size();

        // Delete the fuenteReclutamiento
        restFuenteReclutamientoMockMvc.perform(delete("/api/fuente-reclutamientos/{id}", fuenteReclutamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuenteReclutamiento> fuenteReclutamientoList = fuenteReclutamientoRepository.findAll();
        assertThat(fuenteReclutamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuenteReclutamiento.class);
        FuenteReclutamiento fuenteReclutamiento1 = new FuenteReclutamiento();
        fuenteReclutamiento1.setId(1L);
        FuenteReclutamiento fuenteReclutamiento2 = new FuenteReclutamiento();
        fuenteReclutamiento2.setId(fuenteReclutamiento1.getId());
        assertThat(fuenteReclutamiento1).isEqualTo(fuenteReclutamiento2);
        fuenteReclutamiento2.setId(2L);
        assertThat(fuenteReclutamiento1).isNotEqualTo(fuenteReclutamiento2);
        fuenteReclutamiento1.setId(null);
        assertThat(fuenteReclutamiento1).isNotEqualTo(fuenteReclutamiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuenteReclutamientoDTO.class);
        FuenteReclutamientoDTO fuenteReclutamientoDTO1 = new FuenteReclutamientoDTO();
        fuenteReclutamientoDTO1.setId(1L);
        FuenteReclutamientoDTO fuenteReclutamientoDTO2 = new FuenteReclutamientoDTO();
        assertThat(fuenteReclutamientoDTO1).isNotEqualTo(fuenteReclutamientoDTO2);
        fuenteReclutamientoDTO2.setId(fuenteReclutamientoDTO1.getId());
        assertThat(fuenteReclutamientoDTO1).isEqualTo(fuenteReclutamientoDTO2);
        fuenteReclutamientoDTO2.setId(2L);
        assertThat(fuenteReclutamientoDTO1).isNotEqualTo(fuenteReclutamientoDTO2);
        fuenteReclutamientoDTO1.setId(null);
        assertThat(fuenteReclutamientoDTO1).isNotEqualTo(fuenteReclutamientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fuenteReclutamientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fuenteReclutamientoMapper.fromId(null)).isNull();
    }
}
