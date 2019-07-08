package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.PermisoAuthority;
import com.kode.ts.domain.Permiso;
import com.kode.ts.repository.PermisoAuthorityRepository;
import com.kode.ts.service.PermisoAuthorityService;
import com.kode.ts.service.dto.PermisoAuthorityDTO;
import com.kode.ts.service.mapper.PermisoAuthorityMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.PermisoAuthorityCriteria;
import com.kode.ts.service.PermisoAuthorityQueryService;

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
 * Integration tests for the {@Link PermisoAuthorityResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class PermisoAuthorityResourceIT {

    private static final String DEFAULT_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY = "BBBBBBBBBB";

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
    private PermisoAuthorityRepository permisoAuthorityRepository;

    @Autowired
    private PermisoAuthorityMapper permisoAuthorityMapper;

    @Autowired
    private PermisoAuthorityService permisoAuthorityService;

    @Autowired
    private PermisoAuthorityQueryService permisoAuthorityQueryService;

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

    private MockMvc restPermisoAuthorityMockMvc;

    private PermisoAuthority permisoAuthority;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PermisoAuthorityResource permisoAuthorityResource = new PermisoAuthorityResource(permisoAuthorityService, permisoAuthorityQueryService);
        this.restPermisoAuthorityMockMvc = MockMvcBuilders.standaloneSetup(permisoAuthorityResource)
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
    public static PermisoAuthority createEntity(EntityManager em) {
        PermisoAuthority permisoAuthority = new PermisoAuthority()
            .authority(DEFAULT_AUTHORITY)
            .activated(DEFAULT_ACTIVATED)
            .deleted(DEFAULT_DELETED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return permisoAuthority;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PermisoAuthority createUpdatedEntity(EntityManager em) {
        PermisoAuthority permisoAuthority = new PermisoAuthority()
            .authority(UPDATED_AUTHORITY)
            .activated(UPDATED_ACTIVATED)
            .deleted(UPDATED_DELETED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return permisoAuthority;
    }

    @BeforeEach
    public void initTest() {
        permisoAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermisoAuthority() throws Exception {
        int databaseSizeBeforeCreate = permisoAuthorityRepository.findAll().size();

        // Create the PermisoAuthority
        PermisoAuthorityDTO permisoAuthorityDTO = permisoAuthorityMapper.toDto(permisoAuthority);
        restPermisoAuthorityMockMvc.perform(post("/api/permiso-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permisoAuthorityDTO)))
            .andExpect(status().isCreated());

        // Validate the PermisoAuthority in the database
        List<PermisoAuthority> permisoAuthorityList = permisoAuthorityRepository.findAll();
        assertThat(permisoAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        PermisoAuthority testPermisoAuthority = permisoAuthorityList.get(permisoAuthorityList.size() - 1);
        assertThat(testPermisoAuthority.getAuthority()).isEqualTo(DEFAULT_AUTHORITY);
        assertThat(testPermisoAuthority.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPermisoAuthority.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testPermisoAuthority.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPermisoAuthority.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPermisoAuthority.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPermisoAuthority.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPermisoAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permisoAuthorityRepository.findAll().size();

        // Create the PermisoAuthority with an existing ID
        permisoAuthority.setId(1L);
        PermisoAuthorityDTO permisoAuthorityDTO = permisoAuthorityMapper.toDto(permisoAuthority);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermisoAuthorityMockMvc.perform(post("/api/permiso-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permisoAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PermisoAuthority in the database
        List<PermisoAuthority> permisoAuthorityList = permisoAuthorityRepository.findAll();
        assertThat(permisoAuthorityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPermisoAuthorities() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList
        restPermisoAuthorityMockMvc.perform(get("/api/permiso-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permisoAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPermisoAuthority() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get the permisoAuthority
        restPermisoAuthorityMockMvc.perform(get("/api/permiso-authorities/{id}", permisoAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(permisoAuthority.getId().intValue()))
            .andExpect(jsonPath("$.authority").value(DEFAULT_AUTHORITY.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByAuthorityIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where authority equals to DEFAULT_AUTHORITY
        defaultPermisoAuthorityShouldBeFound("authority.equals=" + DEFAULT_AUTHORITY);

        // Get all the permisoAuthorityList where authority equals to UPDATED_AUTHORITY
        defaultPermisoAuthorityShouldNotBeFound("authority.equals=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByAuthorityIsInShouldWork() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where authority in DEFAULT_AUTHORITY or UPDATED_AUTHORITY
        defaultPermisoAuthorityShouldBeFound("authority.in=" + DEFAULT_AUTHORITY + "," + UPDATED_AUTHORITY);

        // Get all the permisoAuthorityList where authority equals to UPDATED_AUTHORITY
        defaultPermisoAuthorityShouldNotBeFound("authority.in=" + UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByAuthorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where authority is not null
        defaultPermisoAuthorityShouldBeFound("authority.specified=true");

        // Get all the permisoAuthorityList where authority is null
        defaultPermisoAuthorityShouldNotBeFound("authority.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where activated equals to DEFAULT_ACTIVATED
        defaultPermisoAuthorityShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the permisoAuthorityList where activated equals to UPDATED_ACTIVATED
        defaultPermisoAuthorityShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultPermisoAuthorityShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the permisoAuthorityList where activated equals to UPDATED_ACTIVATED
        defaultPermisoAuthorityShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where activated is not null
        defaultPermisoAuthorityShouldBeFound("activated.specified=true");

        // Get all the permisoAuthorityList where activated is null
        defaultPermisoAuthorityShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where deleted equals to DEFAULT_DELETED
        defaultPermisoAuthorityShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the permisoAuthorityList where deleted equals to UPDATED_DELETED
        defaultPermisoAuthorityShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultPermisoAuthorityShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the permisoAuthorityList where deleted equals to UPDATED_DELETED
        defaultPermisoAuthorityShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where deleted is not null
        defaultPermisoAuthorityShouldBeFound("deleted.specified=true");

        // Get all the permisoAuthorityList where deleted is null
        defaultPermisoAuthorityShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where createdBy equals to DEFAULT_CREATED_BY
        defaultPermisoAuthorityShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the permisoAuthorityList where createdBy equals to UPDATED_CREATED_BY
        defaultPermisoAuthorityShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultPermisoAuthorityShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the permisoAuthorityList where createdBy equals to UPDATED_CREATED_BY
        defaultPermisoAuthorityShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where createdBy is not null
        defaultPermisoAuthorityShouldBeFound("createdBy.specified=true");

        // Get all the permisoAuthorityList where createdBy is null
        defaultPermisoAuthorityShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where createdDate equals to DEFAULT_CREATED_DATE
        defaultPermisoAuthorityShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the permisoAuthorityList where createdDate equals to UPDATED_CREATED_DATE
        defaultPermisoAuthorityShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultPermisoAuthorityShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the permisoAuthorityList where createdDate equals to UPDATED_CREATED_DATE
        defaultPermisoAuthorityShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where createdDate is not null
        defaultPermisoAuthorityShouldBeFound("createdDate.specified=true");

        // Get all the permisoAuthorityList where createdDate is null
        defaultPermisoAuthorityShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPermisoAuthorityShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the permisoAuthorityList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPermisoAuthorityShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPermisoAuthorityShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the permisoAuthorityList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPermisoAuthorityShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where lastModifiedBy is not null
        defaultPermisoAuthorityShouldBeFound("lastModifiedBy.specified=true");

        // Get all the permisoAuthorityList where lastModifiedBy is null
        defaultPermisoAuthorityShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultPermisoAuthorityShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the permisoAuthorityList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPermisoAuthorityShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultPermisoAuthorityShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the permisoAuthorityList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPermisoAuthorityShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        // Get all the permisoAuthorityList where lastModifiedDate is not null
        defaultPermisoAuthorityShouldBeFound("lastModifiedDate.specified=true");

        // Get all the permisoAuthorityList where lastModifiedDate is null
        defaultPermisoAuthorityShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPermisoAuthoritiesByPermisoIsEqualToSomething() throws Exception {
        // Initialize the database
        Permiso permiso = PermisoResourceIT.createEntity(em);
        em.persist(permiso);
        em.flush();
        permisoAuthority.setPermiso(permiso);
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);
        Long permisoId = permiso.getId();

        // Get all the permisoAuthorityList where permiso equals to permisoId
        defaultPermisoAuthorityShouldBeFound("permisoId.equals=" + permisoId);

        // Get all the permisoAuthorityList where permiso equals to permisoId + 1
        defaultPermisoAuthorityShouldNotBeFound("permisoId.equals=" + (permisoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPermisoAuthorityShouldBeFound(String filter) throws Exception {
        restPermisoAuthorityMockMvc.perform(get("/api/permiso-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permisoAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restPermisoAuthorityMockMvc.perform(get("/api/permiso-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPermisoAuthorityShouldNotBeFound(String filter) throws Exception {
        restPermisoAuthorityMockMvc.perform(get("/api/permiso-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPermisoAuthorityMockMvc.perform(get("/api/permiso-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPermisoAuthority() throws Exception {
        // Get the permisoAuthority
        restPermisoAuthorityMockMvc.perform(get("/api/permiso-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermisoAuthority() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        int databaseSizeBeforeUpdate = permisoAuthorityRepository.findAll().size();

        // Update the permisoAuthority
        PermisoAuthority updatedPermisoAuthority = permisoAuthorityRepository.findById(permisoAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedPermisoAuthority are not directly saved in db
        em.detach(updatedPermisoAuthority);
        updatedPermisoAuthority
            .authority(UPDATED_AUTHORITY)
            .activated(UPDATED_ACTIVATED)
            .deleted(UPDATED_DELETED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PermisoAuthorityDTO permisoAuthorityDTO = permisoAuthorityMapper.toDto(updatedPermisoAuthority);

        restPermisoAuthorityMockMvc.perform(put("/api/permiso-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permisoAuthorityDTO)))
            .andExpect(status().isOk());

        // Validate the PermisoAuthority in the database
        List<PermisoAuthority> permisoAuthorityList = permisoAuthorityRepository.findAll();
        assertThat(permisoAuthorityList).hasSize(databaseSizeBeforeUpdate);
        PermisoAuthority testPermisoAuthority = permisoAuthorityList.get(permisoAuthorityList.size() - 1);
        assertThat(testPermisoAuthority.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
        assertThat(testPermisoAuthority.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPermisoAuthority.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testPermisoAuthority.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPermisoAuthority.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPermisoAuthority.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPermisoAuthority.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPermisoAuthority() throws Exception {
        int databaseSizeBeforeUpdate = permisoAuthorityRepository.findAll().size();

        // Create the PermisoAuthority
        PermisoAuthorityDTO permisoAuthorityDTO = permisoAuthorityMapper.toDto(permisoAuthority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermisoAuthorityMockMvc.perform(put("/api/permiso-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permisoAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PermisoAuthority in the database
        List<PermisoAuthority> permisoAuthorityList = permisoAuthorityRepository.findAll();
        assertThat(permisoAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePermisoAuthority() throws Exception {
        // Initialize the database
        permisoAuthorityRepository.saveAndFlush(permisoAuthority);

        int databaseSizeBeforeDelete = permisoAuthorityRepository.findAll().size();

        // Delete the permisoAuthority
        restPermisoAuthorityMockMvc.perform(delete("/api/permiso-authorities/{id}", permisoAuthority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PermisoAuthority> permisoAuthorityList = permisoAuthorityRepository.findAll();
        assertThat(permisoAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermisoAuthority.class);
        PermisoAuthority permisoAuthority1 = new PermisoAuthority();
        permisoAuthority1.setId(1L);
        PermisoAuthority permisoAuthority2 = new PermisoAuthority();
        permisoAuthority2.setId(permisoAuthority1.getId());
        assertThat(permisoAuthority1).isEqualTo(permisoAuthority2);
        permisoAuthority2.setId(2L);
        assertThat(permisoAuthority1).isNotEqualTo(permisoAuthority2);
        permisoAuthority1.setId(null);
        assertThat(permisoAuthority1).isNotEqualTo(permisoAuthority2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermisoAuthorityDTO.class);
        PermisoAuthorityDTO permisoAuthorityDTO1 = new PermisoAuthorityDTO();
        permisoAuthorityDTO1.setId(1L);
        PermisoAuthorityDTO permisoAuthorityDTO2 = new PermisoAuthorityDTO();
        assertThat(permisoAuthorityDTO1).isNotEqualTo(permisoAuthorityDTO2);
        permisoAuthorityDTO2.setId(permisoAuthorityDTO1.getId());
        assertThat(permisoAuthorityDTO1).isEqualTo(permisoAuthorityDTO2);
        permisoAuthorityDTO2.setId(2L);
        assertThat(permisoAuthorityDTO1).isNotEqualTo(permisoAuthorityDTO2);
        permisoAuthorityDTO1.setId(null);
        assertThat(permisoAuthorityDTO1).isNotEqualTo(permisoAuthorityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(permisoAuthorityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(permisoAuthorityMapper.fromId(null)).isNull();
    }
}
