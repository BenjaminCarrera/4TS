package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstatusRequerimiento;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.domain.EstReqCerrado;
import com.kode.ts.repository.EstatusRequerimientoRepository;
import com.kode.ts.service.EstatusRequerimientoService;
import com.kode.ts.service.dto.EstatusRequerimientoDTO;
import com.kode.ts.service.mapper.EstatusRequerimientoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstatusRequerimientoCriteria;
import com.kode.ts.service.EstatusRequerimientoQueryService;

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
 * Integration tests for the {@Link EstatusRequerimientoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstatusRequerimientoResourceIT {

    private static final String DEFAULT_ESTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ESTATUS = "BBBBBBBBBB";

    @Autowired
    private EstatusRequerimientoRepository estatusRequerimientoRepository;

    @Autowired
    private EstatusRequerimientoMapper estatusRequerimientoMapper;

    @Autowired
    private EstatusRequerimientoService estatusRequerimientoService;

    @Autowired
    private EstatusRequerimientoQueryService estatusRequerimientoQueryService;

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

    private MockMvc restEstatusRequerimientoMockMvc;

    private EstatusRequerimiento estatusRequerimiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusRequerimientoResource estatusRequerimientoResource = new EstatusRequerimientoResource(estatusRequerimientoService, estatusRequerimientoQueryService);
        this.restEstatusRequerimientoMockMvc = MockMvcBuilders.standaloneSetup(estatusRequerimientoResource)
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
    public static EstatusRequerimiento createEntity(EntityManager em) {
        EstatusRequerimiento estatusRequerimiento = new EstatusRequerimiento()
            .estatus(DEFAULT_ESTATUS);
        return estatusRequerimiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusRequerimiento createUpdatedEntity(EntityManager em) {
        EstatusRequerimiento estatusRequerimiento = new EstatusRequerimiento()
            .estatus(UPDATED_ESTATUS);
        return estatusRequerimiento;
    }

    @BeforeEach
    public void initTest() {
        estatusRequerimiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusRequerimiento() throws Exception {
        int databaseSizeBeforeCreate = estatusRequerimientoRepository.findAll().size();

        // Create the EstatusRequerimiento
        EstatusRequerimientoDTO estatusRequerimientoDTO = estatusRequerimientoMapper.toDto(estatusRequerimiento);
        restEstatusRequerimientoMockMvc.perform(post("/api/estatus-requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusRequerimientoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusRequerimiento in the database
        List<EstatusRequerimiento> estatusRequerimientoList = estatusRequerimientoRepository.findAll();
        assertThat(estatusRequerimientoList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusRequerimiento testEstatusRequerimiento = estatusRequerimientoList.get(estatusRequerimientoList.size() - 1);
        assertThat(testEstatusRequerimiento.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createEstatusRequerimientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusRequerimientoRepository.findAll().size();

        // Create the EstatusRequerimiento with an existing ID
        estatusRequerimiento.setId(1L);
        EstatusRequerimientoDTO estatusRequerimientoDTO = estatusRequerimientoMapper.toDto(estatusRequerimiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusRequerimientoMockMvc.perform(post("/api/estatus-requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusRequerimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusRequerimiento in the database
        List<EstatusRequerimiento> estatusRequerimientoList = estatusRequerimientoRepository.findAll();
        assertThat(estatusRequerimientoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstatusRequerimientos() throws Exception {
        // Initialize the database
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);

        // Get all the estatusRequerimientoList
        restEstatusRequerimientoMockMvc.perform(get("/api/estatus-requerimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusRequerimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusRequerimiento() throws Exception {
        // Initialize the database
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);

        // Get the estatusRequerimiento
        restEstatusRequerimientoMockMvc.perform(get("/api/estatus-requerimientos/{id}", estatusRequerimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusRequerimiento.getId().intValue()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllEstatusRequerimientosByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);

        // Get all the estatusRequerimientoList where estatus equals to DEFAULT_ESTATUS
        defaultEstatusRequerimientoShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the estatusRequerimientoList where estatus equals to UPDATED_ESTATUS
        defaultEstatusRequerimientoShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusRequerimientosByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);

        // Get all the estatusRequerimientoList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultEstatusRequerimientoShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the estatusRequerimientoList where estatus equals to UPDATED_ESTATUS
        defaultEstatusRequerimientoShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusRequerimientosByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);

        // Get all the estatusRequerimientoList where estatus is not null
        defaultEstatusRequerimientoShouldBeFound("estatus.specified=true");

        // Get all the estatusRequerimientoList where estatus is null
        defaultEstatusRequerimientoShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstatusRequerimientosByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        estatusRequerimiento.addRequerimiento(requerimiento);
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);
        Long requerimientoId = requerimiento.getId();

        // Get all the estatusRequerimientoList where requerimiento equals to requerimientoId
        defaultEstatusRequerimientoShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the estatusRequerimientoList where requerimiento equals to requerimientoId + 1
        defaultEstatusRequerimientoShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllEstatusRequerimientosByEstReqCerradoIsEqualToSomething() throws Exception {
        // Initialize the database
        EstReqCerrado estReqCerrado = EstReqCerradoResourceIT.createEntity(em);
        em.persist(estReqCerrado);
        em.flush();
        estatusRequerimiento.addEstReqCerrado(estReqCerrado);
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);
        Long estReqCerradoId = estReqCerrado.getId();

        // Get all the estatusRequerimientoList where estReqCerrado equals to estReqCerradoId
        defaultEstatusRequerimientoShouldBeFound("estReqCerradoId.equals=" + estReqCerradoId);

        // Get all the estatusRequerimientoList where estReqCerrado equals to estReqCerradoId + 1
        defaultEstatusRequerimientoShouldNotBeFound("estReqCerradoId.equals=" + (estReqCerradoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstatusRequerimientoShouldBeFound(String filter) throws Exception {
        restEstatusRequerimientoMockMvc.perform(get("/api/estatus-requerimientos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusRequerimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));

        // Check, that the count call also returns 1
        restEstatusRequerimientoMockMvc.perform(get("/api/estatus-requerimientos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstatusRequerimientoShouldNotBeFound(String filter) throws Exception {
        restEstatusRequerimientoMockMvc.perform(get("/api/estatus-requerimientos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstatusRequerimientoMockMvc.perform(get("/api/estatus-requerimientos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstatusRequerimiento() throws Exception {
        // Get the estatusRequerimiento
        restEstatusRequerimientoMockMvc.perform(get("/api/estatus-requerimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusRequerimiento() throws Exception {
        // Initialize the database
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);

        int databaseSizeBeforeUpdate = estatusRequerimientoRepository.findAll().size();

        // Update the estatusRequerimiento
        EstatusRequerimiento updatedEstatusRequerimiento = estatusRequerimientoRepository.findById(estatusRequerimiento.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusRequerimiento are not directly saved in db
        em.detach(updatedEstatusRequerimiento);
        updatedEstatusRequerimiento
            .estatus(UPDATED_ESTATUS);
        EstatusRequerimientoDTO estatusRequerimientoDTO = estatusRequerimientoMapper.toDto(updatedEstatusRequerimiento);

        restEstatusRequerimientoMockMvc.perform(put("/api/estatus-requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusRequerimientoDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusRequerimiento in the database
        List<EstatusRequerimiento> estatusRequerimientoList = estatusRequerimientoRepository.findAll();
        assertThat(estatusRequerimientoList).hasSize(databaseSizeBeforeUpdate);
        EstatusRequerimiento testEstatusRequerimiento = estatusRequerimientoList.get(estatusRequerimientoList.size() - 1);
        assertThat(testEstatusRequerimiento.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusRequerimiento() throws Exception {
        int databaseSizeBeforeUpdate = estatusRequerimientoRepository.findAll().size();

        // Create the EstatusRequerimiento
        EstatusRequerimientoDTO estatusRequerimientoDTO = estatusRequerimientoMapper.toDto(estatusRequerimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusRequerimientoMockMvc.perform(put("/api/estatus-requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusRequerimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusRequerimiento in the database
        List<EstatusRequerimiento> estatusRequerimientoList = estatusRequerimientoRepository.findAll();
        assertThat(estatusRequerimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstatusRequerimiento() throws Exception {
        // Initialize the database
        estatusRequerimientoRepository.saveAndFlush(estatusRequerimiento);

        int databaseSizeBeforeDelete = estatusRequerimientoRepository.findAll().size();

        // Delete the estatusRequerimiento
        restEstatusRequerimientoMockMvc.perform(delete("/api/estatus-requerimientos/{id}", estatusRequerimiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EstatusRequerimiento> estatusRequerimientoList = estatusRequerimientoRepository.findAll();
        assertThat(estatusRequerimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusRequerimiento.class);
        EstatusRequerimiento estatusRequerimiento1 = new EstatusRequerimiento();
        estatusRequerimiento1.setId(1L);
        EstatusRequerimiento estatusRequerimiento2 = new EstatusRequerimiento();
        estatusRequerimiento2.setId(estatusRequerimiento1.getId());
        assertThat(estatusRequerimiento1).isEqualTo(estatusRequerimiento2);
        estatusRequerimiento2.setId(2L);
        assertThat(estatusRequerimiento1).isNotEqualTo(estatusRequerimiento2);
        estatusRequerimiento1.setId(null);
        assertThat(estatusRequerimiento1).isNotEqualTo(estatusRequerimiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusRequerimientoDTO.class);
        EstatusRequerimientoDTO estatusRequerimientoDTO1 = new EstatusRequerimientoDTO();
        estatusRequerimientoDTO1.setId(1L);
        EstatusRequerimientoDTO estatusRequerimientoDTO2 = new EstatusRequerimientoDTO();
        assertThat(estatusRequerimientoDTO1).isNotEqualTo(estatusRequerimientoDTO2);
        estatusRequerimientoDTO2.setId(estatusRequerimientoDTO1.getId());
        assertThat(estatusRequerimientoDTO1).isEqualTo(estatusRequerimientoDTO2);
        estatusRequerimientoDTO2.setId(2L);
        assertThat(estatusRequerimientoDTO1).isNotEqualTo(estatusRequerimientoDTO2);
        estatusRequerimientoDTO1.setId(null);
        assertThat(estatusRequerimientoDTO1).isNotEqualTo(estatusRequerimientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusRequerimientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusRequerimientoMapper.fromId(null)).isNull();
    }
}
