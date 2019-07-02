package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.ReqCan;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.domain.EstatusReqCan;
import com.kode.ts.domain.EstatusReqCanRec;
import com.kode.ts.repository.ReqCanRepository;
import com.kode.ts.service.ReqCanService;
import com.kode.ts.service.dto.ReqCanDTO;
import com.kode.ts.service.mapper.ReqCanMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.ReqCanCriteria;
import com.kode.ts.service.ReqCanQueryService;

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
 * Integration tests for the {@Link ReqCanResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class ReqCanResourceIT {

    @Autowired
    private ReqCanRepository reqCanRepository;

    @Autowired
    private ReqCanMapper reqCanMapper;

    @Autowired
    private ReqCanService reqCanService;

    @Autowired
    private ReqCanQueryService reqCanQueryService;

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

    private MockMvc restReqCanMockMvc;

    private ReqCan reqCan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReqCanResource reqCanResource = new ReqCanResource(reqCanService, reqCanQueryService);
        this.restReqCanMockMvc = MockMvcBuilders.standaloneSetup(reqCanResource)
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
    public static ReqCan createEntity(EntityManager em) {
        ReqCan reqCan = new ReqCan();
        return reqCan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReqCan createUpdatedEntity(EntityManager em) {
        ReqCan reqCan = new ReqCan();
        return reqCan;
    }

    @BeforeEach
    public void initTest() {
        reqCan = createEntity(em);
    }

    @Test
    @Transactional
    public void createReqCan() throws Exception {
        int databaseSizeBeforeCreate = reqCanRepository.findAll().size();

        // Create the ReqCan
        ReqCanDTO reqCanDTO = reqCanMapper.toDto(reqCan);
        restReqCanMockMvc.perform(post("/api/req-cans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reqCanDTO)))
            .andExpect(status().isCreated());

        // Validate the ReqCan in the database
        List<ReqCan> reqCanList = reqCanRepository.findAll();
        assertThat(reqCanList).hasSize(databaseSizeBeforeCreate + 1);
        ReqCan testReqCan = reqCanList.get(reqCanList.size() - 1);
    }

    @Test
    @Transactional
    public void createReqCanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reqCanRepository.findAll().size();

        // Create the ReqCan with an existing ID
        reqCan.setId(1L);
        ReqCanDTO reqCanDTO = reqCanMapper.toDto(reqCan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReqCanMockMvc.perform(post("/api/req-cans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reqCanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReqCan in the database
        List<ReqCan> reqCanList = reqCanRepository.findAll();
        assertThat(reqCanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReqCans() throws Exception {
        // Initialize the database
        reqCanRepository.saveAndFlush(reqCan);

        // Get all the reqCanList
        restReqCanMockMvc.perform(get("/api/req-cans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reqCan.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getReqCan() throws Exception {
        // Initialize the database
        reqCanRepository.saveAndFlush(reqCan);

        // Get the reqCan
        restReqCanMockMvc.perform(get("/api/req-cans/{id}", reqCan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reqCan.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllReqCansByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        reqCan.setCandidato(candidato);
        reqCanRepository.saveAndFlush(reqCan);
        Long candidatoId = candidato.getId();

        // Get all the reqCanList where candidato equals to candidatoId
        defaultReqCanShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the reqCanList where candidato equals to candidatoId + 1
        defaultReqCanShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllReqCansByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        reqCan.setRequerimiento(requerimiento);
        reqCanRepository.saveAndFlush(reqCan);
        Long requerimientoId = requerimiento.getId();

        // Get all the reqCanList where requerimiento equals to requerimientoId
        defaultReqCanShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the reqCanList where requerimiento equals to requerimientoId + 1
        defaultReqCanShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllReqCansByEstatusReqCanIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusReqCan estatusReqCan = EstatusReqCanResourceIT.createEntity(em);
        em.persist(estatusReqCan);
        em.flush();
        reqCan.setEstatusReqCan(estatusReqCan);
        reqCanRepository.saveAndFlush(reqCan);
        Long estatusReqCanId = estatusReqCan.getId();

        // Get all the reqCanList where estatusReqCan equals to estatusReqCanId
        defaultReqCanShouldBeFound("estatusReqCanId.equals=" + estatusReqCanId);

        // Get all the reqCanList where estatusReqCan equals to estatusReqCanId + 1
        defaultReqCanShouldNotBeFound("estatusReqCanId.equals=" + (estatusReqCanId + 1));
    }


    @Test
    @Transactional
    public void getAllReqCansByEstatusReqCanRecIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusReqCanRec estatusReqCanRec = EstatusReqCanRecResourceIT.createEntity(em);
        em.persist(estatusReqCanRec);
        em.flush();
        reqCan.setEstatusReqCanRec(estatusReqCanRec);
        reqCanRepository.saveAndFlush(reqCan);
        Long estatusReqCanRecId = estatusReqCanRec.getId();

        // Get all the reqCanList where estatusReqCanRec equals to estatusReqCanRecId
        defaultReqCanShouldBeFound("estatusReqCanRecId.equals=" + estatusReqCanRecId);

        // Get all the reqCanList where estatusReqCanRec equals to estatusReqCanRecId + 1
        defaultReqCanShouldNotBeFound("estatusReqCanRecId.equals=" + (estatusReqCanRecId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReqCanShouldBeFound(String filter) throws Exception {
        restReqCanMockMvc.perform(get("/api/req-cans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reqCan.getId().intValue())));

        // Check, that the count call also returns 1
        restReqCanMockMvc.perform(get("/api/req-cans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReqCanShouldNotBeFound(String filter) throws Exception {
        restReqCanMockMvc.perform(get("/api/req-cans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReqCanMockMvc.perform(get("/api/req-cans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReqCan() throws Exception {
        // Get the reqCan
        restReqCanMockMvc.perform(get("/api/req-cans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReqCan() throws Exception {
        // Initialize the database
        reqCanRepository.saveAndFlush(reqCan);

        int databaseSizeBeforeUpdate = reqCanRepository.findAll().size();

        // Update the reqCan
        ReqCan updatedReqCan = reqCanRepository.findById(reqCan.getId()).get();
        // Disconnect from session so that the updates on updatedReqCan are not directly saved in db
        em.detach(updatedReqCan);
        ReqCanDTO reqCanDTO = reqCanMapper.toDto(updatedReqCan);

        restReqCanMockMvc.perform(put("/api/req-cans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reqCanDTO)))
            .andExpect(status().isOk());

        // Validate the ReqCan in the database
        List<ReqCan> reqCanList = reqCanRepository.findAll();
        assertThat(reqCanList).hasSize(databaseSizeBeforeUpdate);
        ReqCan testReqCan = reqCanList.get(reqCanList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingReqCan() throws Exception {
        int databaseSizeBeforeUpdate = reqCanRepository.findAll().size();

        // Create the ReqCan
        ReqCanDTO reqCanDTO = reqCanMapper.toDto(reqCan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReqCanMockMvc.perform(put("/api/req-cans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reqCanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReqCan in the database
        List<ReqCan> reqCanList = reqCanRepository.findAll();
        assertThat(reqCanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReqCan() throws Exception {
        // Initialize the database
        reqCanRepository.saveAndFlush(reqCan);

        int databaseSizeBeforeDelete = reqCanRepository.findAll().size();

        // Delete the reqCan
        restReqCanMockMvc.perform(delete("/api/req-cans/{id}", reqCan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReqCan> reqCanList = reqCanRepository.findAll();
        assertThat(reqCanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReqCan.class);
        ReqCan reqCan1 = new ReqCan();
        reqCan1.setId(1L);
        ReqCan reqCan2 = new ReqCan();
        reqCan2.setId(reqCan1.getId());
        assertThat(reqCan1).isEqualTo(reqCan2);
        reqCan2.setId(2L);
        assertThat(reqCan1).isNotEqualTo(reqCan2);
        reqCan1.setId(null);
        assertThat(reqCan1).isNotEqualTo(reqCan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReqCanDTO.class);
        ReqCanDTO reqCanDTO1 = new ReqCanDTO();
        reqCanDTO1.setId(1L);
        ReqCanDTO reqCanDTO2 = new ReqCanDTO();
        assertThat(reqCanDTO1).isNotEqualTo(reqCanDTO2);
        reqCanDTO2.setId(reqCanDTO1.getId());
        assertThat(reqCanDTO1).isEqualTo(reqCanDTO2);
        reqCanDTO2.setId(2L);
        assertThat(reqCanDTO1).isNotEqualTo(reqCanDTO2);
        reqCanDTO1.setId(null);
        assertThat(reqCanDTO1).isNotEqualTo(reqCanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reqCanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reqCanMapper.fromId(null)).isNull();
    }
}
