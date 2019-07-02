package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.SkillRequerimiento;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.domain.Skill;
import com.kode.ts.domain.TipoSkill;
import com.kode.ts.repository.SkillRequerimientoRepository;
import com.kode.ts.service.SkillRequerimientoService;
import com.kode.ts.service.dto.SkillRequerimientoDTO;
import com.kode.ts.service.mapper.SkillRequerimientoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.SkillRequerimientoCriteria;
import com.kode.ts.service.SkillRequerimientoQueryService;

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
 * Integration tests for the {@Link SkillRequerimientoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class SkillRequerimientoResourceIT {

    @Autowired
    private SkillRequerimientoRepository skillRequerimientoRepository;

    @Autowired
    private SkillRequerimientoMapper skillRequerimientoMapper;

    @Autowired
    private SkillRequerimientoService skillRequerimientoService;

    @Autowired
    private SkillRequerimientoQueryService skillRequerimientoQueryService;

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

    private MockMvc restSkillRequerimientoMockMvc;

    private SkillRequerimiento skillRequerimiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillRequerimientoResource skillRequerimientoResource = new SkillRequerimientoResource(skillRequerimientoService, skillRequerimientoQueryService);
        this.restSkillRequerimientoMockMvc = MockMvcBuilders.standaloneSetup(skillRequerimientoResource)
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
    public static SkillRequerimiento createEntity(EntityManager em) {
        SkillRequerimiento skillRequerimiento = new SkillRequerimiento();
        return skillRequerimiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillRequerimiento createUpdatedEntity(EntityManager em) {
        SkillRequerimiento skillRequerimiento = new SkillRequerimiento();
        return skillRequerimiento;
    }

    @BeforeEach
    public void initTest() {
        skillRequerimiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillRequerimiento() throws Exception {
        int databaseSizeBeforeCreate = skillRequerimientoRepository.findAll().size();

        // Create the SkillRequerimiento
        SkillRequerimientoDTO skillRequerimientoDTO = skillRequerimientoMapper.toDto(skillRequerimiento);
        restSkillRequerimientoMockMvc.perform(post("/api/skill-requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillRequerimientoDTO)))
            .andExpect(status().isCreated());

        // Validate the SkillRequerimiento in the database
        List<SkillRequerimiento> skillRequerimientoList = skillRequerimientoRepository.findAll();
        assertThat(skillRequerimientoList).hasSize(databaseSizeBeforeCreate + 1);
        SkillRequerimiento testSkillRequerimiento = skillRequerimientoList.get(skillRequerimientoList.size() - 1);
    }

    @Test
    @Transactional
    public void createSkillRequerimientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillRequerimientoRepository.findAll().size();

        // Create the SkillRequerimiento with an existing ID
        skillRequerimiento.setId(1L);
        SkillRequerimientoDTO skillRequerimientoDTO = skillRequerimientoMapper.toDto(skillRequerimiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillRequerimientoMockMvc.perform(post("/api/skill-requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillRequerimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkillRequerimiento in the database
        List<SkillRequerimiento> skillRequerimientoList = skillRequerimientoRepository.findAll();
        assertThat(skillRequerimientoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkillRequerimientos() throws Exception {
        // Initialize the database
        skillRequerimientoRepository.saveAndFlush(skillRequerimiento);

        // Get all the skillRequerimientoList
        restSkillRequerimientoMockMvc.perform(get("/api/skill-requerimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillRequerimiento.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSkillRequerimiento() throws Exception {
        // Initialize the database
        skillRequerimientoRepository.saveAndFlush(skillRequerimiento);

        // Get the skillRequerimiento
        restSkillRequerimientoMockMvc.perform(get("/api/skill-requerimientos/{id}", skillRequerimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillRequerimiento.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllSkillRequerimientosByIdRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento idRequerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(idRequerimiento);
        em.flush();
        skillRequerimiento.setIdRequerimiento(idRequerimiento);
        skillRequerimientoRepository.saveAndFlush(skillRequerimiento);
        Long idRequerimientoId = idRequerimiento.getId();

        // Get all the skillRequerimientoList where idRequerimiento equals to idRequerimientoId
        defaultSkillRequerimientoShouldBeFound("idRequerimientoId.equals=" + idRequerimientoId);

        // Get all the skillRequerimientoList where idRequerimiento equals to idRequerimientoId + 1
        defaultSkillRequerimientoShouldNotBeFound("idRequerimientoId.equals=" + (idRequerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillRequerimientosByIdSkillIsEqualToSomething() throws Exception {
        // Initialize the database
        Skill idSkill = SkillResourceIT.createEntity(em);
        em.persist(idSkill);
        em.flush();
        skillRequerimiento.setIdSkill(idSkill);
        skillRequerimientoRepository.saveAndFlush(skillRequerimiento);
        Long idSkillId = idSkill.getId();

        // Get all the skillRequerimientoList where idSkill equals to idSkillId
        defaultSkillRequerimientoShouldBeFound("idSkillId.equals=" + idSkillId);

        // Get all the skillRequerimientoList where idSkill equals to idSkillId + 1
        defaultSkillRequerimientoShouldNotBeFound("idSkillId.equals=" + (idSkillId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillRequerimientosByTipoSkillIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoSkill tipoSkill = TipoSkillResourceIT.createEntity(em);
        em.persist(tipoSkill);
        em.flush();
        skillRequerimiento.setTipoSkill(tipoSkill);
        skillRequerimientoRepository.saveAndFlush(skillRequerimiento);
        Long tipoSkillId = tipoSkill.getId();

        // Get all the skillRequerimientoList where tipoSkill equals to tipoSkillId
        defaultSkillRequerimientoShouldBeFound("tipoSkillId.equals=" + tipoSkillId);

        // Get all the skillRequerimientoList where tipoSkill equals to tipoSkillId + 1
        defaultSkillRequerimientoShouldNotBeFound("tipoSkillId.equals=" + (tipoSkillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillRequerimientoShouldBeFound(String filter) throws Exception {
        restSkillRequerimientoMockMvc.perform(get("/api/skill-requerimientos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillRequerimiento.getId().intValue())));

        // Check, that the count call also returns 1
        restSkillRequerimientoMockMvc.perform(get("/api/skill-requerimientos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillRequerimientoShouldNotBeFound(String filter) throws Exception {
        restSkillRequerimientoMockMvc.perform(get("/api/skill-requerimientos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillRequerimientoMockMvc.perform(get("/api/skill-requerimientos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSkillRequerimiento() throws Exception {
        // Get the skillRequerimiento
        restSkillRequerimientoMockMvc.perform(get("/api/skill-requerimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillRequerimiento() throws Exception {
        // Initialize the database
        skillRequerimientoRepository.saveAndFlush(skillRequerimiento);

        int databaseSizeBeforeUpdate = skillRequerimientoRepository.findAll().size();

        // Update the skillRequerimiento
        SkillRequerimiento updatedSkillRequerimiento = skillRequerimientoRepository.findById(skillRequerimiento.getId()).get();
        // Disconnect from session so that the updates on updatedSkillRequerimiento are not directly saved in db
        em.detach(updatedSkillRequerimiento);
        SkillRequerimientoDTO skillRequerimientoDTO = skillRequerimientoMapper.toDto(updatedSkillRequerimiento);

        restSkillRequerimientoMockMvc.perform(put("/api/skill-requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillRequerimientoDTO)))
            .andExpect(status().isOk());

        // Validate the SkillRequerimiento in the database
        List<SkillRequerimiento> skillRequerimientoList = skillRequerimientoRepository.findAll();
        assertThat(skillRequerimientoList).hasSize(databaseSizeBeforeUpdate);
        SkillRequerimiento testSkillRequerimiento = skillRequerimientoList.get(skillRequerimientoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillRequerimiento() throws Exception {
        int databaseSizeBeforeUpdate = skillRequerimientoRepository.findAll().size();

        // Create the SkillRequerimiento
        SkillRequerimientoDTO skillRequerimientoDTO = skillRequerimientoMapper.toDto(skillRequerimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillRequerimientoMockMvc.perform(put("/api/skill-requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillRequerimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkillRequerimiento in the database
        List<SkillRequerimiento> skillRequerimientoList = skillRequerimientoRepository.findAll();
        assertThat(skillRequerimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillRequerimiento() throws Exception {
        // Initialize the database
        skillRequerimientoRepository.saveAndFlush(skillRequerimiento);

        int databaseSizeBeforeDelete = skillRequerimientoRepository.findAll().size();

        // Delete the skillRequerimiento
        restSkillRequerimientoMockMvc.perform(delete("/api/skill-requerimientos/{id}", skillRequerimiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkillRequerimiento> skillRequerimientoList = skillRequerimientoRepository.findAll();
        assertThat(skillRequerimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillRequerimiento.class);
        SkillRequerimiento skillRequerimiento1 = new SkillRequerimiento();
        skillRequerimiento1.setId(1L);
        SkillRequerimiento skillRequerimiento2 = new SkillRequerimiento();
        skillRequerimiento2.setId(skillRequerimiento1.getId());
        assertThat(skillRequerimiento1).isEqualTo(skillRequerimiento2);
        skillRequerimiento2.setId(2L);
        assertThat(skillRequerimiento1).isNotEqualTo(skillRequerimiento2);
        skillRequerimiento1.setId(null);
        assertThat(skillRequerimiento1).isNotEqualTo(skillRequerimiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillRequerimientoDTO.class);
        SkillRequerimientoDTO skillRequerimientoDTO1 = new SkillRequerimientoDTO();
        skillRequerimientoDTO1.setId(1L);
        SkillRequerimientoDTO skillRequerimientoDTO2 = new SkillRequerimientoDTO();
        assertThat(skillRequerimientoDTO1).isNotEqualTo(skillRequerimientoDTO2);
        skillRequerimientoDTO2.setId(skillRequerimientoDTO1.getId());
        assertThat(skillRequerimientoDTO1).isEqualTo(skillRequerimientoDTO2);
        skillRequerimientoDTO2.setId(2L);
        assertThat(skillRequerimientoDTO1).isNotEqualTo(skillRequerimientoDTO2);
        skillRequerimientoDTO1.setId(null);
        assertThat(skillRequerimientoDTO1).isNotEqualTo(skillRequerimientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(skillRequerimientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(skillRequerimientoMapper.fromId(null)).isNull();
    }
}
