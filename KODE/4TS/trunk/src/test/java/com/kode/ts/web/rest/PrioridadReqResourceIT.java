package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.PrioridadReq;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.PrioridadReqRepository;
import com.kode.ts.service.PrioridadReqService;
import com.kode.ts.service.dto.PrioridadReqDTO;
import com.kode.ts.service.mapper.PrioridadReqMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.PrioridadReqCriteria;
import com.kode.ts.service.PrioridadReqQueryService;

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
 * Integration tests for the {@Link PrioridadReqResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class PrioridadReqResourceIT {

    private static final String DEFAULT_PRIORIDAD = "AAAAAAAAAA";
    private static final String UPDATED_PRIORIDAD = "BBBBBBBBBB";

    @Autowired
    private PrioridadReqRepository prioridadReqRepository;

    @Autowired
    private PrioridadReqMapper prioridadReqMapper;

    @Autowired
    private PrioridadReqService prioridadReqService;

    @Autowired
    private PrioridadReqQueryService prioridadReqQueryService;

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

    private MockMvc restPrioridadReqMockMvc;

    private PrioridadReq prioridadReq;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrioridadReqResource prioridadReqResource = new PrioridadReqResource(prioridadReqService, prioridadReqQueryService);
        this.restPrioridadReqMockMvc = MockMvcBuilders.standaloneSetup(prioridadReqResource)
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
    public static PrioridadReq createEntity(EntityManager em) {
        PrioridadReq prioridadReq = new PrioridadReq()
            .prioridad(DEFAULT_PRIORIDAD);
        return prioridadReq;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrioridadReq createUpdatedEntity(EntityManager em) {
        PrioridadReq prioridadReq = new PrioridadReq()
            .prioridad(UPDATED_PRIORIDAD);
        return prioridadReq;
    }

    @BeforeEach
    public void initTest() {
        prioridadReq = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrioridadReq() throws Exception {
        int databaseSizeBeforeCreate = prioridadReqRepository.findAll().size();

        // Create the PrioridadReq
        PrioridadReqDTO prioridadReqDTO = prioridadReqMapper.toDto(prioridadReq);
        restPrioridadReqMockMvc.perform(post("/api/prioridad-reqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prioridadReqDTO)))
            .andExpect(status().isCreated());

        // Validate the PrioridadReq in the database
        List<PrioridadReq> prioridadReqList = prioridadReqRepository.findAll();
        assertThat(prioridadReqList).hasSize(databaseSizeBeforeCreate + 1);
        PrioridadReq testPrioridadReq = prioridadReqList.get(prioridadReqList.size() - 1);
        assertThat(testPrioridadReq.getPrioridad()).isEqualTo(DEFAULT_PRIORIDAD);
    }

    @Test
    @Transactional
    public void createPrioridadReqWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prioridadReqRepository.findAll().size();

        // Create the PrioridadReq with an existing ID
        prioridadReq.setId(1L);
        PrioridadReqDTO prioridadReqDTO = prioridadReqMapper.toDto(prioridadReq);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrioridadReqMockMvc.perform(post("/api/prioridad-reqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prioridadReqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrioridadReq in the database
        List<PrioridadReq> prioridadReqList = prioridadReqRepository.findAll();
        assertThat(prioridadReqList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrioridadReqs() throws Exception {
        // Initialize the database
        prioridadReqRepository.saveAndFlush(prioridadReq);

        // Get all the prioridadReqList
        restPrioridadReqMockMvc.perform(get("/api/prioridad-reqs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prioridadReq.getId().intValue())))
            .andExpect(jsonPath("$.[*].prioridad").value(hasItem(DEFAULT_PRIORIDAD.toString())));
    }
    
    @Test
    @Transactional
    public void getPrioridadReq() throws Exception {
        // Initialize the database
        prioridadReqRepository.saveAndFlush(prioridadReq);

        // Get the prioridadReq
        restPrioridadReqMockMvc.perform(get("/api/prioridad-reqs/{id}", prioridadReq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prioridadReq.getId().intValue()))
            .andExpect(jsonPath("$.prioridad").value(DEFAULT_PRIORIDAD.toString()));
    }

    @Test
    @Transactional
    public void getAllPrioridadReqsByPrioridadIsEqualToSomething() throws Exception {
        // Initialize the database
        prioridadReqRepository.saveAndFlush(prioridadReq);

        // Get all the prioridadReqList where prioridad equals to DEFAULT_PRIORIDAD
        defaultPrioridadReqShouldBeFound("prioridad.equals=" + DEFAULT_PRIORIDAD);

        // Get all the prioridadReqList where prioridad equals to UPDATED_PRIORIDAD
        defaultPrioridadReqShouldNotBeFound("prioridad.equals=" + UPDATED_PRIORIDAD);
    }

    @Test
    @Transactional
    public void getAllPrioridadReqsByPrioridadIsInShouldWork() throws Exception {
        // Initialize the database
        prioridadReqRepository.saveAndFlush(prioridadReq);

        // Get all the prioridadReqList where prioridad in DEFAULT_PRIORIDAD or UPDATED_PRIORIDAD
        defaultPrioridadReqShouldBeFound("prioridad.in=" + DEFAULT_PRIORIDAD + "," + UPDATED_PRIORIDAD);

        // Get all the prioridadReqList where prioridad equals to UPDATED_PRIORIDAD
        defaultPrioridadReqShouldNotBeFound("prioridad.in=" + UPDATED_PRIORIDAD);
    }

    @Test
    @Transactional
    public void getAllPrioridadReqsByPrioridadIsNullOrNotNull() throws Exception {
        // Initialize the database
        prioridadReqRepository.saveAndFlush(prioridadReq);

        // Get all the prioridadReqList where prioridad is not null
        defaultPrioridadReqShouldBeFound("prioridad.specified=true");

        // Get all the prioridadReqList where prioridad is null
        defaultPrioridadReqShouldNotBeFound("prioridad.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrioridadReqsByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        prioridadReq.addRequerimiento(requerimiento);
        prioridadReqRepository.saveAndFlush(prioridadReq);
        Long requerimientoId = requerimiento.getId();

        // Get all the prioridadReqList where requerimiento equals to requerimientoId
        defaultPrioridadReqShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the prioridadReqList where requerimiento equals to requerimientoId + 1
        defaultPrioridadReqShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrioridadReqShouldBeFound(String filter) throws Exception {
        restPrioridadReqMockMvc.perform(get("/api/prioridad-reqs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prioridadReq.getId().intValue())))
            .andExpect(jsonPath("$.[*].prioridad").value(hasItem(DEFAULT_PRIORIDAD)));

        // Check, that the count call also returns 1
        restPrioridadReqMockMvc.perform(get("/api/prioridad-reqs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrioridadReqShouldNotBeFound(String filter) throws Exception {
        restPrioridadReqMockMvc.perform(get("/api/prioridad-reqs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrioridadReqMockMvc.perform(get("/api/prioridad-reqs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPrioridadReq() throws Exception {
        // Get the prioridadReq
        restPrioridadReqMockMvc.perform(get("/api/prioridad-reqs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrioridadReq() throws Exception {
        // Initialize the database
        prioridadReqRepository.saveAndFlush(prioridadReq);

        int databaseSizeBeforeUpdate = prioridadReqRepository.findAll().size();

        // Update the prioridadReq
        PrioridadReq updatedPrioridadReq = prioridadReqRepository.findById(prioridadReq.getId()).get();
        // Disconnect from session so that the updates on updatedPrioridadReq are not directly saved in db
        em.detach(updatedPrioridadReq);
        updatedPrioridadReq
            .prioridad(UPDATED_PRIORIDAD);
        PrioridadReqDTO prioridadReqDTO = prioridadReqMapper.toDto(updatedPrioridadReq);

        restPrioridadReqMockMvc.perform(put("/api/prioridad-reqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prioridadReqDTO)))
            .andExpect(status().isOk());

        // Validate the PrioridadReq in the database
        List<PrioridadReq> prioridadReqList = prioridadReqRepository.findAll();
        assertThat(prioridadReqList).hasSize(databaseSizeBeforeUpdate);
        PrioridadReq testPrioridadReq = prioridadReqList.get(prioridadReqList.size() - 1);
        assertThat(testPrioridadReq.getPrioridad()).isEqualTo(UPDATED_PRIORIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingPrioridadReq() throws Exception {
        int databaseSizeBeforeUpdate = prioridadReqRepository.findAll().size();

        // Create the PrioridadReq
        PrioridadReqDTO prioridadReqDTO = prioridadReqMapper.toDto(prioridadReq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrioridadReqMockMvc.perform(put("/api/prioridad-reqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prioridadReqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrioridadReq in the database
        List<PrioridadReq> prioridadReqList = prioridadReqRepository.findAll();
        assertThat(prioridadReqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrioridadReq() throws Exception {
        // Initialize the database
        prioridadReqRepository.saveAndFlush(prioridadReq);

        int databaseSizeBeforeDelete = prioridadReqRepository.findAll().size();

        // Delete the prioridadReq
        restPrioridadReqMockMvc.perform(delete("/api/prioridad-reqs/{id}", prioridadReq.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PrioridadReq> prioridadReqList = prioridadReqRepository.findAll();
        assertThat(prioridadReqList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrioridadReq.class);
        PrioridadReq prioridadReq1 = new PrioridadReq();
        prioridadReq1.setId(1L);
        PrioridadReq prioridadReq2 = new PrioridadReq();
        prioridadReq2.setId(prioridadReq1.getId());
        assertThat(prioridadReq1).isEqualTo(prioridadReq2);
        prioridadReq2.setId(2L);
        assertThat(prioridadReq1).isNotEqualTo(prioridadReq2);
        prioridadReq1.setId(null);
        assertThat(prioridadReq1).isNotEqualTo(prioridadReq2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrioridadReqDTO.class);
        PrioridadReqDTO prioridadReqDTO1 = new PrioridadReqDTO();
        prioridadReqDTO1.setId(1L);
        PrioridadReqDTO prioridadReqDTO2 = new PrioridadReqDTO();
        assertThat(prioridadReqDTO1).isNotEqualTo(prioridadReqDTO2);
        prioridadReqDTO2.setId(prioridadReqDTO1.getId());
        assertThat(prioridadReqDTO1).isEqualTo(prioridadReqDTO2);
        prioridadReqDTO2.setId(2L);
        assertThat(prioridadReqDTO1).isNotEqualTo(prioridadReqDTO2);
        prioridadReqDTO1.setId(null);
        assertThat(prioridadReqDTO1).isNotEqualTo(prioridadReqDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prioridadReqMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prioridadReqMapper.fromId(null)).isNull();
    }
}
