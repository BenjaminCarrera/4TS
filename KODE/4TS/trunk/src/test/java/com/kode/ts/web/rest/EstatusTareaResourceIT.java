package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstatusTarea;
import com.kode.ts.domain.Tarea;
import com.kode.ts.repository.EstatusTareaRepository;
import com.kode.ts.service.EstatusTareaService;
import com.kode.ts.service.dto.EstatusTareaDTO;
import com.kode.ts.service.mapper.EstatusTareaMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstatusTareaCriteria;
import com.kode.ts.service.EstatusTareaQueryService;

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
 * Integration tests for the {@Link EstatusTareaResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstatusTareaResourceIT {

    private static final String DEFAULT_ESTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ESTATUS = "BBBBBBBBBB";

    @Autowired
    private EstatusTareaRepository estatusTareaRepository;

    @Autowired
    private EstatusTareaMapper estatusTareaMapper;

    @Autowired
    private EstatusTareaService estatusTareaService;

    @Autowired
    private EstatusTareaQueryService estatusTareaQueryService;

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

    private MockMvc restEstatusTareaMockMvc;

    private EstatusTarea estatusTarea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusTareaResource estatusTareaResource = new EstatusTareaResource(estatusTareaService, estatusTareaQueryService);
        this.restEstatusTareaMockMvc = MockMvcBuilders.standaloneSetup(estatusTareaResource)
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
    public static EstatusTarea createEntity(EntityManager em) {
        EstatusTarea estatusTarea = new EstatusTarea()
            .estatus(DEFAULT_ESTATUS);
        return estatusTarea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusTarea createUpdatedEntity(EntityManager em) {
        EstatusTarea estatusTarea = new EstatusTarea()
            .estatus(UPDATED_ESTATUS);
        return estatusTarea;
    }

    @BeforeEach
    public void initTest() {
        estatusTarea = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusTarea() throws Exception {
        int databaseSizeBeforeCreate = estatusTareaRepository.findAll().size();

        // Create the EstatusTarea
        EstatusTareaDTO estatusTareaDTO = estatusTareaMapper.toDto(estatusTarea);
        restEstatusTareaMockMvc.perform(post("/api/estatus-tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusTareaDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusTarea in the database
        List<EstatusTarea> estatusTareaList = estatusTareaRepository.findAll();
        assertThat(estatusTareaList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusTarea testEstatusTarea = estatusTareaList.get(estatusTareaList.size() - 1);
        assertThat(testEstatusTarea.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createEstatusTareaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusTareaRepository.findAll().size();

        // Create the EstatusTarea with an existing ID
        estatusTarea.setId(1L);
        EstatusTareaDTO estatusTareaDTO = estatusTareaMapper.toDto(estatusTarea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusTareaMockMvc.perform(post("/api/estatus-tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusTareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusTarea in the database
        List<EstatusTarea> estatusTareaList = estatusTareaRepository.findAll();
        assertThat(estatusTareaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstatusTareas() throws Exception {
        // Initialize the database
        estatusTareaRepository.saveAndFlush(estatusTarea);

        // Get all the estatusTareaList
        restEstatusTareaMockMvc.perform(get("/api/estatus-tareas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusTarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusTarea() throws Exception {
        // Initialize the database
        estatusTareaRepository.saveAndFlush(estatusTarea);

        // Get the estatusTarea
        restEstatusTareaMockMvc.perform(get("/api/estatus-tareas/{id}", estatusTarea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusTarea.getId().intValue()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllEstatusTareasByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        estatusTareaRepository.saveAndFlush(estatusTarea);

        // Get all the estatusTareaList where estatus equals to DEFAULT_ESTATUS
        defaultEstatusTareaShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the estatusTareaList where estatus equals to UPDATED_ESTATUS
        defaultEstatusTareaShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusTareasByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        estatusTareaRepository.saveAndFlush(estatusTarea);

        // Get all the estatusTareaList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultEstatusTareaShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the estatusTareaList where estatus equals to UPDATED_ESTATUS
        defaultEstatusTareaShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusTareasByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        estatusTareaRepository.saveAndFlush(estatusTarea);

        // Get all the estatusTareaList where estatus is not null
        defaultEstatusTareaShouldBeFound("estatus.specified=true");

        // Get all the estatusTareaList where estatus is null
        defaultEstatusTareaShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstatusTareasByTareaIsEqualToSomething() throws Exception {
        // Initialize the database
        Tarea tarea = TareaResourceIT.createEntity(em);
        em.persist(tarea);
        em.flush();
        estatusTarea.addTarea(tarea);
        estatusTareaRepository.saveAndFlush(estatusTarea);
        Long tareaId = tarea.getId();

        // Get all the estatusTareaList where tarea equals to tareaId
        defaultEstatusTareaShouldBeFound("tareaId.equals=" + tareaId);

        // Get all the estatusTareaList where tarea equals to tareaId + 1
        defaultEstatusTareaShouldNotBeFound("tareaId.equals=" + (tareaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstatusTareaShouldBeFound(String filter) throws Exception {
        restEstatusTareaMockMvc.perform(get("/api/estatus-tareas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusTarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));

        // Check, that the count call also returns 1
        restEstatusTareaMockMvc.perform(get("/api/estatus-tareas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstatusTareaShouldNotBeFound(String filter) throws Exception {
        restEstatusTareaMockMvc.perform(get("/api/estatus-tareas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstatusTareaMockMvc.perform(get("/api/estatus-tareas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstatusTarea() throws Exception {
        // Get the estatusTarea
        restEstatusTareaMockMvc.perform(get("/api/estatus-tareas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusTarea() throws Exception {
        // Initialize the database
        estatusTareaRepository.saveAndFlush(estatusTarea);

        int databaseSizeBeforeUpdate = estatusTareaRepository.findAll().size();

        // Update the estatusTarea
        EstatusTarea updatedEstatusTarea = estatusTareaRepository.findById(estatusTarea.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusTarea are not directly saved in db
        em.detach(updatedEstatusTarea);
        updatedEstatusTarea
            .estatus(UPDATED_ESTATUS);
        EstatusTareaDTO estatusTareaDTO = estatusTareaMapper.toDto(updatedEstatusTarea);

        restEstatusTareaMockMvc.perform(put("/api/estatus-tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusTareaDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusTarea in the database
        List<EstatusTarea> estatusTareaList = estatusTareaRepository.findAll();
        assertThat(estatusTareaList).hasSize(databaseSizeBeforeUpdate);
        EstatusTarea testEstatusTarea = estatusTareaList.get(estatusTareaList.size() - 1);
        assertThat(testEstatusTarea.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusTarea() throws Exception {
        int databaseSizeBeforeUpdate = estatusTareaRepository.findAll().size();

        // Create the EstatusTarea
        EstatusTareaDTO estatusTareaDTO = estatusTareaMapper.toDto(estatusTarea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusTareaMockMvc.perform(put("/api/estatus-tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusTareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusTarea in the database
        List<EstatusTarea> estatusTareaList = estatusTareaRepository.findAll();
        assertThat(estatusTareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstatusTarea() throws Exception {
        // Initialize the database
        estatusTareaRepository.saveAndFlush(estatusTarea);

        int databaseSizeBeforeDelete = estatusTareaRepository.findAll().size();

        // Delete the estatusTarea
        restEstatusTareaMockMvc.perform(delete("/api/estatus-tareas/{id}", estatusTarea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstatusTarea> estatusTareaList = estatusTareaRepository.findAll();
        assertThat(estatusTareaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusTarea.class);
        EstatusTarea estatusTarea1 = new EstatusTarea();
        estatusTarea1.setId(1L);
        EstatusTarea estatusTarea2 = new EstatusTarea();
        estatusTarea2.setId(estatusTarea1.getId());
        assertThat(estatusTarea1).isEqualTo(estatusTarea2);
        estatusTarea2.setId(2L);
        assertThat(estatusTarea1).isNotEqualTo(estatusTarea2);
        estatusTarea1.setId(null);
        assertThat(estatusTarea1).isNotEqualTo(estatusTarea2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusTareaDTO.class);
        EstatusTareaDTO estatusTareaDTO1 = new EstatusTareaDTO();
        estatusTareaDTO1.setId(1L);
        EstatusTareaDTO estatusTareaDTO2 = new EstatusTareaDTO();
        assertThat(estatusTareaDTO1).isNotEqualTo(estatusTareaDTO2);
        estatusTareaDTO2.setId(estatusTareaDTO1.getId());
        assertThat(estatusTareaDTO1).isEqualTo(estatusTareaDTO2);
        estatusTareaDTO2.setId(2L);
        assertThat(estatusTareaDTO1).isNotEqualTo(estatusTareaDTO2);
        estatusTareaDTO1.setId(null);
        assertThat(estatusTareaDTO1).isNotEqualTo(estatusTareaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusTareaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusTareaMapper.fromId(null)).isNull();
    }
}
