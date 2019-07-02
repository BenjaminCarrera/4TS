package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.domain.SkillRequerimiento;
import com.kode.ts.domain.Tarea;
import com.kode.ts.domain.Bitacora;
import com.kode.ts.domain.Cuenta;
import com.kode.ts.domain.User;
import com.kode.ts.domain.EstatusRequerimiento;
import com.kode.ts.domain.PrioridadReq;
import com.kode.ts.domain.TipoSolicitud;
import com.kode.ts.domain.TipoIngreso;
import com.kode.ts.domain.BaseTarifa;
import com.kode.ts.domain.EsqContRec;
import com.kode.ts.domain.Perfil;
import com.kode.ts.domain.NivelPerfil;
import com.kode.ts.domain.EstReqCerrado;
import com.kode.ts.domain.TipoPeriodo;
import com.kode.ts.repository.RequerimientoRepository;
import com.kode.ts.service.RequerimientoService;
import com.kode.ts.service.dto.RequerimientoDTO;
import com.kode.ts.service.mapper.RequerimientoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.RequerimientoCriteria;
import com.kode.ts.service.RequerimientoQueryService;

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
 * Integration tests for the {@Link RequerimientoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class RequerimientoResourceIT {

    private static final Instant DEFAULT_FECHA_ALDA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALDA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_RESOLUCION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_RESOLUCION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMPLAZO_DE = "AAAAAAAAAA";
    private static final String UPDATED_REMPLAZO_DE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VACANTES_SOLICITADAS = 1;
    private static final Integer UPDATED_VACANTES_SOLICITADAS = 2;

    private static final String DEFAULT_PROYECTO = "AAAAAAAAAA";
    private static final String UPDATED_PROYECTO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CONTACTO = "BBBBBBBBBB";

    private static final Float DEFAULT_TARIFA_SUELDO_NET = 1F;
    private static final Float UPDATED_TARIFA_SUELDO_NET = 2F;

    private static final String DEFAULT_PRESTACIONES = "AAAAAAAAAA";
    private static final String UPDATED_PRESTACIONES = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURACION_ASIGNACION = 1;
    private static final Integer UPDATED_DURACION_ASIGNACION = 2;

    private static final String DEFAULT_LUGAR_TRABAJO = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR_TRABAJO = "BBBBBBBBBB";

    private static final Float DEFAULT_COOR_LAT = 1F;
    private static final Float UPDATED_COOR_LAT = 2F;

    private static final Float DEFAULT_COOR_LONG = 1F;
    private static final Float UPDATED_COOR_LONG = 2F;

    private static final String DEFAULT_HORARIO = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMACION_EXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_INFORMACION_EXAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMACION_ADICIONAL = "AAAAAAAAAA";
    private static final String UPDATED_INFORMACION_ADICIONAL = "BBBBBBBBBB";

    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private RequerimientoMapper requerimientoMapper;

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired
    private RequerimientoQueryService requerimientoQueryService;

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

    private MockMvc restRequerimientoMockMvc;

    private Requerimiento requerimiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequerimientoResource requerimientoResource = new RequerimientoResource(requerimientoService, requerimientoQueryService);
        this.restRequerimientoMockMvc = MockMvcBuilders.standaloneSetup(requerimientoResource)
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
    public static Requerimiento createEntity(EntityManager em) {
        Requerimiento requerimiento = new Requerimiento()
            .fechaAlda(DEFAULT_FECHA_ALDA)
            .fechaResolucion(DEFAULT_FECHA_RESOLUCION)
            .remplazoDe(DEFAULT_REMPLAZO_DE)
            .vacantesSolicitadas(DEFAULT_VACANTES_SOLICITADAS)
            .proyecto(DEFAULT_PROYECTO)
            .nombreContacto(DEFAULT_NOMBRE_CONTACTO)
            .tarifaSueldoNet(DEFAULT_TARIFA_SUELDO_NET)
            .prestaciones(DEFAULT_PRESTACIONES)
            .duracionAsignacion(DEFAULT_DURACION_ASIGNACION)
            .lugarTrabajo(DEFAULT_LUGAR_TRABAJO)
            .coorLat(DEFAULT_COOR_LAT)
            .coorLong(DEFAULT_COOR_LONG)
            .horario(DEFAULT_HORARIO)
            .informacionExamen(DEFAULT_INFORMACION_EXAMEN)
            .informacionAdicional(DEFAULT_INFORMACION_ADICIONAL);
        return requerimiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requerimiento createUpdatedEntity(EntityManager em) {
        Requerimiento requerimiento = new Requerimiento()
            .fechaAlda(UPDATED_FECHA_ALDA)
            .fechaResolucion(UPDATED_FECHA_RESOLUCION)
            .remplazoDe(UPDATED_REMPLAZO_DE)
            .vacantesSolicitadas(UPDATED_VACANTES_SOLICITADAS)
            .proyecto(UPDATED_PROYECTO)
            .nombreContacto(UPDATED_NOMBRE_CONTACTO)
            .tarifaSueldoNet(UPDATED_TARIFA_SUELDO_NET)
            .prestaciones(UPDATED_PRESTACIONES)
            .duracionAsignacion(UPDATED_DURACION_ASIGNACION)
            .lugarTrabajo(UPDATED_LUGAR_TRABAJO)
            .coorLat(UPDATED_COOR_LAT)
            .coorLong(UPDATED_COOR_LONG)
            .horario(UPDATED_HORARIO)
            .informacionExamen(UPDATED_INFORMACION_EXAMEN)
            .informacionAdicional(UPDATED_INFORMACION_ADICIONAL);
        return requerimiento;
    }

    @BeforeEach
    public void initTest() {
        requerimiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequerimiento() throws Exception {
        int databaseSizeBeforeCreate = requerimientoRepository.findAll().size();

        // Create the Requerimiento
        RequerimientoDTO requerimientoDTO = requerimientoMapper.toDto(requerimiento);
        restRequerimientoMockMvc.perform(post("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Requerimiento in the database
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeCreate + 1);
        Requerimiento testRequerimiento = requerimientoList.get(requerimientoList.size() - 1);
        assertThat(testRequerimiento.getFechaAlda()).isEqualTo(DEFAULT_FECHA_ALDA);
        assertThat(testRequerimiento.getFechaResolucion()).isEqualTo(DEFAULT_FECHA_RESOLUCION);
        assertThat(testRequerimiento.getRemplazoDe()).isEqualTo(DEFAULT_REMPLAZO_DE);
        assertThat(testRequerimiento.getVacantesSolicitadas()).isEqualTo(DEFAULT_VACANTES_SOLICITADAS);
        assertThat(testRequerimiento.getProyecto()).isEqualTo(DEFAULT_PROYECTO);
        assertThat(testRequerimiento.getNombreContacto()).isEqualTo(DEFAULT_NOMBRE_CONTACTO);
        assertThat(testRequerimiento.getTarifaSueldoNet()).isEqualTo(DEFAULT_TARIFA_SUELDO_NET);
        assertThat(testRequerimiento.getPrestaciones()).isEqualTo(DEFAULT_PRESTACIONES);
        assertThat(testRequerimiento.getDuracionAsignacion()).isEqualTo(DEFAULT_DURACION_ASIGNACION);
        assertThat(testRequerimiento.getLugarTrabajo()).isEqualTo(DEFAULT_LUGAR_TRABAJO);
        assertThat(testRequerimiento.getCoorLat()).isEqualTo(DEFAULT_COOR_LAT);
        assertThat(testRequerimiento.getCoorLong()).isEqualTo(DEFAULT_COOR_LONG);
        assertThat(testRequerimiento.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testRequerimiento.getInformacionExamen()).isEqualTo(DEFAULT_INFORMACION_EXAMEN);
        assertThat(testRequerimiento.getInformacionAdicional()).isEqualTo(DEFAULT_INFORMACION_ADICIONAL);
    }

    @Test
    @Transactional
    public void createRequerimientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requerimientoRepository.findAll().size();

        // Create the Requerimiento with an existing ID
        requerimiento.setId(1L);
        RequerimientoDTO requerimientoDTO = requerimientoMapper.toDto(requerimiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequerimientoMockMvc.perform(post("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requerimiento in the database
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaAldaIsRequired() throws Exception {
        int databaseSizeBeforeTest = requerimientoRepository.findAll().size();
        // set the field null
        requerimiento.setFechaAlda(null);

        // Create the Requerimiento, which fails.
        RequerimientoDTO requerimientoDTO = requerimientoMapper.toDto(requerimiento);

        restRequerimientoMockMvc.perform(post("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoDTO)))
            .andExpect(status().isBadRequest());

        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequerimientos() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList
        restRequerimientoMockMvc.perform(get("/api/requerimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requerimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaAlda").value(hasItem(DEFAULT_FECHA_ALDA.toString())))
            .andExpect(jsonPath("$.[*].fechaResolucion").value(hasItem(DEFAULT_FECHA_RESOLUCION.toString())))
            .andExpect(jsonPath("$.[*].remplazoDe").value(hasItem(DEFAULT_REMPLAZO_DE.toString())))
            .andExpect(jsonPath("$.[*].vacantesSolicitadas").value(hasItem(DEFAULT_VACANTES_SOLICITADAS)))
            .andExpect(jsonPath("$.[*].proyecto").value(hasItem(DEFAULT_PROYECTO.toString())))
            .andExpect(jsonPath("$.[*].nombreContacto").value(hasItem(DEFAULT_NOMBRE_CONTACTO.toString())))
            .andExpect(jsonPath("$.[*].tarifaSueldoNet").value(hasItem(DEFAULT_TARIFA_SUELDO_NET.doubleValue())))
            .andExpect(jsonPath("$.[*].prestaciones").value(hasItem(DEFAULT_PRESTACIONES.toString())))
            .andExpect(jsonPath("$.[*].duracionAsignacion").value(hasItem(DEFAULT_DURACION_ASIGNACION)))
            .andExpect(jsonPath("$.[*].lugarTrabajo").value(hasItem(DEFAULT_LUGAR_TRABAJO.toString())))
            .andExpect(jsonPath("$.[*].coorLat").value(hasItem(DEFAULT_COOR_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].coorLong").value(hasItem(DEFAULT_COOR_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())))
            .andExpect(jsonPath("$.[*].informacionExamen").value(hasItem(DEFAULT_INFORMACION_EXAMEN.toString())))
            .andExpect(jsonPath("$.[*].informacionAdicional").value(hasItem(DEFAULT_INFORMACION_ADICIONAL.toString())));
    }
    
    @Test
    @Transactional
    public void getRequerimiento() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get the requerimiento
        restRequerimientoMockMvc.perform(get("/api/requerimientos/{id}", requerimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requerimiento.getId().intValue()))
            .andExpect(jsonPath("$.fechaAlda").value(DEFAULT_FECHA_ALDA.toString()))
            .andExpect(jsonPath("$.fechaResolucion").value(DEFAULT_FECHA_RESOLUCION.toString()))
            .andExpect(jsonPath("$.remplazoDe").value(DEFAULT_REMPLAZO_DE.toString()))
            .andExpect(jsonPath("$.vacantesSolicitadas").value(DEFAULT_VACANTES_SOLICITADAS))
            .andExpect(jsonPath("$.proyecto").value(DEFAULT_PROYECTO.toString()))
            .andExpect(jsonPath("$.nombreContacto").value(DEFAULT_NOMBRE_CONTACTO.toString()))
            .andExpect(jsonPath("$.tarifaSueldoNet").value(DEFAULT_TARIFA_SUELDO_NET.doubleValue()))
            .andExpect(jsonPath("$.prestaciones").value(DEFAULT_PRESTACIONES.toString()))
            .andExpect(jsonPath("$.duracionAsignacion").value(DEFAULT_DURACION_ASIGNACION))
            .andExpect(jsonPath("$.lugarTrabajo").value(DEFAULT_LUGAR_TRABAJO.toString()))
            .andExpect(jsonPath("$.coorLat").value(DEFAULT_COOR_LAT.doubleValue()))
            .andExpect(jsonPath("$.coorLong").value(DEFAULT_COOR_LONG.doubleValue()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()))
            .andExpect(jsonPath("$.informacionExamen").value(DEFAULT_INFORMACION_EXAMEN.toString()))
            .andExpect(jsonPath("$.informacionAdicional").value(DEFAULT_INFORMACION_ADICIONAL.toString()));
    }

    @Test
    @Transactional
    public void getAllRequerimientosByFechaAldaIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where fechaAlda equals to DEFAULT_FECHA_ALDA
        defaultRequerimientoShouldBeFound("fechaAlda.equals=" + DEFAULT_FECHA_ALDA);

        // Get all the requerimientoList where fechaAlda equals to UPDATED_FECHA_ALDA
        defaultRequerimientoShouldNotBeFound("fechaAlda.equals=" + UPDATED_FECHA_ALDA);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByFechaAldaIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where fechaAlda in DEFAULT_FECHA_ALDA or UPDATED_FECHA_ALDA
        defaultRequerimientoShouldBeFound("fechaAlda.in=" + DEFAULT_FECHA_ALDA + "," + UPDATED_FECHA_ALDA);

        // Get all the requerimientoList where fechaAlda equals to UPDATED_FECHA_ALDA
        defaultRequerimientoShouldNotBeFound("fechaAlda.in=" + UPDATED_FECHA_ALDA);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByFechaAldaIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where fechaAlda is not null
        defaultRequerimientoShouldBeFound("fechaAlda.specified=true");

        // Get all the requerimientoList where fechaAlda is null
        defaultRequerimientoShouldNotBeFound("fechaAlda.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByFechaResolucionIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where fechaResolucion equals to DEFAULT_FECHA_RESOLUCION
        defaultRequerimientoShouldBeFound("fechaResolucion.equals=" + DEFAULT_FECHA_RESOLUCION);

        // Get all the requerimientoList where fechaResolucion equals to UPDATED_FECHA_RESOLUCION
        defaultRequerimientoShouldNotBeFound("fechaResolucion.equals=" + UPDATED_FECHA_RESOLUCION);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByFechaResolucionIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where fechaResolucion in DEFAULT_FECHA_RESOLUCION or UPDATED_FECHA_RESOLUCION
        defaultRequerimientoShouldBeFound("fechaResolucion.in=" + DEFAULT_FECHA_RESOLUCION + "," + UPDATED_FECHA_RESOLUCION);

        // Get all the requerimientoList where fechaResolucion equals to UPDATED_FECHA_RESOLUCION
        defaultRequerimientoShouldNotBeFound("fechaResolucion.in=" + UPDATED_FECHA_RESOLUCION);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByFechaResolucionIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where fechaResolucion is not null
        defaultRequerimientoShouldBeFound("fechaResolucion.specified=true");

        // Get all the requerimientoList where fechaResolucion is null
        defaultRequerimientoShouldNotBeFound("fechaResolucion.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByRemplazoDeIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where remplazoDe equals to DEFAULT_REMPLAZO_DE
        defaultRequerimientoShouldBeFound("remplazoDe.equals=" + DEFAULT_REMPLAZO_DE);

        // Get all the requerimientoList where remplazoDe equals to UPDATED_REMPLAZO_DE
        defaultRequerimientoShouldNotBeFound("remplazoDe.equals=" + UPDATED_REMPLAZO_DE);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByRemplazoDeIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where remplazoDe in DEFAULT_REMPLAZO_DE or UPDATED_REMPLAZO_DE
        defaultRequerimientoShouldBeFound("remplazoDe.in=" + DEFAULT_REMPLAZO_DE + "," + UPDATED_REMPLAZO_DE);

        // Get all the requerimientoList where remplazoDe equals to UPDATED_REMPLAZO_DE
        defaultRequerimientoShouldNotBeFound("remplazoDe.in=" + UPDATED_REMPLAZO_DE);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByRemplazoDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where remplazoDe is not null
        defaultRequerimientoShouldBeFound("remplazoDe.specified=true");

        // Get all the requerimientoList where remplazoDe is null
        defaultRequerimientoShouldNotBeFound("remplazoDe.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByVacantesSolicitadasIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where vacantesSolicitadas equals to DEFAULT_VACANTES_SOLICITADAS
        defaultRequerimientoShouldBeFound("vacantesSolicitadas.equals=" + DEFAULT_VACANTES_SOLICITADAS);

        // Get all the requerimientoList where vacantesSolicitadas equals to UPDATED_VACANTES_SOLICITADAS
        defaultRequerimientoShouldNotBeFound("vacantesSolicitadas.equals=" + UPDATED_VACANTES_SOLICITADAS);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByVacantesSolicitadasIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where vacantesSolicitadas in DEFAULT_VACANTES_SOLICITADAS or UPDATED_VACANTES_SOLICITADAS
        defaultRequerimientoShouldBeFound("vacantesSolicitadas.in=" + DEFAULT_VACANTES_SOLICITADAS + "," + UPDATED_VACANTES_SOLICITADAS);

        // Get all the requerimientoList where vacantesSolicitadas equals to UPDATED_VACANTES_SOLICITADAS
        defaultRequerimientoShouldNotBeFound("vacantesSolicitadas.in=" + UPDATED_VACANTES_SOLICITADAS);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByVacantesSolicitadasIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where vacantesSolicitadas is not null
        defaultRequerimientoShouldBeFound("vacantesSolicitadas.specified=true");

        // Get all the requerimientoList where vacantesSolicitadas is null
        defaultRequerimientoShouldNotBeFound("vacantesSolicitadas.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByVacantesSolicitadasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where vacantesSolicitadas greater than or equals to DEFAULT_VACANTES_SOLICITADAS
        defaultRequerimientoShouldBeFound("vacantesSolicitadas.greaterOrEqualThan=" + DEFAULT_VACANTES_SOLICITADAS);

        // Get all the requerimientoList where vacantesSolicitadas greater than or equals to UPDATED_VACANTES_SOLICITADAS
        defaultRequerimientoShouldNotBeFound("vacantesSolicitadas.greaterOrEqualThan=" + UPDATED_VACANTES_SOLICITADAS);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByVacantesSolicitadasIsLessThanSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where vacantesSolicitadas less than or equals to DEFAULT_VACANTES_SOLICITADAS
        defaultRequerimientoShouldNotBeFound("vacantesSolicitadas.lessThan=" + DEFAULT_VACANTES_SOLICITADAS);

        // Get all the requerimientoList where vacantesSolicitadas less than or equals to UPDATED_VACANTES_SOLICITADAS
        defaultRequerimientoShouldBeFound("vacantesSolicitadas.lessThan=" + UPDATED_VACANTES_SOLICITADAS);
    }


    @Test
    @Transactional
    public void getAllRequerimientosByProyectoIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where proyecto equals to DEFAULT_PROYECTO
        defaultRequerimientoShouldBeFound("proyecto.equals=" + DEFAULT_PROYECTO);

        // Get all the requerimientoList where proyecto equals to UPDATED_PROYECTO
        defaultRequerimientoShouldNotBeFound("proyecto.equals=" + UPDATED_PROYECTO);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByProyectoIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where proyecto in DEFAULT_PROYECTO or UPDATED_PROYECTO
        defaultRequerimientoShouldBeFound("proyecto.in=" + DEFAULT_PROYECTO + "," + UPDATED_PROYECTO);

        // Get all the requerimientoList where proyecto equals to UPDATED_PROYECTO
        defaultRequerimientoShouldNotBeFound("proyecto.in=" + UPDATED_PROYECTO);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByProyectoIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where proyecto is not null
        defaultRequerimientoShouldBeFound("proyecto.specified=true");

        // Get all the requerimientoList where proyecto is null
        defaultRequerimientoShouldNotBeFound("proyecto.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByNombreContactoIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where nombreContacto equals to DEFAULT_NOMBRE_CONTACTO
        defaultRequerimientoShouldBeFound("nombreContacto.equals=" + DEFAULT_NOMBRE_CONTACTO);

        // Get all the requerimientoList where nombreContacto equals to UPDATED_NOMBRE_CONTACTO
        defaultRequerimientoShouldNotBeFound("nombreContacto.equals=" + UPDATED_NOMBRE_CONTACTO);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByNombreContactoIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where nombreContacto in DEFAULT_NOMBRE_CONTACTO or UPDATED_NOMBRE_CONTACTO
        defaultRequerimientoShouldBeFound("nombreContacto.in=" + DEFAULT_NOMBRE_CONTACTO + "," + UPDATED_NOMBRE_CONTACTO);

        // Get all the requerimientoList where nombreContacto equals to UPDATED_NOMBRE_CONTACTO
        defaultRequerimientoShouldNotBeFound("nombreContacto.in=" + UPDATED_NOMBRE_CONTACTO);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByNombreContactoIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where nombreContacto is not null
        defaultRequerimientoShouldBeFound("nombreContacto.specified=true");

        // Get all the requerimientoList where nombreContacto is null
        defaultRequerimientoShouldNotBeFound("nombreContacto.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByTarifaSueldoNetIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where tarifaSueldoNet equals to DEFAULT_TARIFA_SUELDO_NET
        defaultRequerimientoShouldBeFound("tarifaSueldoNet.equals=" + DEFAULT_TARIFA_SUELDO_NET);

        // Get all the requerimientoList where tarifaSueldoNet equals to UPDATED_TARIFA_SUELDO_NET
        defaultRequerimientoShouldNotBeFound("tarifaSueldoNet.equals=" + UPDATED_TARIFA_SUELDO_NET);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByTarifaSueldoNetIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where tarifaSueldoNet in DEFAULT_TARIFA_SUELDO_NET or UPDATED_TARIFA_SUELDO_NET
        defaultRequerimientoShouldBeFound("tarifaSueldoNet.in=" + DEFAULT_TARIFA_SUELDO_NET + "," + UPDATED_TARIFA_SUELDO_NET);

        // Get all the requerimientoList where tarifaSueldoNet equals to UPDATED_TARIFA_SUELDO_NET
        defaultRequerimientoShouldNotBeFound("tarifaSueldoNet.in=" + UPDATED_TARIFA_SUELDO_NET);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByTarifaSueldoNetIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where tarifaSueldoNet is not null
        defaultRequerimientoShouldBeFound("tarifaSueldoNet.specified=true");

        // Get all the requerimientoList where tarifaSueldoNet is null
        defaultRequerimientoShouldNotBeFound("tarifaSueldoNet.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByPrestacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where prestaciones equals to DEFAULT_PRESTACIONES
        defaultRequerimientoShouldBeFound("prestaciones.equals=" + DEFAULT_PRESTACIONES);

        // Get all the requerimientoList where prestaciones equals to UPDATED_PRESTACIONES
        defaultRequerimientoShouldNotBeFound("prestaciones.equals=" + UPDATED_PRESTACIONES);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByPrestacionesIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where prestaciones in DEFAULT_PRESTACIONES or UPDATED_PRESTACIONES
        defaultRequerimientoShouldBeFound("prestaciones.in=" + DEFAULT_PRESTACIONES + "," + UPDATED_PRESTACIONES);

        // Get all the requerimientoList where prestaciones equals to UPDATED_PRESTACIONES
        defaultRequerimientoShouldNotBeFound("prestaciones.in=" + UPDATED_PRESTACIONES);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByPrestacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where prestaciones is not null
        defaultRequerimientoShouldBeFound("prestaciones.specified=true");

        // Get all the requerimientoList where prestaciones is null
        defaultRequerimientoShouldNotBeFound("prestaciones.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByDuracionAsignacionIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where duracionAsignacion equals to DEFAULT_DURACION_ASIGNACION
        defaultRequerimientoShouldBeFound("duracionAsignacion.equals=" + DEFAULT_DURACION_ASIGNACION);

        // Get all the requerimientoList where duracionAsignacion equals to UPDATED_DURACION_ASIGNACION
        defaultRequerimientoShouldNotBeFound("duracionAsignacion.equals=" + UPDATED_DURACION_ASIGNACION);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByDuracionAsignacionIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where duracionAsignacion in DEFAULT_DURACION_ASIGNACION or UPDATED_DURACION_ASIGNACION
        defaultRequerimientoShouldBeFound("duracionAsignacion.in=" + DEFAULT_DURACION_ASIGNACION + "," + UPDATED_DURACION_ASIGNACION);

        // Get all the requerimientoList where duracionAsignacion equals to UPDATED_DURACION_ASIGNACION
        defaultRequerimientoShouldNotBeFound("duracionAsignacion.in=" + UPDATED_DURACION_ASIGNACION);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByDuracionAsignacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where duracionAsignacion is not null
        defaultRequerimientoShouldBeFound("duracionAsignacion.specified=true");

        // Get all the requerimientoList where duracionAsignacion is null
        defaultRequerimientoShouldNotBeFound("duracionAsignacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByDuracionAsignacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where duracionAsignacion greater than or equals to DEFAULT_DURACION_ASIGNACION
        defaultRequerimientoShouldBeFound("duracionAsignacion.greaterOrEqualThan=" + DEFAULT_DURACION_ASIGNACION);

        // Get all the requerimientoList where duracionAsignacion greater than or equals to UPDATED_DURACION_ASIGNACION
        defaultRequerimientoShouldNotBeFound("duracionAsignacion.greaterOrEqualThan=" + UPDATED_DURACION_ASIGNACION);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByDuracionAsignacionIsLessThanSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where duracionAsignacion less than or equals to DEFAULT_DURACION_ASIGNACION
        defaultRequerimientoShouldNotBeFound("duracionAsignacion.lessThan=" + DEFAULT_DURACION_ASIGNACION);

        // Get all the requerimientoList where duracionAsignacion less than or equals to UPDATED_DURACION_ASIGNACION
        defaultRequerimientoShouldBeFound("duracionAsignacion.lessThan=" + UPDATED_DURACION_ASIGNACION);
    }


    @Test
    @Transactional
    public void getAllRequerimientosByLugarTrabajoIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where lugarTrabajo equals to DEFAULT_LUGAR_TRABAJO
        defaultRequerimientoShouldBeFound("lugarTrabajo.equals=" + DEFAULT_LUGAR_TRABAJO);

        // Get all the requerimientoList where lugarTrabajo equals to UPDATED_LUGAR_TRABAJO
        defaultRequerimientoShouldNotBeFound("lugarTrabajo.equals=" + UPDATED_LUGAR_TRABAJO);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByLugarTrabajoIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where lugarTrabajo in DEFAULT_LUGAR_TRABAJO or UPDATED_LUGAR_TRABAJO
        defaultRequerimientoShouldBeFound("lugarTrabajo.in=" + DEFAULT_LUGAR_TRABAJO + "," + UPDATED_LUGAR_TRABAJO);

        // Get all the requerimientoList where lugarTrabajo equals to UPDATED_LUGAR_TRABAJO
        defaultRequerimientoShouldNotBeFound("lugarTrabajo.in=" + UPDATED_LUGAR_TRABAJO);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByLugarTrabajoIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where lugarTrabajo is not null
        defaultRequerimientoShouldBeFound("lugarTrabajo.specified=true");

        // Get all the requerimientoList where lugarTrabajo is null
        defaultRequerimientoShouldNotBeFound("lugarTrabajo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByCoorLatIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where coorLat equals to DEFAULT_COOR_LAT
        defaultRequerimientoShouldBeFound("coorLat.equals=" + DEFAULT_COOR_LAT);

        // Get all the requerimientoList where coorLat equals to UPDATED_COOR_LAT
        defaultRequerimientoShouldNotBeFound("coorLat.equals=" + UPDATED_COOR_LAT);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByCoorLatIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where coorLat in DEFAULT_COOR_LAT or UPDATED_COOR_LAT
        defaultRequerimientoShouldBeFound("coorLat.in=" + DEFAULT_COOR_LAT + "," + UPDATED_COOR_LAT);

        // Get all the requerimientoList where coorLat equals to UPDATED_COOR_LAT
        defaultRequerimientoShouldNotBeFound("coorLat.in=" + UPDATED_COOR_LAT);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByCoorLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where coorLat is not null
        defaultRequerimientoShouldBeFound("coorLat.specified=true");

        // Get all the requerimientoList where coorLat is null
        defaultRequerimientoShouldNotBeFound("coorLat.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByCoorLongIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where coorLong equals to DEFAULT_COOR_LONG
        defaultRequerimientoShouldBeFound("coorLong.equals=" + DEFAULT_COOR_LONG);

        // Get all the requerimientoList where coorLong equals to UPDATED_COOR_LONG
        defaultRequerimientoShouldNotBeFound("coorLong.equals=" + UPDATED_COOR_LONG);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByCoorLongIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where coorLong in DEFAULT_COOR_LONG or UPDATED_COOR_LONG
        defaultRequerimientoShouldBeFound("coorLong.in=" + DEFAULT_COOR_LONG + "," + UPDATED_COOR_LONG);

        // Get all the requerimientoList where coorLong equals to UPDATED_COOR_LONG
        defaultRequerimientoShouldNotBeFound("coorLong.in=" + UPDATED_COOR_LONG);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByCoorLongIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where coorLong is not null
        defaultRequerimientoShouldBeFound("coorLong.specified=true");

        // Get all the requerimientoList where coorLong is null
        defaultRequerimientoShouldNotBeFound("coorLong.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByHorarioIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where horario equals to DEFAULT_HORARIO
        defaultRequerimientoShouldBeFound("horario.equals=" + DEFAULT_HORARIO);

        // Get all the requerimientoList where horario equals to UPDATED_HORARIO
        defaultRequerimientoShouldNotBeFound("horario.equals=" + UPDATED_HORARIO);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByHorarioIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where horario in DEFAULT_HORARIO or UPDATED_HORARIO
        defaultRequerimientoShouldBeFound("horario.in=" + DEFAULT_HORARIO + "," + UPDATED_HORARIO);

        // Get all the requerimientoList where horario equals to UPDATED_HORARIO
        defaultRequerimientoShouldNotBeFound("horario.in=" + UPDATED_HORARIO);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByHorarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where horario is not null
        defaultRequerimientoShouldBeFound("horario.specified=true");

        // Get all the requerimientoList where horario is null
        defaultRequerimientoShouldNotBeFound("horario.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByInformacionExamenIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where informacionExamen equals to DEFAULT_INFORMACION_EXAMEN
        defaultRequerimientoShouldBeFound("informacionExamen.equals=" + DEFAULT_INFORMACION_EXAMEN);

        // Get all the requerimientoList where informacionExamen equals to UPDATED_INFORMACION_EXAMEN
        defaultRequerimientoShouldNotBeFound("informacionExamen.equals=" + UPDATED_INFORMACION_EXAMEN);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByInformacionExamenIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where informacionExamen in DEFAULT_INFORMACION_EXAMEN or UPDATED_INFORMACION_EXAMEN
        defaultRequerimientoShouldBeFound("informacionExamen.in=" + DEFAULT_INFORMACION_EXAMEN + "," + UPDATED_INFORMACION_EXAMEN);

        // Get all the requerimientoList where informacionExamen equals to UPDATED_INFORMACION_EXAMEN
        defaultRequerimientoShouldNotBeFound("informacionExamen.in=" + UPDATED_INFORMACION_EXAMEN);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByInformacionExamenIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where informacionExamen is not null
        defaultRequerimientoShouldBeFound("informacionExamen.specified=true");

        // Get all the requerimientoList where informacionExamen is null
        defaultRequerimientoShouldNotBeFound("informacionExamen.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosByInformacionAdicionalIsEqualToSomething() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where informacionAdicional equals to DEFAULT_INFORMACION_ADICIONAL
        defaultRequerimientoShouldBeFound("informacionAdicional.equals=" + DEFAULT_INFORMACION_ADICIONAL);

        // Get all the requerimientoList where informacionAdicional equals to UPDATED_INFORMACION_ADICIONAL
        defaultRequerimientoShouldNotBeFound("informacionAdicional.equals=" + UPDATED_INFORMACION_ADICIONAL);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByInformacionAdicionalIsInShouldWork() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where informacionAdicional in DEFAULT_INFORMACION_ADICIONAL or UPDATED_INFORMACION_ADICIONAL
        defaultRequerimientoShouldBeFound("informacionAdicional.in=" + DEFAULT_INFORMACION_ADICIONAL + "," + UPDATED_INFORMACION_ADICIONAL);

        // Get all the requerimientoList where informacionAdicional equals to UPDATED_INFORMACION_ADICIONAL
        defaultRequerimientoShouldNotBeFound("informacionAdicional.in=" + UPDATED_INFORMACION_ADICIONAL);
    }

    @Test
    @Transactional
    public void getAllRequerimientosByInformacionAdicionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList where informacionAdicional is not null
        defaultRequerimientoShouldBeFound("informacionAdicional.specified=true");

        // Get all the requerimientoList where informacionAdicional is null
        defaultRequerimientoShouldNotBeFound("informacionAdicional.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequerimientosBySkillRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillRequerimiento skillRequerimiento = SkillRequerimientoResourceIT.createEntity(em);
        em.persist(skillRequerimiento);
        em.flush();
        requerimiento.addSkillRequerimiento(skillRequerimiento);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long skillRequerimientoId = skillRequerimiento.getId();

        // Get all the requerimientoList where skillRequerimiento equals to skillRequerimientoId
        defaultRequerimientoShouldBeFound("skillRequerimientoId.equals=" + skillRequerimientoId);

        // Get all the requerimientoList where skillRequerimiento equals to skillRequerimientoId + 1
        defaultRequerimientoShouldNotBeFound("skillRequerimientoId.equals=" + (skillRequerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByTareaIsEqualToSomething() throws Exception {
        // Initialize the database
        Tarea tarea = TareaResourceIT.createEntity(em);
        em.persist(tarea);
        em.flush();
        requerimiento.addTarea(tarea);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long tareaId = tarea.getId();

        // Get all the requerimientoList where tarea equals to tareaId
        defaultRequerimientoShouldBeFound("tareaId.equals=" + tareaId);

        // Get all the requerimientoList where tarea equals to tareaId + 1
        defaultRequerimientoShouldNotBeFound("tareaId.equals=" + (tareaId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByBitacoraIsEqualToSomething() throws Exception {
        // Initialize the database
        Bitacora bitacora = BitacoraResourceIT.createEntity(em);
        em.persist(bitacora);
        em.flush();
        requerimiento.addBitacora(bitacora);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long bitacoraId = bitacora.getId();

        // Get all the requerimientoList where bitacora equals to bitacoraId
        defaultRequerimientoShouldBeFound("bitacoraId.equals=" + bitacoraId);

        // Get all the requerimientoList where bitacora equals to bitacoraId + 1
        defaultRequerimientoShouldNotBeFound("bitacoraId.equals=" + (bitacoraId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByCuentaIsEqualToSomething() throws Exception {
        // Initialize the database
        Cuenta cuenta = CuentaResourceIT.createEntity(em);
        em.persist(cuenta);
        em.flush();
        requerimiento.setCuenta(cuenta);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long cuentaId = cuenta.getId();

        // Get all the requerimientoList where cuenta equals to cuentaId
        defaultRequerimientoShouldBeFound("cuentaId.equals=" + cuentaId);

        // Get all the requerimientoList where cuenta equals to cuentaId + 1
        defaultRequerimientoShouldNotBeFound("cuentaId.equals=" + (cuentaId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosBySubCuentaIsEqualToSomething() throws Exception {
        // Initialize the database
        Cuenta subCuenta = CuentaResourceIT.createEntity(em);
        em.persist(subCuenta);
        em.flush();
        requerimiento.setSubCuenta(subCuenta);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long subCuentaId = subCuenta.getId();

        // Get all the requerimientoList where subCuenta equals to subCuentaId
        defaultRequerimientoShouldBeFound("subCuentaId.equals=" + subCuentaId);

        // Get all the requerimientoList where subCuenta equals to subCuentaId + 1
        defaultRequerimientoShouldNotBeFound("subCuentaId.equals=" + (subCuentaId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByUsuarioCreadorIsEqualToSomething() throws Exception {
        // Initialize the database
        User usuarioCreador = UserResourceIT.createEntity(em);
        em.persist(usuarioCreador);
        em.flush();
        requerimiento.setUsuarioCreador(usuarioCreador);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long usuarioCreadorId = usuarioCreador.getId();

        // Get all the requerimientoList where usuarioCreador equals to usuarioCreadorId
        defaultRequerimientoShouldBeFound("usuarioCreadorId.equals=" + usuarioCreadorId);

        // Get all the requerimientoList where usuarioCreador equals to usuarioCreadorId + 1
        defaultRequerimientoShouldNotBeFound("usuarioCreadorId.equals=" + (usuarioCreadorId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByUsuarioAsignadoIsEqualToSomething() throws Exception {
        // Initialize the database
        User usuarioAsignado = UserResourceIT.createEntity(em);
        em.persist(usuarioAsignado);
        em.flush();
        requerimiento.setUsuarioAsignado(usuarioAsignado);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long usuarioAsignadoId = usuarioAsignado.getId();

        // Get all the requerimientoList where usuarioAsignado equals to usuarioAsignadoId
        defaultRequerimientoShouldBeFound("usuarioAsignadoId.equals=" + usuarioAsignadoId);

        // Get all the requerimientoList where usuarioAsignado equals to usuarioAsignadoId + 1
        defaultRequerimientoShouldNotBeFound("usuarioAsignadoId.equals=" + (usuarioAsignadoId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByEstatusRequerimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusRequerimiento estatusRequerimiento = EstatusRequerimientoResourceIT.createEntity(em);
        em.persist(estatusRequerimiento);
        em.flush();
        requerimiento.setEstatusRequerimiento(estatusRequerimiento);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long estatusRequerimientoId = estatusRequerimiento.getId();

        // Get all the requerimientoList where estatusRequerimiento equals to estatusRequerimientoId
        defaultRequerimientoShouldBeFound("estatusRequerimientoId.equals=" + estatusRequerimientoId);

        // Get all the requerimientoList where estatusRequerimiento equals to estatusRequerimientoId + 1
        defaultRequerimientoShouldNotBeFound("estatusRequerimientoId.equals=" + (estatusRequerimientoId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByPrioridadIsEqualToSomething() throws Exception {
        // Initialize the database
        PrioridadReq prioridad = PrioridadReqResourceIT.createEntity(em);
        em.persist(prioridad);
        em.flush();
        requerimiento.setPrioridad(prioridad);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long prioridadId = prioridad.getId();

        // Get all the requerimientoList where prioridad equals to prioridadId
        defaultRequerimientoShouldBeFound("prioridadId.equals=" + prioridadId);

        // Get all the requerimientoList where prioridad equals to prioridadId + 1
        defaultRequerimientoShouldNotBeFound("prioridadId.equals=" + (prioridadId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByTipoSolicitudIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoSolicitud tipoSolicitud = TipoSolicitudResourceIT.createEntity(em);
        em.persist(tipoSolicitud);
        em.flush();
        requerimiento.setTipoSolicitud(tipoSolicitud);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long tipoSolicitudId = tipoSolicitud.getId();

        // Get all the requerimientoList where tipoSolicitud equals to tipoSolicitudId
        defaultRequerimientoShouldBeFound("tipoSolicitudId.equals=" + tipoSolicitudId);

        // Get all the requerimientoList where tipoSolicitud equals to tipoSolicitudId + 1
        defaultRequerimientoShouldNotBeFound("tipoSolicitudId.equals=" + (tipoSolicitudId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByTipoIngresoIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoIngreso tipoIngreso = TipoIngresoResourceIT.createEntity(em);
        em.persist(tipoIngreso);
        em.flush();
        requerimiento.setTipoIngreso(tipoIngreso);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long tipoIngresoId = tipoIngreso.getId();

        // Get all the requerimientoList where tipoIngreso equals to tipoIngresoId
        defaultRequerimientoShouldBeFound("tipoIngresoId.equals=" + tipoIngresoId);

        // Get all the requerimientoList where tipoIngreso equals to tipoIngresoId + 1
        defaultRequerimientoShouldNotBeFound("tipoIngresoId.equals=" + (tipoIngresoId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByBaseTarifaIsEqualToSomething() throws Exception {
        // Initialize the database
        BaseTarifa baseTarifa = BaseTarifaResourceIT.createEntity(em);
        em.persist(baseTarifa);
        em.flush();
        requerimiento.setBaseTarifa(baseTarifa);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long baseTarifaId = baseTarifa.getId();

        // Get all the requerimientoList where baseTarifa equals to baseTarifaId
        defaultRequerimientoShouldBeFound("baseTarifaId.equals=" + baseTarifaId);

        // Get all the requerimientoList where baseTarifa equals to baseTarifaId + 1
        defaultRequerimientoShouldNotBeFound("baseTarifaId.equals=" + (baseTarifaId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByEsquemaContratacionIsEqualToSomething() throws Exception {
        // Initialize the database
        EsqContRec esquemaContratacion = EsqContRecResourceIT.createEntity(em);
        em.persist(esquemaContratacion);
        em.flush();
        requerimiento.setEsquemaContratacion(esquemaContratacion);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long esquemaContratacionId = esquemaContratacion.getId();

        // Get all the requerimientoList where esquemaContratacion equals to esquemaContratacionId
        defaultRequerimientoShouldBeFound("esquemaContratacionId.equals=" + esquemaContratacionId);

        // Get all the requerimientoList where esquemaContratacion equals to esquemaContratacionId + 1
        defaultRequerimientoShouldNotBeFound("esquemaContratacionId.equals=" + (esquemaContratacionId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        Perfil perfil = PerfilResourceIT.createEntity(em);
        em.persist(perfil);
        em.flush();
        requerimiento.setPerfil(perfil);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long perfilId = perfil.getId();

        // Get all the requerimientoList where perfil equals to perfilId
        defaultRequerimientoShouldBeFound("perfilId.equals=" + perfilId);

        // Get all the requerimientoList where perfil equals to perfilId + 1
        defaultRequerimientoShouldNotBeFound("perfilId.equals=" + (perfilId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByNivelPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        NivelPerfil nivelPerfil = NivelPerfilResourceIT.createEntity(em);
        em.persist(nivelPerfil);
        em.flush();
        requerimiento.setNivelPerfil(nivelPerfil);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long nivelPerfilId = nivelPerfil.getId();

        // Get all the requerimientoList where nivelPerfil equals to nivelPerfilId
        defaultRequerimientoShouldBeFound("nivelPerfilId.equals=" + nivelPerfilId);

        // Get all the requerimientoList where nivelPerfil equals to nivelPerfilId + 1
        defaultRequerimientoShouldNotBeFound("nivelPerfilId.equals=" + (nivelPerfilId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByEstatusReqCanIsEqualToSomething() throws Exception {
        // Initialize the database
        EstReqCerrado estatusReqCan = EstReqCerradoResourceIT.createEntity(em);
        em.persist(estatusReqCan);
        em.flush();
        requerimiento.setEstatusReqCan(estatusReqCan);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long estatusReqCanId = estatusReqCan.getId();

        // Get all the requerimientoList where estatusReqCan equals to estatusReqCanId
        defaultRequerimientoShouldBeFound("estatusReqCanId.equals=" + estatusReqCanId);

        // Get all the requerimientoList where estatusReqCan equals to estatusReqCanId + 1
        defaultRequerimientoShouldNotBeFound("estatusReqCanId.equals=" + (estatusReqCanId + 1));
    }


    @Test
    @Transactional
    public void getAllRequerimientosByTipoPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoPeriodo tipoPeriodo = TipoPeriodoResourceIT.createEntity(em);
        em.persist(tipoPeriodo);
        em.flush();
        requerimiento.setTipoPeriodo(tipoPeriodo);
        requerimientoRepository.saveAndFlush(requerimiento);
        Long tipoPeriodoId = tipoPeriodo.getId();

        // Get all the requerimientoList where tipoPeriodo equals to tipoPeriodoId
        defaultRequerimientoShouldBeFound("tipoPeriodoId.equals=" + tipoPeriodoId);

        // Get all the requerimientoList where tipoPeriodo equals to tipoPeriodoId + 1
        defaultRequerimientoShouldNotBeFound("tipoPeriodoId.equals=" + (tipoPeriodoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRequerimientoShouldBeFound(String filter) throws Exception {
        restRequerimientoMockMvc.perform(get("/api/requerimientos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requerimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaAlda").value(hasItem(DEFAULT_FECHA_ALDA.toString())))
            .andExpect(jsonPath("$.[*].fechaResolucion").value(hasItem(DEFAULT_FECHA_RESOLUCION.toString())))
            .andExpect(jsonPath("$.[*].remplazoDe").value(hasItem(DEFAULT_REMPLAZO_DE)))
            .andExpect(jsonPath("$.[*].vacantesSolicitadas").value(hasItem(DEFAULT_VACANTES_SOLICITADAS)))
            .andExpect(jsonPath("$.[*].proyecto").value(hasItem(DEFAULT_PROYECTO)))
            .andExpect(jsonPath("$.[*].nombreContacto").value(hasItem(DEFAULT_NOMBRE_CONTACTO)))
            .andExpect(jsonPath("$.[*].tarifaSueldoNet").value(hasItem(DEFAULT_TARIFA_SUELDO_NET.doubleValue())))
            .andExpect(jsonPath("$.[*].prestaciones").value(hasItem(DEFAULT_PRESTACIONES)))
            .andExpect(jsonPath("$.[*].duracionAsignacion").value(hasItem(DEFAULT_DURACION_ASIGNACION)))
            .andExpect(jsonPath("$.[*].lugarTrabajo").value(hasItem(DEFAULT_LUGAR_TRABAJO)))
            .andExpect(jsonPath("$.[*].coorLat").value(hasItem(DEFAULT_COOR_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].coorLong").value(hasItem(DEFAULT_COOR_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO)))
            .andExpect(jsonPath("$.[*].informacionExamen").value(hasItem(DEFAULT_INFORMACION_EXAMEN)))
            .andExpect(jsonPath("$.[*].informacionAdicional").value(hasItem(DEFAULT_INFORMACION_ADICIONAL)));

        // Check, that the count call also returns 1
        restRequerimientoMockMvc.perform(get("/api/requerimientos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRequerimientoShouldNotBeFound(String filter) throws Exception {
        restRequerimientoMockMvc.perform(get("/api/requerimientos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRequerimientoMockMvc.perform(get("/api/requerimientos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRequerimiento() throws Exception {
        // Get the requerimiento
        restRequerimientoMockMvc.perform(get("/api/requerimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequerimiento() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        int databaseSizeBeforeUpdate = requerimientoRepository.findAll().size();

        // Update the requerimiento
        Requerimiento updatedRequerimiento = requerimientoRepository.findById(requerimiento.getId()).get();
        // Disconnect from session so that the updates on updatedRequerimiento are not directly saved in db
        em.detach(updatedRequerimiento);
        updatedRequerimiento
            .fechaAlda(UPDATED_FECHA_ALDA)
            .fechaResolucion(UPDATED_FECHA_RESOLUCION)
            .remplazoDe(UPDATED_REMPLAZO_DE)
            .vacantesSolicitadas(UPDATED_VACANTES_SOLICITADAS)
            .proyecto(UPDATED_PROYECTO)
            .nombreContacto(UPDATED_NOMBRE_CONTACTO)
            .tarifaSueldoNet(UPDATED_TARIFA_SUELDO_NET)
            .prestaciones(UPDATED_PRESTACIONES)
            .duracionAsignacion(UPDATED_DURACION_ASIGNACION)
            .lugarTrabajo(UPDATED_LUGAR_TRABAJO)
            .coorLat(UPDATED_COOR_LAT)
            .coorLong(UPDATED_COOR_LONG)
            .horario(UPDATED_HORARIO)
            .informacionExamen(UPDATED_INFORMACION_EXAMEN)
            .informacionAdicional(UPDATED_INFORMACION_ADICIONAL);
        RequerimientoDTO requerimientoDTO = requerimientoMapper.toDto(updatedRequerimiento);

        restRequerimientoMockMvc.perform(put("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoDTO)))
            .andExpect(status().isOk());

        // Validate the Requerimiento in the database
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeUpdate);
        Requerimiento testRequerimiento = requerimientoList.get(requerimientoList.size() - 1);
        assertThat(testRequerimiento.getFechaAlda()).isEqualTo(UPDATED_FECHA_ALDA);
        assertThat(testRequerimiento.getFechaResolucion()).isEqualTo(UPDATED_FECHA_RESOLUCION);
        assertThat(testRequerimiento.getRemplazoDe()).isEqualTo(UPDATED_REMPLAZO_DE);
        assertThat(testRequerimiento.getVacantesSolicitadas()).isEqualTo(UPDATED_VACANTES_SOLICITADAS);
        assertThat(testRequerimiento.getProyecto()).isEqualTo(UPDATED_PROYECTO);
        assertThat(testRequerimiento.getNombreContacto()).isEqualTo(UPDATED_NOMBRE_CONTACTO);
        assertThat(testRequerimiento.getTarifaSueldoNet()).isEqualTo(UPDATED_TARIFA_SUELDO_NET);
        assertThat(testRequerimiento.getPrestaciones()).isEqualTo(UPDATED_PRESTACIONES);
        assertThat(testRequerimiento.getDuracionAsignacion()).isEqualTo(UPDATED_DURACION_ASIGNACION);
        assertThat(testRequerimiento.getLugarTrabajo()).isEqualTo(UPDATED_LUGAR_TRABAJO);
        assertThat(testRequerimiento.getCoorLat()).isEqualTo(UPDATED_COOR_LAT);
        assertThat(testRequerimiento.getCoorLong()).isEqualTo(UPDATED_COOR_LONG);
        assertThat(testRequerimiento.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testRequerimiento.getInformacionExamen()).isEqualTo(UPDATED_INFORMACION_EXAMEN);
        assertThat(testRequerimiento.getInformacionAdicional()).isEqualTo(UPDATED_INFORMACION_ADICIONAL);
    }

    @Test
    @Transactional
    public void updateNonExistingRequerimiento() throws Exception {
        int databaseSizeBeforeUpdate = requerimientoRepository.findAll().size();

        // Create the Requerimiento
        RequerimientoDTO requerimientoDTO = requerimientoMapper.toDto(requerimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequerimientoMockMvc.perform(put("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requerimiento in the database
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRequerimiento() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        int databaseSizeBeforeDelete = requerimientoRepository.findAll().size();

        // Delete the requerimiento
        restRequerimientoMockMvc.perform(delete("/api/requerimientos/{id}", requerimiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requerimiento.class);
        Requerimiento requerimiento1 = new Requerimiento();
        requerimiento1.setId(1L);
        Requerimiento requerimiento2 = new Requerimiento();
        requerimiento2.setId(requerimiento1.getId());
        assertThat(requerimiento1).isEqualTo(requerimiento2);
        requerimiento2.setId(2L);
        assertThat(requerimiento1).isNotEqualTo(requerimiento2);
        requerimiento1.setId(null);
        assertThat(requerimiento1).isNotEqualTo(requerimiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequerimientoDTO.class);
        RequerimientoDTO requerimientoDTO1 = new RequerimientoDTO();
        requerimientoDTO1.setId(1L);
        RequerimientoDTO requerimientoDTO2 = new RequerimientoDTO();
        assertThat(requerimientoDTO1).isNotEqualTo(requerimientoDTO2);
        requerimientoDTO2.setId(requerimientoDTO1.getId());
        assertThat(requerimientoDTO1).isEqualTo(requerimientoDTO2);
        requerimientoDTO2.setId(2L);
        assertThat(requerimientoDTO1).isNotEqualTo(requerimientoDTO2);
        requerimientoDTO1.setId(null);
        assertThat(requerimientoDTO1).isNotEqualTo(requerimientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requerimientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requerimientoMapper.fromId(null)).isNull();
    }
}
