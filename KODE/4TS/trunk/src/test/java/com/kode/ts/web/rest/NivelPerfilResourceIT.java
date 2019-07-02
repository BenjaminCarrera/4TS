package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.NivelPerfil;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.NivelPerfilRepository;
import com.kode.ts.service.NivelPerfilService;
import com.kode.ts.service.dto.NivelPerfilDTO;
import com.kode.ts.service.mapper.NivelPerfilMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.NivelPerfilCriteria;
import com.kode.ts.service.NivelPerfilQueryService;

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
 * Integration tests for the {@Link NivelPerfilResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class NivelPerfilResourceIT {

    private static final String DEFAULT_NIVEL = "AAAAAAAAAA";
    private static final String UPDATED_NIVEL = "BBBBBBBBBB";

    @Autowired
    private NivelPerfilRepository nivelPerfilRepository;

    @Autowired
    private NivelPerfilMapper nivelPerfilMapper;

    @Autowired
    private NivelPerfilService nivelPerfilService;

    @Autowired
    private NivelPerfilQueryService nivelPerfilQueryService;

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

    private MockMvc restNivelPerfilMockMvc;

    private NivelPerfil nivelPerfil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NivelPerfilResource nivelPerfilResource = new NivelPerfilResource(nivelPerfilService, nivelPerfilQueryService);
        this.restNivelPerfilMockMvc = MockMvcBuilders.standaloneSetup(nivelPerfilResource)
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
    public static NivelPerfil createEntity(EntityManager em) {
        NivelPerfil nivelPerfil = new NivelPerfil()
            .nivel(DEFAULT_NIVEL);
        return nivelPerfil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NivelPerfil createUpdatedEntity(EntityManager em) {
        NivelPerfil nivelPerfil = new NivelPerfil()
            .nivel(UPDATED_NIVEL);
        return nivelPerfil;
    }

    @BeforeEach
    public void initTest() {
        nivelPerfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createNivelPerfil() throws Exception {
        int databaseSizeBeforeCreate = nivelPerfilRepository.findAll().size();

        // Create the NivelPerfil
        NivelPerfilDTO nivelPerfilDTO = nivelPerfilMapper.toDto(nivelPerfil);
        restNivelPerfilMockMvc.perform(post("/api/nivel-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelPerfilDTO)))
            .andExpect(status().isCreated());

        // Validate the NivelPerfil in the database
        List<NivelPerfil> nivelPerfilList = nivelPerfilRepository.findAll();
        assertThat(nivelPerfilList).hasSize(databaseSizeBeforeCreate + 1);
        NivelPerfil testNivelPerfil = nivelPerfilList.get(nivelPerfilList.size() - 1);
        assertThat(testNivelPerfil.getNivel()).isEqualTo(DEFAULT_NIVEL);
    }

    @Test
    @Transactional
    public void createNivelPerfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nivelPerfilRepository.findAll().size();

        // Create the NivelPerfil with an existing ID
        nivelPerfil.setId(1L);
        NivelPerfilDTO nivelPerfilDTO = nivelPerfilMapper.toDto(nivelPerfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNivelPerfilMockMvc.perform(post("/api/nivel-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelPerfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NivelPerfil in the database
        List<NivelPerfil> nivelPerfilList = nivelPerfilRepository.findAll();
        assertThat(nivelPerfilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNivelPerfils() throws Exception {
        // Initialize the database
        nivelPerfilRepository.saveAndFlush(nivelPerfil);

        // Get all the nivelPerfilList
        restNivelPerfilMockMvc.perform(get("/api/nivel-perfils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivelPerfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getNivelPerfil() throws Exception {
        // Initialize the database
        nivelPerfilRepository.saveAndFlush(nivelPerfil);

        // Get the nivelPerfil
        restNivelPerfilMockMvc.perform(get("/api/nivel-perfils/{id}", nivelPerfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nivelPerfil.getId().intValue()))
            .andExpect(jsonPath("$.nivel").value(DEFAULT_NIVEL.toString()));
    }

    @Test
    @Transactional
    public void getAllNivelPerfilsByNivelIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelPerfilRepository.saveAndFlush(nivelPerfil);

        // Get all the nivelPerfilList where nivel equals to DEFAULT_NIVEL
        defaultNivelPerfilShouldBeFound("nivel.equals=" + DEFAULT_NIVEL);

        // Get all the nivelPerfilList where nivel equals to UPDATED_NIVEL
        defaultNivelPerfilShouldNotBeFound("nivel.equals=" + UPDATED_NIVEL);
    }

    @Test
    @Transactional
    public void getAllNivelPerfilsByNivelIsInShouldWork() throws Exception {
        // Initialize the database
        nivelPerfilRepository.saveAndFlush(nivelPerfil);

        // Get all the nivelPerfilList where nivel in DEFAULT_NIVEL or UPDATED_NIVEL
        defaultNivelPerfilShouldBeFound("nivel.in=" + DEFAULT_NIVEL + "," + UPDATED_NIVEL);

        // Get all the nivelPerfilList where nivel equals to UPDATED_NIVEL
        defaultNivelPerfilShouldNotBeFound("nivel.in=" + UPDATED_NIVEL);
    }

    @Test
    @Transactional
    public void getAllNivelPerfilsByNivelIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelPerfilRepository.saveAndFlush(nivelPerfil);

        // Get all the nivelPerfilList where nivel is not null
        defaultNivelPerfilShouldBeFound("nivel.specified=true");

        // Get all the nivelPerfilList where nivel is null
        defaultNivelPerfilShouldNotBeFound("nivel.specified=false");
    }

    @Test
    @Transactional
    public void getAllNivelPerfilsByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        nivelPerfil.addCandidato(candidato);
        nivelPerfilRepository.saveAndFlush(nivelPerfil);
        Long candidatoId = candidato.getId();

        // Get all the nivelPerfilList where candidato equals to candidatoId
        defaultNivelPerfilShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the nivelPerfilList where candidato equals to candidatoId + 1
        defaultNivelPerfilShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllNivelPerfilsByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        nivelPerfil.addRequerimiento(requerimiento);
        nivelPerfilRepository.saveAndFlush(nivelPerfil);
        Long requerimientoId = requerimiento.getId();

        // Get all the nivelPerfilList where requerimiento equals to requerimientoId
        defaultNivelPerfilShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the nivelPerfilList where requerimiento equals to requerimientoId + 1
        defaultNivelPerfilShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNivelPerfilShouldBeFound(String filter) throws Exception {
        restNivelPerfilMockMvc.perform(get("/api/nivel-perfils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivelPerfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL)));

        // Check, that the count call also returns 1
        restNivelPerfilMockMvc.perform(get("/api/nivel-perfils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNivelPerfilShouldNotBeFound(String filter) throws Exception {
        restNivelPerfilMockMvc.perform(get("/api/nivel-perfils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNivelPerfilMockMvc.perform(get("/api/nivel-perfils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNivelPerfil() throws Exception {
        // Get the nivelPerfil
        restNivelPerfilMockMvc.perform(get("/api/nivel-perfils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNivelPerfil() throws Exception {
        // Initialize the database
        nivelPerfilRepository.saveAndFlush(nivelPerfil);

        int databaseSizeBeforeUpdate = nivelPerfilRepository.findAll().size();

        // Update the nivelPerfil
        NivelPerfil updatedNivelPerfil = nivelPerfilRepository.findById(nivelPerfil.getId()).get();
        // Disconnect from session so that the updates on updatedNivelPerfil are not directly saved in db
        em.detach(updatedNivelPerfil);
        updatedNivelPerfil
            .nivel(UPDATED_NIVEL);
        NivelPerfilDTO nivelPerfilDTO = nivelPerfilMapper.toDto(updatedNivelPerfil);

        restNivelPerfilMockMvc.perform(put("/api/nivel-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelPerfilDTO)))
            .andExpect(status().isOk());

        // Validate the NivelPerfil in the database
        List<NivelPerfil> nivelPerfilList = nivelPerfilRepository.findAll();
        assertThat(nivelPerfilList).hasSize(databaseSizeBeforeUpdate);
        NivelPerfil testNivelPerfil = nivelPerfilList.get(nivelPerfilList.size() - 1);
        assertThat(testNivelPerfil.getNivel()).isEqualTo(UPDATED_NIVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingNivelPerfil() throws Exception {
        int databaseSizeBeforeUpdate = nivelPerfilRepository.findAll().size();

        // Create the NivelPerfil
        NivelPerfilDTO nivelPerfilDTO = nivelPerfilMapper.toDto(nivelPerfil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelPerfilMockMvc.perform(put("/api/nivel-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelPerfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NivelPerfil in the database
        List<NivelPerfil> nivelPerfilList = nivelPerfilRepository.findAll();
        assertThat(nivelPerfilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNivelPerfil() throws Exception {
        // Initialize the database
        nivelPerfilRepository.saveAndFlush(nivelPerfil);

        int databaseSizeBeforeDelete = nivelPerfilRepository.findAll().size();

        // Delete the nivelPerfil
        restNivelPerfilMockMvc.perform(delete("/api/nivel-perfils/{id}", nivelPerfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NivelPerfil> nivelPerfilList = nivelPerfilRepository.findAll();
        assertThat(nivelPerfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NivelPerfil.class);
        NivelPerfil nivelPerfil1 = new NivelPerfil();
        nivelPerfil1.setId(1L);
        NivelPerfil nivelPerfil2 = new NivelPerfil();
        nivelPerfil2.setId(nivelPerfil1.getId());
        assertThat(nivelPerfil1).isEqualTo(nivelPerfil2);
        nivelPerfil2.setId(2L);
        assertThat(nivelPerfil1).isNotEqualTo(nivelPerfil2);
        nivelPerfil1.setId(null);
        assertThat(nivelPerfil1).isNotEqualTo(nivelPerfil2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NivelPerfilDTO.class);
        NivelPerfilDTO nivelPerfilDTO1 = new NivelPerfilDTO();
        nivelPerfilDTO1.setId(1L);
        NivelPerfilDTO nivelPerfilDTO2 = new NivelPerfilDTO();
        assertThat(nivelPerfilDTO1).isNotEqualTo(nivelPerfilDTO2);
        nivelPerfilDTO2.setId(nivelPerfilDTO1.getId());
        assertThat(nivelPerfilDTO1).isEqualTo(nivelPerfilDTO2);
        nivelPerfilDTO2.setId(2L);
        assertThat(nivelPerfilDTO1).isNotEqualTo(nivelPerfilDTO2);
        nivelPerfilDTO1.setId(null);
        assertThat(nivelPerfilDTO1).isNotEqualTo(nivelPerfilDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nivelPerfilMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nivelPerfilMapper.fromId(null)).isNull();
    }
}
