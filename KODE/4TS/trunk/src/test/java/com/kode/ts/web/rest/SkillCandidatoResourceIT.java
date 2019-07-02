package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.SkillCandidato;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.Skill;
import com.kode.ts.domain.DominioSkill;
import com.kode.ts.repository.SkillCandidatoRepository;
import com.kode.ts.service.SkillCandidatoService;
import com.kode.ts.service.dto.SkillCandidatoDTO;
import com.kode.ts.service.mapper.SkillCandidatoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.SkillCandidatoCriteria;
import com.kode.ts.service.SkillCandidatoQueryService;

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
 * Integration tests for the {@Link SkillCandidatoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class SkillCandidatoResourceIT {

    private static final Float DEFAULT_CALIFICACION_SKILL = 1F;
    private static final Float UPDATED_CALIFICACION_SKILL = 2F;

    @Autowired
    private SkillCandidatoRepository skillCandidatoRepository;

    @Autowired
    private SkillCandidatoMapper skillCandidatoMapper;

    @Autowired
    private SkillCandidatoService skillCandidatoService;

    @Autowired
    private SkillCandidatoQueryService skillCandidatoQueryService;

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

    private MockMvc restSkillCandidatoMockMvc;

    private SkillCandidato skillCandidato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillCandidatoResource skillCandidatoResource = new SkillCandidatoResource(skillCandidatoService, skillCandidatoQueryService);
        this.restSkillCandidatoMockMvc = MockMvcBuilders.standaloneSetup(skillCandidatoResource)
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
    public static SkillCandidato createEntity(EntityManager em) {
        SkillCandidato skillCandidato = new SkillCandidato()
            .calificacionSkill(DEFAULT_CALIFICACION_SKILL);
        return skillCandidato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillCandidato createUpdatedEntity(EntityManager em) {
        SkillCandidato skillCandidato = new SkillCandidato()
            .calificacionSkill(UPDATED_CALIFICACION_SKILL);
        return skillCandidato;
    }

    @BeforeEach
    public void initTest() {
        skillCandidato = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillCandidato() throws Exception {
        int databaseSizeBeforeCreate = skillCandidatoRepository.findAll().size();

        // Create the SkillCandidato
        SkillCandidatoDTO skillCandidatoDTO = skillCandidatoMapper.toDto(skillCandidato);
        restSkillCandidatoMockMvc.perform(post("/api/skill-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCandidatoDTO)))
            .andExpect(status().isCreated());

        // Validate the SkillCandidato in the database
        List<SkillCandidato> skillCandidatoList = skillCandidatoRepository.findAll();
        assertThat(skillCandidatoList).hasSize(databaseSizeBeforeCreate + 1);
        SkillCandidato testSkillCandidato = skillCandidatoList.get(skillCandidatoList.size() - 1);
        assertThat(testSkillCandidato.getCalificacionSkill()).isEqualTo(DEFAULT_CALIFICACION_SKILL);
    }

    @Test
    @Transactional
    public void createSkillCandidatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillCandidatoRepository.findAll().size();

        // Create the SkillCandidato with an existing ID
        skillCandidato.setId(1L);
        SkillCandidatoDTO skillCandidatoDTO = skillCandidatoMapper.toDto(skillCandidato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillCandidatoMockMvc.perform(post("/api/skill-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCandidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkillCandidato in the database
        List<SkillCandidato> skillCandidatoList = skillCandidatoRepository.findAll();
        assertThat(skillCandidatoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkillCandidatoes() throws Exception {
        // Initialize the database
        skillCandidatoRepository.saveAndFlush(skillCandidato);

        // Get all the skillCandidatoList
        restSkillCandidatoMockMvc.perform(get("/api/skill-candidatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillCandidato.getId().intValue())))
            .andExpect(jsonPath("$.[*].calificacionSkill").value(hasItem(DEFAULT_CALIFICACION_SKILL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSkillCandidato() throws Exception {
        // Initialize the database
        skillCandidatoRepository.saveAndFlush(skillCandidato);

        // Get the skillCandidato
        restSkillCandidatoMockMvc.perform(get("/api/skill-candidatoes/{id}", skillCandidato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillCandidato.getId().intValue()))
            .andExpect(jsonPath("$.calificacionSkill").value(DEFAULT_CALIFICACION_SKILL.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllSkillCandidatoesByCalificacionSkillIsEqualToSomething() throws Exception {
        // Initialize the database
        skillCandidatoRepository.saveAndFlush(skillCandidato);

        // Get all the skillCandidatoList where calificacionSkill equals to DEFAULT_CALIFICACION_SKILL
        defaultSkillCandidatoShouldBeFound("calificacionSkill.equals=" + DEFAULT_CALIFICACION_SKILL);

        // Get all the skillCandidatoList where calificacionSkill equals to UPDATED_CALIFICACION_SKILL
        defaultSkillCandidatoShouldNotBeFound("calificacionSkill.equals=" + UPDATED_CALIFICACION_SKILL);
    }

    @Test
    @Transactional
    public void getAllSkillCandidatoesByCalificacionSkillIsInShouldWork() throws Exception {
        // Initialize the database
        skillCandidatoRepository.saveAndFlush(skillCandidato);

        // Get all the skillCandidatoList where calificacionSkill in DEFAULT_CALIFICACION_SKILL or UPDATED_CALIFICACION_SKILL
        defaultSkillCandidatoShouldBeFound("calificacionSkill.in=" + DEFAULT_CALIFICACION_SKILL + "," + UPDATED_CALIFICACION_SKILL);

        // Get all the skillCandidatoList where calificacionSkill equals to UPDATED_CALIFICACION_SKILL
        defaultSkillCandidatoShouldNotBeFound("calificacionSkill.in=" + UPDATED_CALIFICACION_SKILL);
    }

    @Test
    @Transactional
    public void getAllSkillCandidatoesByCalificacionSkillIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillCandidatoRepository.saveAndFlush(skillCandidato);

        // Get all the skillCandidatoList where calificacionSkill is not null
        defaultSkillCandidatoShouldBeFound("calificacionSkill.specified=true");

        // Get all the skillCandidatoList where calificacionSkill is null
        defaultSkillCandidatoShouldNotBeFound("calificacionSkill.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillCandidatoesByIdCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato idCandidato = CandidatoResourceIT.createEntity(em);
        em.persist(idCandidato);
        em.flush();
        skillCandidato.setIdCandidato(idCandidato);
        skillCandidatoRepository.saveAndFlush(skillCandidato);
        Long idCandidatoId = idCandidato.getId();

        // Get all the skillCandidatoList where idCandidato equals to idCandidatoId
        defaultSkillCandidatoShouldBeFound("idCandidatoId.equals=" + idCandidatoId);

        // Get all the skillCandidatoList where idCandidato equals to idCandidatoId + 1
        defaultSkillCandidatoShouldNotBeFound("idCandidatoId.equals=" + (idCandidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillCandidatoesByIdSkillIsEqualToSomething() throws Exception {
        // Initialize the database
        Skill idSkill = SkillResourceIT.createEntity(em);
        em.persist(idSkill);
        em.flush();
        skillCandidato.setIdSkill(idSkill);
        skillCandidatoRepository.saveAndFlush(skillCandidato);
        Long idSkillId = idSkill.getId();

        // Get all the skillCandidatoList where idSkill equals to idSkillId
        defaultSkillCandidatoShouldBeFound("idSkillId.equals=" + idSkillId);

        // Get all the skillCandidatoList where idSkill equals to idSkillId + 1
        defaultSkillCandidatoShouldNotBeFound("idSkillId.equals=" + (idSkillId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillCandidatoesByNivelSkillIsEqualToSomething() throws Exception {
        // Initialize the database
        DominioSkill nivelSkill = DominioSkillResourceIT.createEntity(em);
        em.persist(nivelSkill);
        em.flush();
        skillCandidato.setNivelSkill(nivelSkill);
        skillCandidatoRepository.saveAndFlush(skillCandidato);
        Long nivelSkillId = nivelSkill.getId();

        // Get all the skillCandidatoList where nivelSkill equals to nivelSkillId
        defaultSkillCandidatoShouldBeFound("nivelSkillId.equals=" + nivelSkillId);

        // Get all the skillCandidatoList where nivelSkill equals to nivelSkillId + 1
        defaultSkillCandidatoShouldNotBeFound("nivelSkillId.equals=" + (nivelSkillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillCandidatoShouldBeFound(String filter) throws Exception {
        restSkillCandidatoMockMvc.perform(get("/api/skill-candidatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillCandidato.getId().intValue())))
            .andExpect(jsonPath("$.[*].calificacionSkill").value(hasItem(DEFAULT_CALIFICACION_SKILL.doubleValue())));

        // Check, that the count call also returns 1
        restSkillCandidatoMockMvc.perform(get("/api/skill-candidatoes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillCandidatoShouldNotBeFound(String filter) throws Exception {
        restSkillCandidatoMockMvc.perform(get("/api/skill-candidatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillCandidatoMockMvc.perform(get("/api/skill-candidatoes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSkillCandidato() throws Exception {
        // Get the skillCandidato
        restSkillCandidatoMockMvc.perform(get("/api/skill-candidatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillCandidato() throws Exception {
        // Initialize the database
        skillCandidatoRepository.saveAndFlush(skillCandidato);

        int databaseSizeBeforeUpdate = skillCandidatoRepository.findAll().size();

        // Update the skillCandidato
        SkillCandidato updatedSkillCandidato = skillCandidatoRepository.findById(skillCandidato.getId()).get();
        // Disconnect from session so that the updates on updatedSkillCandidato are not directly saved in db
        em.detach(updatedSkillCandidato);
        updatedSkillCandidato
            .calificacionSkill(UPDATED_CALIFICACION_SKILL);
        SkillCandidatoDTO skillCandidatoDTO = skillCandidatoMapper.toDto(updatedSkillCandidato);

        restSkillCandidatoMockMvc.perform(put("/api/skill-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCandidatoDTO)))
            .andExpect(status().isOk());

        // Validate the SkillCandidato in the database
        List<SkillCandidato> skillCandidatoList = skillCandidatoRepository.findAll();
        assertThat(skillCandidatoList).hasSize(databaseSizeBeforeUpdate);
        SkillCandidato testSkillCandidato = skillCandidatoList.get(skillCandidatoList.size() - 1);
        assertThat(testSkillCandidato.getCalificacionSkill()).isEqualTo(UPDATED_CALIFICACION_SKILL);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillCandidato() throws Exception {
        int databaseSizeBeforeUpdate = skillCandidatoRepository.findAll().size();

        // Create the SkillCandidato
        SkillCandidatoDTO skillCandidatoDTO = skillCandidatoMapper.toDto(skillCandidato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillCandidatoMockMvc.perform(put("/api/skill-candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCandidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkillCandidato in the database
        List<SkillCandidato> skillCandidatoList = skillCandidatoRepository.findAll();
        assertThat(skillCandidatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillCandidato() throws Exception {
        // Initialize the database
        skillCandidatoRepository.saveAndFlush(skillCandidato);

        int databaseSizeBeforeDelete = skillCandidatoRepository.findAll().size();

        // Delete the skillCandidato
        restSkillCandidatoMockMvc.perform(delete("/api/skill-candidatoes/{id}", skillCandidato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkillCandidato> skillCandidatoList = skillCandidatoRepository.findAll();
        assertThat(skillCandidatoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillCandidato.class);
        SkillCandidato skillCandidato1 = new SkillCandidato();
        skillCandidato1.setId(1L);
        SkillCandidato skillCandidato2 = new SkillCandidato();
        skillCandidato2.setId(skillCandidato1.getId());
        assertThat(skillCandidato1).isEqualTo(skillCandidato2);
        skillCandidato2.setId(2L);
        assertThat(skillCandidato1).isNotEqualTo(skillCandidato2);
        skillCandidato1.setId(null);
        assertThat(skillCandidato1).isNotEqualTo(skillCandidato2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillCandidatoDTO.class);
        SkillCandidatoDTO skillCandidatoDTO1 = new SkillCandidatoDTO();
        skillCandidatoDTO1.setId(1L);
        SkillCandidatoDTO skillCandidatoDTO2 = new SkillCandidatoDTO();
        assertThat(skillCandidatoDTO1).isNotEqualTo(skillCandidatoDTO2);
        skillCandidatoDTO2.setId(skillCandidatoDTO1.getId());
        assertThat(skillCandidatoDTO1).isEqualTo(skillCandidatoDTO2);
        skillCandidatoDTO2.setId(2L);
        assertThat(skillCandidatoDTO1).isNotEqualTo(skillCandidatoDTO2);
        skillCandidatoDTO1.setId(null);
        assertThat(skillCandidatoDTO1).isNotEqualTo(skillCandidatoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(skillCandidatoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(skillCandidatoMapper.fromId(null)).isNull();
    }
}
