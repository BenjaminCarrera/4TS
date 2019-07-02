package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.ReferenciasLaborales;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.ReferenciasLaboralesRepository;
import com.kode.ts.service.ReferenciasLaboralesService;
import com.kode.ts.service.dto.ReferenciasLaboralesDTO;
import com.kode.ts.service.mapper.ReferenciasLaboralesMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.ReferenciasLaboralesCriteria;
import com.kode.ts.service.ReferenciasLaboralesQueryService;

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
 * Integration tests for the {@Link ReferenciasLaboralesResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class ReferenciasLaboralesResourceIT {

    private static final String DEFAULT_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_PUESTO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_PUESTO_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CONTACO = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CONTACO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO = "BBBBBBBBBB";

    @Autowired
    private ReferenciasLaboralesRepository referenciasLaboralesRepository;

    @Autowired
    private ReferenciasLaboralesMapper referenciasLaboralesMapper;

    @Autowired
    private ReferenciasLaboralesService referenciasLaboralesService;

    @Autowired
    private ReferenciasLaboralesQueryService referenciasLaboralesQueryService;

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

    private MockMvc restReferenciasLaboralesMockMvc;

    private ReferenciasLaborales referenciasLaborales;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReferenciasLaboralesResource referenciasLaboralesResource = new ReferenciasLaboralesResource(referenciasLaboralesService, referenciasLaboralesQueryService);
        this.restReferenciasLaboralesMockMvc = MockMvcBuilders.standaloneSetup(referenciasLaboralesResource)
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
    public static ReferenciasLaborales createEntity(EntityManager em) {
        ReferenciasLaborales referenciasLaborales = new ReferenciasLaborales()
            .empresa(DEFAULT_EMPRESA)
            .nombreContacto(DEFAULT_NOMBRE_CONTACTO)
            .puestoContacto(DEFAULT_PUESTO_CONTACTO)
            .emailContaco(DEFAULT_EMAIL_CONTACO)
            .telefonoContacto(DEFAULT_TELEFONO_CONTACTO);
        return referenciasLaborales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenciasLaborales createUpdatedEntity(EntityManager em) {
        ReferenciasLaborales referenciasLaborales = new ReferenciasLaborales()
            .empresa(UPDATED_EMPRESA)
            .nombreContacto(UPDATED_NOMBRE_CONTACTO)
            .puestoContacto(UPDATED_PUESTO_CONTACTO)
            .emailContaco(UPDATED_EMAIL_CONTACO)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO);
        return referenciasLaborales;
    }

    @BeforeEach
    public void initTest() {
        referenciasLaborales = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferenciasLaborales() throws Exception {
        int databaseSizeBeforeCreate = referenciasLaboralesRepository.findAll().size();

        // Create the ReferenciasLaborales
        ReferenciasLaboralesDTO referenciasLaboralesDTO = referenciasLaboralesMapper.toDto(referenciasLaborales);
        restReferenciasLaboralesMockMvc.perform(post("/api/referencias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenciasLaboralesDTO)))
            .andExpect(status().isCreated());

        // Validate the ReferenciasLaborales in the database
        List<ReferenciasLaborales> referenciasLaboralesList = referenciasLaboralesRepository.findAll();
        assertThat(referenciasLaboralesList).hasSize(databaseSizeBeforeCreate + 1);
        ReferenciasLaborales testReferenciasLaborales = referenciasLaboralesList.get(referenciasLaboralesList.size() - 1);
        assertThat(testReferenciasLaborales.getEmpresa()).isEqualTo(DEFAULT_EMPRESA);
        assertThat(testReferenciasLaborales.getNombreContacto()).isEqualTo(DEFAULT_NOMBRE_CONTACTO);
        assertThat(testReferenciasLaborales.getPuestoContacto()).isEqualTo(DEFAULT_PUESTO_CONTACTO);
        assertThat(testReferenciasLaborales.getEmailContaco()).isEqualTo(DEFAULT_EMAIL_CONTACO);
        assertThat(testReferenciasLaborales.getTelefonoContacto()).isEqualTo(DEFAULT_TELEFONO_CONTACTO);
    }

    @Test
    @Transactional
    public void createReferenciasLaboralesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referenciasLaboralesRepository.findAll().size();

        // Create the ReferenciasLaborales with an existing ID
        referenciasLaborales.setId(1L);
        ReferenciasLaboralesDTO referenciasLaboralesDTO = referenciasLaboralesMapper.toDto(referenciasLaborales);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferenciasLaboralesMockMvc.perform(post("/api/referencias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenciasLaboralesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferenciasLaborales in the database
        List<ReferenciasLaborales> referenciasLaboralesList = referenciasLaboralesRepository.findAll();
        assertThat(referenciasLaboralesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReferenciasLaborales() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList
        restReferenciasLaboralesMockMvc.perform(get("/api/referencias-laborales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenciasLaborales.getId().intValue())))
            .andExpect(jsonPath("$.[*].empresa").value(hasItem(DEFAULT_EMPRESA.toString())))
            .andExpect(jsonPath("$.[*].nombreContacto").value(hasItem(DEFAULT_NOMBRE_CONTACTO.toString())))
            .andExpect(jsonPath("$.[*].puestoContacto").value(hasItem(DEFAULT_PUESTO_CONTACTO.toString())))
            .andExpect(jsonPath("$.[*].emailContaco").value(hasItem(DEFAULT_EMAIL_CONTACO.toString())))
            .andExpect(jsonPath("$.[*].telefonoContacto").value(hasItem(DEFAULT_TELEFONO_CONTACTO.toString())));
    }
    
    @Test
    @Transactional
    public void getReferenciasLaborales() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get the referenciasLaborales
        restReferenciasLaboralesMockMvc.perform(get("/api/referencias-laborales/{id}", referenciasLaborales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referenciasLaborales.getId().intValue()))
            .andExpect(jsonPath("$.empresa").value(DEFAULT_EMPRESA.toString()))
            .andExpect(jsonPath("$.nombreContacto").value(DEFAULT_NOMBRE_CONTACTO.toString()))
            .andExpect(jsonPath("$.puestoContacto").value(DEFAULT_PUESTO_CONTACTO.toString()))
            .andExpect(jsonPath("$.emailContaco").value(DEFAULT_EMAIL_CONTACO.toString()))
            .andExpect(jsonPath("$.telefonoContacto").value(DEFAULT_TELEFONO_CONTACTO.toString()));
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where empresa equals to DEFAULT_EMPRESA
        defaultReferenciasLaboralesShouldBeFound("empresa.equals=" + DEFAULT_EMPRESA);

        // Get all the referenciasLaboralesList where empresa equals to UPDATED_EMPRESA
        defaultReferenciasLaboralesShouldNotBeFound("empresa.equals=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where empresa in DEFAULT_EMPRESA or UPDATED_EMPRESA
        defaultReferenciasLaboralesShouldBeFound("empresa.in=" + DEFAULT_EMPRESA + "," + UPDATED_EMPRESA);

        // Get all the referenciasLaboralesList where empresa equals to UPDATED_EMPRESA
        defaultReferenciasLaboralesShouldNotBeFound("empresa.in=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where empresa is not null
        defaultReferenciasLaboralesShouldBeFound("empresa.specified=true");

        // Get all the referenciasLaboralesList where empresa is null
        defaultReferenciasLaboralesShouldNotBeFound("empresa.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByNombreContactoIsEqualToSomething() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where nombreContacto equals to DEFAULT_NOMBRE_CONTACTO
        defaultReferenciasLaboralesShouldBeFound("nombreContacto.equals=" + DEFAULT_NOMBRE_CONTACTO);

        // Get all the referenciasLaboralesList where nombreContacto equals to UPDATED_NOMBRE_CONTACTO
        defaultReferenciasLaboralesShouldNotBeFound("nombreContacto.equals=" + UPDATED_NOMBRE_CONTACTO);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByNombreContactoIsInShouldWork() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where nombreContacto in DEFAULT_NOMBRE_CONTACTO or UPDATED_NOMBRE_CONTACTO
        defaultReferenciasLaboralesShouldBeFound("nombreContacto.in=" + DEFAULT_NOMBRE_CONTACTO + "," + UPDATED_NOMBRE_CONTACTO);

        // Get all the referenciasLaboralesList where nombreContacto equals to UPDATED_NOMBRE_CONTACTO
        defaultReferenciasLaboralesShouldNotBeFound("nombreContacto.in=" + UPDATED_NOMBRE_CONTACTO);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByNombreContactoIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where nombreContacto is not null
        defaultReferenciasLaboralesShouldBeFound("nombreContacto.specified=true");

        // Get all the referenciasLaboralesList where nombreContacto is null
        defaultReferenciasLaboralesShouldNotBeFound("nombreContacto.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByPuestoContactoIsEqualToSomething() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where puestoContacto equals to DEFAULT_PUESTO_CONTACTO
        defaultReferenciasLaboralesShouldBeFound("puestoContacto.equals=" + DEFAULT_PUESTO_CONTACTO);

        // Get all the referenciasLaboralesList where puestoContacto equals to UPDATED_PUESTO_CONTACTO
        defaultReferenciasLaboralesShouldNotBeFound("puestoContacto.equals=" + UPDATED_PUESTO_CONTACTO);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByPuestoContactoIsInShouldWork() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where puestoContacto in DEFAULT_PUESTO_CONTACTO or UPDATED_PUESTO_CONTACTO
        defaultReferenciasLaboralesShouldBeFound("puestoContacto.in=" + DEFAULT_PUESTO_CONTACTO + "," + UPDATED_PUESTO_CONTACTO);

        // Get all the referenciasLaboralesList where puestoContacto equals to UPDATED_PUESTO_CONTACTO
        defaultReferenciasLaboralesShouldNotBeFound("puestoContacto.in=" + UPDATED_PUESTO_CONTACTO);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByPuestoContactoIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where puestoContacto is not null
        defaultReferenciasLaboralesShouldBeFound("puestoContacto.specified=true");

        // Get all the referenciasLaboralesList where puestoContacto is null
        defaultReferenciasLaboralesShouldNotBeFound("puestoContacto.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByEmailContacoIsEqualToSomething() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where emailContaco equals to DEFAULT_EMAIL_CONTACO
        defaultReferenciasLaboralesShouldBeFound("emailContaco.equals=" + DEFAULT_EMAIL_CONTACO);

        // Get all the referenciasLaboralesList where emailContaco equals to UPDATED_EMAIL_CONTACO
        defaultReferenciasLaboralesShouldNotBeFound("emailContaco.equals=" + UPDATED_EMAIL_CONTACO);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByEmailContacoIsInShouldWork() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where emailContaco in DEFAULT_EMAIL_CONTACO or UPDATED_EMAIL_CONTACO
        defaultReferenciasLaboralesShouldBeFound("emailContaco.in=" + DEFAULT_EMAIL_CONTACO + "," + UPDATED_EMAIL_CONTACO);

        // Get all the referenciasLaboralesList where emailContaco equals to UPDATED_EMAIL_CONTACO
        defaultReferenciasLaboralesShouldNotBeFound("emailContaco.in=" + UPDATED_EMAIL_CONTACO);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByEmailContacoIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where emailContaco is not null
        defaultReferenciasLaboralesShouldBeFound("emailContaco.specified=true");

        // Get all the referenciasLaboralesList where emailContaco is null
        defaultReferenciasLaboralesShouldNotBeFound("emailContaco.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByTelefonoContactoIsEqualToSomething() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where telefonoContacto equals to DEFAULT_TELEFONO_CONTACTO
        defaultReferenciasLaboralesShouldBeFound("telefonoContacto.equals=" + DEFAULT_TELEFONO_CONTACTO);

        // Get all the referenciasLaboralesList where telefonoContacto equals to UPDATED_TELEFONO_CONTACTO
        defaultReferenciasLaboralesShouldNotBeFound("telefonoContacto.equals=" + UPDATED_TELEFONO_CONTACTO);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByTelefonoContactoIsInShouldWork() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where telefonoContacto in DEFAULT_TELEFONO_CONTACTO or UPDATED_TELEFONO_CONTACTO
        defaultReferenciasLaboralesShouldBeFound("telefonoContacto.in=" + DEFAULT_TELEFONO_CONTACTO + "," + UPDATED_TELEFONO_CONTACTO);

        // Get all the referenciasLaboralesList where telefonoContacto equals to UPDATED_TELEFONO_CONTACTO
        defaultReferenciasLaboralesShouldNotBeFound("telefonoContacto.in=" + UPDATED_TELEFONO_CONTACTO);
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByTelefonoContactoIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        // Get all the referenciasLaboralesList where telefonoContacto is not null
        defaultReferenciasLaboralesShouldBeFound("telefonoContacto.specified=true");

        // Get all the referenciasLaboralesList where telefonoContacto is null
        defaultReferenciasLaboralesShouldNotBeFound("telefonoContacto.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenciasLaboralesByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        referenciasLaborales.setCandidato(candidato);
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);
        Long candidatoId = candidato.getId();

        // Get all the referenciasLaboralesList where candidato equals to candidatoId
        defaultReferenciasLaboralesShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the referenciasLaboralesList where candidato equals to candidatoId + 1
        defaultReferenciasLaboralesShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReferenciasLaboralesShouldBeFound(String filter) throws Exception {
        restReferenciasLaboralesMockMvc.perform(get("/api/referencias-laborales?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenciasLaborales.getId().intValue())))
            .andExpect(jsonPath("$.[*].empresa").value(hasItem(DEFAULT_EMPRESA)))
            .andExpect(jsonPath("$.[*].nombreContacto").value(hasItem(DEFAULT_NOMBRE_CONTACTO)))
            .andExpect(jsonPath("$.[*].puestoContacto").value(hasItem(DEFAULT_PUESTO_CONTACTO)))
            .andExpect(jsonPath("$.[*].emailContaco").value(hasItem(DEFAULT_EMAIL_CONTACO)))
            .andExpect(jsonPath("$.[*].telefonoContacto").value(hasItem(DEFAULT_TELEFONO_CONTACTO)));

        // Check, that the count call also returns 1
        restReferenciasLaboralesMockMvc.perform(get("/api/referencias-laborales/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReferenciasLaboralesShouldNotBeFound(String filter) throws Exception {
        restReferenciasLaboralesMockMvc.perform(get("/api/referencias-laborales?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReferenciasLaboralesMockMvc.perform(get("/api/referencias-laborales/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReferenciasLaborales() throws Exception {
        // Get the referenciasLaborales
        restReferenciasLaboralesMockMvc.perform(get("/api/referencias-laborales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferenciasLaborales() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        int databaseSizeBeforeUpdate = referenciasLaboralesRepository.findAll().size();

        // Update the referenciasLaborales
        ReferenciasLaborales updatedReferenciasLaborales = referenciasLaboralesRepository.findById(referenciasLaborales.getId()).get();
        // Disconnect from session so that the updates on updatedReferenciasLaborales are not directly saved in db
        em.detach(updatedReferenciasLaborales);
        updatedReferenciasLaborales
            .empresa(UPDATED_EMPRESA)
            .nombreContacto(UPDATED_NOMBRE_CONTACTO)
            .puestoContacto(UPDATED_PUESTO_CONTACTO)
            .emailContaco(UPDATED_EMAIL_CONTACO)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO);
        ReferenciasLaboralesDTO referenciasLaboralesDTO = referenciasLaboralesMapper.toDto(updatedReferenciasLaborales);

        restReferenciasLaboralesMockMvc.perform(put("/api/referencias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenciasLaboralesDTO)))
            .andExpect(status().isOk());

        // Validate the ReferenciasLaborales in the database
        List<ReferenciasLaborales> referenciasLaboralesList = referenciasLaboralesRepository.findAll();
        assertThat(referenciasLaboralesList).hasSize(databaseSizeBeforeUpdate);
        ReferenciasLaborales testReferenciasLaborales = referenciasLaboralesList.get(referenciasLaboralesList.size() - 1);
        assertThat(testReferenciasLaborales.getEmpresa()).isEqualTo(UPDATED_EMPRESA);
        assertThat(testReferenciasLaborales.getNombreContacto()).isEqualTo(UPDATED_NOMBRE_CONTACTO);
        assertThat(testReferenciasLaborales.getPuestoContacto()).isEqualTo(UPDATED_PUESTO_CONTACTO);
        assertThat(testReferenciasLaborales.getEmailContaco()).isEqualTo(UPDATED_EMAIL_CONTACO);
        assertThat(testReferenciasLaborales.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
    }

    @Test
    @Transactional
    public void updateNonExistingReferenciasLaborales() throws Exception {
        int databaseSizeBeforeUpdate = referenciasLaboralesRepository.findAll().size();

        // Create the ReferenciasLaborales
        ReferenciasLaboralesDTO referenciasLaboralesDTO = referenciasLaboralesMapper.toDto(referenciasLaborales);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenciasLaboralesMockMvc.perform(put("/api/referencias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenciasLaboralesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferenciasLaborales in the database
        List<ReferenciasLaborales> referenciasLaboralesList = referenciasLaboralesRepository.findAll();
        assertThat(referenciasLaboralesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReferenciasLaborales() throws Exception {
        // Initialize the database
        referenciasLaboralesRepository.saveAndFlush(referenciasLaborales);

        int databaseSizeBeforeDelete = referenciasLaboralesRepository.findAll().size();

        // Delete the referenciasLaborales
        restReferenciasLaboralesMockMvc.perform(delete("/api/referencias-laborales/{id}", referenciasLaborales.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReferenciasLaborales> referenciasLaboralesList = referenciasLaboralesRepository.findAll();
        assertThat(referenciasLaboralesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenciasLaborales.class);
        ReferenciasLaborales referenciasLaborales1 = new ReferenciasLaborales();
        referenciasLaborales1.setId(1L);
        ReferenciasLaborales referenciasLaborales2 = new ReferenciasLaborales();
        referenciasLaborales2.setId(referenciasLaborales1.getId());
        assertThat(referenciasLaborales1).isEqualTo(referenciasLaborales2);
        referenciasLaborales2.setId(2L);
        assertThat(referenciasLaborales1).isNotEqualTo(referenciasLaborales2);
        referenciasLaborales1.setId(null);
        assertThat(referenciasLaborales1).isNotEqualTo(referenciasLaborales2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenciasLaboralesDTO.class);
        ReferenciasLaboralesDTO referenciasLaboralesDTO1 = new ReferenciasLaboralesDTO();
        referenciasLaboralesDTO1.setId(1L);
        ReferenciasLaboralesDTO referenciasLaboralesDTO2 = new ReferenciasLaboralesDTO();
        assertThat(referenciasLaboralesDTO1).isNotEqualTo(referenciasLaboralesDTO2);
        referenciasLaboralesDTO2.setId(referenciasLaboralesDTO1.getId());
        assertThat(referenciasLaboralesDTO1).isEqualTo(referenciasLaboralesDTO2);
        referenciasLaboralesDTO2.setId(2L);
        assertThat(referenciasLaboralesDTO1).isNotEqualTo(referenciasLaboralesDTO2);
        referenciasLaboralesDTO1.setId(null);
        assertThat(referenciasLaboralesDTO1).isNotEqualTo(referenciasLaboralesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(referenciasLaboralesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(referenciasLaboralesMapper.fromId(null)).isNull();
    }
}
