package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.CategoriaSkill;
import com.kode.ts.domain.Skill;
import com.kode.ts.repository.CategoriaSkillRepository;
import com.kode.ts.service.CategoriaSkillService;
import com.kode.ts.service.dto.CategoriaSkillDTO;
import com.kode.ts.service.mapper.CategoriaSkillMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.CategoriaSkillCriteria;
import com.kode.ts.service.CategoriaSkillQueryService;

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
 * Integration tests for the {@Link CategoriaSkillResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class CategoriaSkillResourceIT {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    @Autowired
    private CategoriaSkillRepository categoriaSkillRepository;

    @Autowired
    private CategoriaSkillMapper categoriaSkillMapper;

    @Autowired
    private CategoriaSkillService categoriaSkillService;

    @Autowired
    private CategoriaSkillQueryService categoriaSkillQueryService;

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

    private MockMvc restCategoriaSkillMockMvc;

    private CategoriaSkill categoriaSkill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaSkillResource categoriaSkillResource = new CategoriaSkillResource(categoriaSkillService, categoriaSkillQueryService);
        this.restCategoriaSkillMockMvc = MockMvcBuilders.standaloneSetup(categoriaSkillResource)
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
    public static CategoriaSkill createEntity(EntityManager em) {
        CategoriaSkill categoriaSkill = new CategoriaSkill()
            .categoria(DEFAULT_CATEGORIA);
        return categoriaSkill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaSkill createUpdatedEntity(EntityManager em) {
        CategoriaSkill categoriaSkill = new CategoriaSkill()
            .categoria(UPDATED_CATEGORIA);
        return categoriaSkill;
    }

    @BeforeEach
    public void initTest() {
        categoriaSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaSkill() throws Exception {
        int databaseSizeBeforeCreate = categoriaSkillRepository.findAll().size();

        // Create the CategoriaSkill
        CategoriaSkillDTO categoriaSkillDTO = categoriaSkillMapper.toDto(categoriaSkill);
        restCategoriaSkillMockMvc.perform(post("/api/categoria-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoriaSkill in the database
        List<CategoriaSkill> categoriaSkillList = categoriaSkillRepository.findAll();
        assertThat(categoriaSkillList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaSkill testCategoriaSkill = categoriaSkillList.get(categoriaSkillList.size() - 1);
        assertThat(testCategoriaSkill.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
    }

    @Test
    @Transactional
    public void createCategoriaSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaSkillRepository.findAll().size();

        // Create the CategoriaSkill with an existing ID
        categoriaSkill.setId(1L);
        CategoriaSkillDTO categoriaSkillDTO = categoriaSkillMapper.toDto(categoriaSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaSkillMockMvc.perform(post("/api/categoria-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaSkill in the database
        List<CategoriaSkill> categoriaSkillList = categoriaSkillRepository.findAll();
        assertThat(categoriaSkillList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategoriaSkills() throws Exception {
        // Initialize the database
        categoriaSkillRepository.saveAndFlush(categoriaSkill);

        // Get all the categoriaSkillList
        restCategoriaSkillMockMvc.perform(get("/api/categoria-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoriaSkill() throws Exception {
        // Initialize the database
        categoriaSkillRepository.saveAndFlush(categoriaSkill);

        // Get the categoriaSkill
        restCategoriaSkillMockMvc.perform(get("/api/categoria-skills/{id}", categoriaSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaSkill.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }

    @Test
    @Transactional
    public void getAllCategoriaSkillsByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaSkillRepository.saveAndFlush(categoriaSkill);

        // Get all the categoriaSkillList where categoria equals to DEFAULT_CATEGORIA
        defaultCategoriaSkillShouldBeFound("categoria.equals=" + DEFAULT_CATEGORIA);

        // Get all the categoriaSkillList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriaSkillShouldNotBeFound("categoria.equals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaSkillsByCategoriaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaSkillRepository.saveAndFlush(categoriaSkill);

        // Get all the categoriaSkillList where categoria in DEFAULT_CATEGORIA or UPDATED_CATEGORIA
        defaultCategoriaSkillShouldBeFound("categoria.in=" + DEFAULT_CATEGORIA + "," + UPDATED_CATEGORIA);

        // Get all the categoriaSkillList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriaSkillShouldNotBeFound("categoria.in=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaSkillsByCategoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaSkillRepository.saveAndFlush(categoriaSkill);

        // Get all the categoriaSkillList where categoria is not null
        defaultCategoriaSkillShouldBeFound("categoria.specified=true");

        // Get all the categoriaSkillList where categoria is null
        defaultCategoriaSkillShouldNotBeFound("categoria.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriaSkillsBySkillIsEqualToSomething() throws Exception {
        // Initialize the database
        Skill skill = SkillResourceIT.createEntity(em);
        em.persist(skill);
        em.flush();
        categoriaSkill.addSkill(skill);
        categoriaSkillRepository.saveAndFlush(categoriaSkill);
        Long skillId = skill.getId();

        // Get all the categoriaSkillList where skill equals to skillId
        defaultCategoriaSkillShouldBeFound("skillId.equals=" + skillId);

        // Get all the categoriaSkillList where skill equals to skillId + 1
        defaultCategoriaSkillShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaSkillShouldBeFound(String filter) throws Exception {
        restCategoriaSkillMockMvc.perform(get("/api/categoria-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));

        // Check, that the count call also returns 1
        restCategoriaSkillMockMvc.perform(get("/api/categoria-skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaSkillShouldNotBeFound(String filter) throws Exception {
        restCategoriaSkillMockMvc.perform(get("/api/categoria-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaSkillMockMvc.perform(get("/api/categoria-skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategoriaSkill() throws Exception {
        // Get the categoriaSkill
        restCategoriaSkillMockMvc.perform(get("/api/categoria-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaSkill() throws Exception {
        // Initialize the database
        categoriaSkillRepository.saveAndFlush(categoriaSkill);

        int databaseSizeBeforeUpdate = categoriaSkillRepository.findAll().size();

        // Update the categoriaSkill
        CategoriaSkill updatedCategoriaSkill = categoriaSkillRepository.findById(categoriaSkill.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaSkill are not directly saved in db
        em.detach(updatedCategoriaSkill);
        updatedCategoriaSkill
            .categoria(UPDATED_CATEGORIA);
        CategoriaSkillDTO categoriaSkillDTO = categoriaSkillMapper.toDto(updatedCategoriaSkill);

        restCategoriaSkillMockMvc.perform(put("/api/categoria-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaSkillDTO)))
            .andExpect(status().isOk());

        // Validate the CategoriaSkill in the database
        List<CategoriaSkill> categoriaSkillList = categoriaSkillRepository.findAll();
        assertThat(categoriaSkillList).hasSize(databaseSizeBeforeUpdate);
        CategoriaSkill testCategoriaSkill = categoriaSkillList.get(categoriaSkillList.size() - 1);
        assertThat(testCategoriaSkill.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaSkill() throws Exception {
        int databaseSizeBeforeUpdate = categoriaSkillRepository.findAll().size();

        // Create the CategoriaSkill
        CategoriaSkillDTO categoriaSkillDTO = categoriaSkillMapper.toDto(categoriaSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaSkillMockMvc.perform(put("/api/categoria-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaSkill in the database
        List<CategoriaSkill> categoriaSkillList = categoriaSkillRepository.findAll();
        assertThat(categoriaSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoriaSkill() throws Exception {
        // Initialize the database
        categoriaSkillRepository.saveAndFlush(categoriaSkill);

        int databaseSizeBeforeDelete = categoriaSkillRepository.findAll().size();

        // Delete the categoriaSkill
        restCategoriaSkillMockMvc.perform(delete("/api/categoria-skills/{id}", categoriaSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CategoriaSkill> categoriaSkillList = categoriaSkillRepository.findAll();
        assertThat(categoriaSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaSkill.class);
        CategoriaSkill categoriaSkill1 = new CategoriaSkill();
        categoriaSkill1.setId(1L);
        CategoriaSkill categoriaSkill2 = new CategoriaSkill();
        categoriaSkill2.setId(categoriaSkill1.getId());
        assertThat(categoriaSkill1).isEqualTo(categoriaSkill2);
        categoriaSkill2.setId(2L);
        assertThat(categoriaSkill1).isNotEqualTo(categoriaSkill2);
        categoriaSkill1.setId(null);
        assertThat(categoriaSkill1).isNotEqualTo(categoriaSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaSkillDTO.class);
        CategoriaSkillDTO categoriaSkillDTO1 = new CategoriaSkillDTO();
        categoriaSkillDTO1.setId(1L);
        CategoriaSkillDTO categoriaSkillDTO2 = new CategoriaSkillDTO();
        assertThat(categoriaSkillDTO1).isNotEqualTo(categoriaSkillDTO2);
        categoriaSkillDTO2.setId(categoriaSkillDTO1.getId());
        assertThat(categoriaSkillDTO1).isEqualTo(categoriaSkillDTO2);
        categoriaSkillDTO2.setId(2L);
        assertThat(categoriaSkillDTO1).isNotEqualTo(categoriaSkillDTO2);
        categoriaSkillDTO1.setId(null);
        assertThat(categoriaSkillDTO1).isNotEqualTo(categoriaSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categoriaSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categoriaSkillMapper.fromId(null)).isNull();
    }
}
