package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EsqContRec;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.EsqContRecRepository;
import com.kode.ts.service.EsqContRecService;
import com.kode.ts.service.dto.EsqContRecDTO;
import com.kode.ts.service.mapper.EsqContRecMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EsqContRecCriteria;
import com.kode.ts.service.EsqContRecQueryService;

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
 * Integration tests for the {@Link EsqContRecResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EsqContRecResourceIT {

    private static final String DEFAULT_ESQUEMA = "AAAAAAAAAA";
    private static final String UPDATED_ESQUEMA = "BBBBBBBBBB";

    @Autowired
    private EsqContRecRepository esqContRecRepository;

    @Autowired
    private EsqContRecMapper esqContRecMapper;

    @Autowired
    private EsqContRecService esqContRecService;

    @Autowired
    private EsqContRecQueryService esqContRecQueryService;

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

    private MockMvc restEsqContRecMockMvc;

    private EsqContRec esqContRec;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EsqContRecResource esqContRecResource = new EsqContRecResource(esqContRecService, esqContRecQueryService);
        this.restEsqContRecMockMvc = MockMvcBuilders.standaloneSetup(esqContRecResource)
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
    public static EsqContRec createEntity(EntityManager em) {
        EsqContRec esqContRec = new EsqContRec()
            .esquema(DEFAULT_ESQUEMA);
        return esqContRec;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EsqContRec createUpdatedEntity(EntityManager em) {
        EsqContRec esqContRec = new EsqContRec()
            .esquema(UPDATED_ESQUEMA);
        return esqContRec;
    }

    @BeforeEach
    public void initTest() {
        esqContRec = createEntity(em);
    }

    @Test
    @Transactional
    public void createEsqContRec() throws Exception {
        int databaseSizeBeforeCreate = esqContRecRepository.findAll().size();

        // Create the EsqContRec
        EsqContRecDTO esqContRecDTO = esqContRecMapper.toDto(esqContRec);
        restEsqContRecMockMvc.perform(post("/api/esq-cont-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esqContRecDTO)))
            .andExpect(status().isCreated());

        // Validate the EsqContRec in the database
        List<EsqContRec> esqContRecList = esqContRecRepository.findAll();
        assertThat(esqContRecList).hasSize(databaseSizeBeforeCreate + 1);
        EsqContRec testEsqContRec = esqContRecList.get(esqContRecList.size() - 1);
        assertThat(testEsqContRec.getEsquema()).isEqualTo(DEFAULT_ESQUEMA);
    }

    @Test
    @Transactional
    public void createEsqContRecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = esqContRecRepository.findAll().size();

        // Create the EsqContRec with an existing ID
        esqContRec.setId(1L);
        EsqContRecDTO esqContRecDTO = esqContRecMapper.toDto(esqContRec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEsqContRecMockMvc.perform(post("/api/esq-cont-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esqContRecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EsqContRec in the database
        List<EsqContRec> esqContRecList = esqContRecRepository.findAll();
        assertThat(esqContRecList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEsqContRecs() throws Exception {
        // Initialize the database
        esqContRecRepository.saveAndFlush(esqContRec);

        // Get all the esqContRecList
        restEsqContRecMockMvc.perform(get("/api/esq-cont-recs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esqContRec.getId().intValue())))
            .andExpect(jsonPath("$.[*].esquema").value(hasItem(DEFAULT_ESQUEMA.toString())));
    }
    
    @Test
    @Transactional
    public void getEsqContRec() throws Exception {
        // Initialize the database
        esqContRecRepository.saveAndFlush(esqContRec);

        // Get the esqContRec
        restEsqContRecMockMvc.perform(get("/api/esq-cont-recs/{id}", esqContRec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(esqContRec.getId().intValue()))
            .andExpect(jsonPath("$.esquema").value(DEFAULT_ESQUEMA.toString()));
    }

    @Test
    @Transactional
    public void getAllEsqContRecsByEsquemaIsEqualToSomething() throws Exception {
        // Initialize the database
        esqContRecRepository.saveAndFlush(esqContRec);

        // Get all the esqContRecList where esquema equals to DEFAULT_ESQUEMA
        defaultEsqContRecShouldBeFound("esquema.equals=" + DEFAULT_ESQUEMA);

        // Get all the esqContRecList where esquema equals to UPDATED_ESQUEMA
        defaultEsqContRecShouldNotBeFound("esquema.equals=" + UPDATED_ESQUEMA);
    }

    @Test
    @Transactional
    public void getAllEsqContRecsByEsquemaIsInShouldWork() throws Exception {
        // Initialize the database
        esqContRecRepository.saveAndFlush(esqContRec);

        // Get all the esqContRecList where esquema in DEFAULT_ESQUEMA or UPDATED_ESQUEMA
        defaultEsqContRecShouldBeFound("esquema.in=" + DEFAULT_ESQUEMA + "," + UPDATED_ESQUEMA);

        // Get all the esqContRecList where esquema equals to UPDATED_ESQUEMA
        defaultEsqContRecShouldNotBeFound("esquema.in=" + UPDATED_ESQUEMA);
    }

    @Test
    @Transactional
    public void getAllEsqContRecsByEsquemaIsNullOrNotNull() throws Exception {
        // Initialize the database
        esqContRecRepository.saveAndFlush(esqContRec);

        // Get all the esqContRecList where esquema is not null
        defaultEsqContRecShouldBeFound("esquema.specified=true");

        // Get all the esqContRecList where esquema is null
        defaultEsqContRecShouldNotBeFound("esquema.specified=false");
    }

    @Test
    @Transactional
    public void getAllEsqContRecsByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        esqContRec.addCandidato(candidato);
        esqContRecRepository.saveAndFlush(esqContRec);
        Long candidatoId = candidato.getId();

        // Get all the esqContRecList where candidato equals to candidatoId
        defaultEsqContRecShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the esqContRecList where candidato equals to candidatoId + 1
        defaultEsqContRecShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllEsqContRecsByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        esqContRec.addRequerimiento(requerimiento);
        esqContRecRepository.saveAndFlush(esqContRec);
        Long requerimientoId = requerimiento.getId();

        // Get all the esqContRecList where requerimiento equals to requerimientoId
        defaultEsqContRecShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the esqContRecList where requerimiento equals to requerimientoId + 1
        defaultEsqContRecShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEsqContRecShouldBeFound(String filter) throws Exception {
        restEsqContRecMockMvc.perform(get("/api/esq-cont-recs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esqContRec.getId().intValue())))
            .andExpect(jsonPath("$.[*].esquema").value(hasItem(DEFAULT_ESQUEMA)));

        // Check, that the count call also returns 1
        restEsqContRecMockMvc.perform(get("/api/esq-cont-recs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEsqContRecShouldNotBeFound(String filter) throws Exception {
        restEsqContRecMockMvc.perform(get("/api/esq-cont-recs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEsqContRecMockMvc.perform(get("/api/esq-cont-recs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEsqContRec() throws Exception {
        // Get the esqContRec
        restEsqContRecMockMvc.perform(get("/api/esq-cont-recs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEsqContRec() throws Exception {
        // Initialize the database
        esqContRecRepository.saveAndFlush(esqContRec);

        int databaseSizeBeforeUpdate = esqContRecRepository.findAll().size();

        // Update the esqContRec
        EsqContRec updatedEsqContRec = esqContRecRepository.findById(esqContRec.getId()).get();
        // Disconnect from session so that the updates on updatedEsqContRec are not directly saved in db
        em.detach(updatedEsqContRec);
        updatedEsqContRec
            .esquema(UPDATED_ESQUEMA);
        EsqContRecDTO esqContRecDTO = esqContRecMapper.toDto(updatedEsqContRec);

        restEsqContRecMockMvc.perform(put("/api/esq-cont-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esqContRecDTO)))
            .andExpect(status().isOk());

        // Validate the EsqContRec in the database
        List<EsqContRec> esqContRecList = esqContRecRepository.findAll();
        assertThat(esqContRecList).hasSize(databaseSizeBeforeUpdate);
        EsqContRec testEsqContRec = esqContRecList.get(esqContRecList.size() - 1);
        assertThat(testEsqContRec.getEsquema()).isEqualTo(UPDATED_ESQUEMA);
    }

    @Test
    @Transactional
    public void updateNonExistingEsqContRec() throws Exception {
        int databaseSizeBeforeUpdate = esqContRecRepository.findAll().size();

        // Create the EsqContRec
        EsqContRecDTO esqContRecDTO = esqContRecMapper.toDto(esqContRec);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsqContRecMockMvc.perform(put("/api/esq-cont-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esqContRecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EsqContRec in the database
        List<EsqContRec> esqContRecList = esqContRecRepository.findAll();
        assertThat(esqContRecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEsqContRec() throws Exception {
        // Initialize the database
        esqContRecRepository.saveAndFlush(esqContRec);

        int databaseSizeBeforeDelete = esqContRecRepository.findAll().size();

        // Delete the esqContRec
        restEsqContRecMockMvc.perform(delete("/api/esq-cont-recs/{id}", esqContRec.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EsqContRec> esqContRecList = esqContRecRepository.findAll();
        assertThat(esqContRecList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsqContRec.class);
        EsqContRec esqContRec1 = new EsqContRec();
        esqContRec1.setId(1L);
        EsqContRec esqContRec2 = new EsqContRec();
        esqContRec2.setId(esqContRec1.getId());
        assertThat(esqContRec1).isEqualTo(esqContRec2);
        esqContRec2.setId(2L);
        assertThat(esqContRec1).isNotEqualTo(esqContRec2);
        esqContRec1.setId(null);
        assertThat(esqContRec1).isNotEqualTo(esqContRec2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsqContRecDTO.class);
        EsqContRecDTO esqContRecDTO1 = new EsqContRecDTO();
        esqContRecDTO1.setId(1L);
        EsqContRecDTO esqContRecDTO2 = new EsqContRecDTO();
        assertThat(esqContRecDTO1).isNotEqualTo(esqContRecDTO2);
        esqContRecDTO2.setId(esqContRecDTO1.getId());
        assertThat(esqContRecDTO1).isEqualTo(esqContRecDTO2);
        esqContRecDTO2.setId(2L);
        assertThat(esqContRecDTO1).isNotEqualTo(esqContRecDTO2);
        esqContRecDTO1.setId(null);
        assertThat(esqContRecDTO1).isNotEqualTo(esqContRecDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(esqContRecMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(esqContRecMapper.fromId(null)).isNull();
    }
}
