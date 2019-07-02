package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.TipoSkill;
import com.kode.ts.domain.SkillRequerimiento;
import com.kode.ts.repository.TipoSkillRepository;
import com.kode.ts.service.TipoSkillService;
import com.kode.ts.service.dto.TipoSkillDTO;
import com.kode.ts.service.mapper.TipoSkillMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.TipoSkillCriteria;
import com.kode.ts.service.TipoSkillQueryService;

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
 * Integration tests for the {@Link TipoSkillResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class TipoSkillResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private TipoSkillRepository tipoSkillRepository;

    @Autowired
    private TipoSkillMapper tipoSkillMapper;

    @Autowired
    private TipoSkillService tipoSkillService;

    @Autowired
    private TipoSkillQueryService tipoSkillQueryService;

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

    private MockMvc restTipoSkillMockMvc;

    private TipoSkill tipoSkill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoSkillResource tipoSkillResource = new TipoSkillResource(tipoSkillService, tipoSkillQueryService);
        this.restTipoSkillMockMvc = MockMvcBuilders.standaloneSetup(tipoSkillResource)
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
    public static TipoSkill createEntity(EntityManager em) {
        TipoSkill tipoSkill = new TipoSkill()
            .tipo(DEFAULT_TIPO);
        return tipoSkill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoSkill createUpdatedEntity(EntityManager em) {
        TipoSkill tipoSkill = new TipoSkill()
            .tipo(UPDATED_TIPO);
        return tipoSkill;
    }

    @BeforeEach
    public void initTest() {
        tipoSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoSkill() throws Exception {
        int databaseSizeBeforeCreate = tipoSkillRepository.findAll().size();

        // Create the TipoSkill
        TipoSkillDTO tipoSkillDTO = tipoSkillMapper.toDto(tipoSkill);
        restTipoSkillMockMvc.perform(post("/api/tipo-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoSkill in the database
        List<TipoSkill> tipoSkillList = tipoSkillRepository.findAll();
        assertThat(tipoSkillList).hasSize(databaseSizeBeforeCreate + 1);
        TipoSkill testTipoSkill = tipoSkillList.get(tipoSkillList.size() - 1);
        assertThat(testTipoSkill.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createTipoSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoSkillRepository.findAll().size();

        // Create the TipoSkill with an existing ID
        tipoSkill.setId(1L);
        TipoSkillDTO tipoSkillDTO = tipoSkillMapper.toDto(tipoSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoSkillMockMvc.perform(post("/api/tipo-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSkill in the database
        List<TipoSkill> tipoSkillList = tipoSkillRepository.findAll();
        assertThat(tipoSkillList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoSkills() throws Exception {
        // Initialize the database
        tipoSkillRepository.saveAndFlush(tipoSkill);

        // Get all the tipoSkillList
        restTipoSkillMockMvc.perform(get("/api/tipo-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoSkill() throws Exception {
        // Initialize the database
        tipoSkillRepository.saveAndFlush(tipoSkill);

        // Get the tipoSkill
        restTipoSkillMockMvc.perform(get("/api/tipo-skills/{id}", tipoSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoSkill.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoSkillsByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoSkillRepository.saveAndFlush(tipoSkill);

        // Get all the tipoSkillList where tipo equals to DEFAULT_TIPO
        defaultTipoSkillShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the tipoSkillList where tipo equals to UPDATED_TIPO
        defaultTipoSkillShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTipoSkillsByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoSkillRepository.saveAndFlush(tipoSkill);

        // Get all the tipoSkillList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultTipoSkillShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the tipoSkillList where tipo equals to UPDATED_TIPO
        defaultTipoSkillShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllTipoSkillsByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoSkillRepository.saveAndFlush(tipoSkill);

        // Get all the tipoSkillList where tipo is not null
        defaultTipoSkillShouldBeFound("tipo.specified=true");

        // Get all the tipoSkillList where tipo is null
        defaultTipoSkillShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoSkillsBySkillRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillRequerimiento skillRequerimiento = SkillRequerimientoResourceIT.createEntity(em);
        em.persist(skillRequerimiento);
        em.flush();
        tipoSkill.addSkillRequerimiento(skillRequerimiento);
        tipoSkillRepository.saveAndFlush(tipoSkill);
        Long skillRequerimientoId = skillRequerimiento.getId();

        // Get all the tipoSkillList where skillRequerimiento equals to skillRequerimientoId
        defaultTipoSkillShouldBeFound("skillRequerimientoId.equals=" + skillRequerimientoId);

        // Get all the tipoSkillList where skillRequerimiento equals to skillRequerimientoId + 1
        defaultTipoSkillShouldNotBeFound("skillRequerimientoId.equals=" + (skillRequerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoSkillShouldBeFound(String filter) throws Exception {
        restTipoSkillMockMvc.perform(get("/api/tipo-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));

        // Check, that the count call also returns 1
        restTipoSkillMockMvc.perform(get("/api/tipo-skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoSkillShouldNotBeFound(String filter) throws Exception {
        restTipoSkillMockMvc.perform(get("/api/tipo-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoSkillMockMvc.perform(get("/api/tipo-skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoSkill() throws Exception {
        // Get the tipoSkill
        restTipoSkillMockMvc.perform(get("/api/tipo-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoSkill() throws Exception {
        // Initialize the database
        tipoSkillRepository.saveAndFlush(tipoSkill);

        int databaseSizeBeforeUpdate = tipoSkillRepository.findAll().size();

        // Update the tipoSkill
        TipoSkill updatedTipoSkill = tipoSkillRepository.findById(tipoSkill.getId()).get();
        // Disconnect from session so that the updates on updatedTipoSkill are not directly saved in db
        em.detach(updatedTipoSkill);
        updatedTipoSkill
            .tipo(UPDATED_TIPO);
        TipoSkillDTO tipoSkillDTO = tipoSkillMapper.toDto(updatedTipoSkill);

        restTipoSkillMockMvc.perform(put("/api/tipo-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSkillDTO)))
            .andExpect(status().isOk());

        // Validate the TipoSkill in the database
        List<TipoSkill> tipoSkillList = tipoSkillRepository.findAll();
        assertThat(tipoSkillList).hasSize(databaseSizeBeforeUpdate);
        TipoSkill testTipoSkill = tipoSkillList.get(tipoSkillList.size() - 1);
        assertThat(testTipoSkill.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoSkill() throws Exception {
        int databaseSizeBeforeUpdate = tipoSkillRepository.findAll().size();

        // Create the TipoSkill
        TipoSkillDTO tipoSkillDTO = tipoSkillMapper.toDto(tipoSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoSkillMockMvc.perform(put("/api/tipo-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSkill in the database
        List<TipoSkill> tipoSkillList = tipoSkillRepository.findAll();
        assertThat(tipoSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoSkill() throws Exception {
        // Initialize the database
        tipoSkillRepository.saveAndFlush(tipoSkill);

        int databaseSizeBeforeDelete = tipoSkillRepository.findAll().size();

        // Delete the tipoSkill
        restTipoSkillMockMvc.perform(delete("/api/tipo-skills/{id}", tipoSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TipoSkill> tipoSkillList = tipoSkillRepository.findAll();
        assertThat(tipoSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSkill.class);
        TipoSkill tipoSkill1 = new TipoSkill();
        tipoSkill1.setId(1L);
        TipoSkill tipoSkill2 = new TipoSkill();
        tipoSkill2.setId(tipoSkill1.getId());
        assertThat(tipoSkill1).isEqualTo(tipoSkill2);
        tipoSkill2.setId(2L);
        assertThat(tipoSkill1).isNotEqualTo(tipoSkill2);
        tipoSkill1.setId(null);
        assertThat(tipoSkill1).isNotEqualTo(tipoSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSkillDTO.class);
        TipoSkillDTO tipoSkillDTO1 = new TipoSkillDTO();
        tipoSkillDTO1.setId(1L);
        TipoSkillDTO tipoSkillDTO2 = new TipoSkillDTO();
        assertThat(tipoSkillDTO1).isNotEqualTo(tipoSkillDTO2);
        tipoSkillDTO2.setId(tipoSkillDTO1.getId());
        assertThat(tipoSkillDTO1).isEqualTo(tipoSkillDTO2);
        tipoSkillDTO2.setId(2L);
        assertThat(tipoSkillDTO1).isNotEqualTo(tipoSkillDTO2);
        tipoSkillDTO1.setId(null);
        assertThat(tipoSkillDTO1).isNotEqualTo(tipoSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoSkillMapper.fromId(null)).isNull();
    }
}
