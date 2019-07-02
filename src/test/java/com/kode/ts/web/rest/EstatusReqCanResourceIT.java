package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstatusReqCan;
import com.kode.ts.domain.EstatusReqCanRec;
import com.kode.ts.repository.EstatusReqCanRepository;
import com.kode.ts.service.EstatusReqCanService;
import com.kode.ts.service.dto.EstatusReqCanDTO;
import com.kode.ts.service.mapper.EstatusReqCanMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstatusReqCanCriteria;
import com.kode.ts.service.EstatusReqCanQueryService;

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
 * Integration tests for the {@Link EstatusReqCanResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstatusReqCanResourceIT {

    private static final String DEFAULT_ESTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ESTATUS = "BBBBBBBBBB";

    @Autowired
    private EstatusReqCanRepository estatusReqCanRepository;

    @Autowired
    private EstatusReqCanMapper estatusReqCanMapper;

    @Autowired
    private EstatusReqCanService estatusReqCanService;

    @Autowired
    private EstatusReqCanQueryService estatusReqCanQueryService;

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

    private MockMvc restEstatusReqCanMockMvc;

    private EstatusReqCan estatusReqCan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusReqCanResource estatusReqCanResource = new EstatusReqCanResource(estatusReqCanService, estatusReqCanQueryService);
        this.restEstatusReqCanMockMvc = MockMvcBuilders.standaloneSetup(estatusReqCanResource)
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
    public static EstatusReqCan createEntity(EntityManager em) {
        EstatusReqCan estatusReqCan = new EstatusReqCan()
            .estatus(DEFAULT_ESTATUS);
        return estatusReqCan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusReqCan createUpdatedEntity(EntityManager em) {
        EstatusReqCan estatusReqCan = new EstatusReqCan()
            .estatus(UPDATED_ESTATUS);
        return estatusReqCan;
    }

    @BeforeEach
    public void initTest() {
        estatusReqCan = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusReqCan() throws Exception {
        int databaseSizeBeforeCreate = estatusReqCanRepository.findAll().size();

        // Create the EstatusReqCan
        EstatusReqCanDTO estatusReqCanDTO = estatusReqCanMapper.toDto(estatusReqCan);
        restEstatusReqCanMockMvc.perform(post("/api/estatus-req-cans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusReqCanDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusReqCan in the database
        List<EstatusReqCan> estatusReqCanList = estatusReqCanRepository.findAll();
        assertThat(estatusReqCanList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusReqCan testEstatusReqCan = estatusReqCanList.get(estatusReqCanList.size() - 1);
        assertThat(testEstatusReqCan.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createEstatusReqCanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusReqCanRepository.findAll().size();

        // Create the EstatusReqCan with an existing ID
        estatusReqCan.setId(1L);
        EstatusReqCanDTO estatusReqCanDTO = estatusReqCanMapper.toDto(estatusReqCan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusReqCanMockMvc.perform(post("/api/estatus-req-cans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusReqCanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusReqCan in the database
        List<EstatusReqCan> estatusReqCanList = estatusReqCanRepository.findAll();
        assertThat(estatusReqCanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstatusReqCans() throws Exception {
        // Initialize the database
        estatusReqCanRepository.saveAndFlush(estatusReqCan);

        // Get all the estatusReqCanList
        restEstatusReqCanMockMvc.perform(get("/api/estatus-req-cans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusReqCan.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusReqCan() throws Exception {
        // Initialize the database
        estatusReqCanRepository.saveAndFlush(estatusReqCan);

        // Get the estatusReqCan
        restEstatusReqCanMockMvc.perform(get("/api/estatus-req-cans/{id}", estatusReqCan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusReqCan.getId().intValue()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllEstatusReqCansByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        estatusReqCanRepository.saveAndFlush(estatusReqCan);

        // Get all the estatusReqCanList where estatus equals to DEFAULT_ESTATUS
        defaultEstatusReqCanShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the estatusReqCanList where estatus equals to UPDATED_ESTATUS
        defaultEstatusReqCanShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusReqCansByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        estatusReqCanRepository.saveAndFlush(estatusReqCan);

        // Get all the estatusReqCanList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultEstatusReqCanShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the estatusReqCanList where estatus equals to UPDATED_ESTATUS
        defaultEstatusReqCanShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllEstatusReqCansByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        estatusReqCanRepository.saveAndFlush(estatusReqCan);

        // Get all the estatusReqCanList where estatus is not null
        defaultEstatusReqCanShouldBeFound("estatus.specified=true");

        // Get all the estatusReqCanList where estatus is null
        defaultEstatusReqCanShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstatusReqCansByEstatusReqCanRecIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusReqCanRec estatusReqCanRec = EstatusReqCanRecResourceIT.createEntity(em);
        em.persist(estatusReqCanRec);
        em.flush();
        estatusReqCan.addEstatusReqCanRec(estatusReqCanRec);
        estatusReqCanRepository.saveAndFlush(estatusReqCan);
        Long estatusReqCanRecId = estatusReqCanRec.getId();

        // Get all the estatusReqCanList where estatusReqCanRec equals to estatusReqCanRecId
        defaultEstatusReqCanShouldBeFound("estatusReqCanRecId.equals=" + estatusReqCanRecId);

        // Get all the estatusReqCanList where estatusReqCanRec equals to estatusReqCanRecId + 1
        defaultEstatusReqCanShouldNotBeFound("estatusReqCanRecId.equals=" + (estatusReqCanRecId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstatusReqCanShouldBeFound(String filter) throws Exception {
        restEstatusReqCanMockMvc.perform(get("/api/estatus-req-cans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusReqCan.getId().intValue())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));

        // Check, that the count call also returns 1
        restEstatusReqCanMockMvc.perform(get("/api/estatus-req-cans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstatusReqCanShouldNotBeFound(String filter) throws Exception {
        restEstatusReqCanMockMvc.perform(get("/api/estatus-req-cans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstatusReqCanMockMvc.perform(get("/api/estatus-req-cans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstatusReqCan() throws Exception {
        // Get the estatusReqCan
        restEstatusReqCanMockMvc.perform(get("/api/estatus-req-cans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusReqCan() throws Exception {
        // Initialize the database
        estatusReqCanRepository.saveAndFlush(estatusReqCan);

        int databaseSizeBeforeUpdate = estatusReqCanRepository.findAll().size();

        // Update the estatusReqCan
        EstatusReqCan updatedEstatusReqCan = estatusReqCanRepository.findById(estatusReqCan.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusReqCan are not directly saved in db
        em.detach(updatedEstatusReqCan);
        updatedEstatusReqCan
            .estatus(UPDATED_ESTATUS);
        EstatusReqCanDTO estatusReqCanDTO = estatusReqCanMapper.toDto(updatedEstatusReqCan);

        restEstatusReqCanMockMvc.perform(put("/api/estatus-req-cans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusReqCanDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusReqCan in the database
        List<EstatusReqCan> estatusReqCanList = estatusReqCanRepository.findAll();
        assertThat(estatusReqCanList).hasSize(databaseSizeBeforeUpdate);
        EstatusReqCan testEstatusReqCan = estatusReqCanList.get(estatusReqCanList.size() - 1);
        assertThat(testEstatusReqCan.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusReqCan() throws Exception {
        int databaseSizeBeforeUpdate = estatusReqCanRepository.findAll().size();

        // Create the EstatusReqCan
        EstatusReqCanDTO estatusReqCanDTO = estatusReqCanMapper.toDto(estatusReqCan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusReqCanMockMvc.perform(put("/api/estatus-req-cans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusReqCanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusReqCan in the database
        List<EstatusReqCan> estatusReqCanList = estatusReqCanRepository.findAll();
        assertThat(estatusReqCanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstatusReqCan() throws Exception {
        // Initialize the database
        estatusReqCanRepository.saveAndFlush(estatusReqCan);

        int databaseSizeBeforeDelete = estatusReqCanRepository.findAll().size();

        // Delete the estatusReqCan
        restEstatusReqCanMockMvc.perform(delete("/api/estatus-req-cans/{id}", estatusReqCan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstatusReqCan> estatusReqCanList = estatusReqCanRepository.findAll();
        assertThat(estatusReqCanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusReqCan.class);
        EstatusReqCan estatusReqCan1 = new EstatusReqCan();
        estatusReqCan1.setId(1L);
        EstatusReqCan estatusReqCan2 = new EstatusReqCan();
        estatusReqCan2.setId(estatusReqCan1.getId());
        assertThat(estatusReqCan1).isEqualTo(estatusReqCan2);
        estatusReqCan2.setId(2L);
        assertThat(estatusReqCan1).isNotEqualTo(estatusReqCan2);
        estatusReqCan1.setId(null);
        assertThat(estatusReqCan1).isNotEqualTo(estatusReqCan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusReqCanDTO.class);
        EstatusReqCanDTO estatusReqCanDTO1 = new EstatusReqCanDTO();
        estatusReqCanDTO1.setId(1L);
        EstatusReqCanDTO estatusReqCanDTO2 = new EstatusReqCanDTO();
        assertThat(estatusReqCanDTO1).isNotEqualTo(estatusReqCanDTO2);
        estatusReqCanDTO2.setId(estatusReqCanDTO1.getId());
        assertThat(estatusReqCanDTO1).isEqualTo(estatusReqCanDTO2);
        estatusReqCanDTO2.setId(2L);
        assertThat(estatusReqCanDTO1).isNotEqualTo(estatusReqCanDTO2);
        estatusReqCanDTO1.setId(null);
        assertThat(estatusReqCanDTO1).isNotEqualTo(estatusReqCanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusReqCanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusReqCanMapper.fromId(null)).isNull();
    }
}
