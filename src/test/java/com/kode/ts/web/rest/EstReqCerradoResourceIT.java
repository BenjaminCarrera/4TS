package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.EstReqCerrado;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.domain.EstatusRequerimiento;
import com.kode.ts.repository.EstReqCerradoRepository;
import com.kode.ts.service.EstReqCerradoService;
import com.kode.ts.service.dto.EstReqCerradoDTO;
import com.kode.ts.service.mapper.EstReqCerradoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.EstReqCerradoCriteria;
import com.kode.ts.service.EstReqCerradoQueryService;

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
 * Integration tests for the {@Link EstReqCerradoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class EstReqCerradoResourceIT {

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    @Autowired
    private EstReqCerradoRepository estReqCerradoRepository;

    @Autowired
    private EstReqCerradoMapper estReqCerradoMapper;

    @Autowired
    private EstReqCerradoService estReqCerradoService;

    @Autowired
    private EstReqCerradoQueryService estReqCerradoQueryService;

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

    private MockMvc restEstReqCerradoMockMvc;

    private EstReqCerrado estReqCerrado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstReqCerradoResource estReqCerradoResource = new EstReqCerradoResource(estReqCerradoService, estReqCerradoQueryService);
        this.restEstReqCerradoMockMvc = MockMvcBuilders.standaloneSetup(estReqCerradoResource)
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
    public static EstReqCerrado createEntity(EntityManager em) {
        EstReqCerrado estReqCerrado = new EstReqCerrado()
            .motivo(DEFAULT_MOTIVO);
        return estReqCerrado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstReqCerrado createUpdatedEntity(EntityManager em) {
        EstReqCerrado estReqCerrado = new EstReqCerrado()
            .motivo(UPDATED_MOTIVO);
        return estReqCerrado;
    }

    @BeforeEach
    public void initTest() {
        estReqCerrado = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstReqCerrado() throws Exception {
        int databaseSizeBeforeCreate = estReqCerradoRepository.findAll().size();

        // Create the EstReqCerrado
        EstReqCerradoDTO estReqCerradoDTO = estReqCerradoMapper.toDto(estReqCerrado);
        restEstReqCerradoMockMvc.perform(post("/api/est-req-cerrados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estReqCerradoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstReqCerrado in the database
        List<EstReqCerrado> estReqCerradoList = estReqCerradoRepository.findAll();
        assertThat(estReqCerradoList).hasSize(databaseSizeBeforeCreate + 1);
        EstReqCerrado testEstReqCerrado = estReqCerradoList.get(estReqCerradoList.size() - 1);
        assertThat(testEstReqCerrado.getMotivo()).isEqualTo(DEFAULT_MOTIVO);
    }

    @Test
    @Transactional
    public void createEstReqCerradoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estReqCerradoRepository.findAll().size();

        // Create the EstReqCerrado with an existing ID
        estReqCerrado.setId(1L);
        EstReqCerradoDTO estReqCerradoDTO = estReqCerradoMapper.toDto(estReqCerrado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstReqCerradoMockMvc.perform(post("/api/est-req-cerrados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estReqCerradoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstReqCerrado in the database
        List<EstReqCerrado> estReqCerradoList = estReqCerradoRepository.findAll();
        assertThat(estReqCerradoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstReqCerrados() throws Exception {
        // Initialize the database
        estReqCerradoRepository.saveAndFlush(estReqCerrado);

        // Get all the estReqCerradoList
        restEstReqCerradoMockMvc.perform(get("/api/est-req-cerrados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estReqCerrado.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())));
    }
    
    @Test
    @Transactional
    public void getEstReqCerrado() throws Exception {
        // Initialize the database
        estReqCerradoRepository.saveAndFlush(estReqCerrado);

        // Get the estReqCerrado
        restEstReqCerradoMockMvc.perform(get("/api/est-req-cerrados/{id}", estReqCerrado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estReqCerrado.getId().intValue()))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO.toString()));
    }

    @Test
    @Transactional
    public void getAllEstReqCerradosByMotivoIsEqualToSomething() throws Exception {
        // Initialize the database
        estReqCerradoRepository.saveAndFlush(estReqCerrado);

        // Get all the estReqCerradoList where motivo equals to DEFAULT_MOTIVO
        defaultEstReqCerradoShouldBeFound("motivo.equals=" + DEFAULT_MOTIVO);

        // Get all the estReqCerradoList where motivo equals to UPDATED_MOTIVO
        defaultEstReqCerradoShouldNotBeFound("motivo.equals=" + UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void getAllEstReqCerradosByMotivoIsInShouldWork() throws Exception {
        // Initialize the database
        estReqCerradoRepository.saveAndFlush(estReqCerrado);

        // Get all the estReqCerradoList where motivo in DEFAULT_MOTIVO or UPDATED_MOTIVO
        defaultEstReqCerradoShouldBeFound("motivo.in=" + DEFAULT_MOTIVO + "," + UPDATED_MOTIVO);

        // Get all the estReqCerradoList where motivo equals to UPDATED_MOTIVO
        defaultEstReqCerradoShouldNotBeFound("motivo.in=" + UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void getAllEstReqCerradosByMotivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        estReqCerradoRepository.saveAndFlush(estReqCerrado);

        // Get all the estReqCerradoList where motivo is not null
        defaultEstReqCerradoShouldBeFound("motivo.specified=true");

        // Get all the estReqCerradoList where motivo is null
        defaultEstReqCerradoShouldNotBeFound("motivo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEstReqCerradosByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        estReqCerrado.addRequerimiento(requerimiento);
        estReqCerradoRepository.saveAndFlush(estReqCerrado);
        Long requerimientoId = requerimiento.getId();

        // Get all the estReqCerradoList where requerimiento equals to requerimientoId
        defaultEstReqCerradoShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the estReqCerradoList where requerimiento equals to requerimientoId + 1
        defaultEstReqCerradoShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllEstReqCerradosByEstatusRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusRequerimiento estatusRequerimiento = EstatusRequerimientoResourceIT.createEntity(em);
        em.persist(estatusRequerimiento);
        em.flush();
        estReqCerrado.setEstatusRequerimiento(estatusRequerimiento);
        estReqCerradoRepository.saveAndFlush(estReqCerrado);
        Long estatusRequerimientoId = estatusRequerimiento.getId();

        // Get all the estReqCerradoList where estatusRequerimiento equals to estatusRequerimientoId
        defaultEstReqCerradoShouldBeFound("estatusRequerimientoId.equals=" + estatusRequerimientoId);

        // Get all the estReqCerradoList where estatusRequerimiento equals to estatusRequerimientoId + 1
        defaultEstReqCerradoShouldNotBeFound("estatusRequerimientoId.equals=" + (estatusRequerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstReqCerradoShouldBeFound(String filter) throws Exception {
        restEstReqCerradoMockMvc.perform(get("/api/est-req-cerrados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estReqCerrado.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO)));

        // Check, that the count call also returns 1
        restEstReqCerradoMockMvc.perform(get("/api/est-req-cerrados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstReqCerradoShouldNotBeFound(String filter) throws Exception {
        restEstReqCerradoMockMvc.perform(get("/api/est-req-cerrados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstReqCerradoMockMvc.perform(get("/api/est-req-cerrados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEstReqCerrado() throws Exception {
        // Get the estReqCerrado
        restEstReqCerradoMockMvc.perform(get("/api/est-req-cerrados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstReqCerrado() throws Exception {
        // Initialize the database
        estReqCerradoRepository.saveAndFlush(estReqCerrado);

        int databaseSizeBeforeUpdate = estReqCerradoRepository.findAll().size();

        // Update the estReqCerrado
        EstReqCerrado updatedEstReqCerrado = estReqCerradoRepository.findById(estReqCerrado.getId()).get();
        // Disconnect from session so that the updates on updatedEstReqCerrado are not directly saved in db
        em.detach(updatedEstReqCerrado);
        updatedEstReqCerrado
            .motivo(UPDATED_MOTIVO);
        EstReqCerradoDTO estReqCerradoDTO = estReqCerradoMapper.toDto(updatedEstReqCerrado);

        restEstReqCerradoMockMvc.perform(put("/api/est-req-cerrados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estReqCerradoDTO)))
            .andExpect(status().isOk());

        // Validate the EstReqCerrado in the database
        List<EstReqCerrado> estReqCerradoList = estReqCerradoRepository.findAll();
        assertThat(estReqCerradoList).hasSize(databaseSizeBeforeUpdate);
        EstReqCerrado testEstReqCerrado = estReqCerradoList.get(estReqCerradoList.size() - 1);
        assertThat(testEstReqCerrado.getMotivo()).isEqualTo(UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstReqCerrado() throws Exception {
        int databaseSizeBeforeUpdate = estReqCerradoRepository.findAll().size();

        // Create the EstReqCerrado
        EstReqCerradoDTO estReqCerradoDTO = estReqCerradoMapper.toDto(estReqCerrado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstReqCerradoMockMvc.perform(put("/api/est-req-cerrados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estReqCerradoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstReqCerrado in the database
        List<EstReqCerrado> estReqCerradoList = estReqCerradoRepository.findAll();
        assertThat(estReqCerradoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstReqCerrado() throws Exception {
        // Initialize the database
        estReqCerradoRepository.saveAndFlush(estReqCerrado);

        int databaseSizeBeforeDelete = estReqCerradoRepository.findAll().size();

        // Delete the estReqCerrado
        restEstReqCerradoMockMvc.perform(delete("/api/est-req-cerrados/{id}", estReqCerrado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstReqCerrado> estReqCerradoList = estReqCerradoRepository.findAll();
        assertThat(estReqCerradoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstReqCerrado.class);
        EstReqCerrado estReqCerrado1 = new EstReqCerrado();
        estReqCerrado1.setId(1L);
        EstReqCerrado estReqCerrado2 = new EstReqCerrado();
        estReqCerrado2.setId(estReqCerrado1.getId());
        assertThat(estReqCerrado1).isEqualTo(estReqCerrado2);
        estReqCerrado2.setId(2L);
        assertThat(estReqCerrado1).isNotEqualTo(estReqCerrado2);
        estReqCerrado1.setId(null);
        assertThat(estReqCerrado1).isNotEqualTo(estReqCerrado2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstReqCerradoDTO.class);
        EstReqCerradoDTO estReqCerradoDTO1 = new EstReqCerradoDTO();
        estReqCerradoDTO1.setId(1L);
        EstReqCerradoDTO estReqCerradoDTO2 = new EstReqCerradoDTO();
        assertThat(estReqCerradoDTO1).isNotEqualTo(estReqCerradoDTO2);
        estReqCerradoDTO2.setId(estReqCerradoDTO1.getId());
        assertThat(estReqCerradoDTO1).isEqualTo(estReqCerradoDTO2);
        estReqCerradoDTO2.setId(2L);
        assertThat(estReqCerradoDTO1).isNotEqualTo(estReqCerradoDTO2);
        estReqCerradoDTO1.setId(null);
        assertThat(estReqCerradoDTO1).isNotEqualTo(estReqCerradoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estReqCerradoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estReqCerradoMapper.fromId(null)).isNull();
    }
}
