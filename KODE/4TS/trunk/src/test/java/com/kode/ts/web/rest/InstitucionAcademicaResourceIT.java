package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.InstitucionAcademica;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.InstitucionAcademicaRepository;
import com.kode.ts.service.InstitucionAcademicaService;
import com.kode.ts.service.dto.InstitucionAcademicaDTO;
import com.kode.ts.service.mapper.InstitucionAcademicaMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.InstitucionAcademicaCriteria;
import com.kode.ts.service.InstitucionAcademicaQueryService;

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
 * Integration tests for the {@Link InstitucionAcademicaResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class InstitucionAcademicaResourceIT {

    private static final String DEFAULT_INSTITUCION = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUCION = "BBBBBBBBBB";

    @Autowired
    private InstitucionAcademicaRepository institucionAcademicaRepository;

    @Autowired
    private InstitucionAcademicaMapper institucionAcademicaMapper;

    @Autowired
    private InstitucionAcademicaService institucionAcademicaService;

    @Autowired
    private InstitucionAcademicaQueryService institucionAcademicaQueryService;

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

    private MockMvc restInstitucionAcademicaMockMvc;

    private InstitucionAcademica institucionAcademica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstitucionAcademicaResource institucionAcademicaResource = new InstitucionAcademicaResource(institucionAcademicaService, institucionAcademicaQueryService);
        this.restInstitucionAcademicaMockMvc = MockMvcBuilders.standaloneSetup(institucionAcademicaResource)
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
    public static InstitucionAcademica createEntity(EntityManager em) {
        InstitucionAcademica institucionAcademica = new InstitucionAcademica()
            .institucion(DEFAULT_INSTITUCION);
        return institucionAcademica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstitucionAcademica createUpdatedEntity(EntityManager em) {
        InstitucionAcademica institucionAcademica = new InstitucionAcademica()
            .institucion(UPDATED_INSTITUCION);
        return institucionAcademica;
    }

    @BeforeEach
    public void initTest() {
        institucionAcademica = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstitucionAcademica() throws Exception {
        int databaseSizeBeforeCreate = institucionAcademicaRepository.findAll().size();

        // Create the InstitucionAcademica
        InstitucionAcademicaDTO institucionAcademicaDTO = institucionAcademicaMapper.toDto(institucionAcademica);
        restInstitucionAcademicaMockMvc.perform(post("/api/institucion-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucionAcademicaDTO)))
            .andExpect(status().isCreated());

        // Validate the InstitucionAcademica in the database
        List<InstitucionAcademica> institucionAcademicaList = institucionAcademicaRepository.findAll();
        assertThat(institucionAcademicaList).hasSize(databaseSizeBeforeCreate + 1);
        InstitucionAcademica testInstitucionAcademica = institucionAcademicaList.get(institucionAcademicaList.size() - 1);
        assertThat(testInstitucionAcademica.getInstitucion()).isEqualTo(DEFAULT_INSTITUCION);
    }

    @Test
    @Transactional
    public void createInstitucionAcademicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = institucionAcademicaRepository.findAll().size();

        // Create the InstitucionAcademica with an existing ID
        institucionAcademica.setId(1L);
        InstitucionAcademicaDTO institucionAcademicaDTO = institucionAcademicaMapper.toDto(institucionAcademica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstitucionAcademicaMockMvc.perform(post("/api/institucion-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucionAcademicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstitucionAcademica in the database
        List<InstitucionAcademica> institucionAcademicaList = institucionAcademicaRepository.findAll();
        assertThat(institucionAcademicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstitucionAcademicas() throws Exception {
        // Initialize the database
        institucionAcademicaRepository.saveAndFlush(institucionAcademica);

        // Get all the institucionAcademicaList
        restInstitucionAcademicaMockMvc.perform(get("/api/institucion-academicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institucionAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].institucion").value(hasItem(DEFAULT_INSTITUCION.toString())));
    }
    
    @Test
    @Transactional
    public void getInstitucionAcademica() throws Exception {
        // Initialize the database
        institucionAcademicaRepository.saveAndFlush(institucionAcademica);

        // Get the institucionAcademica
        restInstitucionAcademicaMockMvc.perform(get("/api/institucion-academicas/{id}", institucionAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(institucionAcademica.getId().intValue()))
            .andExpect(jsonPath("$.institucion").value(DEFAULT_INSTITUCION.toString()));
    }

    @Test
    @Transactional
    public void getAllInstitucionAcademicasByInstitucionIsEqualToSomething() throws Exception {
        // Initialize the database
        institucionAcademicaRepository.saveAndFlush(institucionAcademica);

        // Get all the institucionAcademicaList where institucion equals to DEFAULT_INSTITUCION
        defaultInstitucionAcademicaShouldBeFound("institucion.equals=" + DEFAULT_INSTITUCION);

        // Get all the institucionAcademicaList where institucion equals to UPDATED_INSTITUCION
        defaultInstitucionAcademicaShouldNotBeFound("institucion.equals=" + UPDATED_INSTITUCION);
    }

    @Test
    @Transactional
    public void getAllInstitucionAcademicasByInstitucionIsInShouldWork() throws Exception {
        // Initialize the database
        institucionAcademicaRepository.saveAndFlush(institucionAcademica);

        // Get all the institucionAcademicaList where institucion in DEFAULT_INSTITUCION or UPDATED_INSTITUCION
        defaultInstitucionAcademicaShouldBeFound("institucion.in=" + DEFAULT_INSTITUCION + "," + UPDATED_INSTITUCION);

        // Get all the institucionAcademicaList where institucion equals to UPDATED_INSTITUCION
        defaultInstitucionAcademicaShouldNotBeFound("institucion.in=" + UPDATED_INSTITUCION);
    }

    @Test
    @Transactional
    public void getAllInstitucionAcademicasByInstitucionIsNullOrNotNull() throws Exception {
        // Initialize the database
        institucionAcademicaRepository.saveAndFlush(institucionAcademica);

        // Get all the institucionAcademicaList where institucion is not null
        defaultInstitucionAcademicaShouldBeFound("institucion.specified=true");

        // Get all the institucionAcademicaList where institucion is null
        defaultInstitucionAcademicaShouldNotBeFound("institucion.specified=false");
    }

    @Test
    @Transactional
    public void getAllInstitucionAcademicasByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        institucionAcademica.addCandidato(candidato);
        institucionAcademicaRepository.saveAndFlush(institucionAcademica);
        Long candidatoId = candidato.getId();

        // Get all the institucionAcademicaList where candidato equals to candidatoId
        defaultInstitucionAcademicaShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the institucionAcademicaList where candidato equals to candidatoId + 1
        defaultInstitucionAcademicaShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInstitucionAcademicaShouldBeFound(String filter) throws Exception {
        restInstitucionAcademicaMockMvc.perform(get("/api/institucion-academicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institucionAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].institucion").value(hasItem(DEFAULT_INSTITUCION)));

        // Check, that the count call also returns 1
        restInstitucionAcademicaMockMvc.perform(get("/api/institucion-academicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInstitucionAcademicaShouldNotBeFound(String filter) throws Exception {
        restInstitucionAcademicaMockMvc.perform(get("/api/institucion-academicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInstitucionAcademicaMockMvc.perform(get("/api/institucion-academicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInstitucionAcademica() throws Exception {
        // Get the institucionAcademica
        restInstitucionAcademicaMockMvc.perform(get("/api/institucion-academicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitucionAcademica() throws Exception {
        // Initialize the database
        institucionAcademicaRepository.saveAndFlush(institucionAcademica);

        int databaseSizeBeforeUpdate = institucionAcademicaRepository.findAll().size();

        // Update the institucionAcademica
        InstitucionAcademica updatedInstitucionAcademica = institucionAcademicaRepository.findById(institucionAcademica.getId()).get();
        // Disconnect from session so that the updates on updatedInstitucionAcademica are not directly saved in db
        em.detach(updatedInstitucionAcademica);
        updatedInstitucionAcademica
            .institucion(UPDATED_INSTITUCION);
        InstitucionAcademicaDTO institucionAcademicaDTO = institucionAcademicaMapper.toDto(updatedInstitucionAcademica);

        restInstitucionAcademicaMockMvc.perform(put("/api/institucion-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucionAcademicaDTO)))
            .andExpect(status().isOk());

        // Validate the InstitucionAcademica in the database
        List<InstitucionAcademica> institucionAcademicaList = institucionAcademicaRepository.findAll();
        assertThat(institucionAcademicaList).hasSize(databaseSizeBeforeUpdate);
        InstitucionAcademica testInstitucionAcademica = institucionAcademicaList.get(institucionAcademicaList.size() - 1);
        assertThat(testInstitucionAcademica.getInstitucion()).isEqualTo(UPDATED_INSTITUCION);
    }

    @Test
    @Transactional
    public void updateNonExistingInstitucionAcademica() throws Exception {
        int databaseSizeBeforeUpdate = institucionAcademicaRepository.findAll().size();

        // Create the InstitucionAcademica
        InstitucionAcademicaDTO institucionAcademicaDTO = institucionAcademicaMapper.toDto(institucionAcademica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstitucionAcademicaMockMvc.perform(put("/api/institucion-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucionAcademicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstitucionAcademica in the database
        List<InstitucionAcademica> institucionAcademicaList = institucionAcademicaRepository.findAll();
        assertThat(institucionAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstitucionAcademica() throws Exception {
        // Initialize the database
        institucionAcademicaRepository.saveAndFlush(institucionAcademica);

        int databaseSizeBeforeDelete = institucionAcademicaRepository.findAll().size();

        // Delete the institucionAcademica
        restInstitucionAcademicaMockMvc.perform(delete("/api/institucion-academicas/{id}", institucionAcademica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstitucionAcademica> institucionAcademicaList = institucionAcademicaRepository.findAll();
        assertThat(institucionAcademicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstitucionAcademica.class);
        InstitucionAcademica institucionAcademica1 = new InstitucionAcademica();
        institucionAcademica1.setId(1L);
        InstitucionAcademica institucionAcademica2 = new InstitucionAcademica();
        institucionAcademica2.setId(institucionAcademica1.getId());
        assertThat(institucionAcademica1).isEqualTo(institucionAcademica2);
        institucionAcademica2.setId(2L);
        assertThat(institucionAcademica1).isNotEqualTo(institucionAcademica2);
        institucionAcademica1.setId(null);
        assertThat(institucionAcademica1).isNotEqualTo(institucionAcademica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstitucionAcademicaDTO.class);
        InstitucionAcademicaDTO institucionAcademicaDTO1 = new InstitucionAcademicaDTO();
        institucionAcademicaDTO1.setId(1L);
        InstitucionAcademicaDTO institucionAcademicaDTO2 = new InstitucionAcademicaDTO();
        assertThat(institucionAcademicaDTO1).isNotEqualTo(institucionAcademicaDTO2);
        institucionAcademicaDTO2.setId(institucionAcademicaDTO1.getId());
        assertThat(institucionAcademicaDTO1).isEqualTo(institucionAcademicaDTO2);
        institucionAcademicaDTO2.setId(2L);
        assertThat(institucionAcademicaDTO1).isNotEqualTo(institucionAcademicaDTO2);
        institucionAcademicaDTO1.setId(null);
        assertThat(institucionAcademicaDTO1).isNotEqualTo(institucionAcademicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(institucionAcademicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(institucionAcademicaMapper.fromId(null)).isNull();
    }
}
