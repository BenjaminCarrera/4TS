package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.TipoTarea;
import com.kode.ts.domain.Tarea;
import com.kode.ts.repository.TipoTareaRepository;
import com.kode.ts.service.TipoTareaService;
import com.kode.ts.service.dto.TipoTareaDTO;
import com.kode.ts.service.mapper.TipoTareaMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.TipoTareaCriteria;
import com.kode.ts.service.TipoTareaQueryService;

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
 * Integration tests for the {@Link TipoTareaResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class TipoTareaResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private TipoTareaRepository tipoTareaRepository;

    @Autowired
    private TipoTareaMapper tipoTareaMapper;

    @Autowired
    private TipoTareaService tipoTareaService;

    @Autowired
    private TipoTareaQueryService tipoTareaQueryService;

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

    private MockMvc restTipoTareaMockMvc;

    private TipoTarea tipoTarea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoTareaResource tipoTareaResource = new TipoTareaResource(tipoTareaService, tipoTareaQueryService);
        this.restTipoTareaMockMvc = MockMvcBuilders.standaloneSetup(tipoTareaResource)
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
    public static TipoTarea createEntity(EntityManager em) {
        TipoTarea tipoTarea = new TipoTarea()
            .tipo(DEFAULT_TIPO);
        return tipoTarea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTarea createUpdatedEntity(EntityManager em) {
        TipoTarea tipoTarea = new TipoTarea()
            .tipo(UPDATED_TIPO);
        return tipoTarea;
    }

    @BeforeEach
    public void initTest() {
        tipoTarea = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoTarea() throws Exception {
        int databaseSizeBeforeCreate = tipoTareaRepository.findAll().size();

        // Create the TipoTarea
        TipoTareaDTO tipoTareaDTO = tipoTareaMapper.toDto(tipoTarea);
        restTipoTareaMockMvc.perform(post("/api/tipo-tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoTareaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoTarea in the database
        List<TipoTarea> tipoTareaList = tipoTareaRepository.findAll();
        assertThat(tipoTareaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoTarea testTipoTarea = tipoTareaList.get(tipoTareaList.size() - 1);
        assertThat(testTipoTarea.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createTipoTareaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoTareaRepository.findAll().size();

        // Create the TipoTarea with an existing ID
        tipoTarea.setId(1L);
        TipoTareaDTO tipoTareaDTO = tipoTareaMapper.toDto(tipoTarea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoTareaMockMvc.perform(post("/api/tipo-tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoTareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoTarea in the database
        List<TipoTarea> tipoTareaList = tipoTareaRepository.findAll();
        assertThat(tipoTareaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoTareas() throws Exception {
        // Initialize the database
        tipoTareaRepository.saveAndFlush(tipoTarea);

        // Get all the tipoTareaList
        restTipoTareaMockMvc.perform(get("/api/tipo-tareas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoTarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoTarea() throws Exception {
        // Initialize the database
        tipoTareaRepository.saveAndFlush(tipoTarea);

        // Get the tipoTarea
        restTipoTareaMockMvc.perform(get("/api/tipo-tareas/{id}", tipoTarea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoTarea.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoTareasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoTareaRepository.saveAndFlush(tipoTarea);

        // Get all the tipoTareaList where tipo equals to DEFAULT_TIPO
        defaultTipoTareaShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the tipoTareaList where tipo equals to UPDATED_TIPO
        defaultTipoTareaShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTipoTareasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoTareaRepository.saveAndFlush(tipoTarea);

        // Get all the tipoTareaList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultTipoTareaShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the tipoTareaList where tipo equals to UPDATED_TIPO
        defaultTipoTareaShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTipoTareasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoTareaRepository.saveAndFlush(tipoTarea);

        // Get all the tipoTareaList where tipo is not null
        defaultTipoTareaShouldBeFound("tipo.specified=true");

        // Get all the tipoTareaList where tipo is null
        defaultTipoTareaShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoTareasByTareaIsEqualToSomething() throws Exception {
        // Initialize the database
        Tarea tarea = TareaResourceIT.createEntity(em);
        em.persist(tarea);
        em.flush();
        tipoTarea.addTarea(tarea);
        tipoTareaRepository.saveAndFlush(tipoTarea);
        Long tareaId = tarea.getId();

        // Get all the tipoTareaList where tarea equals to tareaId
        defaultTipoTareaShouldBeFound("tareaId.equals=" + tareaId);

        // Get all the tipoTareaList where tarea equals to tareaId + 1
        defaultTipoTareaShouldNotBeFound("tareaId.equals=" + (tareaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoTareaShouldBeFound(String filter) throws Exception {
        restTipoTareaMockMvc.perform(get("/api/tipo-tareas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoTarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));

        // Check, that the count call also returns 1
        restTipoTareaMockMvc.perform(get("/api/tipo-tareas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoTareaShouldNotBeFound(String filter) throws Exception {
        restTipoTareaMockMvc.perform(get("/api/tipo-tareas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoTareaMockMvc.perform(get("/api/tipo-tareas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoTarea() throws Exception {
        // Get the tipoTarea
        restTipoTareaMockMvc.perform(get("/api/tipo-tareas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoTarea() throws Exception {
        // Initialize the database
        tipoTareaRepository.saveAndFlush(tipoTarea);

        int databaseSizeBeforeUpdate = tipoTareaRepository.findAll().size();

        // Update the tipoTarea
        TipoTarea updatedTipoTarea = tipoTareaRepository.findById(tipoTarea.getId()).get();
        // Disconnect from session so that the updates on updatedTipoTarea are not directly saved in db
        em.detach(updatedTipoTarea);
        updatedTipoTarea
            .tipo(UPDATED_TIPO);
        TipoTareaDTO tipoTareaDTO = tipoTareaMapper.toDto(updatedTipoTarea);

        restTipoTareaMockMvc.perform(put("/api/tipo-tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoTareaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoTarea in the database
        List<TipoTarea> tipoTareaList = tipoTareaRepository.findAll();
        assertThat(tipoTareaList).hasSize(databaseSizeBeforeUpdate);
        TipoTarea testTipoTarea = tipoTareaList.get(tipoTareaList.size() - 1);
        assertThat(testTipoTarea.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoTarea() throws Exception {
        int databaseSizeBeforeUpdate = tipoTareaRepository.findAll().size();

        // Create the TipoTarea
        TipoTareaDTO tipoTareaDTO = tipoTareaMapper.toDto(tipoTarea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoTareaMockMvc.perform(put("/api/tipo-tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoTareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoTarea in the database
        List<TipoTarea> tipoTareaList = tipoTareaRepository.findAll();
        assertThat(tipoTareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoTarea() throws Exception {
        // Initialize the database
        tipoTareaRepository.saveAndFlush(tipoTarea);

        int databaseSizeBeforeDelete = tipoTareaRepository.findAll().size();

        // Delete the tipoTarea
        restTipoTareaMockMvc.perform(delete("/api/tipo-tareas/{id}", tipoTarea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TipoTarea> tipoTareaList = tipoTareaRepository.findAll();
        assertThat(tipoTareaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoTarea.class);
        TipoTarea tipoTarea1 = new TipoTarea();
        tipoTarea1.setId(1L);
        TipoTarea tipoTarea2 = new TipoTarea();
        tipoTarea2.setId(tipoTarea1.getId());
        assertThat(tipoTarea1).isEqualTo(tipoTarea2);
        tipoTarea2.setId(2L);
        assertThat(tipoTarea1).isNotEqualTo(tipoTarea2);
        tipoTarea1.setId(null);
        assertThat(tipoTarea1).isNotEqualTo(tipoTarea2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoTareaDTO.class);
        TipoTareaDTO tipoTareaDTO1 = new TipoTareaDTO();
        tipoTareaDTO1.setId(1L);
        TipoTareaDTO tipoTareaDTO2 = new TipoTareaDTO();
        assertThat(tipoTareaDTO1).isNotEqualTo(tipoTareaDTO2);
        tipoTareaDTO2.setId(tipoTareaDTO1.getId());
        assertThat(tipoTareaDTO1).isEqualTo(tipoTareaDTO2);
        tipoTareaDTO2.setId(2L);
        assertThat(tipoTareaDTO1).isNotEqualTo(tipoTareaDTO2);
        tipoTareaDTO1.setId(null);
        assertThat(tipoTareaDTO1).isNotEqualTo(tipoTareaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoTareaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoTareaMapper.fromId(null)).isNull();
    }
}
