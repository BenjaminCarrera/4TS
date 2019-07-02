package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Colonia;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.Municipio;
import com.kode.ts.domain.CodigoPostal;
import com.kode.ts.repository.ColoniaRepository;
import com.kode.ts.service.ColoniaService;
import com.kode.ts.service.dto.ColoniaDTO;
import com.kode.ts.service.mapper.ColoniaMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.ColoniaCriteria;
import com.kode.ts.service.ColoniaQueryService;

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
 * Integration tests for the {@Link ColoniaResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class ColoniaResourceIT {

    private static final String DEFAULT_COLONIA = "AAAAAAAAAA";
    private static final String UPDATED_COLONIA = "BBBBBBBBBB";

    @Autowired
    private ColoniaRepository coloniaRepository;

    @Autowired
    private ColoniaMapper coloniaMapper;

    @Autowired
    private ColoniaService coloniaService;

    @Autowired
    private ColoniaQueryService coloniaQueryService;

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

    private MockMvc restColoniaMockMvc;

    private Colonia colonia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColoniaResource coloniaResource = new ColoniaResource(coloniaService, coloniaQueryService);
        this.restColoniaMockMvc = MockMvcBuilders.standaloneSetup(coloniaResource)
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
    public static Colonia createEntity(EntityManager em) {
        Colonia colonia = new Colonia()
            .colonia(DEFAULT_COLONIA);
        return colonia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colonia createUpdatedEntity(EntityManager em) {
        Colonia colonia = new Colonia()
            .colonia(UPDATED_COLONIA);
        return colonia;
    }

    @BeforeEach
    public void initTest() {
        colonia = createEntity(em);
    }

    @Test
    @Transactional
    public void createColonia() throws Exception {
        int databaseSizeBeforeCreate = coloniaRepository.findAll().size();

        // Create the Colonia
        ColoniaDTO coloniaDTO = coloniaMapper.toDto(colonia);
        restColoniaMockMvc.perform(post("/api/colonias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloniaDTO)))
            .andExpect(status().isCreated());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeCreate + 1);
        Colonia testColonia = coloniaList.get(coloniaList.size() - 1);
        assertThat(testColonia.getColonia()).isEqualTo(DEFAULT_COLONIA);
    }

    @Test
    @Transactional
    public void createColoniaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coloniaRepository.findAll().size();

        // Create the Colonia with an existing ID
        colonia.setId(1L);
        ColoniaDTO coloniaDTO = coloniaMapper.toDto(colonia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColoniaMockMvc.perform(post("/api/colonias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloniaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllColonias() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        // Get all the coloniaList
        restColoniaMockMvc.perform(get("/api/colonias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colonia.getId().intValue())))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA.toString())));
    }
    
    @Test
    @Transactional
    public void getColonia() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        // Get the colonia
        restColoniaMockMvc.perform(get("/api/colonias/{id}", colonia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(colonia.getId().intValue()))
            .andExpect(jsonPath("$.colonia").value(DEFAULT_COLONIA.toString()));
    }

    @Test
    @Transactional
    public void getAllColoniasByColoniaIsEqualToSomething() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        // Get all the coloniaList where colonia equals to DEFAULT_COLONIA
        defaultColoniaShouldBeFound("colonia.equals=" + DEFAULT_COLONIA);

        // Get all the coloniaList where colonia equals to UPDATED_COLONIA
        defaultColoniaShouldNotBeFound("colonia.equals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllColoniasByColoniaIsInShouldWork() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        // Get all the coloniaList where colonia in DEFAULT_COLONIA or UPDATED_COLONIA
        defaultColoniaShouldBeFound("colonia.in=" + DEFAULT_COLONIA + "," + UPDATED_COLONIA);

        // Get all the coloniaList where colonia equals to UPDATED_COLONIA
        defaultColoniaShouldNotBeFound("colonia.in=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllColoniasByColoniaIsNullOrNotNull() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        // Get all the coloniaList where colonia is not null
        defaultColoniaShouldBeFound("colonia.specified=true");

        // Get all the coloniaList where colonia is null
        defaultColoniaShouldNotBeFound("colonia.specified=false");
    }

    @Test
    @Transactional
    public void getAllColoniasByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        colonia.addCandidato(candidato);
        coloniaRepository.saveAndFlush(colonia);
        Long candidatoId = candidato.getId();

        // Get all the coloniaList where candidato equals to candidatoId
        defaultColoniaShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the coloniaList where candidato equals to candidatoId + 1
        defaultColoniaShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllColoniasByMunicipioIsEqualToSomething() throws Exception {
        // Initialize the database
        Municipio municipio = MunicipioResourceIT.createEntity(em);
        em.persist(municipio);
        em.flush();
        colonia.setMunicipio(municipio);
        coloniaRepository.saveAndFlush(colonia);
        Long municipioId = municipio.getId();

        // Get all the coloniaList where municipio equals to municipioId
        defaultColoniaShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the coloniaList where municipio equals to municipioId + 1
        defaultColoniaShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }


    @Test
    @Transactional
    public void getAllColoniasByCodigoPostalIsEqualToSomething() throws Exception {
        // Initialize the database
        CodigoPostal codigoPostal = CodigoPostalResourceIT.createEntity(em);
        em.persist(codigoPostal);
        em.flush();
        colonia.setCodigoPostal(codigoPostal);
        coloniaRepository.saveAndFlush(colonia);
        Long codigoPostalId = codigoPostal.getId();

        // Get all the coloniaList where codigoPostal equals to codigoPostalId
        defaultColoniaShouldBeFound("codigoPostalId.equals=" + codigoPostalId);

        // Get all the coloniaList where codigoPostal equals to codigoPostalId + 1
        defaultColoniaShouldNotBeFound("codigoPostalId.equals=" + (codigoPostalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultColoniaShouldBeFound(String filter) throws Exception {
        restColoniaMockMvc.perform(get("/api/colonias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colonia.getId().intValue())))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)));

        // Check, that the count call also returns 1
        restColoniaMockMvc.perform(get("/api/colonias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultColoniaShouldNotBeFound(String filter) throws Exception {
        restColoniaMockMvc.perform(get("/api/colonias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restColoniaMockMvc.perform(get("/api/colonias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingColonia() throws Exception {
        // Get the colonia
        restColoniaMockMvc.perform(get("/api/colonias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColonia() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();

        // Update the colonia
        Colonia updatedColonia = coloniaRepository.findById(colonia.getId()).get();
        // Disconnect from session so that the updates on updatedColonia are not directly saved in db
        em.detach(updatedColonia);
        updatedColonia
            .colonia(UPDATED_COLONIA);
        ColoniaDTO coloniaDTO = coloniaMapper.toDto(updatedColonia);

        restColoniaMockMvc.perform(put("/api/colonias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloniaDTO)))
            .andExpect(status().isOk());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
        Colonia testColonia = coloniaList.get(coloniaList.size() - 1);
        assertThat(testColonia.getColonia()).isEqualTo(UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void updateNonExistingColonia() throws Exception {
        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();

        // Create the Colonia
        ColoniaDTO coloniaDTO = coloniaMapper.toDto(colonia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColoniaMockMvc.perform(put("/api/colonias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coloniaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColonia() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        int databaseSizeBeforeDelete = coloniaRepository.findAll().size();

        // Delete the colonia
        restColoniaMockMvc.perform(delete("/api/colonias/{id}", colonia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colonia.class);
        Colonia colonia1 = new Colonia();
        colonia1.setId(1L);
        Colonia colonia2 = new Colonia();
        colonia2.setId(colonia1.getId());
        assertThat(colonia1).isEqualTo(colonia2);
        colonia2.setId(2L);
        assertThat(colonia1).isNotEqualTo(colonia2);
        colonia1.setId(null);
        assertThat(colonia1).isNotEqualTo(colonia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColoniaDTO.class);
        ColoniaDTO coloniaDTO1 = new ColoniaDTO();
        coloniaDTO1.setId(1L);
        ColoniaDTO coloniaDTO2 = new ColoniaDTO();
        assertThat(coloniaDTO1).isNotEqualTo(coloniaDTO2);
        coloniaDTO2.setId(coloniaDTO1.getId());
        assertThat(coloniaDTO1).isEqualTo(coloniaDTO2);
        coloniaDTO2.setId(2L);
        assertThat(coloniaDTO1).isNotEqualTo(coloniaDTO2);
        coloniaDTO1.setId(null);
        assertThat(coloniaDTO1).isNotEqualTo(coloniaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coloniaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coloniaMapper.fromId(null)).isNull();
    }
}
