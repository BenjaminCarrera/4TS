package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstatusReqCanRec;
import com.kode.ts.domain.EstatusReqCan;
import com.kode.ts.repository.EstatusReqCanRecRepository;
import com.kode.ts.service.EstatusReqCanRecService;
import com.kode.ts.service.dto.EstatusReqCanRecDTO;
import com.kode.ts.service.mapper.EstatusReqCanRecMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstatusReqCanRecCriteria;
import com.kode.ts.service.EstatusReqCanRecQueryService;

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
 * Integration tests for the {@Link EstatusReqCanRecResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstatusReqCanRecResourceIT {

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    @Autowired
    private EstatusReqCanRecRepository estatusReqCanRecRepository;

    @Autowired
    private EstatusReqCanRecMapper estatusReqCanRecMapper;

    @Autowired
    private EstatusReqCanRecService estatusReqCanRecService;

    @Autowired
    private EstatusReqCanRecQueryService estatusReqCanRecQueryService;

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

    private MockMvc restEstatusReqCanRecMockMvc;

    private EstatusReqCanRec estatusReqCanRec;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusReqCanRecResource estatusReqCanRecResource = new EstatusReqCanRecResource(estatusReqCanRecService, estatusReqCanRecQueryService);
        this.restEstatusReqCanRecMockMvc = MockMvcBuilders.standaloneSetup(estatusReqCanRecResource)
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
    public static EstatusReqCanRec createEntity(EntityManager em) {
        EstatusReqCanRec estatusReqCanRec = new EstatusReqCanRec()
            .motivo(DEFAULT_MOTIVO);
        return estatusReqCanRec;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusReqCanRec createUpdatedEntity(EntityManager em) {
        EstatusReqCanRec estatusReqCanRec = new EstatusReqCanRec()
            .motivo(UPDATED_MOTIVO);
        return estatusReqCanRec;
    }

    @BeforeEach
    public void initTest() {
        estatusReqCanRec = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusReqCanRec() throws Exception {
        int databaseSizeBeforeCreate = estatusReqCanRecRepository.findAll().size();

        // Create the EstatusReqCanRec
        EstatusReqCanRecDTO estatusReqCanRecDTO = estatusReqCanRecMapper.toDto(estatusReqCanRec);
        restEstatusReqCanRecMockMvc.perform(post("/api/estatus-req-can-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusReqCanRecDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusReqCanRec in the database
        List<EstatusReqCanRec> estatusReqCanRecList = estatusReqCanRecRepository.findAll();
        assertThat(estatusReqCanRecList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusReqCanRec testEstatusReqCanRec = estatusReqCanRecList.get(estatusReqCanRecList.size() - 1);
        assertThat(testEstatusReqCanRec.getMotivo()).isEqualTo(DEFAULT_MOTIVO);
    }

    @Test
    @Transactional
    public void createEstatusReqCanRecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusReqCanRecRepository.findAll().size();

        // Create the EstatusReqCanRec with an existing ID
        estatusReqCanRec.setId(1L);
        EstatusReqCanRecDTO estatusReqCanRecDTO = estatusReqCanRecMapper.toDto(estatusReqCanRec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusReqCanRecMockMvc.perform(post("/api/estatus-req-can-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusReqCanRecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusReqCanRec in the database
        List<EstatusReqCanRec> estatusReqCanRecList = estatusReqCanRecRepository.findAll();
        assertThat(estatusReqCanRecList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstatusReqCanRecs() throws Exception {
        // Initialize the database
        estatusReqCanRecRepository.saveAndFlush(estatusReqCanRec);

        // Get all the estatusReqCanRecList
        restEstatusReqCanRecMockMvc.perform(get("/api/estatus-req-can-recs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusReqCanRec.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusReqCanRec() throws Exception {
        // Initialize the database
        estatusReqCanRecRepository.saveAndFlush(estatusReqCanRec);

        // Get the estatusReqCanRec
        restEstatusReqCanRecMockMvc.perform(get("/api/estatus-req-can-recs/{id}", estatusReqCanRec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusReqCanRec.getId().intValue()))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO.toString()));
    }

    @Test
    @Transactional
    public void getAllEstatusReqCanRecsByMotivoIsEqualToSomething() throws Exception {
        // Initialize the database
        estatusReqCanRecRepository.saveAndFlush(estatusReqCanRec);

        // Get all the estatusReqCanRecList where motivo equals to DEFAULT_MOTIVO
        defaultEstatusReqCanRecShouldBeFound("motivo.equals=" + DEFAULT_MOTIVO);

        // Get all the estatusReqCanRecList where motivo equals to UPDATED_MOTIVO
        defaultEstatusReqCanRecShouldNotBeFound("motivo.equals=" + UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void getAllEstatusReqCanRecsByMotivoIsInShouldWork() throws Exception {
        // Initialize the database
        estatusReqCanRecRepository.saveAndFlush(estatusReqCanRec);

        // Get all the estatusReqCanRecList where motivo in DEFAULT_MOTIVO or UPDATED_MOTIVO
        defaultEstatusReqCanRecShouldBeFound("motivo.in=" + DEFAULT_MOTIVO + "," + UPDATED_MOTIVO);

        // Get all the estatusReqCanRecList where motivo equals to UPDATED_MOTIVO
        defaultEstatusReqCanRecShouldNotBeFound("motivo.in=" + UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void getAllEstatusReqCanRecsByMotivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estatusReqCanRecRepository.saveAndFlush(estatusReqCanRec);

        // Get all the estatusReqCanRecList where motivo is not null
        defaultEstatusReqCanRecShouldBeFound("motivo.specified=true");

        // Get all the estatusReqCanRecList where motivo is null
        defaultEstatusReqCanRecShouldNotBeFound("motivo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstatusReqCanRecsByEstatusReqCanIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusReqCan estatusReqCan = EstatusReqCanResourceIT.createEntity(em);
        em.persist(estatusReqCan);
        em.flush();
        estatusReqCanRec.setEstatusReqCan(estatusReqCan);
        estatusReqCanRecRepository.saveAndFlush(estatusReqCanRec);
        Long estatusReqCanId = estatusReqCan.getId();

        // Get all the estatusReqCanRecList where estatusReqCan equals to estatusReqCanId
        defaultEstatusReqCanRecShouldBeFound("estatusReqCanId.equals=" + estatusReqCanId);

        // Get all the estatusReqCanRecList where estatusReqCan equals to estatusReqCanId + 1
        defaultEstatusReqCanRecShouldNotBeFound("estatusReqCanId.equals=" + (estatusReqCanId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstatusReqCanRecShouldBeFound(String filter) throws Exception {
        restEstatusReqCanRecMockMvc.perform(get("/api/estatus-req-can-recs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusReqCanRec.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO)));

        // Check, that the count call also returns 1
        restEstatusReqCanRecMockMvc.perform(get("/api/estatus-req-can-recs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstatusReqCanRecShouldNotBeFound(String filter) throws Exception {
        restEstatusReqCanRecMockMvc.perform(get("/api/estatus-req-can-recs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstatusReqCanRecMockMvc.perform(get("/api/estatus-req-can-recs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstatusReqCanRec() throws Exception {
        // Get the estatusReqCanRec
        restEstatusReqCanRecMockMvc.perform(get("/api/estatus-req-can-recs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusReqCanRec() throws Exception {
        // Initialize the database
        estatusReqCanRecRepository.saveAndFlush(estatusReqCanRec);

        int databaseSizeBeforeUpdate = estatusReqCanRecRepository.findAll().size();

        // Update the estatusReqCanRec
        EstatusReqCanRec updatedEstatusReqCanRec = estatusReqCanRecRepository.findById(estatusReqCanRec.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusReqCanRec are not directly saved in db
        em.detach(updatedEstatusReqCanRec);
        updatedEstatusReqCanRec
            .motivo(UPDATED_MOTIVO);
        EstatusReqCanRecDTO estatusReqCanRecDTO = estatusReqCanRecMapper.toDto(updatedEstatusReqCanRec);

        restEstatusReqCanRecMockMvc.perform(put("/api/estatus-req-can-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusReqCanRecDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusReqCanRec in the database
        List<EstatusReqCanRec> estatusReqCanRecList = estatusReqCanRecRepository.findAll();
        assertThat(estatusReqCanRecList).hasSize(databaseSizeBeforeUpdate);
        EstatusReqCanRec testEstatusReqCanRec = estatusReqCanRecList.get(estatusReqCanRecList.size() - 1);
        assertThat(testEstatusReqCanRec.getMotivo()).isEqualTo(UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusReqCanRec() throws Exception {
        int databaseSizeBeforeUpdate = estatusReqCanRecRepository.findAll().size();

        // Create the EstatusReqCanRec
        EstatusReqCanRecDTO estatusReqCanRecDTO = estatusReqCanRecMapper.toDto(estatusReqCanRec);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusReqCanRecMockMvc.perform(put("/api/estatus-req-can-recs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusReqCanRecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusReqCanRec in the database
        List<EstatusReqCanRec> estatusReqCanRecList = estatusReqCanRecRepository.findAll();
        assertThat(estatusReqCanRecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstatusReqCanRec() throws Exception {
        // Initialize the database
        estatusReqCanRecRepository.saveAndFlush(estatusReqCanRec);

        int databaseSizeBeforeDelete = estatusReqCanRecRepository.findAll().size();

        // Delete the estatusReqCanRec
        restEstatusReqCanRecMockMvc.perform(delete("/api/estatus-req-can-recs/{id}", estatusReqCanRec.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EstatusReqCanRec> estatusReqCanRecList = estatusReqCanRecRepository.findAll();
        assertThat(estatusReqCanRecList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusReqCanRec.class);
        EstatusReqCanRec estatusReqCanRec1 = new EstatusReqCanRec();
        estatusReqCanRec1.setId(1L);
        EstatusReqCanRec estatusReqCanRec2 = new EstatusReqCanRec();
        estatusReqCanRec2.setId(estatusReqCanRec1.getId());
        assertThat(estatusReqCanRec1).isEqualTo(estatusReqCanRec2);
        estatusReqCanRec2.setId(2L);
        assertThat(estatusReqCanRec1).isNotEqualTo(estatusReqCanRec2);
        estatusReqCanRec1.setId(null);
        assertThat(estatusReqCanRec1).isNotEqualTo(estatusReqCanRec2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusReqCanRecDTO.class);
        EstatusReqCanRecDTO estatusReqCanRecDTO1 = new EstatusReqCanRecDTO();
        estatusReqCanRecDTO1.setId(1L);
        EstatusReqCanRecDTO estatusReqCanRecDTO2 = new EstatusReqCanRecDTO();
        assertThat(estatusReqCanRecDTO1).isNotEqualTo(estatusReqCanRecDTO2);
        estatusReqCanRecDTO2.setId(estatusReqCanRecDTO1.getId());
        assertThat(estatusReqCanRecDTO1).isEqualTo(estatusReqCanRecDTO2);
        estatusReqCanRecDTO2.setId(2L);
        assertThat(estatusReqCanRecDTO1).isNotEqualTo(estatusReqCanRecDTO2);
        estatusReqCanRecDTO1.setId(null);
        assertThat(estatusReqCanRecDTO1).isNotEqualTo(estatusReqCanRecDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusReqCanRecMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusReqCanRecMapper.fromId(null)).isNull();
    }
}
