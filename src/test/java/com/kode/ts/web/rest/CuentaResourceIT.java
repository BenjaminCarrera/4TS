package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Cuenta;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.CuentaRepository;
import com.kode.ts.service.CuentaService;
import com.kode.ts.service.dto.CuentaDTO;
import com.kode.ts.service.mapper.CuentaMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.CuentaCriteria;
import com.kode.ts.service.CuentaQueryService;

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
 * Integration tests for the {@Link CuentaResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class CuentaResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaQueryService cuentaQueryService;

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

    private MockMvc restCuentaMockMvc;

    private Cuenta cuenta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CuentaResource cuentaResource = new CuentaResource(cuentaService, cuentaQueryService);
        this.restCuentaMockMvc = MockMvcBuilders.standaloneSetup(cuentaResource)
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
    public static Cuenta createEntity(EntityManager em) {
        Cuenta cuenta = new Cuenta()
            .clave(DEFAULT_CLAVE)
            .nombre(DEFAULT_NOMBRE);
        return cuenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuenta createUpdatedEntity(EntityManager em) {
        Cuenta cuenta = new Cuenta()
            .clave(UPDATED_CLAVE)
            .nombre(UPDATED_NOMBRE);
        return cuenta;
    }

    @BeforeEach
    public void initTest() {
        cuenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createCuenta() throws Exception {
        int databaseSizeBeforeCreate = cuentaRepository.findAll().size();

        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);
        restCuentaMockMvc.perform(post("/api/cuentas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaDTO)))
            .andExpect(status().isCreated());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeCreate + 1);
        Cuenta testCuenta = cuentaList.get(cuentaList.size() - 1);
        assertThat(testCuenta.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testCuenta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createCuentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cuentaRepository.findAll().size();

        // Create the Cuenta with an existing ID
        cuenta.setId(1L);
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuentaMockMvc.perform(post("/api/cuentas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCuentas() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList
        restCuentaMockMvc.perform(get("/api/cuentas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get the cuenta
        restCuentaMockMvc.perform(get("/api/cuentas/{id}", cuenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cuenta.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getAllCuentasByClaveIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList where clave equals to DEFAULT_CLAVE
        defaultCuentaShouldBeFound("clave.equals=" + DEFAULT_CLAVE);

        // Get all the cuentaList where clave equals to UPDATED_CLAVE
        defaultCuentaShouldNotBeFound("clave.equals=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCuentasByClaveIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList where clave in DEFAULT_CLAVE or UPDATED_CLAVE
        defaultCuentaShouldBeFound("clave.in=" + DEFAULT_CLAVE + "," + UPDATED_CLAVE);

        // Get all the cuentaList where clave equals to UPDATED_CLAVE
        defaultCuentaShouldNotBeFound("clave.in=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCuentasByClaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList where clave is not null
        defaultCuentaShouldBeFound("clave.specified=true");

        // Get all the cuentaList where clave is null
        defaultCuentaShouldNotBeFound("clave.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList where nombre equals to DEFAULT_NOMBRE
        defaultCuentaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the cuentaList where nombre equals to UPDATED_NOMBRE
        defaultCuentaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCuentasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCuentaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the cuentaList where nombre equals to UPDATED_NOMBRE
        defaultCuentaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCuentasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList where nombre is not null
        defaultCuentaShouldBeFound("nombre.specified=true");

        // Get all the cuentaList where nombre is null
        defaultCuentaShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentasByCandidatoInteresIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidatoInteres = CandidatoResourceIT.createEntity(em);
        em.persist(candidatoInteres);
        em.flush();
        cuenta.addCandidatoInteres(candidatoInteres);
        cuentaRepository.saveAndFlush(cuenta);
        Long candidatoInteresId = candidatoInteres.getId();

        // Get all the cuentaList where candidatoInteres equals to candidatoInteresId
        defaultCuentaShouldBeFound("candidatoInteresId.equals=" + candidatoInteresId);

        // Get all the cuentaList where candidatoInteres equals to candidatoInteresId + 1
        defaultCuentaShouldNotBeFound("candidatoInteresId.equals=" + (candidatoInteresId + 1));
    }


    @Test
    @Transactional
    public void getAllCuentasByCandidatoRechazadasIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidatoRechazadas = CandidatoResourceIT.createEntity(em);
        em.persist(candidatoRechazadas);
        em.flush();
        cuenta.addCandidatoRechazadas(candidatoRechazadas);
        cuentaRepository.saveAndFlush(cuenta);
        Long candidatoRechazadasId = candidatoRechazadas.getId();

        // Get all the cuentaList where candidatoRechazadas equals to candidatoRechazadasId
        defaultCuentaShouldBeFound("candidatoRechazadasId.equals=" + candidatoRechazadasId);

        // Get all the cuentaList where candidatoRechazadas equals to candidatoRechazadasId + 1
        defaultCuentaShouldNotBeFound("candidatoRechazadasId.equals=" + (candidatoRechazadasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCuentaShouldBeFound(String filter) throws Exception {
        restCuentaMockMvc.perform(get("/api/cuentas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

        // Check, that the count call also returns 1
        restCuentaMockMvc.perform(get("/api/cuentas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCuentaShouldNotBeFound(String filter) throws Exception {
        restCuentaMockMvc.perform(get("/api/cuentas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCuentaMockMvc.perform(get("/api/cuentas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCuenta() throws Exception {
        // Get the cuenta
        restCuentaMockMvc.perform(get("/api/cuentas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        int databaseSizeBeforeUpdate = cuentaRepository.findAll().size();

        // Update the cuenta
        Cuenta updatedCuenta = cuentaRepository.findById(cuenta.getId()).get();
        // Disconnect from session so that the updates on updatedCuenta are not directly saved in db
        em.detach(updatedCuenta);
        updatedCuenta
            .clave(UPDATED_CLAVE)
            .nombre(UPDATED_NOMBRE);
        CuentaDTO cuentaDTO = cuentaMapper.toDto(updatedCuenta);

        restCuentaMockMvc.perform(put("/api/cuentas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaDTO)))
            .andExpect(status().isOk());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeUpdate);
        Cuenta testCuenta = cuentaList.get(cuentaList.size() - 1);
        assertThat(testCuenta.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testCuenta.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingCuenta() throws Exception {
        int databaseSizeBeforeUpdate = cuentaRepository.findAll().size();

        // Create the Cuenta
        CuentaDTO cuentaDTO = cuentaMapper.toDto(cuenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaMockMvc.perform(put("/api/cuentas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        int databaseSizeBeforeDelete = cuentaRepository.findAll().size();

        // Delete the cuenta
        restCuentaMockMvc.perform(delete("/api/cuentas/{id}", cuenta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cuenta.class);
        Cuenta cuenta1 = new Cuenta();
        cuenta1.setId(1L);
        Cuenta cuenta2 = new Cuenta();
        cuenta2.setId(cuenta1.getId());
        assertThat(cuenta1).isEqualTo(cuenta2);
        cuenta2.setId(2L);
        assertThat(cuenta1).isNotEqualTo(cuenta2);
        cuenta1.setId(null);
        assertThat(cuenta1).isNotEqualTo(cuenta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuentaDTO.class);
        CuentaDTO cuentaDTO1 = new CuentaDTO();
        cuentaDTO1.setId(1L);
        CuentaDTO cuentaDTO2 = new CuentaDTO();
        assertThat(cuentaDTO1).isNotEqualTo(cuentaDTO2);
        cuentaDTO2.setId(cuentaDTO1.getId());
        assertThat(cuentaDTO1).isEqualTo(cuentaDTO2);
        cuentaDTO2.setId(2L);
        assertThat(cuentaDTO1).isNotEqualTo(cuentaDTO2);
        cuentaDTO1.setId(null);
        assertThat(cuentaDTO1).isNotEqualTo(cuentaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cuentaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cuentaMapper.fromId(null)).isNull();
    }
}
