package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.TipoPeriodo;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.TipoPeriodoRepository;
import com.kode.ts.service.TipoPeriodoService;
import com.kode.ts.service.dto.TipoPeriodoDTO;
import com.kode.ts.service.mapper.TipoPeriodoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.TipoPeriodoCriteria;
import com.kode.ts.service.TipoPeriodoQueryService;

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
 * Integration tests for the {@Link TipoPeriodoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class TipoPeriodoResourceIT {

    private static final String DEFAULT_PERIODO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO = "BBBBBBBBBB";

    @Autowired
    private TipoPeriodoRepository tipoPeriodoRepository;

    @Autowired
    private TipoPeriodoMapper tipoPeriodoMapper;

    @Autowired
    private TipoPeriodoService tipoPeriodoService;

    @Autowired
    private TipoPeriodoQueryService tipoPeriodoQueryService;

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

    private MockMvc restTipoPeriodoMockMvc;

    private TipoPeriodo tipoPeriodo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoPeriodoResource tipoPeriodoResource = new TipoPeriodoResource(tipoPeriodoService, tipoPeriodoQueryService);
        this.restTipoPeriodoMockMvc = MockMvcBuilders.standaloneSetup(tipoPeriodoResource)
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
    public static TipoPeriodo createEntity(EntityManager em) {
        TipoPeriodo tipoPeriodo = new TipoPeriodo()
            .periodo(DEFAULT_PERIODO);
        return tipoPeriodo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPeriodo createUpdatedEntity(EntityManager em) {
        TipoPeriodo tipoPeriodo = new TipoPeriodo()
            .periodo(UPDATED_PERIODO);
        return tipoPeriodo;
    }

    @BeforeEach
    public void initTest() {
        tipoPeriodo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoPeriodo() throws Exception {
        int databaseSizeBeforeCreate = tipoPeriodoRepository.findAll().size();

        // Create the TipoPeriodo
        TipoPeriodoDTO tipoPeriodoDTO = tipoPeriodoMapper.toDto(tipoPeriodo);
        restTipoPeriodoMockMvc.perform(post("/api/tipo-periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPeriodoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoPeriodo in the database
        List<TipoPeriodo> tipoPeriodoList = tipoPeriodoRepository.findAll();
        assertThat(tipoPeriodoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoPeriodo testTipoPeriodo = tipoPeriodoList.get(tipoPeriodoList.size() - 1);
        assertThat(testTipoPeriodo.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
    }

    @Test
    @Transactional
    public void createTipoPeriodoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoPeriodoRepository.findAll().size();

        // Create the TipoPeriodo with an existing ID
        tipoPeriodo.setId(1L);
        TipoPeriodoDTO tipoPeriodoDTO = tipoPeriodoMapper.toDto(tipoPeriodo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPeriodoMockMvc.perform(post("/api/tipo-periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPeriodoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPeriodo in the database
        List<TipoPeriodo> tipoPeriodoList = tipoPeriodoRepository.findAll();
        assertThat(tipoPeriodoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoPeriodos() throws Exception {
        // Initialize the database
        tipoPeriodoRepository.saveAndFlush(tipoPeriodo);

        // Get all the tipoPeriodoList
        restTipoPeriodoMockMvc.perform(get("/api/tipo-periodos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPeriodo.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoPeriodo() throws Exception {
        // Initialize the database
        tipoPeriodoRepository.saveAndFlush(tipoPeriodo);

        // Get the tipoPeriodo
        restTipoPeriodoMockMvc.perform(get("/api/tipo-periodos/{id}", tipoPeriodo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPeriodo.getId().intValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoPeriodosByPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoPeriodoRepository.saveAndFlush(tipoPeriodo);

        // Get all the tipoPeriodoList where periodo equals to DEFAULT_PERIODO
        defaultTipoPeriodoShouldBeFound("periodo.equals=" + DEFAULT_PERIODO);

        // Get all the tipoPeriodoList where periodo equals to UPDATED_PERIODO
        defaultTipoPeriodoShouldNotBeFound("periodo.equals=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    public void getAllTipoPeriodosByPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoPeriodoRepository.saveAndFlush(tipoPeriodo);

        // Get all the tipoPeriodoList where periodo in DEFAULT_PERIODO or UPDATED_PERIODO
        defaultTipoPeriodoShouldBeFound("periodo.in=" + DEFAULT_PERIODO + "," + UPDATED_PERIODO);

        // Get all the tipoPeriodoList where periodo equals to UPDATED_PERIODO
        defaultTipoPeriodoShouldNotBeFound("periodo.in=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    public void getAllTipoPeriodosByPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoPeriodoRepository.saveAndFlush(tipoPeriodo);

        // Get all the tipoPeriodoList where periodo is not null
        defaultTipoPeriodoShouldBeFound("periodo.specified=true");

        // Get all the tipoPeriodoList where periodo is null
        defaultTipoPeriodoShouldNotBeFound("periodo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoPeriodosByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        tipoPeriodo.addRequerimiento(requerimiento);
        tipoPeriodoRepository.saveAndFlush(tipoPeriodo);
        Long requerimientoId = requerimiento.getId();

        // Get all the tipoPeriodoList where requerimiento equals to requerimientoId
        defaultTipoPeriodoShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the tipoPeriodoList where requerimiento equals to requerimientoId + 1
        defaultTipoPeriodoShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoPeriodoShouldBeFound(String filter) throws Exception {
        restTipoPeriodoMockMvc.perform(get("/api/tipo-periodos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPeriodo.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)));

        // Check, that the count call also returns 1
        restTipoPeriodoMockMvc.perform(get("/api/tipo-periodos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoPeriodoShouldNotBeFound(String filter) throws Exception {
        restTipoPeriodoMockMvc.perform(get("/api/tipo-periodos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoPeriodoMockMvc.perform(get("/api/tipo-periodos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoPeriodo() throws Exception {
        // Get the tipoPeriodo
        restTipoPeriodoMockMvc.perform(get("/api/tipo-periodos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoPeriodo() throws Exception {
        // Initialize the database
        tipoPeriodoRepository.saveAndFlush(tipoPeriodo);

        int databaseSizeBeforeUpdate = tipoPeriodoRepository.findAll().size();

        // Update the tipoPeriodo
        TipoPeriodo updatedTipoPeriodo = tipoPeriodoRepository.findById(tipoPeriodo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoPeriodo are not directly saved in db
        em.detach(updatedTipoPeriodo);
        updatedTipoPeriodo
            .periodo(UPDATED_PERIODO);
        TipoPeriodoDTO tipoPeriodoDTO = tipoPeriodoMapper.toDto(updatedTipoPeriodo);

        restTipoPeriodoMockMvc.perform(put("/api/tipo-periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPeriodoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoPeriodo in the database
        List<TipoPeriodo> tipoPeriodoList = tipoPeriodoRepository.findAll();
        assertThat(tipoPeriodoList).hasSize(databaseSizeBeforeUpdate);
        TipoPeriodo testTipoPeriodo = tipoPeriodoList.get(tipoPeriodoList.size() - 1);
        assertThat(testTipoPeriodo.getPeriodo()).isEqualTo(UPDATED_PERIODO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = tipoPeriodoRepository.findAll().size();

        // Create the TipoPeriodo
        TipoPeriodoDTO tipoPeriodoDTO = tipoPeriodoMapper.toDto(tipoPeriodo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPeriodoMockMvc.perform(put("/api/tipo-periodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPeriodoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPeriodo in the database
        List<TipoPeriodo> tipoPeriodoList = tipoPeriodoRepository.findAll();
        assertThat(tipoPeriodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoPeriodo() throws Exception {
        // Initialize the database
        tipoPeriodoRepository.saveAndFlush(tipoPeriodo);

        int databaseSizeBeforeDelete = tipoPeriodoRepository.findAll().size();

        // Delete the tipoPeriodo
        restTipoPeriodoMockMvc.perform(delete("/api/tipo-periodos/{id}", tipoPeriodo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoPeriodo> tipoPeriodoList = tipoPeriodoRepository.findAll();
        assertThat(tipoPeriodoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPeriodo.class);
        TipoPeriodo tipoPeriodo1 = new TipoPeriodo();
        tipoPeriodo1.setId(1L);
        TipoPeriodo tipoPeriodo2 = new TipoPeriodo();
        tipoPeriodo2.setId(tipoPeriodo1.getId());
        assertThat(tipoPeriodo1).isEqualTo(tipoPeriodo2);
        tipoPeriodo2.setId(2L);
        assertThat(tipoPeriodo1).isNotEqualTo(tipoPeriodo2);
        tipoPeriodo1.setId(null);
        assertThat(tipoPeriodo1).isNotEqualTo(tipoPeriodo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPeriodoDTO.class);
        TipoPeriodoDTO tipoPeriodoDTO1 = new TipoPeriodoDTO();
        tipoPeriodoDTO1.setId(1L);
        TipoPeriodoDTO tipoPeriodoDTO2 = new TipoPeriodoDTO();
        assertThat(tipoPeriodoDTO1).isNotEqualTo(tipoPeriodoDTO2);
        tipoPeriodoDTO2.setId(tipoPeriodoDTO1.getId());
        assertThat(tipoPeriodoDTO1).isEqualTo(tipoPeriodoDTO2);
        tipoPeriodoDTO2.setId(2L);
        assertThat(tipoPeriodoDTO1).isNotEqualTo(tipoPeriodoDTO2);
        tipoPeriodoDTO1.setId(null);
        assertThat(tipoPeriodoDTO1).isNotEqualTo(tipoPeriodoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoPeriodoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoPeriodoMapper.fromId(null)).isNull();
    }
}
