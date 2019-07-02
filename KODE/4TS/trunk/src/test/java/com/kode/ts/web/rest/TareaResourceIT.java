package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Tarea;
import com.kode.ts.domain.Bitacora;
import com.kode.ts.domain.User;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.EstatusTarea;
import com.kode.ts.domain.TipoTarea;
import com.kode.ts.repository.TareaRepository;
import com.kode.ts.service.TareaService;
import com.kode.ts.service.dto.TareaDTO;
import com.kode.ts.service.mapper.TareaMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.TareaCriteria;
import com.kode.ts.service.TareaQueryService;

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
 * Integration tests for the {@Link TareaResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class TareaResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TareaMapper tareaMapper;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private TareaQueryService tareaQueryService;

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

    private MockMvc restTareaMockMvc;

    private Tarea tarea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TareaResource tareaResource = new TareaResource(tareaService, tareaQueryService);
        this.restTareaMockMvc = MockMvcBuilders.standaloneSetup(tareaResource)
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
    public static Tarea createEntity(EntityManager em) {
        Tarea tarea = new Tarea()
            .descripcion(DEFAULT_DESCRIPCION)
            .titulo(DEFAULT_TITULO);
        return tarea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarea createUpdatedEntity(EntityManager em) {
        Tarea tarea = new Tarea()
            .descripcion(UPDATED_DESCRIPCION)
            .titulo(UPDATED_TITULO);
        return tarea;
    }

    @BeforeEach
    public void initTest() {
        tarea = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarea() throws Exception {
        int databaseSizeBeforeCreate = tareaRepository.findAll().size();

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);
        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTarea.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createTareaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tareaRepository.findAll().size();

        // Create the Tarea with an existing ID
        tarea.setId(1L);
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTareas() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList
        restTareaMockMvc.perform(get("/api/tareas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())));
    }
    
    @Test
    @Transactional
    public void getTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get the tarea
        restTareaMockMvc.perform(get("/api/tareas/{id}", tarea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarea.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()));
    }

    @Test
    @Transactional
    public void getAllTareasByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTareaShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the tareaList where descripcion equals to UPDATED_DESCRIPCION
        defaultTareaShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTareasByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTareaShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the tareaList where descripcion equals to UPDATED_DESCRIPCION
        defaultTareaShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTareasByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where descripcion is not null
        defaultTareaShouldBeFound("descripcion.specified=true");

        // Get all the tareaList where descripcion is null
        defaultTareaShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllTareasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where titulo equals to DEFAULT_TITULO
        defaultTareaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the tareaList where titulo equals to UPDATED_TITULO
        defaultTareaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllTareasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultTareaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the tareaList where titulo equals to UPDATED_TITULO
        defaultTareaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllTareasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList where titulo is not null
        defaultTareaShouldBeFound("titulo.specified=true");

        // Get all the tareaList where titulo is null
        defaultTareaShouldNotBeFound("titulo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTareasByBitacoraIsEqualToSomething() throws Exception {
        // Initialize the database
        Bitacora bitacora = BitacoraResourceIT.createEntity(em);
        em.persist(bitacora);
        em.flush();
        tarea.addBitacora(bitacora);
        tareaRepository.saveAndFlush(tarea);
        Long bitacoraId = bitacora.getId();

        // Get all the tareaList where bitacora equals to bitacoraId
        defaultTareaShouldBeFound("bitacoraId.equals=" + bitacoraId);

        // Get all the tareaList where bitacora equals to bitacoraId + 1
        defaultTareaShouldNotBeFound("bitacoraId.equals=" + (bitacoraId + 1));
    }


    @Test
    @Transactional
    public void getAllTareasByUsuarioCreadorIsEqualToSomething() throws Exception {
        // Initialize the database
        User usuarioCreador = UserResourceIT.createEntity(em);
        em.persist(usuarioCreador);
        em.flush();
        tarea.setUsuarioCreador(usuarioCreador);
        tareaRepository.saveAndFlush(tarea);
        Long usuarioCreadorId = usuarioCreador.getId();

        // Get all the tareaList where usuarioCreador equals to usuarioCreadorId
        defaultTareaShouldBeFound("usuarioCreadorId.equals=" + usuarioCreadorId);

        // Get all the tareaList where usuarioCreador equals to usuarioCreadorId + 1
        defaultTareaShouldNotBeFound("usuarioCreadorId.equals=" + (usuarioCreadorId + 1));
    }


    @Test
    @Transactional
    public void getAllTareasByUsuarioEjecutorIsEqualToSomething() throws Exception {
        // Initialize the database
        User usuarioEjecutor = UserResourceIT.createEntity(em);
        em.persist(usuarioEjecutor);
        em.flush();
        tarea.setUsuarioEjecutor(usuarioEjecutor);
        tareaRepository.saveAndFlush(tarea);
        Long usuarioEjecutorId = usuarioEjecutor.getId();

        // Get all the tareaList where usuarioEjecutor equals to usuarioEjecutorId
        defaultTareaShouldBeFound("usuarioEjecutorId.equals=" + usuarioEjecutorId);

        // Get all the tareaList where usuarioEjecutor equals to usuarioEjecutorId + 1
        defaultTareaShouldNotBeFound("usuarioEjecutorId.equals=" + (usuarioEjecutorId + 1));
    }


    @Test
    @Transactional
    public void getAllTareasByRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        Requerimiento requerimiento = RequerimientoResourceIT.createEntity(em);
        em.persist(requerimiento);
        em.flush();
        tarea.setRequerimiento(requerimiento);
        tareaRepository.saveAndFlush(tarea);
        Long requerimientoId = requerimiento.getId();

        // Get all the tareaList where requerimiento equals to requerimientoId
        defaultTareaShouldBeFound("requerimientoId.equals=" + requerimientoId);

        // Get all the tareaList where requerimiento equals to requerimientoId + 1
        defaultTareaShouldNotBeFound("requerimientoId.equals=" + (requerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllTareasByCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidato candidato = CandidatoResourceIT.createEntity(em);
        em.persist(candidato);
        em.flush();
        tarea.setCandidato(candidato);
        tareaRepository.saveAndFlush(tarea);
        Long candidatoId = candidato.getId();

        // Get all the tareaList where candidato equals to candidatoId
        defaultTareaShouldBeFound("candidatoId.equals=" + candidatoId);

        // Get all the tareaList where candidato equals to candidatoId + 1
        defaultTareaShouldNotBeFound("candidatoId.equals=" + (candidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllTareasByEstatusTareaIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusTarea estatusTarea = EstatusTareaResourceIT.createEntity(em);
        em.persist(estatusTarea);
        em.flush();
        tarea.setEstatusTarea(estatusTarea);
        tareaRepository.saveAndFlush(tarea);
        Long estatusTareaId = estatusTarea.getId();

        // Get all the tareaList where estatusTarea equals to estatusTareaId
        defaultTareaShouldBeFound("estatusTareaId.equals=" + estatusTareaId);

        // Get all the tareaList where estatusTarea equals to estatusTareaId + 1
        defaultTareaShouldNotBeFound("estatusTareaId.equals=" + (estatusTareaId + 1));
    }


    @Test
    @Transactional
    public void getAllTareasByTipoTareaIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoTarea tipoTarea = TipoTareaResourceIT.createEntity(em);
        em.persist(tipoTarea);
        em.flush();
        tarea.setTipoTarea(tipoTarea);
        tareaRepository.saveAndFlush(tarea);
        Long tipoTareaId = tipoTarea.getId();

        // Get all the tareaList where tipoTarea equals to tipoTareaId
        defaultTareaShouldBeFound("tipoTareaId.equals=" + tipoTareaId);

        // Get all the tareaList where tipoTarea equals to tipoTareaId + 1
        defaultTareaShouldNotBeFound("tipoTareaId.equals=" + (tipoTareaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTareaShouldBeFound(String filter) throws Exception {
        restTareaMockMvc.perform(get("/api/tareas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));

        // Check, that the count call also returns 1
        restTareaMockMvc.perform(get("/api/tareas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTareaShouldNotBeFound(String filter) throws Exception {
        restTareaMockMvc.perform(get("/api/tareas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTareaMockMvc.perform(get("/api/tareas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTarea() throws Exception {
        // Get the tarea
        restTareaMockMvc.perform(get("/api/tareas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Update the tarea
        Tarea updatedTarea = tareaRepository.findById(tarea.getId()).get();
        // Disconnect from session so that the updates on updatedTarea are not directly saved in db
        em.detach(updatedTarea);
        updatedTarea
            .descripcion(UPDATED_DESCRIPCION)
            .titulo(UPDATED_TITULO);
        TareaDTO tareaDTO = tareaMapper.toDto(updatedTarea);

        restTareaMockMvc.perform(put("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isOk());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTarea.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTareaMockMvc.perform(put("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeDelete = tareaRepository.findAll().size();

        // Delete the tarea
        restTareaMockMvc.perform(delete("/api/tareas/{id}", tarea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarea.class);
        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        Tarea tarea2 = new Tarea();
        tarea2.setId(tarea1.getId());
        assertThat(tarea1).isEqualTo(tarea2);
        tarea2.setId(2L);
        assertThat(tarea1).isNotEqualTo(tarea2);
        tarea1.setId(null);
        assertThat(tarea1).isNotEqualTo(tarea2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TareaDTO.class);
        TareaDTO tareaDTO1 = new TareaDTO();
        tareaDTO1.setId(1L);
        TareaDTO tareaDTO2 = new TareaDTO();
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
        tareaDTO2.setId(tareaDTO1.getId());
        assertThat(tareaDTO1).isEqualTo(tareaDTO2);
        tareaDTO2.setId(2L);
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
        tareaDTO1.setId(null);
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tareaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tareaMapper.fromId(null)).isNull();
    }
}
