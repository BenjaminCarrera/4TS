package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Skill;
import com.kode.ts.domain.SkillCandidato;
import com.kode.ts.domain.SkillRequerimiento;
import com.kode.ts.domain.CategoriaSkill;
import com.kode.ts.repository.SkillRepository;
import com.kode.ts.service.SkillService;
import com.kode.ts.service.dto.SkillDTO;
import com.kode.ts.service.mapper.SkillMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.SkillCriteria;
import com.kode.ts.service.SkillQueryService;

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
 * Integration tests for the {@Link SkillResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class SkillResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillQueryService skillQueryService;

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

    private MockMvc restSkillMockMvc;

    private Skill skill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillResource skillResource = new SkillResource(skillService, skillQueryService);
        this.restSkillMockMvc = MockMvcBuilders.standaloneSetup(skillResource)
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
    public static Skill createEntity(EntityManager em) {
        Skill skill = new Skill()
            .nombre(DEFAULT_NOMBRE);
        return skill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createUpdatedEntity(EntityManager em) {
        Skill skill = new Skill()
            .nombre(UPDATED_NOMBRE);
        return skill;
    }

    @BeforeEach
    public void initTest() {
        skill = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkill() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);
        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isCreated());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate + 1);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // Create the Skill with an existing ID
        skill.setId(1L);
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getAllSkillsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where nombre equals to DEFAULT_NOMBRE
        defaultSkillShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the skillList where nombre equals to UPDATED_NOMBRE
        defaultSkillShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSkillsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultSkillShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the skillList where nombre equals to UPDATED_NOMBRE
        defaultSkillShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSkillsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where nombre is not null
        defaultSkillShouldBeFound("nombre.specified=true");

        // Get all the skillList where nombre is null
        defaultSkillShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsBySkillCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillCandidato skillCandidato = SkillCandidatoResourceIT.createEntity(em);
        em.persist(skillCandidato);
        em.flush();
        skill.addSkillCandidato(skillCandidato);
        skillRepository.saveAndFlush(skill);
        Long skillCandidatoId = skillCandidato.getId();

        // Get all the skillList where skillCandidato equals to skillCandidatoId
        defaultSkillShouldBeFound("skillCandidatoId.equals=" + skillCandidatoId);

        // Get all the skillList where skillCandidato equals to skillCandidatoId + 1
        defaultSkillShouldNotBeFound("skillCandidatoId.equals=" + (skillCandidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillsBySkillRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillRequerimiento skillRequerimiento = SkillRequerimientoResourceIT.createEntity(em);
        em.persist(skillRequerimiento);
        em.flush();
        skill.addSkillRequerimiento(skillRequerimiento);
        skillRepository.saveAndFlush(skill);
        Long skillRequerimientoId = skillRequerimiento.getId();

        // Get all the skillList where skillRequerimiento equals to skillRequerimientoId
        defaultSkillShouldBeFound("skillRequerimientoId.equals=" + skillRequerimientoId);

        // Get all the skillList where skillRequerimiento equals to skillRequerimientoId + 1
        defaultSkillShouldNotBeFound("skillRequerimientoId.equals=" + (skillRequerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillsByCategoriaSkillIsEqualToSomething() throws Exception {
        // Initialize the database
        CategoriaSkill categoriaSkill = CategoriaSkillResourceIT.createEntity(em);
        em.persist(categoriaSkill);
        em.flush();
        skill.setCategoriaSkill(categoriaSkill);
        skillRepository.saveAndFlush(skill);
        Long categoriaSkillId = categoriaSkill.getId();

        // Get all the skillList where categoriaSkill equals to categoriaSkillId
        defaultSkillShouldBeFound("categoriaSkillId.equals=" + categoriaSkillId);

        // Get all the skillList where categoriaSkill equals to categoriaSkillId + 1
        defaultSkillShouldNotBeFound("categoriaSkillId.equals=" + (categoriaSkillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillShouldBeFound(String filter) throws Exception {
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

        // Check, that the count call also returns 1
        restSkillMockMvc.perform(get("/api/skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillShouldNotBeFound(String filter) throws Exception {
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillMockMvc.perform(get("/api/skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill
        Skill updatedSkill = skillRepository.findById(skill.getId()).get();
        // Disconnect from session so that the updates on updatedSkill are not directly saved in db
        em.detach(updatedSkill);
        updatedSkill
            .nombre(UPDATED_NOMBRE);
        SkillDTO skillDTO = skillMapper.toDto(updatedSkill);

        restSkillMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeDelete = skillRepository.findAll().size();

        // Delete the skill
        restSkillMockMvc.perform(delete("/api/skills/{id}", skill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skill.class);
        Skill skill1 = new Skill();
        skill1.setId(1L);
        Skill skill2 = new Skill();
        skill2.setId(skill1.getId());
        assertThat(skill1).isEqualTo(skill2);
        skill2.setId(2L);
        assertThat(skill1).isNotEqualTo(skill2);
        skill1.setId(null);
        assertThat(skill1).isNotEqualTo(skill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillDTO.class);
        SkillDTO skillDTO1 = new SkillDTO();
        skillDTO1.setId(1L);
        SkillDTO skillDTO2 = new SkillDTO();
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
        skillDTO2.setId(skillDTO1.getId());
        assertThat(skillDTO1).isEqualTo(skillDTO2);
        skillDTO2.setId(2L);
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
        skillDTO1.setId(null);
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(skillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(skillMapper.fromId(null)).isNull();
    }
}
