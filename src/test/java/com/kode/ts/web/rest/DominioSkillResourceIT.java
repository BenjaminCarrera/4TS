package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.DominioSkill;
import com.kode.ts.domain.SkillCandidato;
import com.kode.ts.repository.DominioSkillRepository;
import com.kode.ts.service.DominioSkillService;
import com.kode.ts.service.dto.DominioSkillDTO;
import com.kode.ts.service.mapper.DominioSkillMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.DominioSkillCriteria;
import com.kode.ts.service.DominioSkillQueryService;

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
 * Integration tests for the {@Link DominioSkillResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class DominioSkillResourceIT {

    private static final String DEFAULT_DOMINIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMINIO = "BBBBBBBBBB";

    @Autowired
    private DominioSkillRepository dominioSkillRepository;

    @Autowired
    private DominioSkillMapper dominioSkillMapper;

    @Autowired
    private DominioSkillService dominioSkillService;

    @Autowired
    private DominioSkillQueryService dominioSkillQueryService;

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

    private MockMvc restDominioSkillMockMvc;

    private DominioSkill dominioSkill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DominioSkillResource dominioSkillResource = new DominioSkillResource(dominioSkillService, dominioSkillQueryService);
        this.restDominioSkillMockMvc = MockMvcBuilders.standaloneSetup(dominioSkillResource)
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
    public static DominioSkill createEntity(EntityManager em) {
        DominioSkill dominioSkill = new DominioSkill()
            .dominio(DEFAULT_DOMINIO);
        return dominioSkill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DominioSkill createUpdatedEntity(EntityManager em) {
        DominioSkill dominioSkill = new DominioSkill()
            .dominio(UPDATED_DOMINIO);
        return dominioSkill;
    }

    @BeforeEach
    public void initTest() {
        dominioSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createDominioSkill() throws Exception {
        int databaseSizeBeforeCreate = dominioSkillRepository.findAll().size();

        // Create the DominioSkill
        DominioSkillDTO dominioSkillDTO = dominioSkillMapper.toDto(dominioSkill);
        restDominioSkillMockMvc.perform(post("/api/dominio-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dominioSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the DominioSkill in the database
        List<DominioSkill> dominioSkillList = dominioSkillRepository.findAll();
        assertThat(dominioSkillList).hasSize(databaseSizeBeforeCreate + 1);
        DominioSkill testDominioSkill = dominioSkillList.get(dominioSkillList.size() - 1);
        assertThat(testDominioSkill.getDominio()).isEqualTo(DEFAULT_DOMINIO);
    }

    @Test
    @Transactional
    public void createDominioSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dominioSkillRepository.findAll().size();

        // Create the DominioSkill with an existing ID
        dominioSkill.setId(1L);
        DominioSkillDTO dominioSkillDTO = dominioSkillMapper.toDto(dominioSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDominioSkillMockMvc.perform(post("/api/dominio-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dominioSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DominioSkill in the database
        List<DominioSkill> dominioSkillList = dominioSkillRepository.findAll();
        assertThat(dominioSkillList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDominioSkills() throws Exception {
        // Initialize the database
        dominioSkillRepository.saveAndFlush(dominioSkill);

        // Get all the dominioSkillList
        restDominioSkillMockMvc.perform(get("/api/dominio-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dominioSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].dominio").value(hasItem(DEFAULT_DOMINIO.toString())));
    }
    
    @Test
    @Transactional
    public void getDominioSkill() throws Exception {
        // Initialize the database
        dominioSkillRepository.saveAndFlush(dominioSkill);

        // Get the dominioSkill
        restDominioSkillMockMvc.perform(get("/api/dominio-skills/{id}", dominioSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dominioSkill.getId().intValue()))
            .andExpect(jsonPath("$.dominio").value(DEFAULT_DOMINIO.toString()));
    }

    @Test
    @Transactional
    public void getAllDominioSkillsByDominioIsEqualToSomething() throws Exception {
        // Initialize the database
        dominioSkillRepository.saveAndFlush(dominioSkill);

        // Get all the dominioSkillList where dominio equals to DEFAULT_DOMINIO
        defaultDominioSkillShouldBeFound("dominio.equals=" + DEFAULT_DOMINIO);

        // Get all the dominioSkillList where dominio equals to UPDATED_DOMINIO
        defaultDominioSkillShouldNotBeFound("dominio.equals=" + UPDATED_DOMINIO);
    }

    @Test
    @Transactional
    public void getAllDominioSkillsByDominioIsInShouldWork() throws Exception {
        // Initialize the database
        dominioSkillRepository.saveAndFlush(dominioSkill);

        // Get all the dominioSkillList where dominio in DEFAULT_DOMINIO or UPDATED_DOMINIO
        defaultDominioSkillShouldBeFound("dominio.in=" + DEFAULT_DOMINIO + "," + UPDATED_DOMINIO);

        // Get all the dominioSkillList where dominio equals to UPDATED_DOMINIO
        defaultDominioSkillShouldNotBeFound("dominio.in=" + UPDATED_DOMINIO);
    }

    @Test
    @Transactional
    public void getAllDominioSkillsByDominioIsNullOrNotNull() throws Exception {
        // Initialize the database
        dominioSkillRepository.saveAndFlush(dominioSkill);

        // Get all the dominioSkillList where dominio is not null
        defaultDominioSkillShouldBeFound("dominio.specified=true");

        // Get all the dominioSkillList where dominio is null
        defaultDominioSkillShouldNotBeFound("dominio.specified=false");
    }

    @Test
    @Transactional
    public void getAllDominioSkillsBySkillCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillCandidato skillCandidato = SkillCandidatoResourceIT.createEntity(em);
        em.persist(skillCandidato);
        em.flush();
        dominioSkill.addSkillCandidato(skillCandidato);
        dominioSkillRepository.saveAndFlush(dominioSkill);
        Long skillCandidatoId = skillCandidato.getId();

        // Get all the dominioSkillList where skillCandidato equals to skillCandidatoId
        defaultDominioSkillShouldBeFound("skillCandidatoId.equals=" + skillCandidatoId);

        // Get all the dominioSkillList where skillCandidato equals to skillCandidatoId + 1
        defaultDominioSkillShouldNotBeFound("skillCandidatoId.equals=" + (skillCandidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDominioSkillShouldBeFound(String filter) throws Exception {
        restDominioSkillMockMvc.perform(get("/api/dominio-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dominioSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].dominio").value(hasItem(DEFAULT_DOMINIO)));

        // Check, that the count call also returns 1
        restDominioSkillMockMvc.perform(get("/api/dominio-skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDominioSkillShouldNotBeFound(String filter) throws Exception {
        restDominioSkillMockMvc.perform(get("/api/dominio-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDominioSkillMockMvc.perform(get("/api/dominio-skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDominioSkill() throws Exception {
        // Get the dominioSkill
        restDominioSkillMockMvc.perform(get("/api/dominio-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDominioSkill() throws Exception {
        // Initialize the database
        dominioSkillRepository.saveAndFlush(dominioSkill);

        int databaseSizeBeforeUpdate = dominioSkillRepository.findAll().size();

        // Update the dominioSkill
        DominioSkill updatedDominioSkill = dominioSkillRepository.findById(dominioSkill.getId()).get();
        // Disconnect from session so that the updates on updatedDominioSkill are not directly saved in db
        em.detach(updatedDominioSkill);
        updatedDominioSkill
            .dominio(UPDATED_DOMINIO);
        DominioSkillDTO dominioSkillDTO = dominioSkillMapper.toDto(updatedDominioSkill);

        restDominioSkillMockMvc.perform(put("/api/dominio-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dominioSkillDTO)))
            .andExpect(status().isOk());

        // Validate the DominioSkill in the database
        List<DominioSkill> dominioSkillList = dominioSkillRepository.findAll();
        assertThat(dominioSkillList).hasSize(databaseSizeBeforeUpdate);
        DominioSkill testDominioSkill = dominioSkillList.get(dominioSkillList.size() - 1);
        assertThat(testDominioSkill.getDominio()).isEqualTo(UPDATED_DOMINIO);
    }

    @Test
    @Transactional
    public void updateNonExistingDominioSkill() throws Exception {
        int databaseSizeBeforeUpdate = dominioSkillRepository.findAll().size();

        // Create the DominioSkill
        DominioSkillDTO dominioSkillDTO = dominioSkillMapper.toDto(dominioSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDominioSkillMockMvc.perform(put("/api/dominio-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dominioSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DominioSkill in the database
        List<DominioSkill> dominioSkillList = dominioSkillRepository.findAll();
        assertThat(dominioSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDominioSkill() throws Exception {
        // Initialize the database
        dominioSkillRepository.saveAndFlush(dominioSkill);

        int databaseSizeBeforeDelete = dominioSkillRepository.findAll().size();

        // Delete the dominioSkill
        restDominioSkillMockMvc.perform(delete("/api/dominio-skills/{id}", dominioSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DominioSkill> dominioSkillList = dominioSkillRepository.findAll();
        assertThat(dominioSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DominioSkill.class);
        DominioSkill dominioSkill1 = new DominioSkill();
        dominioSkill1.setId(1L);
        DominioSkill dominioSkill2 = new DominioSkill();
        dominioSkill2.setId(dominioSkill1.getId());
        assertThat(dominioSkill1).isEqualTo(dominioSkill2);
        dominioSkill2.setId(2L);
        assertThat(dominioSkill1).isNotEqualTo(dominioSkill2);
        dominioSkill1.setId(null);
        assertThat(dominioSkill1).isNotEqualTo(dominioSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DominioSkillDTO.class);
        DominioSkillDTO dominioSkillDTO1 = new DominioSkillDTO();
        dominioSkillDTO1.setId(1L);
        DominioSkillDTO dominioSkillDTO2 = new DominioSkillDTO();
        assertThat(dominioSkillDTO1).isNotEqualTo(dominioSkillDTO2);
        dominioSkillDTO2.setId(dominioSkillDTO1.getId());
        assertThat(dominioSkillDTO1).isEqualTo(dominioSkillDTO2);
        dominioSkillDTO2.setId(2L);
        assertThat(dominioSkillDTO1).isNotEqualTo(dominioSkillDTO2);
        dominioSkillDTO1.setId(null);
        assertThat(dominioSkillDTO1).isNotEqualTo(dominioSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dominioSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dominioSkillMapper.fromId(null)).isNull();
    }
}
