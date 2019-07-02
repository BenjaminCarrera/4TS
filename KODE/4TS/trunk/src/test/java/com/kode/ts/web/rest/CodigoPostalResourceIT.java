package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.CodigoPostal;
import com.kode.ts.domain.Colonia;
import com.kode.ts.domain.Municipio;
import com.kode.ts.repository.CodigoPostalRepository;
import com.kode.ts.service.CodigoPostalService;
import com.kode.ts.service.dto.CodigoPostalDTO;
import com.kode.ts.service.mapper.CodigoPostalMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.CodigoPostalCriteria;
import com.kode.ts.service.CodigoPostalQueryService;

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
 * Integration tests for the {@Link CodigoPostalResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class CodigoPostalResourceIT {

    private static final String DEFAULT_CODIGO_POSTAL = "AAAAA";
    private static final String UPDATED_CODIGO_POSTAL = "BBBBB";

    @Autowired
    private CodigoPostalRepository codigoPostalRepository;

    @Autowired
    private CodigoPostalMapper codigoPostalMapper;

    @Autowired
    private CodigoPostalService codigoPostalService;

    @Autowired
    private CodigoPostalQueryService codigoPostalQueryService;

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

    private MockMvc restCodigoPostalMockMvc;

    private CodigoPostal codigoPostal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CodigoPostalResource codigoPostalResource = new CodigoPostalResource(codigoPostalService, codigoPostalQueryService);
        this.restCodigoPostalMockMvc = MockMvcBuilders.standaloneSetup(codigoPostalResource)
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
    public static CodigoPostal createEntity(EntityManager em) {
        CodigoPostal codigoPostal = new CodigoPostal()
            .codigoPostal(DEFAULT_CODIGO_POSTAL);
        return codigoPostal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodigoPostal createUpdatedEntity(EntityManager em) {
        CodigoPostal codigoPostal = new CodigoPostal()
            .codigoPostal(UPDATED_CODIGO_POSTAL);
        return codigoPostal;
    }

    @BeforeEach
    public void initTest() {
        codigoPostal = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodigoPostal() throws Exception {
        int databaseSizeBeforeCreate = codigoPostalRepository.findAll().size();

        // Create the CodigoPostal
        CodigoPostalDTO codigoPostalDTO = codigoPostalMapper.toDto(codigoPostal);
        restCodigoPostalMockMvc.perform(post("/api/codigo-postals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codigoPostalDTO)))
            .andExpect(status().isCreated());

        // Validate the CodigoPostal in the database
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeCreate + 1);
        CodigoPostal testCodigoPostal = codigoPostalList.get(codigoPostalList.size() - 1);
        assertThat(testCodigoPostal.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    public void createCodigoPostalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codigoPostalRepository.findAll().size();

        // Create the CodigoPostal with an existing ID
        codigoPostal.setId(1L);
        CodigoPostalDTO codigoPostalDTO = codigoPostalMapper.toDto(codigoPostal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodigoPostalMockMvc.perform(post("/api/codigo-postals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codigoPostalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodigoPostal in the database
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCodigoPostals() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        // Get all the codigoPostalList
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codigoPostal.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.toString())));
    }
    
    @Test
    @Transactional
    public void getCodigoPostal() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        // Get the codigoPostal
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals/{id}", codigoPostal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(codigoPostal.getId().intValue()))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL.toString()));
    }

    @Test
    @Transactional
    public void getAllCodigoPostalsByCodigoPostalIsEqualToSomething() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        // Get all the codigoPostalList where codigoPostal equals to DEFAULT_CODIGO_POSTAL
        defaultCodigoPostalShouldBeFound("codigoPostal.equals=" + DEFAULT_CODIGO_POSTAL);

        // Get all the codigoPostalList where codigoPostal equals to UPDATED_CODIGO_POSTAL
        defaultCodigoPostalShouldNotBeFound("codigoPostal.equals=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCodigoPostalsByCodigoPostalIsInShouldWork() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        // Get all the codigoPostalList where codigoPostal in DEFAULT_CODIGO_POSTAL or UPDATED_CODIGO_POSTAL
        defaultCodigoPostalShouldBeFound("codigoPostal.in=" + DEFAULT_CODIGO_POSTAL + "," + UPDATED_CODIGO_POSTAL);

        // Get all the codigoPostalList where codigoPostal equals to UPDATED_CODIGO_POSTAL
        defaultCodigoPostalShouldNotBeFound("codigoPostal.in=" + UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCodigoPostalsByCodigoPostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        // Get all the codigoPostalList where codigoPostal is not null
        defaultCodigoPostalShouldBeFound("codigoPostal.specified=true");

        // Get all the codigoPostalList where codigoPostal is null
        defaultCodigoPostalShouldNotBeFound("codigoPostal.specified=false");
    }

    @Test
    @Transactional
    public void getAllCodigoPostalsByColoniaIsEqualToSomething() throws Exception {
        // Initialize the database
        Colonia colonia = ColoniaResourceIT.createEntity(em);
        em.persist(colonia);
        em.flush();
        codigoPostal.addColonia(colonia);
        codigoPostalRepository.saveAndFlush(codigoPostal);
        Long coloniaId = colonia.getId();

        // Get all the codigoPostalList where colonia equals to coloniaId
        defaultCodigoPostalShouldBeFound("coloniaId.equals=" + coloniaId);

        // Get all the codigoPostalList where colonia equals to coloniaId + 1
        defaultCodigoPostalShouldNotBeFound("coloniaId.equals=" + (coloniaId + 1));
    }


    @Test
    @Transactional
    public void getAllCodigoPostalsByMunicipioIsEqualToSomething() throws Exception {
        // Initialize the database
        Municipio municipio = MunicipioResourceIT.createEntity(em);
        em.persist(municipio);
        em.flush();
        codigoPostal.setMunicipio(municipio);
        codigoPostalRepository.saveAndFlush(codigoPostal);
        Long municipioId = municipio.getId();

        // Get all the codigoPostalList where municipio equals to municipioId
        defaultCodigoPostalShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the codigoPostalList where municipio equals to municipioId + 1
        defaultCodigoPostalShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCodigoPostalShouldBeFound(String filter) throws Exception {
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codigoPostal.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)));

        // Check, that the count call also returns 1
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCodigoPostalShouldNotBeFound(String filter) throws Exception {
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCodigoPostal() throws Exception {
        // Get the codigoPostal
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodigoPostal() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        int databaseSizeBeforeUpdate = codigoPostalRepository.findAll().size();

        // Update the codigoPostal
        CodigoPostal updatedCodigoPostal = codigoPostalRepository.findById(codigoPostal.getId()).get();
        // Disconnect from session so that the updates on updatedCodigoPostal are not directly saved in db
        em.detach(updatedCodigoPostal);
        updatedCodigoPostal
            .codigoPostal(UPDATED_CODIGO_POSTAL);
        CodigoPostalDTO codigoPostalDTO = codigoPostalMapper.toDto(updatedCodigoPostal);

        restCodigoPostalMockMvc.perform(put("/api/codigo-postals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codigoPostalDTO)))
            .andExpect(status().isOk());

        // Validate the CodigoPostal in the database
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeUpdate);
        CodigoPostal testCodigoPostal = codigoPostalList.get(codigoPostalList.size() - 1);
        assertThat(testCodigoPostal.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingCodigoPostal() throws Exception {
        int databaseSizeBeforeUpdate = codigoPostalRepository.findAll().size();

        // Create the CodigoPostal
        CodigoPostalDTO codigoPostalDTO = codigoPostalMapper.toDto(codigoPostal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodigoPostalMockMvc.perform(put("/api/codigo-postals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codigoPostalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodigoPostal in the database
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCodigoPostal() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        int databaseSizeBeforeDelete = codigoPostalRepository.findAll().size();

        // Delete the codigoPostal
        restCodigoPostalMockMvc.perform(delete("/api/codigo-postals/{id}", codigoPostal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodigoPostal.class);
        CodigoPostal codigoPostal1 = new CodigoPostal();
        codigoPostal1.setId(1L);
        CodigoPostal codigoPostal2 = new CodigoPostal();
        codigoPostal2.setId(codigoPostal1.getId());
        assertThat(codigoPostal1).isEqualTo(codigoPostal2);
        codigoPostal2.setId(2L);
        assertThat(codigoPostal1).isNotEqualTo(codigoPostal2);
        codigoPostal1.setId(null);
        assertThat(codigoPostal1).isNotEqualTo(codigoPostal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodigoPostalDTO.class);
        CodigoPostalDTO codigoPostalDTO1 = new CodigoPostalDTO();
        codigoPostalDTO1.setId(1L);
        CodigoPostalDTO codigoPostalDTO2 = new CodigoPostalDTO();
        assertThat(codigoPostalDTO1).isNotEqualTo(codigoPostalDTO2);
        codigoPostalDTO2.setId(codigoPostalDTO1.getId());
        assertThat(codigoPostalDTO1).isEqualTo(codigoPostalDTO2);
        codigoPostalDTO2.setId(2L);
        assertThat(codigoPostalDTO1).isNotEqualTo(codigoPostalDTO2);
        codigoPostalDTO1.setId(null);
        assertThat(codigoPostalDTO1).isNotEqualTo(codigoPostalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(codigoPostalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(codigoPostalMapper.fromId(null)).isNull();
    }
}
