package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstatusLaboral;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.EstatusLaboralRepository;
import com.kode.ts.service.EstatusLaboralService;
import com.kode.ts.service.dto.EstatusLaboralDTO;
import com.kode.ts.service.mapper.EstatusLaboralMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstatusLaboralCriteria;
import com.kode.ts.service.EstatusLaboralQueryService;

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
 * Integration tests for the {@Link EstatusLaboralResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstatusLaboralResourceIT {

    private static final String DEFAULT_ESTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ESTATUS = "BBBBBBBBBB";

    @Autowired
    private EstatusLaboralRepository estatusLaboralRepository;

    @Autowired
    private EstatusLaboralMapper estatusLaboralMapper;

    @Autowired
    private EstatusLaboralService estatusLaboralService;

    @Autowired
    private EstatusLaboralQueryService estatusLaboralQueryService;

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

    private MockMvc restEstatusLaboralMockMvc;

    private EstatusLaboral estatusLaboral;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusLaboralResource estatusLaboralResource = new EstatusLaboralResource(estatusLaboralService, estatusLaboralQueryService);
        this.restEstatusLaboralMockMvc = MockMvcBuilders.standaloneSetup(estatusLaboralResource)
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
    public static EstatusLaboral createEntity(EntityManager em) {
        EstatusLaboral estatusLaboral = new EstatusLaboral()
            .estatus(DEFAULT_ESTATUS);
        return estatusLaboral;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusLaboral createUpdatedEntity(EntityManager em) {
        EstatusLaboral estatusLaboral = new EstatusLaboral()
            .estatus(UPDATED_ESTATUS);
        return estatusLaboral;
    }

    @BeforeEach
    public void initTest() {
        estatusLaboral = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusLaboral() throws Exception {
        int databaseSizeBeforeCreate = estatusLaboralRepository.findAll().size();

        // Create the EstatusLaboral
        EstatusLaboralDTO estatusLaboralDTO = estatusLaboralMapper.toDto(estatusLaboral);
        restEstatusLaboralMockMvc.perform(post("/api/estatus-laborals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusLaboralDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusLaboral in the database
        List<EstatusLaboral> estatusLaboralList = estatusLaboralRepository.findAll();
        assertThat(estatusLaboralList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusLaboral testEstatusLaboral = estatusLaboralList.get(estatusLaboralList.size() - 1);
        assertThat(testEstatusLaboral.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createEstatusLaboralWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusLaboralRepository.findAll().size();

        // Create the EstatusLaboral with an existing ID
        estatusLaboral.setId(1L);
        EstatusLaboralDTO estatusLaboralDTO = estatusLaboralMapper.toDto(estatusLaboral);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusLaboralMockMvc.perform(post("/api/estatus-laborals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusLaboralDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusLaboral in the database
        List<EstatusLaboral> estatusLaboralList = estatusLaboralRepository.findAll();
        assertThat(estatusLaboralList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstatusLaborals() throws Exception {
        // Initialize the database
        estatusLaboralRepository.saveAndFlush(estatusLaboral);

        // Get all the estatusLaboralList
        restEstatusLaboralMockMvc.perform(get("/api/estatus-laborals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusLaboral.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusLaboral() throws Exception {
        // Initialize the database
        estatusLaboralRepository.saveAndFlush(estatusLaboral);

        // Get the estatusLaboral
        restEstatusLaboralMockMvc.perform(get("/api/estatus-laborals/{id}", estatusLaboral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusLaboral.getId().intValue()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllEstatusLaboralsByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        estatusLaboralRepository.saveAndFlush(estatusLaboral);

        // Get all the estatusLaboralList where estatus equals to DEFAULT_ESTATUS
        defaultEstatusLaboralShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the estatusLaboralList where estatus equals to UPDATED_ESTATUS
        defaultEstatusLaboralShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusLaboralsByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        estatusLaboralRepository.saveAndFlush(estatusLaboral);

        // Get all the estatusLaboralList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultEstatusLaboralShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the estatusLaboralList where estatus equals to UPDATED_ESTATUS
        defaultEstatusLaboralShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusLaboralsByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        estatusLaboralRepository.saveAndFlush(estatusLaboral);

        // Get all the estatusLaboralList where estatus is not null
        defaultEstatusLaboralShouldBeFound("estatus.specified=true");

        // Get all the estatusLaboralList where estatus is null
        defaultEstatusLaboralShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstatusLaboralsByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        estatusLaboral.addCandidato(candidato);
        estatusLaboralRepository.saveAndFlush(estatusLaboral);
        Long candidatoId = candidato.getId();

        // Get all the estatusLaboralList where candidato equals to candidatoId
        defaultEstatusLaboralShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the estatusLaboralList where candidato equals to candidatoId + 1
        defaultEstatusLaboralShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstatusLaboralShouldBeFound(String filter) throws Exception {
        restEstatusLaboralMockMvc.perform(get("/api/estatus-laborals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusLaboral.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));

        // Check, that the count call also returns 1
        restEstatusLaboralMockMvc.perform(get("/api/estatus-laborals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstatusLaboralShouldNotBeFound(String filter) throws Exception {
        restEstatusLaboralMockMvc.perform(get("/api/estatus-laborals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstatusLaboralMockMvc.perform(get("/api/estatus-laborals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstatusLaboral() throws Exception {
        // Get the estatusLaboral
        restEstatusLaboralMockMvc.perform(get("/api/estatus-laborals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusLaboral() throws Exception {
        // Initialize the database
        estatusLaboralRepository.saveAndFlush(estatusLaboral);

        int databaseSizeBeforeUpdate = estatusLaboralRepository.findAll().size();

        // Update the estatusLaboral
        EstatusLaboral updatedEstatusLaboral = estatusLaboralRepository.findById(estatusLaboral.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusLaboral are not directly saved in db
        em.detach(updatedEstatusLaboral);
        updatedEstatusLaboral
            .estatus(UPDATED_ESTATUS);
        EstatusLaboralDTO estatusLaboralDTO = estatusLaboralMapper.toDto(updatedEstatusLaboral);

        restEstatusLaboralMockMvc.perform(put("/api/estatus-laborals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusLaboralDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusLaboral in the database
        List<EstatusLaboral> estatusLaboralList = estatusLaboralRepository.findAll();
        assertThat(estatusLaboralList).hasSize(databaseSizeBeforeUpdate);
        EstatusLaboral testEstatusLaboral = estatusLaboralList.get(estatusLaboralList.size() - 1);
        assertThat(testEstatusLaboral.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusLaboral() throws Exception {
        int databaseSizeBeforeUpdate = estatusLaboralRepository.findAll().size();

        // Create the EstatusLaboral
        EstatusLaboralDTO estatusLaboralDTO = estatusLaboralMapper.toDto(estatusLaboral);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusLaboralMockMvc.perform(put("/api/estatus-laborals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusLaboralDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusLaboral in the database
        List<EstatusLaboral> estatusLaboralList = estatusLaboralRepository.findAll();
        assertThat(estatusLaboralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstatusLaboral() throws Exception {
        // Initialize the database
        estatusLaboralRepository.saveAndFlush(estatusLaboral);

        int databaseSizeBeforeDelete = estatusLaboralRepository.findAll().size();

        // Delete the estatusLaboral
        restEstatusLaboralMockMvc.perform(delete("/api/estatus-laborals/{id}", estatusLaboral.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EstatusLaboral> estatusLaboralList = estatusLaboralRepository.findAll();
        assertThat(estatusLaboralList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusLaboral.class);
        EstatusLaboral estatusLaboral1 = new EstatusLaboral();
        estatusLaboral1.setId(1L);
        EstatusLaboral estatusLaboral2 = new EstatusLaboral();
        estatusLaboral2.setId(estatusLaboral1.getId());
        assertThat(estatusLaboral1).isEqualTo(estatusLaboral2);
        estatusLaboral2.setId(2L);
        assertThat(estatusLaboral1).isNotEqualTo(estatusLaboral2);
        estatusLaboral1.setId(null);
        assertThat(estatusLaboral1).isNotEqualTo(estatusLaboral2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusLaboralDTO.class);
        EstatusLaboralDTO estatusLaboralDTO1 = new EstatusLaboralDTO();
        estatusLaboralDTO1.setId(1L);
        EstatusLaboralDTO estatusLaboralDTO2 = new EstatusLaboralDTO();
        assertThat(estatusLaboralDTO1).isNotEqualTo(estatusLaboralDTO2);
        estatusLaboralDTO2.setId(estatusLaboralDTO1.getId());
        assertThat(estatusLaboralDTO1).isEqualTo(estatusLaboralDTO2);
        estatusLaboralDTO2.setId(2L);
        assertThat(estatusLaboralDTO1).isNotEqualTo(estatusLaboralDTO2);
        estatusLaboralDTO1.setId(null);
        assertThat(estatusLaboralDTO1).isNotEqualTo(estatusLaboralDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusLaboralMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusLaboralMapper.fromId(null)).isNull();
    }
}
