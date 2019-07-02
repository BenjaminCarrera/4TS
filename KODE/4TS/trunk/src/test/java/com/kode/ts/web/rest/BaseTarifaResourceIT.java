package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.BaseTarifa;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.BaseTarifaRepository;
import com.kode.ts.service.BaseTarifaService;
import com.kode.ts.service.dto.BaseTarifaDTO;
import com.kode.ts.service.mapper.BaseTarifaMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.BaseTarifaCriteria;
import com.kode.ts.service.BaseTarifaQueryService;

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
 * Integration tests for the {@Link BaseTarifaResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class BaseTarifaResourceIT {

    private static final String DEFAULT_BASE = "AAAAAAAAAA";
    private static final String UPDATED_BASE = "BBBBBBBBBB";

    @Autowired
    private BaseTarifaRepository baseTarifaRepository;

    @Autowired
    private BaseTarifaMapper baseTarifaMapper;

    @Autowired
    private BaseTarifaService baseTarifaService;

    @Autowired
    private BaseTarifaQueryService baseTarifaQueryService;

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

    private MockMvc restBaseTarifaMockMvc;

    private BaseTarifa baseTarifa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BaseTarifaResource baseTarifaResource = new BaseTarifaResource(baseTarifaService, baseTarifaQueryService);
        this.restBaseTarifaMockMvc = MockMvcBuilders.standaloneSetup(baseTarifaResource)
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
    public static BaseTarifa createEntity(EntityManager em) {
        BaseTarifa baseTarifa = new BaseTarifa()
            .base(DEFAULT_BASE);
        return baseTarifa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaseTarifa createUpdatedEntity(EntityManager em) {
        BaseTarifa baseTarifa = new BaseTarifa()
            .base(UPDATED_BASE);
        return baseTarifa;
    }

    @BeforeEach
    public void initTest() {
        baseTarifa = createEntity(em);
    }

    @Test
    @Transactional
    public void createBaseTarifa() throws Exception {
        int databaseSizeBeforeCreate = baseTarifaRepository.findAll().size();

        // Create the BaseTarifa
        BaseTarifaDTO baseTarifaDTO = baseTarifaMapper.toDto(baseTarifa);
        restBaseTarifaMockMvc.perform(post("/api/base-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseTarifaDTO)))
            .andExpect(status().isCreated());

        // Validate the BaseTarifa in the database
        List<BaseTarifa> baseTarifaList = baseTarifaRepository.findAll();
        assertThat(baseTarifaList).hasSize(databaseSizeBeforeCreate + 1);
        BaseTarifa testBaseTarifa = baseTarifaList.get(baseTarifaList.size() - 1);
        assertThat(testBaseTarifa.getBase()).isEqualTo(DEFAULT_BASE);
    }

    @Test
    @Transactional
    public void createBaseTarifaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseTarifaRepository.findAll().size();

        // Create the BaseTarifa with an existing ID
        baseTarifa.setId(1L);
        BaseTarifaDTO baseTarifaDTO = baseTarifaMapper.toDto(baseTarifa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaseTarifaMockMvc.perform(post("/api/base-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseTarifaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BaseTarifa in the database
        List<BaseTarifa> baseTarifaList = baseTarifaRepository.findAll();
        assertThat(baseTarifaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBaseTarifas() throws Exception {
        // Initialize the database
        baseTarifaRepository.saveAndFlush(baseTarifa);

        // Get all the baseTarifaList
        restBaseTarifaMockMvc.perform(get("/api/base-tarifas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baseTarifa.getId().intValue())))
            .andExpect(jsonPath("$.[*].base").value(hasItem(DEFAULT_BASE.toString())));
    }
    
    @Test
    @Transactional
    public void getBaseTarifa() throws Exception {
        // Initialize the database
        baseTarifaRepository.saveAndFlush(baseTarifa);

        // Get the baseTarifa
        restBaseTarifaMockMvc.perform(get("/api/base-tarifas/{id}", baseTarifa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(baseTarifa.getId().intValue()))
            .andExpect(jsonPath("$.base").value(DEFAULT_BASE.toString()));
    }

    @Test
    @Transactional
    public void getAllBaseTarifasByBaseIsEqualToSomething() throws Exception {
        // Initialize the database
        baseTarifaRepository.saveAndFlush(baseTarifa);

        // Get all the baseTarifaList where base equals to DEFAULT_BASE
        defaultBaseTarifaShouldBeFound("base.equals=" + DEFAULT_BASE);

        // Get all the baseTarifaList where base equals to UPDATED_BASE
        defaultBaseTarifaShouldNotBeFound("base.equals=" + UPDATED_BASE);
    }

    @Test
    @Transactional
    public void getAllBaseTarifasByBaseIsInShouldWork() throws Exception {
        // Initialize the database
        baseTarifaRepository.saveAndFlush(baseTarifa);

        // Get all the baseTarifaList where base in DEFAULT_BASE or UPDATED_BASE
        defaultBaseTarifaShouldBeFound("base.in=" + DEFAULT_BASE + "," + UPDATED_BASE);

        // Get all the baseTarifaList where base equals to UPDATED_BASE
        defaultBaseTarifaShouldNotBeFound("base.in=" + UPDATED_BASE);
    }

    @Test
    @Transactional
    public void getAllBaseTarifasByBaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        baseTarifaRepository.saveAndFlush(baseTarifa);

        // Get all the baseTarifaList where base is not null
        defaultBaseTarifaShouldBeFound("base.specified=true");

        // Get all the baseTarifaList where base is null
        defaultBaseTarifaShouldNotBeFound("base.specified=false");
    }

    @Test
    @Transactional
    public void getAllBaseTarifasByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        baseTarifa.addRequerimiento(requerimiento);
        baseTarifaRepository.saveAndFlush(baseTarifa);
        Long requerimientoId = requerimiento.getId();

        // Get all the baseTarifaList where requerimiento equals to requerimientoId
        defaultBaseTarifaShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the baseTarifaList where requerimiento equals to requerimientoId + 1
        defaultBaseTarifaShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBaseTarifaShouldBeFound(String filter) throws Exception {
        restBaseTarifaMockMvc.perform(get("/api/base-tarifas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baseTarifa.getId().intValue())))
            .andExpect(jsonPath("$.[*].base").value(hasItem(DEFAULT_BASE)));

        // Check, that the count call also returns 1
        restBaseTarifaMockMvc.perform(get("/api/base-tarifas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBaseTarifaShouldNotBeFound(String filter) throws Exception {
        restBaseTarifaMockMvc.perform(get("/api/base-tarifas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBaseTarifaMockMvc.perform(get("/api/base-tarifas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBaseTarifa() throws Exception {
        // Get the baseTarifa
        restBaseTarifaMockMvc.perform(get("/api/base-tarifas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBaseTarifa() throws Exception {
        // Initialize the database
        baseTarifaRepository.saveAndFlush(baseTarifa);

        int databaseSizeBeforeUpdate = baseTarifaRepository.findAll().size();

        // Update the baseTarifa
        BaseTarifa updatedBaseTarifa = baseTarifaRepository.findById(baseTarifa.getId()).get();
        // Disconnect from session so that the updates on updatedBaseTarifa are not directly saved in db
        em.detach(updatedBaseTarifa);
        updatedBaseTarifa
            .base(UPDATED_BASE);
        BaseTarifaDTO baseTarifaDTO = baseTarifaMapper.toDto(updatedBaseTarifa);

        restBaseTarifaMockMvc.perform(put("/api/base-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseTarifaDTO)))
            .andExpect(status().isOk());

        // Validate the BaseTarifa in the database
        List<BaseTarifa> baseTarifaList = baseTarifaRepository.findAll();
        assertThat(baseTarifaList).hasSize(databaseSizeBeforeUpdate);
        BaseTarifa testBaseTarifa = baseTarifaList.get(baseTarifaList.size() - 1);
        assertThat(testBaseTarifa.getBase()).isEqualTo(UPDATED_BASE);
    }

    @Test
    @Transactional
    public void updateNonExistingBaseTarifa() throws Exception {
        int databaseSizeBeforeUpdate = baseTarifaRepository.findAll().size();

        // Create the BaseTarifa
        BaseTarifaDTO baseTarifaDTO = baseTarifaMapper.toDto(baseTarifa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseTarifaMockMvc.perform(put("/api/base-tarifas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseTarifaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BaseTarifa in the database
        List<BaseTarifa> baseTarifaList = baseTarifaRepository.findAll();
        assertThat(baseTarifaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBaseTarifa() throws Exception {
        // Initialize the database
        baseTarifaRepository.saveAndFlush(baseTarifa);

        int databaseSizeBeforeDelete = baseTarifaRepository.findAll().size();

        // Delete the baseTarifa
        restBaseTarifaMockMvc.perform(delete("/api/base-tarifas/{id}", baseTarifa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BaseTarifa> baseTarifaList = baseTarifaRepository.findAll();
        assertThat(baseTarifaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseTarifa.class);
        BaseTarifa baseTarifa1 = new BaseTarifa();
        baseTarifa1.setId(1L);
        BaseTarifa baseTarifa2 = new BaseTarifa();
        baseTarifa2.setId(baseTarifa1.getId());
        assertThat(baseTarifa1).isEqualTo(baseTarifa2);
        baseTarifa2.setId(2L);
        assertThat(baseTarifa1).isNotEqualTo(baseTarifa2);
        baseTarifa1.setId(null);
        assertThat(baseTarifa1).isNotEqualTo(baseTarifa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseTarifaDTO.class);
        BaseTarifaDTO baseTarifaDTO1 = new BaseTarifaDTO();
        baseTarifaDTO1.setId(1L);
        BaseTarifaDTO baseTarifaDTO2 = new BaseTarifaDTO();
        assertThat(baseTarifaDTO1).isNotEqualTo(baseTarifaDTO2);
        baseTarifaDTO2.setId(baseTarifaDTO1.getId());
        assertThat(baseTarifaDTO1).isEqualTo(baseTarifaDTO2);
        baseTarifaDTO2.setId(2L);
        assertThat(baseTarifaDTO1).isNotEqualTo(baseTarifaDTO2);
        baseTarifaDTO1.setId(null);
        assertThat(baseTarifaDTO1).isNotEqualTo(baseTarifaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(baseTarifaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(baseTarifaMapper.fromId(null)).isNull();
    }
}
