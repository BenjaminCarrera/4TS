package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.FormacionAcademica;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.FormacionAcademicaRepository;
import com.kode.ts.service.FormacionAcademicaService;
import com.kode.ts.service.dto.FormacionAcademicaDTO;
import com.kode.ts.service.mapper.FormacionAcademicaMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.FormacionAcademicaCriteria;
import com.kode.ts.service.FormacionAcademicaQueryService;

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
 * Integration tests for the {@Link FormacionAcademicaResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class FormacionAcademicaResourceIT {

    private static final String DEFAULT_FORMACION_ACADEMICA = "AAAAAAAAAA";
    private static final String UPDATED_FORMACION_ACADEMICA = "BBBBBBBBBB";

    @Autowired
    private FormacionAcademicaRepository formacionAcademicaRepository;

    @Autowired
    private FormacionAcademicaMapper formacionAcademicaMapper;

    @Autowired
    private FormacionAcademicaService formacionAcademicaService;

    @Autowired
    private FormacionAcademicaQueryService formacionAcademicaQueryService;

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

    private MockMvc restFormacionAcademicaMockMvc;

    private FormacionAcademica formacionAcademica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormacionAcademicaResource formacionAcademicaResource = new FormacionAcademicaResource(formacionAcademicaService, formacionAcademicaQueryService);
        this.restFormacionAcademicaMockMvc = MockMvcBuilders.standaloneSetup(formacionAcademicaResource)
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
    public static FormacionAcademica createEntity(EntityManager em) {
        FormacionAcademica formacionAcademica = new FormacionAcademica()
            .formacionAcademica(DEFAULT_FORMACION_ACADEMICA);
        return formacionAcademica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormacionAcademica createUpdatedEntity(EntityManager em) {
        FormacionAcademica formacionAcademica = new FormacionAcademica()
            .formacionAcademica(UPDATED_FORMACION_ACADEMICA);
        return formacionAcademica;
    }

    @BeforeEach
    public void initTest() {
        formacionAcademica = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormacionAcademica() throws Exception {
        int databaseSizeBeforeCreate = formacionAcademicaRepository.findAll().size();

        // Create the FormacionAcademica
        FormacionAcademicaDTO formacionAcademicaDTO = formacionAcademicaMapper.toDto(formacionAcademica);
        restFormacionAcademicaMockMvc.perform(post("/api/formacion-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formacionAcademicaDTO)))
            .andExpect(status().isCreated());

        // Validate the FormacionAcademica in the database
        List<FormacionAcademica> formacionAcademicaList = formacionAcademicaRepository.findAll();
        assertThat(formacionAcademicaList).hasSize(databaseSizeBeforeCreate + 1);
        FormacionAcademica testFormacionAcademica = formacionAcademicaList.get(formacionAcademicaList.size() - 1);
        assertThat(testFormacionAcademica.getFormacionAcademica()).isEqualTo(DEFAULT_FORMACION_ACADEMICA);
    }

    @Test
    @Transactional
    public void createFormacionAcademicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formacionAcademicaRepository.findAll().size();

        // Create the FormacionAcademica with an existing ID
        formacionAcademica.setId(1L);
        FormacionAcademicaDTO formacionAcademicaDTO = formacionAcademicaMapper.toDto(formacionAcademica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormacionAcademicaMockMvc.perform(post("/api/formacion-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formacionAcademicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormacionAcademica in the database
        List<FormacionAcademica> formacionAcademicaList = formacionAcademicaRepository.findAll();
        assertThat(formacionAcademicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFormacionAcademicas() throws Exception {
        // Initialize the database
        formacionAcademicaRepository.saveAndFlush(formacionAcademica);

        // Get all the formacionAcademicaList
        restFormacionAcademicaMockMvc.perform(get("/api/formacion-academicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formacionAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].formacionAcademica").value(hasItem(DEFAULT_FORMACION_ACADEMICA.toString())));
    }
    
    @Test
    @Transactional
    public void getFormacionAcademica() throws Exception {
        // Initialize the database
        formacionAcademicaRepository.saveAndFlush(formacionAcademica);

        // Get the formacionAcademica
        restFormacionAcademicaMockMvc.perform(get("/api/formacion-academicas/{id}", formacionAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formacionAcademica.getId().intValue()))
            .andExpect(jsonPath("$.formacionAcademica").value(DEFAULT_FORMACION_ACADEMICA.toString()));
    }

    @Test
    @Transactional
    public void getAllFormacionAcademicasByFormacionAcademicaIsEqualToSomething() throws Exception {
        // Initialize the database
        formacionAcademicaRepository.saveAndFlush(formacionAcademica);

        // Get all the formacionAcademicaList where formacionAcademica equals to DEFAULT_FORMACION_ACADEMICA
        defaultFormacionAcademicaShouldBeFound("formacionAcademica.equals=" + DEFAULT_FORMACION_ACADEMICA);

        // Get all the formacionAcademicaList where formacionAcademica equals to UPDATED_FORMACION_ACADEMICA
        defaultFormacionAcademicaShouldNotBeFound("formacionAcademica.equals=" + UPDATED_FORMACION_ACADEMICA);
    }

    @Test
    @Transactional
    public void getAllFormacionAcademicasByFormacionAcademicaIsInShouldWork() throws Exception {
        // Initialize the database
        formacionAcademicaRepository.saveAndFlush(formacionAcademica);

        // Get all the formacionAcademicaList where formacionAcademica in DEFAULT_FORMACION_ACADEMICA or UPDATED_FORMACION_ACADEMICA
        defaultFormacionAcademicaShouldBeFound("formacionAcademica.in=" + DEFAULT_FORMACION_ACADEMICA + "," + UPDATED_FORMACION_ACADEMICA);

        // Get all the formacionAcademicaList where formacionAcademica equals to UPDATED_FORMACION_ACADEMICA
        defaultFormacionAcademicaShouldNotBeFound("formacionAcademica.in=" + UPDATED_FORMACION_ACADEMICA);
    }

    @Test
    @Transactional
    public void getAllFormacionAcademicasByFormacionAcademicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        formacionAcademicaRepository.saveAndFlush(formacionAcademica);

        // Get all the formacionAcademicaList where formacionAcademica is not null
        defaultFormacionAcademicaShouldBeFound("formacionAcademica.specified=true");

        // Get all the formacionAcademicaList where formacionAcademica is null
        defaultFormacionAcademicaShouldNotBeFound("formacionAcademica.specified=false");
    }

    @Test
    @Transactional
    public void getAllFormacionAcademicasByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        formacionAcademica.addCandidato(candidato);
        formacionAcademicaRepository.saveAndFlush(formacionAcademica);
        Long candidatoId = candidato.getId();

        // Get all the formacionAcademicaList where candidato equals to candidatoId
        defaultFormacionAcademicaShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the formacionAcademicaList where candidato equals to candidatoId + 1
        defaultFormacionAcademicaShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFormacionAcademicaShouldBeFound(String filter) throws Exception {
        restFormacionAcademicaMockMvc.perform(get("/api/formacion-academicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formacionAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].formacionAcademica").value(hasItem(DEFAULT_FORMACION_ACADEMICA)));

        // Check, that the count call also returns 1
        restFormacionAcademicaMockMvc.perform(get("/api/formacion-academicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFormacionAcademicaShouldNotBeFound(String filter) throws Exception {
        restFormacionAcademicaMockMvc.perform(get("/api/formacion-academicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFormacionAcademicaMockMvc.perform(get("/api/formacion-academicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFormacionAcademica() throws Exception {
        // Get the formacionAcademica
        restFormacionAcademicaMockMvc.perform(get("/api/formacion-academicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormacionAcademica() throws Exception {
        // Initialize the database
        formacionAcademicaRepository.saveAndFlush(formacionAcademica);

        int databaseSizeBeforeUpdate = formacionAcademicaRepository.findAll().size();

        // Update the formacionAcademica
        FormacionAcademica updatedFormacionAcademica = formacionAcademicaRepository.findById(formacionAcademica.getId()).get();
        // Disconnect from session so that the updates on updatedFormacionAcademica are not directly saved in db
        em.detach(updatedFormacionAcademica);
        updatedFormacionAcademica
            .formacionAcademica(UPDATED_FORMACION_ACADEMICA);
        FormacionAcademicaDTO formacionAcademicaDTO = formacionAcademicaMapper.toDto(updatedFormacionAcademica);

        restFormacionAcademicaMockMvc.perform(put("/api/formacion-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formacionAcademicaDTO)))
            .andExpect(status().isOk());

        // Validate the FormacionAcademica in the database
        List<FormacionAcademica> formacionAcademicaList = formacionAcademicaRepository.findAll();
        assertThat(formacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
        FormacionAcademica testFormacionAcademica = formacionAcademicaList.get(formacionAcademicaList.size() - 1);
        assertThat(testFormacionAcademica.getFormacionAcademica()).isEqualTo(UPDATED_FORMACION_ACADEMICA);
    }

    @Test
    @Transactional
    public void updateNonExistingFormacionAcademica() throws Exception {
        int databaseSizeBeforeUpdate = formacionAcademicaRepository.findAll().size();

        // Create the FormacionAcademica
        FormacionAcademicaDTO formacionAcademicaDTO = formacionAcademicaMapper.toDto(formacionAcademica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormacionAcademicaMockMvc.perform(put("/api/formacion-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formacionAcademicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormacionAcademica in the database
        List<FormacionAcademica> formacionAcademicaList = formacionAcademicaRepository.findAll();
        assertThat(formacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormacionAcademica() throws Exception {
        // Initialize the database
        formacionAcademicaRepository.saveAndFlush(formacionAcademica);

        int databaseSizeBeforeDelete = formacionAcademicaRepository.findAll().size();

        // Delete the formacionAcademica
        restFormacionAcademicaMockMvc.perform(delete("/api/formacion-academicas/{id}", formacionAcademica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormacionAcademica> formacionAcademicaList = formacionAcademicaRepository.findAll();
        assertThat(formacionAcademicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormacionAcademica.class);
        FormacionAcademica formacionAcademica1 = new FormacionAcademica();
        formacionAcademica1.setId(1L);
        FormacionAcademica formacionAcademica2 = new FormacionAcademica();
        formacionAcademica2.setId(formacionAcademica1.getId());
        assertThat(formacionAcademica1).isEqualTo(formacionAcademica2);
        formacionAcademica2.setId(2L);
        assertThat(formacionAcademica1).isNotEqualTo(formacionAcademica2);
        formacionAcademica1.setId(null);
        assertThat(formacionAcademica1).isNotEqualTo(formacionAcademica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormacionAcademicaDTO.class);
        FormacionAcademicaDTO formacionAcademicaDTO1 = new FormacionAcademicaDTO();
        formacionAcademicaDTO1.setId(1L);
        FormacionAcademicaDTO formacionAcademicaDTO2 = new FormacionAcademicaDTO();
        assertThat(formacionAcademicaDTO1).isNotEqualTo(formacionAcademicaDTO2);
        formacionAcademicaDTO2.setId(formacionAcademicaDTO1.getId());
        assertThat(formacionAcademicaDTO1).isEqualTo(formacionAcademicaDTO2);
        formacionAcademicaDTO2.setId(2L);
        assertThat(formacionAcademicaDTO1).isNotEqualTo(formacionAcademicaDTO2);
        formacionAcademicaDTO1.setId(null);
        assertThat(formacionAcademicaDTO1).isNotEqualTo(formacionAcademicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formacionAcademicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formacionAcademicaMapper.fromId(null)).isNull();
    }
}
