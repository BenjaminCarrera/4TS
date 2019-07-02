package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstatusAcademico;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.EstatusAcademicoRepository;
import com.kode.ts.service.EstatusAcademicoService;
import com.kode.ts.service.dto.EstatusAcademicoDTO;
import com.kode.ts.service.mapper.EstatusAcademicoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstatusAcademicoCriteria;
import com.kode.ts.service.EstatusAcademicoQueryService;

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
 * Integration tests for the {@Link EstatusAcademicoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstatusAcademicoResourceIT {

    private static final String DEFAULT_ESTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ESTATUS = "BBBBBBBBBB";

    @Autowired
    private EstatusAcademicoRepository estatusAcademicoRepository;

    @Autowired
    private EstatusAcademicoMapper estatusAcademicoMapper;

    @Autowired
    private EstatusAcademicoService estatusAcademicoService;

    @Autowired
    private EstatusAcademicoQueryService estatusAcademicoQueryService;

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

    private MockMvc restEstatusAcademicoMockMvc;

    private EstatusAcademico estatusAcademico;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusAcademicoResource estatusAcademicoResource = new EstatusAcademicoResource(estatusAcademicoService, estatusAcademicoQueryService);
        this.restEstatusAcademicoMockMvc = MockMvcBuilders.standaloneSetup(estatusAcademicoResource)
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
    public static EstatusAcademico createEntity(EntityManager em) {
        EstatusAcademico estatusAcademico = new EstatusAcademico()
            .estatus(DEFAULT_ESTATUS);
        return estatusAcademico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusAcademico createUpdatedEntity(EntityManager em) {
        EstatusAcademico estatusAcademico = new EstatusAcademico()
            .estatus(UPDATED_ESTATUS);
        return estatusAcademico;
    }

    @BeforeEach
    public void initTest() {
        estatusAcademico = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusAcademico() throws Exception {
        int databaseSizeBeforeCreate = estatusAcademicoRepository.findAll().size();

        // Create the EstatusAcademico
        EstatusAcademicoDTO estatusAcademicoDTO = estatusAcademicoMapper.toDto(estatusAcademico);
        restEstatusAcademicoMockMvc.perform(post("/api/estatus-academicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusAcademicoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusAcademico in the database
        List<EstatusAcademico> estatusAcademicoList = estatusAcademicoRepository.findAll();
        assertThat(estatusAcademicoList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusAcademico testEstatusAcademico = estatusAcademicoList.get(estatusAcademicoList.size() - 1);
        assertThat(testEstatusAcademico.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createEstatusAcademicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusAcademicoRepository.findAll().size();

        // Create the EstatusAcademico with an existing ID
        estatusAcademico.setId(1L);
        EstatusAcademicoDTO estatusAcademicoDTO = estatusAcademicoMapper.toDto(estatusAcademico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusAcademicoMockMvc.perform(post("/api/estatus-academicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusAcademicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusAcademico in the database
        List<EstatusAcademico> estatusAcademicoList = estatusAcademicoRepository.findAll();
        assertThat(estatusAcademicoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstatusAcademicos() throws Exception {
        // Initialize the database
        estatusAcademicoRepository.saveAndFlush(estatusAcademico);

        // Get all the estatusAcademicoList
        restEstatusAcademicoMockMvc.perform(get("/api/estatus-academicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusAcademico.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusAcademico() throws Exception {
        // Initialize the database
        estatusAcademicoRepository.saveAndFlush(estatusAcademico);

        // Get the estatusAcademico
        restEstatusAcademicoMockMvc.perform(get("/api/estatus-academicos/{id}", estatusAcademico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusAcademico.getId().intValue()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllEstatusAcademicosByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        estatusAcademicoRepository.saveAndFlush(estatusAcademico);

        // Get all the estatusAcademicoList where estatus equals to DEFAULT_ESTATUS
        defaultEstatusAcademicoShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the estatusAcademicoList where estatus equals to UPDATED_ESTATUS
        defaultEstatusAcademicoShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusAcademicosByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        estatusAcademicoRepository.saveAndFlush(estatusAcademico);

        // Get all the estatusAcademicoList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultEstatusAcademicoShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the estatusAcademicoList where estatus equals to UPDATED_ESTATUS
        defaultEstatusAcademicoShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusAcademicosByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        estatusAcademicoRepository.saveAndFlush(estatusAcademico);

        // Get all the estatusAcademicoList where estatus is not null
        defaultEstatusAcademicoShouldBeFound("estatus.specified=true");

        // Get all the estatusAcademicoList where estatus is null
        defaultEstatusAcademicoShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstatusAcademicosByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        estatusAcademico.addCandidato(candidato);
        estatusAcademicoRepository.saveAndFlush(estatusAcademico);
        Long candidatoId = candidato.getId();

        // Get all the estatusAcademicoList where candidato equals to candidatoId
        defaultEstatusAcademicoShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the estatusAcademicoList where candidato equals to candidatoId + 1
        defaultEstatusAcademicoShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstatusAcademicoShouldBeFound(String filter) throws Exception {
        restEstatusAcademicoMockMvc.perform(get("/api/estatus-academicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusAcademico.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));

        // Check, that the count call also returns 1
        restEstatusAcademicoMockMvc.perform(get("/api/estatus-academicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstatusAcademicoShouldNotBeFound(String filter) throws Exception {
        restEstatusAcademicoMockMvc.perform(get("/api/estatus-academicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstatusAcademicoMockMvc.perform(get("/api/estatus-academicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstatusAcademico() throws Exception {
        // Get the estatusAcademico
        restEstatusAcademicoMockMvc.perform(get("/api/estatus-academicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusAcademico() throws Exception {
        // Initialize the database
        estatusAcademicoRepository.saveAndFlush(estatusAcademico);

        int databaseSizeBeforeUpdate = estatusAcademicoRepository.findAll().size();

        // Update the estatusAcademico
        EstatusAcademico updatedEstatusAcademico = estatusAcademicoRepository.findById(estatusAcademico.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusAcademico are not directly saved in db
        em.detach(updatedEstatusAcademico);
        updatedEstatusAcademico
            .estatus(UPDATED_ESTATUS);
        EstatusAcademicoDTO estatusAcademicoDTO = estatusAcademicoMapper.toDto(updatedEstatusAcademico);

        restEstatusAcademicoMockMvc.perform(put("/api/estatus-academicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusAcademicoDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusAcademico in the database
        List<EstatusAcademico> estatusAcademicoList = estatusAcademicoRepository.findAll();
        assertThat(estatusAcademicoList).hasSize(databaseSizeBeforeUpdate);
        EstatusAcademico testEstatusAcademico = estatusAcademicoList.get(estatusAcademicoList.size() - 1);
        assertThat(testEstatusAcademico.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusAcademico() throws Exception {
        int databaseSizeBeforeUpdate = estatusAcademicoRepository.findAll().size();

        // Create the EstatusAcademico
        EstatusAcademicoDTO estatusAcademicoDTO = estatusAcademicoMapper.toDto(estatusAcademico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusAcademicoMockMvc.perform(put("/api/estatus-academicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusAcademicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusAcademico in the database
        List<EstatusAcademico> estatusAcademicoList = estatusAcademicoRepository.findAll();
        assertThat(estatusAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstatusAcademico() throws Exception {
        // Initialize the database
        estatusAcademicoRepository.saveAndFlush(estatusAcademico);

        int databaseSizeBeforeDelete = estatusAcademicoRepository.findAll().size();

        // Delete the estatusAcademico
        restEstatusAcademicoMockMvc.perform(delete("/api/estatus-academicos/{id}", estatusAcademico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EstatusAcademico> estatusAcademicoList = estatusAcademicoRepository.findAll();
        assertThat(estatusAcademicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusAcademico.class);
        EstatusAcademico estatusAcademico1 = new EstatusAcademico();
        estatusAcademico1.setId(1L);
        EstatusAcademico estatusAcademico2 = new EstatusAcademico();
        estatusAcademico2.setId(estatusAcademico1.getId());
        assertThat(estatusAcademico1).isEqualTo(estatusAcademico2);
        estatusAcademico2.setId(2L);
        assertThat(estatusAcademico1).isNotEqualTo(estatusAcademico2);
        estatusAcademico1.setId(null);
        assertThat(estatusAcademico1).isNotEqualTo(estatusAcademico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusAcademicoDTO.class);
        EstatusAcademicoDTO estatusAcademicoDTO1 = new EstatusAcademicoDTO();
        estatusAcademicoDTO1.setId(1L);
        EstatusAcademicoDTO estatusAcademicoDTO2 = new EstatusAcademicoDTO();
        assertThat(estatusAcademicoDTO1).isNotEqualTo(estatusAcademicoDTO2);
        estatusAcademicoDTO2.setId(estatusAcademicoDTO1.getId());
        assertThat(estatusAcademicoDTO1).isEqualTo(estatusAcademicoDTO2);
        estatusAcademicoDTO2.setId(2L);
        assertThat(estatusAcademicoDTO1).isNotEqualTo(estatusAcademicoDTO2);
        estatusAcademicoDTO1.setId(null);
        assertThat(estatusAcademicoDTO1).isNotEqualTo(estatusAcademicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusAcademicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusAcademicoMapper.fromId(null)).isNull();
    }
}
