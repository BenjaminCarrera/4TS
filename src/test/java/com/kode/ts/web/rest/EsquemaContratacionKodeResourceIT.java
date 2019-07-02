package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EsquemaContratacionKode;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.EsquemaContratacionKodeRepository;
import com.kode.ts.service.EsquemaContratacionKodeService;
import com.kode.ts.service.dto.EsquemaContratacionKodeDTO;
import com.kode.ts.service.mapper.EsquemaContratacionKodeMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EsquemaContratacionKodeCriteria;
import com.kode.ts.service.EsquemaContratacionKodeQueryService;

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
 * Integration tests for the {@Link EsquemaContratacionKodeResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EsquemaContratacionKodeResourceIT {

    private static final String DEFAULT_ESQUEMA = "AAAAAAAAAA";
    private static final String UPDATED_ESQUEMA = "BBBBBBBBBB";

    @Autowired
    private EsquemaContratacionKodeRepository esquemaContratacionKodeRepository;

    @Autowired
    private EsquemaContratacionKodeMapper esquemaContratacionKodeMapper;

    @Autowired
    private EsquemaContratacionKodeService esquemaContratacionKodeService;

    @Autowired
    private EsquemaContratacionKodeQueryService esquemaContratacionKodeQueryService;

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

    private MockMvc restEsquemaContratacionKodeMockMvc;

    private EsquemaContratacionKode esquemaContratacionKode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EsquemaContratacionKodeResource esquemaContratacionKodeResource = new EsquemaContratacionKodeResource(esquemaContratacionKodeService, esquemaContratacionKodeQueryService);
        this.restEsquemaContratacionKodeMockMvc = MockMvcBuilders.standaloneSetup(esquemaContratacionKodeResource)
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
    public static EsquemaContratacionKode createEntity(EntityManager em) {
        EsquemaContratacionKode esquemaContratacionKode = new EsquemaContratacionKode()
            .esquema(DEFAULT_ESQUEMA);
        return esquemaContratacionKode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EsquemaContratacionKode createUpdatedEntity(EntityManager em) {
        EsquemaContratacionKode esquemaContratacionKode = new EsquemaContratacionKode()
            .esquema(UPDATED_ESQUEMA);
        return esquemaContratacionKode;
    }

    @BeforeEach
    public void initTest() {
        esquemaContratacionKode = createEntity(em);
    }

    @Test
    @Transactional
    public void createEsquemaContratacionKode() throws Exception {
        int databaseSizeBeforeCreate = esquemaContratacionKodeRepository.findAll().size();

        // Create the EsquemaContratacionKode
        EsquemaContratacionKodeDTO esquemaContratacionKodeDTO = esquemaContratacionKodeMapper.toDto(esquemaContratacionKode);
        restEsquemaContratacionKodeMockMvc.perform(post("/api/esquema-contratacion-kodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esquemaContratacionKodeDTO)))
            .andExpect(status().isCreated());

        // Validate the EsquemaContratacionKode in the database
        List<EsquemaContratacionKode> esquemaContratacionKodeList = esquemaContratacionKodeRepository.findAll();
        assertThat(esquemaContratacionKodeList).hasSize(databaseSizeBeforeCreate + 1);
        EsquemaContratacionKode testEsquemaContratacionKode = esquemaContratacionKodeList.get(esquemaContratacionKodeList.size() - 1);
        assertThat(testEsquemaContratacionKode.getEsquema()).isEqualTo(DEFAULT_ESQUEMA);
    }

    @Test
    @Transactional
    public void createEsquemaContratacionKodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = esquemaContratacionKodeRepository.findAll().size();

        // Create the EsquemaContratacionKode with an existing ID
        esquemaContratacionKode.setId(1L);
        EsquemaContratacionKodeDTO esquemaContratacionKodeDTO = esquemaContratacionKodeMapper.toDto(esquemaContratacionKode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEsquemaContratacionKodeMockMvc.perform(post("/api/esquema-contratacion-kodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esquemaContratacionKodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EsquemaContratacionKode in the database
        List<EsquemaContratacionKode> esquemaContratacionKodeList = esquemaContratacionKodeRepository.findAll();
        assertThat(esquemaContratacionKodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEsquemaContratacionKodes() throws Exception {
        // Initialize the database
        esquemaContratacionKodeRepository.saveAndFlush(esquemaContratacionKode);

        // Get all the esquemaContratacionKodeList
        restEsquemaContratacionKodeMockMvc.perform(get("/api/esquema-contratacion-kodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esquemaContratacionKode.getId().intValue())))
            .andExpect(jsonPath("$.[*].esquema").value(hasItem(DEFAULT_ESQUEMA.toString())));
    }
    
    @Test
    @Transactional
    public void getEsquemaContratacionKode() throws Exception {
        // Initialize the database
        esquemaContratacionKodeRepository.saveAndFlush(esquemaContratacionKode);

        // Get the esquemaContratacionKode
        restEsquemaContratacionKodeMockMvc.perform(get("/api/esquema-contratacion-kodes/{id}", esquemaContratacionKode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(esquemaContratacionKode.getId().intValue()))
            .andExpect(jsonPath("$.esquema").value(DEFAULT_ESQUEMA.toString()));
    }

    @Test
    @Transactional
    public void getAllEsquemaContratacionKodesByEsquemaIsEqualToSomething() throws Exception {
        // Initialize the database
        esquemaContratacionKodeRepository.saveAndFlush(esquemaContratacionKode);

        // Get all the esquemaContratacionKodeList where esquema equals to DEFAULT_ESQUEMA
        defaultEsquemaContratacionKodeShouldBeFound("esquema.equals=" + DEFAULT_ESQUEMA);

        // Get all the esquemaContratacionKodeList where esquema equals to UPDATED_ESQUEMA
        defaultEsquemaContratacionKodeShouldNotBeFound("esquema.equals=" + UPDATED_ESQUEMA);
    }

    @Test
    @Transactional
    public void getAllEsquemaContratacionKodesByEsquemaIsInShouldWork() throws Exception {
        // Initialize the database
        esquemaContratacionKodeRepository.saveAndFlush(esquemaContratacionKode);

        // Get all the esquemaContratacionKodeList where esquema in DEFAULT_ESQUEMA or UPDATED_ESQUEMA
        defaultEsquemaContratacionKodeShouldBeFound("esquema.in=" + DEFAULT_ESQUEMA + "," + UPDATED_ESQUEMA);

        // Get all the esquemaContratacionKodeList where esquema equals to UPDATED_ESQUEMA
        defaultEsquemaContratacionKodeShouldNotBeFound("esquema.in=" + UPDATED_ESQUEMA);
    }

    @Test
    @Transactional
    public void getAllEsquemaContratacionKodesByEsquemaIsNullOrNotNull() throws Exception {
        // Initialize the database
        esquemaContratacionKodeRepository.saveAndFlush(esquemaContratacionKode);

        // Get all the esquemaContratacionKodeList where esquema is not null
        defaultEsquemaContratacionKodeShouldBeFound("esquema.specified=true");

        // Get all the esquemaContratacionKodeList where esquema is null
        defaultEsquemaContratacionKodeShouldNotBeFound("esquema.specified=false");
    }

    @Test
    @Transactional
    public void getAllEsquemaContratacionKodesByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        esquemaContratacionKode.addCandidato(candidato);
        esquemaContratacionKodeRepository.saveAndFlush(esquemaContratacionKode);
        Long candidatoId = candidato.getId();

        // Get all the esquemaContratacionKodeList where candidato equals to candidatoId
        defaultEsquemaContratacionKodeShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the esquemaContratacionKodeList where candidato equals to candidatoId + 1
        defaultEsquemaContratacionKodeShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEsquemaContratacionKodeShouldBeFound(String filter) throws Exception {
        restEsquemaContratacionKodeMockMvc.perform(get("/api/esquema-contratacion-kodes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esquemaContratacionKode.getId().intValue())))
            .andExpect(jsonPath("$.[*].esquema").value(hasItem(DEFAULT_ESQUEMA)));

        // Check, that the count call also returns 1
        restEsquemaContratacionKodeMockMvc.perform(get("/api/esquema-contratacion-kodes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEsquemaContratacionKodeShouldNotBeFound(String filter) throws Exception {
        restEsquemaContratacionKodeMockMvc.perform(get("/api/esquema-contratacion-kodes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEsquemaContratacionKodeMockMvc.perform(get("/api/esquema-contratacion-kodes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEsquemaContratacionKode() throws Exception {
        // Get the esquemaContratacionKode
        restEsquemaContratacionKodeMockMvc.perform(get("/api/esquema-contratacion-kodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEsquemaContratacionKode() throws Exception {
        // Initialize the database
        esquemaContratacionKodeRepository.saveAndFlush(esquemaContratacionKode);

        int databaseSizeBeforeUpdate = esquemaContratacionKodeRepository.findAll().size();

        // Update the esquemaContratacionKode
        EsquemaContratacionKode updatedEsquemaContratacionKode = esquemaContratacionKodeRepository.findById(esquemaContratacionKode.getId()).get();
        // Disconnect from session so that the updates on updatedEsquemaContratacionKode are not directly saved in db
        em.detach(updatedEsquemaContratacionKode);
        updatedEsquemaContratacionKode
            .esquema(UPDATED_ESQUEMA);
        EsquemaContratacionKodeDTO esquemaContratacionKodeDTO = esquemaContratacionKodeMapper.toDto(updatedEsquemaContratacionKode);

        restEsquemaContratacionKodeMockMvc.perform(put("/api/esquema-contratacion-kodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esquemaContratacionKodeDTO)))
            .andExpect(status().isOk());

        // Validate the EsquemaContratacionKode in the database
        List<EsquemaContratacionKode> esquemaContratacionKodeList = esquemaContratacionKodeRepository.findAll();
        assertThat(esquemaContratacionKodeList).hasSize(databaseSizeBeforeUpdate);
        EsquemaContratacionKode testEsquemaContratacionKode = esquemaContratacionKodeList.get(esquemaContratacionKodeList.size() - 1);
        assertThat(testEsquemaContratacionKode.getEsquema()).isEqualTo(UPDATED_ESQUEMA);
    }

    @Test
    @Transactional
    public void updateNonExistingEsquemaContratacionKode() throws Exception {
        int databaseSizeBeforeUpdate = esquemaContratacionKodeRepository.findAll().size();

        // Create the EsquemaContratacionKode
        EsquemaContratacionKodeDTO esquemaContratacionKodeDTO = esquemaContratacionKodeMapper.toDto(esquemaContratacionKode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsquemaContratacionKodeMockMvc.perform(put("/api/esquema-contratacion-kodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esquemaContratacionKodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EsquemaContratacionKode in the database
        List<EsquemaContratacionKode> esquemaContratacionKodeList = esquemaContratacionKodeRepository.findAll();
        assertThat(esquemaContratacionKodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEsquemaContratacionKode() throws Exception {
        // Initialize the database
        esquemaContratacionKodeRepository.saveAndFlush(esquemaContratacionKode);

        int databaseSizeBeforeDelete = esquemaContratacionKodeRepository.findAll().size();

        // Delete the esquemaContratacionKode
        restEsquemaContratacionKodeMockMvc.perform(delete("/api/esquema-contratacion-kodes/{id}", esquemaContratacionKode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EsquemaContratacionKode> esquemaContratacionKodeList = esquemaContratacionKodeRepository.findAll();
        assertThat(esquemaContratacionKodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsquemaContratacionKode.class);
        EsquemaContratacionKode esquemaContratacionKode1 = new EsquemaContratacionKode();
        esquemaContratacionKode1.setId(1L);
        EsquemaContratacionKode esquemaContratacionKode2 = new EsquemaContratacionKode();
        esquemaContratacionKode2.setId(esquemaContratacionKode1.getId());
        assertThat(esquemaContratacionKode1).isEqualTo(esquemaContratacionKode2);
        esquemaContratacionKode2.setId(2L);
        assertThat(esquemaContratacionKode1).isNotEqualTo(esquemaContratacionKode2);
        esquemaContratacionKode1.setId(null);
        assertThat(esquemaContratacionKode1).isNotEqualTo(esquemaContratacionKode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsquemaContratacionKodeDTO.class);
        EsquemaContratacionKodeDTO esquemaContratacionKodeDTO1 = new EsquemaContratacionKodeDTO();
        esquemaContratacionKodeDTO1.setId(1L);
        EsquemaContratacionKodeDTO esquemaContratacionKodeDTO2 = new EsquemaContratacionKodeDTO();
        assertThat(esquemaContratacionKodeDTO1).isNotEqualTo(esquemaContratacionKodeDTO2);
        esquemaContratacionKodeDTO2.setId(esquemaContratacionKodeDTO1.getId());
        assertThat(esquemaContratacionKodeDTO1).isEqualTo(esquemaContratacionKodeDTO2);
        esquemaContratacionKodeDTO2.setId(2L);
        assertThat(esquemaContratacionKodeDTO1).isNotEqualTo(esquemaContratacionKodeDTO2);
        esquemaContratacionKodeDTO1.setId(null);
        assertThat(esquemaContratacionKodeDTO1).isNotEqualTo(esquemaContratacionKodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(esquemaContratacionKodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(esquemaContratacionKodeMapper.fromId(null)).isNull();
    }
}
