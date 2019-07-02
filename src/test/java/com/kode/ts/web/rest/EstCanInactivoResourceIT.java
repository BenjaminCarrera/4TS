package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstCanInactivo;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.EstatusCandidato;
import com.kode.ts.repository.EstCanInactivoRepository;
import com.kode.ts.service.EstCanInactivoService;
import com.kode.ts.service.dto.EstCanInactivoDTO;
import com.kode.ts.service.mapper.EstCanInactivoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstCanInactivoCriteria;
import com.kode.ts.service.EstCanInactivoQueryService;

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
 * Integration tests for the {@Link EstCanInactivoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstCanInactivoResourceIT {

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    @Autowired
    private EstCanInactivoRepository estCanInactivoRepository;

    @Autowired
    private EstCanInactivoMapper estCanInactivoMapper;

    @Autowired
    private EstCanInactivoService estCanInactivoService;

    @Autowired
    private EstCanInactivoQueryService estCanInactivoQueryService;

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

    private MockMvc restEstCanInactivoMockMvc;

    private EstCanInactivo estCanInactivo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstCanInactivoResource estCanInactivoResource = new EstCanInactivoResource(estCanInactivoService, estCanInactivoQueryService);
        this.restEstCanInactivoMockMvc = MockMvcBuilders.standaloneSetup(estCanInactivoResource)
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
    public static EstCanInactivo createEntity(EntityManager em) {
        EstCanInactivo estCanInactivo = new EstCanInactivo()
            .motivo(DEFAULT_MOTIVO);
        return estCanInactivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstCanInactivo createUpdatedEntity(EntityManager em) {
        EstCanInactivo estCanInactivo = new EstCanInactivo()
            .motivo(UPDATED_MOTIVO);
        return estCanInactivo;
    }

    @BeforeEach
    public void initTest() {
        estCanInactivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstCanInactivo() throws Exception {
        int databaseSizeBeforeCreate = estCanInactivoRepository.findAll().size();

        // Create the EstCanInactivo
        EstCanInactivoDTO estCanInactivoDTO = estCanInactivoMapper.toDto(estCanInactivo);
        restEstCanInactivoMockMvc.perform(post("/api/est-can-inactivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estCanInactivoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstCanInactivo in the database
        List<EstCanInactivo> estCanInactivoList = estCanInactivoRepository.findAll();
        assertThat(estCanInactivoList).hasSize(databaseSizeBeforeCreate + 1);
        EstCanInactivo testEstCanInactivo = estCanInactivoList.get(estCanInactivoList.size() - 1);
        assertThat(testEstCanInactivo.getMotivo()).isEqualTo(DEFAULT_MOTIVO);
    }

    @Test
    @Transactional
    public void createEstCanInactivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estCanInactivoRepository.findAll().size();

        // Create the EstCanInactivo with an existing ID
        estCanInactivo.setId(1L);
        EstCanInactivoDTO estCanInactivoDTO = estCanInactivoMapper.toDto(estCanInactivo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstCanInactivoMockMvc.perform(post("/api/est-can-inactivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estCanInactivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstCanInactivo in the database
        List<EstCanInactivo> estCanInactivoList = estCanInactivoRepository.findAll();
        assertThat(estCanInactivoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstCanInactivos() throws Exception {
        // Initialize the database
        estCanInactivoRepository.saveAndFlush(estCanInactivo);

        // Get all the estCanInactivoList
        restEstCanInactivoMockMvc.perform(get("/api/est-can-inactivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estCanInactivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())));
    }
    
    @Test
    @Transactional
    public void getEstCanInactivo() throws Exception {
        // Initialize the database
        estCanInactivoRepository.saveAndFlush(estCanInactivo);

        // Get the estCanInactivo
        restEstCanInactivoMockMvc.perform(get("/api/est-can-inactivos/{id}", estCanInactivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estCanInactivo.getId().intValue()))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO.toString()));
    }

    @Test
    @Transactional
    public void getAllEstCanInactivosByMotivoIsEqualToSomething() throws Exception {
        // Initialize the database
        estCanInactivoRepository.saveAndFlush(estCanInactivo);

        // Get all the estCanInactivoList where motivo equals to DEFAULT_MOTIVO
        defaultEstCanInactivoShouldBeFound("motivo.equals=" + DEFAULT_MOTIVO);

        // Get all the estCanInactivoList where motivo equals to UPDATED_MOTIVO
        defaultEstCanInactivoShouldNotBeFound("motivo.equals=" + UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void getAllEstCanInactivosByMotivoIsInShouldWork() throws Exception {
        // Initialize the database
        estCanInactivoRepository.saveAndFlush(estCanInactivo);

        // Get all the estCanInactivoList where motivo in DEFAULT_MOTIVO or UPDATED_MOTIVO
        defaultEstCanInactivoShouldBeFound("motivo.in=" + DEFAULT_MOTIVO + "," + UPDATED_MOTIVO);

        // Get all the estCanInactivoList where motivo equals to UPDATED_MOTIVO
        defaultEstCanInactivoShouldNotBeFound("motivo.in=" + UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void getAllEstCanInactivosByMotivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estCanInactivoRepository.saveAndFlush(estCanInactivo);

        // Get all the estCanInactivoList where motivo is not null
        defaultEstCanInactivoShouldBeFound("motivo.specified=true");

        // Get all the estCanInactivoList where motivo is null
        defaultEstCanInactivoShouldNotBeFound("motivo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstCanInactivosByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        estCanInactivo.addCandidato(candidato);
        estCanInactivoRepository.saveAndFlush(estCanInactivo);
        Long candidatoId = candidato.getId();

        // Get all the estCanInactivoList where candidato equals to candidatoId
        defaultEstCanInactivoShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the estCanInactivoList where candidato equals to candidatoId + 1
        defaultEstCanInactivoShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllEstCanInactivosByEstatusCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusCandidato estatusCandidato = EstatusCandidatoResourceIT.createEntity(em);
        em.persist(estatusCandidato);
        em.flush();
        estCanInactivo.setEstatusCandidato(estatusCandidato);
        estCanInactivoRepository.saveAndFlush(estCanInactivo);
        Long estatusCandidatoId = estatusCandidato.getId();

        // Get all the estCanInactivoList where estatusCandidato equals to estatusCandidatoId
        defaultEstCanInactivoShouldBeFound("estatusCandidatoId.equals=" + estatusCandidatoId);

        // Get all the estCanInactivoList where estatusCandidato equals to estatusCandidatoId + 1
        defaultEstCanInactivoShouldNotBeFound("estatusCandidatoId.equals=" + (estatusCandidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstCanInactivoShouldBeFound(String filter) throws Exception {
        restEstCanInactivoMockMvc.perform(get("/api/est-can-inactivos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estCanInactivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO)));

        // Check, that the count call also returns 1
        restEstCanInactivoMockMvc.perform(get("/api/est-can-inactivos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstCanInactivoShouldNotBeFound(String filter) throws Exception {
        restEstCanInactivoMockMvc.perform(get("/api/est-can-inactivos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstCanInactivoMockMvc.perform(get("/api/est-can-inactivos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstCanInactivo() throws Exception {
        // Get the estCanInactivo
        restEstCanInactivoMockMvc.perform(get("/api/est-can-inactivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstCanInactivo() throws Exception {
        // Initialize the database
        estCanInactivoRepository.saveAndFlush(estCanInactivo);

        int databaseSizeBeforeUpdate = estCanInactivoRepository.findAll().size();

        // Update the estCanInactivo
        EstCanInactivo updatedEstCanInactivo = estCanInactivoRepository.findById(estCanInactivo.getId()).get();
        // Disconnect from session so that the updates on updatedEstCanInactivo are not directly saved in db
        em.detach(updatedEstCanInactivo);
        updatedEstCanInactivo
            .motivo(UPDATED_MOTIVO);
        EstCanInactivoDTO estCanInactivoDTO = estCanInactivoMapper.toDto(updatedEstCanInactivo);

        restEstCanInactivoMockMvc.perform(put("/api/est-can-inactivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estCanInactivoDTO)))
            .andExpect(status().isOk());

        // Validate the EstCanInactivo in the database
        List<EstCanInactivo> estCanInactivoList = estCanInactivoRepository.findAll();
        assertThat(estCanInactivoList).hasSize(databaseSizeBeforeUpdate);
        EstCanInactivo testEstCanInactivo = estCanInactivoList.get(estCanInactivoList.size() - 1);
        assertThat(testEstCanInactivo.getMotivo()).isEqualTo(UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstCanInactivo() throws Exception {
        int databaseSizeBeforeUpdate = estCanInactivoRepository.findAll().size();

        // Create the EstCanInactivo
        EstCanInactivoDTO estCanInactivoDTO = estCanInactivoMapper.toDto(estCanInactivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstCanInactivoMockMvc.perform(put("/api/est-can-inactivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estCanInactivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstCanInactivo in the database
        List<EstCanInactivo> estCanInactivoList = estCanInactivoRepository.findAll();
        assertThat(estCanInactivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstCanInactivo() throws Exception {
        // Initialize the database
        estCanInactivoRepository.saveAndFlush(estCanInactivo);

        int databaseSizeBeforeDelete = estCanInactivoRepository.findAll().size();

        // Delete the estCanInactivo
        restEstCanInactivoMockMvc.perform(delete("/api/est-can-inactivos/{id}", estCanInactivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EstCanInactivo> estCanInactivoList = estCanInactivoRepository.findAll();
        assertThat(estCanInactivoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstCanInactivo.class);
        EstCanInactivo estCanInactivo1 = new EstCanInactivo();
        estCanInactivo1.setId(1L);
        EstCanInactivo estCanInactivo2 = new EstCanInactivo();
        estCanInactivo2.setId(estCanInactivo1.getId());
        assertThat(estCanInactivo1).isEqualTo(estCanInactivo2);
        estCanInactivo2.setId(2L);
        assertThat(estCanInactivo1).isNotEqualTo(estCanInactivo2);
        estCanInactivo1.setId(null);
        assertThat(estCanInactivo1).isNotEqualTo(estCanInactivo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstCanInactivoDTO.class);
        EstCanInactivoDTO estCanInactivoDTO1 = new EstCanInactivoDTO();
        estCanInactivoDTO1.setId(1L);
        EstCanInactivoDTO estCanInactivoDTO2 = new EstCanInactivoDTO();
        assertThat(estCanInactivoDTO1).isNotEqualTo(estCanInactivoDTO2);
        estCanInactivoDTO2.setId(estCanInactivoDTO1.getId());
        assertThat(estCanInactivoDTO1).isEqualTo(estCanInactivoDTO2);
        estCanInactivoDTO2.setId(2L);
        assertThat(estCanInactivoDTO1).isNotEqualTo(estCanInactivoDTO2);
        estCanInactivoDTO1.setId(null);
        assertThat(estCanInactivoDTO1).isNotEqualTo(estCanInactivoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estCanInactivoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estCanInactivoMapper.fromId(null)).isNull();
    }
}
