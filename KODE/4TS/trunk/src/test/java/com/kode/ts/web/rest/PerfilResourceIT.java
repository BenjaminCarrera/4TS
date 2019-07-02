package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Perfil;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.PerfilRepository;
import com.kode.ts.service.PerfilService;
import com.kode.ts.service.dto.PerfilDTO;
import com.kode.ts.service.mapper.PerfilMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.PerfilCriteria;
import com.kode.ts.service.PerfilQueryService;

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
 * Integration tests for the {@Link PerfilResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class PerfilResourceIT {

    private static final String DEFAULT_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL = "BBBBBBBBBB";

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PerfilMapper perfilMapper;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PerfilQueryService perfilQueryService;

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

    private MockMvc restPerfilMockMvc;

    private Perfil perfil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerfilResource perfilResource = new PerfilResource(perfilService, perfilQueryService);
        this.restPerfilMockMvc = MockMvcBuilders.standaloneSetup(perfilResource)
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
    public static Perfil createEntity(EntityManager em) {
        Perfil perfil = new Perfil()
            .perfil(DEFAULT_PERFIL);
        return perfil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perfil createUpdatedEntity(EntityManager em) {
        Perfil perfil = new Perfil()
            .perfil(UPDATED_PERFIL);
        return perfil;
    }

    @BeforeEach
    public void initTest() {
        perfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfil() throws Exception {
        int databaseSizeBeforeCreate = perfilRepository.findAll().size();

        // Create the Perfil
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);
        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isCreated());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeCreate + 1);
        Perfil testPerfil = perfilList.get(perfilList.size() - 1);
        assertThat(testPerfil.getPerfil()).isEqualTo(DEFAULT_PERFIL);
    }

    @Test
    @Transactional
    public void createPerfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfilRepository.findAll().size();

        // Create the Perfil with an existing ID
        perfil.setId(1L);
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerfils() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList
        restPerfilMockMvc.perform(get("/api/perfils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].perfil").value(hasItem(DEFAULT_PERFIL.toString())));
    }
    
    @Test
    @Transactional
    public void getPerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get the perfil
        restPerfilMockMvc.perform(get("/api/perfils/{id}", perfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perfil.getId().intValue()))
            .andExpect(jsonPath("$.perfil").value(DEFAULT_PERFIL.toString()));
    }

    @Test
    @Transactional
    public void getAllPerfilsByPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where perfil equals to DEFAULT_PERFIL
        defaultPerfilShouldBeFound("perfil.equals=" + DEFAULT_PERFIL);

        // Get all the perfilList where perfil equals to UPDATED_PERFIL
        defaultPerfilShouldNotBeFound("perfil.equals=" + UPDATED_PERFIL);
    }

    @Test
    @Transactional
    public void getAllPerfilsByPerfilIsInShouldWork() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where perfil in DEFAULT_PERFIL or UPDATED_PERFIL
        defaultPerfilShouldBeFound("perfil.in=" + DEFAULT_PERFIL + "," + UPDATED_PERFIL);

        // Get all the perfilList where perfil equals to UPDATED_PERFIL
        defaultPerfilShouldNotBeFound("perfil.in=" + UPDATED_PERFIL);
    }

    @Test
    @Transactional
    public void getAllPerfilsByPerfilIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList where perfil is not null
        defaultPerfilShouldBeFound("perfil.specified=true");

        // Get all the perfilList where perfil is null
        defaultPerfilShouldNotBeFound("perfil.specified=false");
    }

    @Test
    @Transactional
    public void getAllPerfilsByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        perfil.addCandidato(candidato);
        perfilRepository.saveAndFlush(perfil);
        Long candidatoId = candidato.getId();

        // Get all the perfilList where candidato equals to candidatoId
        defaultPerfilShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the perfilList where candidato equals to candidatoId + 1
        defaultPerfilShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllPerfilsByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        perfil.addRequerimiento(requerimiento);
        perfilRepository.saveAndFlush(perfil);
        Long requerimientoId = requerimiento.getId();

        // Get all the perfilList where requerimiento equals to requerimientoId
        defaultPerfilShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the perfilList where requerimiento equals to requerimientoId + 1
        defaultPerfilShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerfilShouldBeFound(String filter) throws Exception {
        restPerfilMockMvc.perform(get("/api/perfils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].perfil").value(hasItem(DEFAULT_PERFIL)));

        // Check, that the count call also returns 1
        restPerfilMockMvc.perform(get("/api/perfils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerfilShouldNotBeFound(String filter) throws Exception {
        restPerfilMockMvc.perform(get("/api/perfils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerfilMockMvc.perform(get("/api/perfils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPerfil() throws Exception {
        // Get the perfil
        restPerfilMockMvc.perform(get("/api/perfils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        int databaseSizeBeforeUpdate = perfilRepository.findAll().size();

        // Update the perfil
        Perfil updatedPerfil = perfilRepository.findById(perfil.getId()).get();
        // Disconnect from session so that the updates on updatedPerfil are not directly saved in db
        em.detach(updatedPerfil);
        updatedPerfil
            .perfil(UPDATED_PERFIL);
        PerfilDTO perfilDTO = perfilMapper.toDto(updatedPerfil);

        restPerfilMockMvc.perform(put("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isOk());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeUpdate);
        Perfil testPerfil = perfilList.get(perfilList.size() - 1);
        assertThat(testPerfil.getPerfil()).isEqualTo(UPDATED_PERFIL);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfil() throws Exception {
        int databaseSizeBeforeUpdate = perfilRepository.findAll().size();

        // Create the Perfil
        PerfilDTO perfilDTO = perfilMapper.toDto(perfil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilMockMvc.perform(put("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        int databaseSizeBeforeDelete = perfilRepository.findAll().size();

        // Delete the perfil
        restPerfilMockMvc.perform(delete("/api/perfils/{id}", perfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Perfil.class);
        Perfil perfil1 = new Perfil();
        perfil1.setId(1L);
        Perfil perfil2 = new Perfil();
        perfil2.setId(perfil1.getId());
        assertThat(perfil1).isEqualTo(perfil2);
        perfil2.setId(2L);
        assertThat(perfil1).isNotEqualTo(perfil2);
        perfil1.setId(null);
        assertThat(perfil1).isNotEqualTo(perfil2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilDTO.class);
        PerfilDTO perfilDTO1 = new PerfilDTO();
        perfilDTO1.setId(1L);
        PerfilDTO perfilDTO2 = new PerfilDTO();
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
        perfilDTO2.setId(perfilDTO1.getId());
        assertThat(perfilDTO1).isEqualTo(perfilDTO2);
        perfilDTO2.setId(2L);
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
        perfilDTO1.setId(null);
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(perfilMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(perfilMapper.fromId(null)).isNull();
    }
}
