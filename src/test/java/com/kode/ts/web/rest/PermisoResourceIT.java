package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Permiso;
import com.kode.ts.repository.PermisoRepository;
import com.kode.ts.service.PermisoService;
import com.kode.ts.service.dto.PermisoDTO;
import com.kode.ts.service.mapper.PermisoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.PermisoCriteria;
import com.kode.ts.service.PermisoQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.kode.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PermisoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class PermisoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private PermisoMapper permisoMapper;

    @Autowired
    private PermisoService permisoService;

    @Autowired
    private PermisoQueryService permisoQueryService;

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

    private MockMvc restPermisoMockMvc;

    private Permiso permiso;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PermisoResource permisoResource = new PermisoResource(permisoService, permisoQueryService);
        this.restPermisoMockMvc = MockMvcBuilders.standaloneSetup(permisoResource)
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
    public static Permiso createEntity(EntityManager em) {
        Permiso permiso = new Permiso()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .activated(DEFAULT_ACTIVATED)
            .deleted(DEFAULT_DELETED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return permiso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permiso createUpdatedEntity(EntityManager em) {
        Permiso permiso = new Permiso()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .activated(UPDATED_ACTIVATED)
            .deleted(UPDATED_DELETED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return permiso;
    }

    @BeforeEach
    public void initTest() {
        permiso = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermiso() throws Exception {
        int databaseSizeBeforeCreate = permisoRepository.findAll().size();

        // Create the Permiso
        PermisoDTO permisoDTO = permisoMapper.toDto(permiso);
        restPermisoMockMvc.perform(post("/api/permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permisoDTO)))
            .andExpect(status().isCreated());

        // Validate the Permiso in the database
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeCreate + 1);
        Permiso testPermiso = permisoList.get(permisoList.size() - 1);
        assertThat(testPermiso.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPermiso.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPermiso.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPermiso.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testPermiso.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPermiso.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPermiso.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPermiso.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPermisoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permisoRepository.findAll().size();

        // Create the Permiso with an existing ID
        permiso.setId(1L);
        PermisoDTO permisoDTO = permisoMapper.toDto(permiso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermisoMockMvc.perform(post("/api/permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permisoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Permiso in the database
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPermisos() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList
        restPermisoMockMvc.perform(get("/api/permisos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permiso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPermiso() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get the permiso
        restPermisoMockMvc.perform(get("/api/permisos/{id}", permiso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(permiso.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllPermisosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where nombre equals to DEFAULT_NOMBRE
        defaultPermisoShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the permisoList where nombre equals to UPDATED_NOMBRE
        defaultPermisoShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPermisosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultPermisoShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the permisoList where nombre equals to UPDATED_NOMBRE
        defaultPermisoShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPermisosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where nombre is not null
        defaultPermisoShouldBeFound("nombre.specified=true");

        // Get all the permisoList where nombre is null
        defaultPermisoShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultPermisoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the permisoList where descripcion equals to UPDATED_DESCRIPCION
        defaultPermisoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllPermisosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultPermisoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the permisoList where descripcion equals to UPDATED_DESCRIPCION
        defaultPermisoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllPermisosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where descripcion is not null
        defaultPermisoShouldBeFound("descripcion.specified=true");

        // Get all the permisoList where descripcion is null
        defaultPermisoShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisosByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where activated equals to DEFAULT_ACTIVATED
        defaultPermisoShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the permisoList where activated equals to UPDATED_ACTIVATED
        defaultPermisoShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllPermisosByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultPermisoShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the permisoList where activated equals to UPDATED_ACTIVATED
        defaultPermisoShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllPermisosByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where activated is not null
        defaultPermisoShouldBeFound("activated.specified=true");

        // Get all the permisoList where activated is null
        defaultPermisoShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisosByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where deleted equals to DEFAULT_DELETED
        defaultPermisoShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the permisoList where deleted equals to UPDATED_DELETED
        defaultPermisoShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllPermisosByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultPermisoShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the permisoList where deleted equals to UPDATED_DELETED
        defaultPermisoShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllPermisosByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where deleted is not null
        defaultPermisoShouldBeFound("deleted.specified=true");

        // Get all the permisoList where deleted is null
        defaultPermisoShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where createdBy equals to DEFAULT_CREATED_BY
        defaultPermisoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the permisoList where createdBy equals to UPDATED_CREATED_BY
        defaultPermisoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPermisosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultPermisoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the permisoList where createdBy equals to UPDATED_CREATED_BY
        defaultPermisoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPermisosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where createdBy is not null
        defaultPermisoShouldBeFound("createdBy.specified=true");

        // Get all the permisoList where createdBy is null
        defaultPermisoShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisosByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where createdDate equals to DEFAULT_CREATED_DATE
        defaultPermisoShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the permisoList where createdDate equals to UPDATED_CREATED_DATE
        defaultPermisoShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPermisosByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultPermisoShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the permisoList where createdDate equals to UPDATED_CREATED_DATE
        defaultPermisoShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPermisosByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where createdDate is not null
        defaultPermisoShouldBeFound("createdDate.specified=true");

        // Get all the permisoList where createdDate is null
        defaultPermisoShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisosByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPermisoShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the permisoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPermisoShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPermisosByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPermisoShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the permisoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPermisoShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPermisosByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where lastModifiedBy is not null
        defaultPermisoShouldBeFound("lastModifiedBy.specified=true");

        // Get all the permisoList where lastModifiedBy is null
        defaultPermisoShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisosByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultPermisoShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the permisoList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPermisoShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPermisosByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultPermisoShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the permisoList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPermisoShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPermisosByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList where lastModifiedDate is not null
        defaultPermisoShouldBeFound("lastModifiedDate.specified=true");

        // Get all the permisoList where lastModifiedDate is null
        defaultPermisoShouldNotBeFound("lastModifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPermisoShouldBeFound(String filter) throws Exception {
        restPermisoMockMvc.perform(get("/api/permisos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permiso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restPermisoMockMvc.perform(get("/api/permisos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPermisoShouldNotBeFound(String filter) throws Exception {
        restPermisoMockMvc.perform(get("/api/permisos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPermisoMockMvc.perform(get("/api/permisos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPermiso() throws Exception {
        // Get the permiso
        restPermisoMockMvc.perform(get("/api/permisos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermiso() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        int databaseSizeBeforeUpdate = permisoRepository.findAll().size();

        // Update the permiso
        Permiso updatedPermiso = permisoRepository.findById(permiso.getId()).get();
        // Disconnect from session so that the updates on updatedPermiso are not directly saved in db
        em.detach(updatedPermiso);
        updatedPermiso
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .activated(UPDATED_ACTIVATED)
            .deleted(UPDATED_DELETED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PermisoDTO permisoDTO = permisoMapper.toDto(updatedPermiso);

        restPermisoMockMvc.perform(put("/api/permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permisoDTO)))
            .andExpect(status().isOk());

        // Validate the Permiso in the database
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeUpdate);
        Permiso testPermiso = permisoList.get(permisoList.size() - 1);
        assertThat(testPermiso.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPermiso.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPermiso.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPermiso.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testPermiso.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPermiso.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPermiso.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPermiso.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPermiso() throws Exception {
        int databaseSizeBeforeUpdate = permisoRepository.findAll().size();

        // Create the Permiso
        PermisoDTO permisoDTO = permisoMapper.toDto(permiso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermisoMockMvc.perform(put("/api/permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permisoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Permiso in the database
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePermiso() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        int databaseSizeBeforeDelete = permisoRepository.findAll().size();

        // Delete the permiso
        restPermisoMockMvc.perform(delete("/api/permisos/{id}", permiso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Permiso.class);
        Permiso permiso1 = new Permiso();
        permiso1.setId(1L);
        Permiso permiso2 = new Permiso();
        permiso2.setId(permiso1.getId());
        assertThat(permiso1).isEqualTo(permiso2);
        permiso2.setId(2L);
        assertThat(permiso1).isNotEqualTo(permiso2);
        permiso1.setId(null);
        assertThat(permiso1).isNotEqualTo(permiso2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermisoDTO.class);
        PermisoDTO permisoDTO1 = new PermisoDTO();
        permisoDTO1.setId(1L);
        PermisoDTO permisoDTO2 = new PermisoDTO();
        assertThat(permisoDTO1).isNotEqualTo(permisoDTO2);
        permisoDTO2.setId(permisoDTO1.getId());
        assertThat(permisoDTO1).isEqualTo(permisoDTO2);
        permisoDTO2.setId(2L);
        assertThat(permisoDTO1).isNotEqualTo(permisoDTO2);
        permisoDTO1.setId(null);
        assertThat(permisoDTO1).isNotEqualTo(permisoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(permisoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(permisoMapper.fromId(null)).isNull();
    }
}
