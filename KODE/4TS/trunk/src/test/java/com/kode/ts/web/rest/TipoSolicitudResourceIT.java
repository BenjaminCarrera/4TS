package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.TipoSolicitud;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.TipoSolicitudRepository;
import com.kode.ts.service.TipoSolicitudService;
import com.kode.ts.service.dto.TipoSolicitudDTO;
import com.kode.ts.service.mapper.TipoSolicitudMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.TipoSolicitudCriteria;
import com.kode.ts.service.TipoSolicitudQueryService;

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
 * Integration tests for the {@Link TipoSolicitudResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class TipoSolicitudResourceIT {

    private static final String DEFAULT_SOLICITUD = "AAAAAAAAAA";
    private static final String UPDATED_SOLICITUD = "BBBBBBBBBB";

    @Autowired
    private TipoSolicitudRepository tipoSolicitudRepository;

    @Autowired
    private TipoSolicitudMapper tipoSolicitudMapper;

    @Autowired
    private TipoSolicitudService tipoSolicitudService;

    @Autowired
    private TipoSolicitudQueryService tipoSolicitudQueryService;

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

    private MockMvc restTipoSolicitudMockMvc;

    private TipoSolicitud tipoSolicitud;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoSolicitudResource tipoSolicitudResource = new TipoSolicitudResource(tipoSolicitudService, tipoSolicitudQueryService);
        this.restTipoSolicitudMockMvc = MockMvcBuilders.standaloneSetup(tipoSolicitudResource)
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
    public static TipoSolicitud createEntity(EntityManager em) {
        TipoSolicitud tipoSolicitud = new TipoSolicitud()
            .solicitud(DEFAULT_SOLICITUD);
        return tipoSolicitud;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoSolicitud createUpdatedEntity(EntityManager em) {
        TipoSolicitud tipoSolicitud = new TipoSolicitud()
            .solicitud(UPDATED_SOLICITUD);
        return tipoSolicitud;
    }

    @BeforeEach
    public void initTest() {
        tipoSolicitud = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoSolicitud() throws Exception {
        int databaseSizeBeforeCreate = tipoSolicitudRepository.findAll().size();

        // Create the TipoSolicitud
        TipoSolicitudDTO tipoSolicitudDTO = tipoSolicitudMapper.toDto(tipoSolicitud);
        restTipoSolicitudMockMvc.perform(post("/api/tipo-solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolicitudDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoSolicitud in the database
        List<TipoSolicitud> tipoSolicitudList = tipoSolicitudRepository.findAll();
        assertThat(tipoSolicitudList).hasSize(databaseSizeBeforeCreate + 1);
        TipoSolicitud testTipoSolicitud = tipoSolicitudList.get(tipoSolicitudList.size() - 1);
        assertThat(testTipoSolicitud.getSolicitud()).isEqualTo(DEFAULT_SOLICITUD);
    }

    @Test
    @Transactional
    public void createTipoSolicitudWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoSolicitudRepository.findAll().size();

        // Create the TipoSolicitud with an existing ID
        tipoSolicitud.setId(1L);
        TipoSolicitudDTO tipoSolicitudDTO = tipoSolicitudMapper.toDto(tipoSolicitud);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoSolicitudMockMvc.perform(post("/api/tipo-solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolicitudDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSolicitud in the database
        List<TipoSolicitud> tipoSolicitudList = tipoSolicitudRepository.findAll();
        assertThat(tipoSolicitudList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoSolicituds() throws Exception {
        // Initialize the database
        tipoSolicitudRepository.saveAndFlush(tipoSolicitud);

        // Get all the tipoSolicitudList
        restTipoSolicitudMockMvc.perform(get("/api/tipo-solicituds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSolicitud.getId().intValue())))
            .andExpect(jsonPath("$.[*].solicitud").value(hasItem(DEFAULT_SOLICITUD.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoSolicitud() throws Exception {
        // Initialize the database
        tipoSolicitudRepository.saveAndFlush(tipoSolicitud);

        // Get the tipoSolicitud
        restTipoSolicitudMockMvc.perform(get("/api/tipo-solicituds/{id}", tipoSolicitud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoSolicitud.getId().intValue()))
            .andExpect(jsonPath("$.solicitud").value(DEFAULT_SOLICITUD.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoSolicitudsBySolicitudIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoSolicitudRepository.saveAndFlush(tipoSolicitud);

        // Get all the tipoSolicitudList where solicitud equals to DEFAULT_SOLICITUD
        defaultTipoSolicitudShouldBeFound("solicitud.equals=" + DEFAULT_SOLICITUD);

        // Get all the tipoSolicitudList where solicitud equals to UPDATED_SOLICITUD
        defaultTipoSolicitudShouldNotBeFound("solicitud.equals=" + UPDATED_SOLICITUD);
    }

    @Test
    @Transactional
    public void getAllTipoSolicitudsBySolicitudIsInShouldWork() throws Exception {
        // Initialize the database
        tipoSolicitudRepository.saveAndFlush(tipoSolicitud);

        // Get all the tipoSolicitudList where solicitud in DEFAULT_SOLICITUD or UPDATED_SOLICITUD
        defaultTipoSolicitudShouldBeFound("solicitud.in=" + DEFAULT_SOLICITUD + "," + UPDATED_SOLICITUD);

        // Get all the tipoSolicitudList where solicitud equals to UPDATED_SOLICITUD
        defaultTipoSolicitudShouldNotBeFound("solicitud.in=" + UPDATED_SOLICITUD);
    }

    @Test
    @Transactional
    public void getAllTipoSolicitudsBySolicitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoSolicitudRepository.saveAndFlush(tipoSolicitud);

        // Get all the tipoSolicitudList where solicitud is not null
        defaultTipoSolicitudShouldBeFound("solicitud.specified=true");

        // Get all the tipoSolicitudList where solicitud is null
        defaultTipoSolicitudShouldNotBeFound("solicitud.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoSolicitudsByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        tipoSolicitud.addRequerimiento(requerimiento);
        tipoSolicitudRepository.saveAndFlush(tipoSolicitud);
        Long requerimientoId = requerimiento.getId();

        // Get all the tipoSolicitudList where requerimiento equals to requerimientoId
        defaultTipoSolicitudShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the tipoSolicitudList where requerimiento equals to requerimientoId + 1
        defaultTipoSolicitudShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoSolicitudShouldBeFound(String filter) throws Exception {
        restTipoSolicitudMockMvc.perform(get("/api/tipo-solicituds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSolicitud.getId().intValue())))
            .andExpect(jsonPath("$.[*].solicitud").value(hasItem(DEFAULT_SOLICITUD)));

        // Check, that the count call also returns 1
        restTipoSolicitudMockMvc.perform(get("/api/tipo-solicituds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoSolicitudShouldNotBeFound(String filter) throws Exception {
        restTipoSolicitudMockMvc.perform(get("/api/tipo-solicituds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoSolicitudMockMvc.perform(get("/api/tipo-solicituds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoSolicitud() throws Exception {
        // Get the tipoSolicitud
        restTipoSolicitudMockMvc.perform(get("/api/tipo-solicituds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoSolicitud() throws Exception {
        // Initialize the database
        tipoSolicitudRepository.saveAndFlush(tipoSolicitud);

        int databaseSizeBeforeUpdate = tipoSolicitudRepository.findAll().size();

        // Update the tipoSolicitud
        TipoSolicitud updatedTipoSolicitud = tipoSolicitudRepository.findById(tipoSolicitud.getId()).get();
        // Disconnect from session so that the updates on updatedTipoSolicitud are not directly saved in db
        em.detach(updatedTipoSolicitud);
        updatedTipoSolicitud
            .solicitud(UPDATED_SOLICITUD);
        TipoSolicitudDTO tipoSolicitudDTO = tipoSolicitudMapper.toDto(updatedTipoSolicitud);

        restTipoSolicitudMockMvc.perform(put("/api/tipo-solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolicitudDTO)))
            .andExpect(status().isOk());

        // Validate the TipoSolicitud in the database
        List<TipoSolicitud> tipoSolicitudList = tipoSolicitudRepository.findAll();
        assertThat(tipoSolicitudList).hasSize(databaseSizeBeforeUpdate);
        TipoSolicitud testTipoSolicitud = tipoSolicitudList.get(tipoSolicitudList.size() - 1);
        assertThat(testTipoSolicitud.getSolicitud()).isEqualTo(UPDATED_SOLICITUD);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = tipoSolicitudRepository.findAll().size();

        // Create the TipoSolicitud
        TipoSolicitudDTO tipoSolicitudDTO = tipoSolicitudMapper.toDto(tipoSolicitud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoSolicitudMockMvc.perform(put("/api/tipo-solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolicitudDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSolicitud in the database
        List<TipoSolicitud> tipoSolicitudList = tipoSolicitudRepository.findAll();
        assertThat(tipoSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoSolicitud() throws Exception {
        // Initialize the database
        tipoSolicitudRepository.saveAndFlush(tipoSolicitud);

        int databaseSizeBeforeDelete = tipoSolicitudRepository.findAll().size();

        // Delete the tipoSolicitud
        restTipoSolicitudMockMvc.perform(delete("/api/tipo-solicituds/{id}", tipoSolicitud.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoSolicitud> tipoSolicitudList = tipoSolicitudRepository.findAll();
        assertThat(tipoSolicitudList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSolicitud.class);
        TipoSolicitud tipoSolicitud1 = new TipoSolicitud();
        tipoSolicitud1.setId(1L);
        TipoSolicitud tipoSolicitud2 = new TipoSolicitud();
        tipoSolicitud2.setId(tipoSolicitud1.getId());
        assertThat(tipoSolicitud1).isEqualTo(tipoSolicitud2);
        tipoSolicitud2.setId(2L);
        assertThat(tipoSolicitud1).isNotEqualTo(tipoSolicitud2);
        tipoSolicitud1.setId(null);
        assertThat(tipoSolicitud1).isNotEqualTo(tipoSolicitud2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSolicitudDTO.class);
        TipoSolicitudDTO tipoSolicitudDTO1 = new TipoSolicitudDTO();
        tipoSolicitudDTO1.setId(1L);
        TipoSolicitudDTO tipoSolicitudDTO2 = new TipoSolicitudDTO();
        assertThat(tipoSolicitudDTO1).isNotEqualTo(tipoSolicitudDTO2);
        tipoSolicitudDTO2.setId(tipoSolicitudDTO1.getId());
        assertThat(tipoSolicitudDTO1).isEqualTo(tipoSolicitudDTO2);
        tipoSolicitudDTO2.setId(2L);
        assertThat(tipoSolicitudDTO1).isNotEqualTo(tipoSolicitudDTO2);
        tipoSolicitudDTO1.setId(null);
        assertThat(tipoSolicitudDTO1).isNotEqualTo(tipoSolicitudDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoSolicitudMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoSolicitudMapper.fromId(null)).isNull();
    }
}
