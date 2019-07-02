package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.ReferenciasLaborales;
import com.kode.ts.domain.SkillCandidato;
import com.kode.ts.domain.Tarea;
import com.kode.ts.domain.Bitacora;
import com.kode.ts.domain.TipoPeriodo;
import com.kode.ts.domain.User;
import com.kode.ts.domain.Documento;
import com.kode.ts.domain.Cuenta;
import com.kode.ts.domain.FuenteReclutamiento;
import com.kode.ts.domain.EstatusCandidato;
import com.kode.ts.domain.Perfil;
import com.kode.ts.domain.NivelPerfil;
import com.kode.ts.domain.InstitucionAcademica;
import com.kode.ts.domain.EstatusAcademico;
import com.kode.ts.domain.EsquemaContratacionKode;
import com.kode.ts.domain.EstatusLaboral;
import com.kode.ts.domain.Colonia;
import com.kode.ts.domain.EsqContRec;
import com.kode.ts.domain.FormacionAcademica;
import com.kode.ts.domain.EstCanInactivo;
import com.kode.ts.repository.CandidatoRepository;
import com.kode.ts.service.CandidatoService;
import com.kode.ts.service.dto.CandidatoDTO;
import com.kode.ts.service.mapper.CandidatoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.CandidatoCriteria;
import com.kode.ts.service.CandidatoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.kode.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kode.ts.domain.enumeration.Sexo;
import com.kode.ts.domain.enumeration.EstadoCivil;
/**
 * Integration tests for the {@Link CandidatoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class CandidatoResourceIT {

    private static final Float DEFAULT_ANOS_EXPERIENCIA = 1F;
    private static final Float UPDATED_ANOS_EXPERIENCIA = 2F;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_EDAD = 1;
    private static final Integer UPDATED_EDAD = 2;

    private static final String DEFAULT_EMAIL_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADICIONAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADICIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ASIGNACION = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ASIGNACION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_KODE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_KODE = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CASA = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CASA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_TRABAJO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_TRABAJO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_ADICIONAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_ADICIONAL = "BBBBBBBBBB";

    private static final Float DEFAULT_COOR_LAT = 1F;
    private static final Float UPDATED_COOR_LAT = 2F;

    private static final Float DEFAULT_COOR_LONG = 1F;
    private static final Float UPDATED_COOR_LONG = 2F;

    private static final String DEFAULT_DIR_CODIGO_POSTAL = "AAAAA";
    private static final String UPDATED_DIR_CODIGO_POSTAL = "BBBBB";

    private static final String DEFAULT_DIR_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_DIR_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_NO_EXT = "AAAAAAAAAA";
    private static final String UPDATED_NO_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_NO_INT = "AAAAAAAAAA";
    private static final String UPDATED_NO_INT = "BBBBBBBBBB";

    private static final Float DEFAULT_SALARIO_NETO = 1F;
    private static final Float UPDATED_SALARIO_NETO = 2F;

    private static final Float DEFAULT_COSTO_TOTAL = 1F;
    private static final Float UPDATED_COSTO_TOTAL = 2F;

    private static final Integer DEFAULT_CONTATO_DURACION_MINIMA = 1;
    private static final Integer UPDATED_CONTATO_DURACION_MINIMA = 2;

    private static final LocalDate DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO = 1;
    private static final Integer UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO = 2;

    private static final LocalDate DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO = "AAAAAAAAAA";
    private static final String UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO = "BBBBBBBBBB";

    private static final String DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR = "AAAAAAAAAA";
    private static final String UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANTECEDENTE_SALARIO_NETO = 1;
    private static final Integer UPDATED_ANTECEDENTE_SALARIO_NETO = 2;

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORCENTAJE_INGLES = 1;
    private static final Integer UPDATED_PORCENTAJE_INGLES = 2;

    private static final String DEFAULT_CURP = "AAAAAAAAAA";
    private static final String UPDATED_CURP = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final String DEFAULT_NSS = "AAAAAAAAAA";
    private static final String UPDATED_NSS = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMENINO;

    private static final EstadoCivil DEFAULT_ESTADO_CIVIL = EstadoCivil.SOLTERO;
    private static final EstadoCivil UPDATED_ESTADO_CIVIL = EstadoCivil.CASADO;

    private static final Instant DEFAULT_FECHA_ALTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_ULTIMO_SEGUIMIENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ULTIMO_SEGUIMIENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_FOTO = "BBBBBBBBBB";

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Mock
    private CandidatoRepository candidatoRepositoryMock;

    @Autowired
    private CandidatoMapper candidatoMapper;

    @Mock
    private CandidatoService candidatoServiceMock;

    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private CandidatoQueryService candidatoQueryService;

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

    private MockMvc restCandidatoMockMvc;

    private Candidato candidato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidatoResource candidatoResource = new CandidatoResource(candidatoService, candidatoQueryService);
        this.restCandidatoMockMvc = MockMvcBuilders.standaloneSetup(candidatoResource)
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
    public static Candidato createEntity(EntityManager em) {
        Candidato candidato = new Candidato()
            .anosExperiencia(DEFAULT_ANOS_EXPERIENCIA)
            .nombre(DEFAULT_NOMBRE)
            .apellidoPaterno(DEFAULT_APELLIDO_PATERNO)
            .apellidoMaterno(DEFAULT_APELLIDO_MATERNO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .edad(DEFAULT_EDAD)
            .emailPrincipal(DEFAULT_EMAIL_PRINCIPAL)
            .emailAdicional(DEFAULT_EMAIL_ADICIONAL)
            .emailAsignacion(DEFAULT_EMAIL_ASIGNACION)
            .emailKode(DEFAULT_EMAIL_KODE)
            .web(DEFAULT_WEB)
            .telefonoCasa(DEFAULT_TELEFONO_CASA)
            .telefonoTrabajo(DEFAULT_TELEFONO_TRABAJO)
            .telefonoCelular(DEFAULT_TELEFONO_CELULAR)
            .telefonoAdicional(DEFAULT_TELEFONO_ADICIONAL)
            .coorLat(DEFAULT_COOR_LAT)
            .coorLong(DEFAULT_COOR_LONG)
            .dirCodigoPostal(DEFAULT_DIR_CODIGO_POSTAL)
            .dirCalle(DEFAULT_DIR_CALLE)
            .noExt(DEFAULT_NO_EXT)
            .noInt(DEFAULT_NO_INT)
            .salarioNeto(DEFAULT_SALARIO_NETO)
            .costoTotal(DEFAULT_COSTO_TOTAL)
            .contatoDuracionMinima(DEFAULT_CONTATO_DURACION_MINIMA)
            .disponibilidadEntrevistaFecha(DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA)
            .disponibilidadEntrevistaPeriodo(DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO)
            .disponibilidadAsignacionFecha(DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA)
            .disponibilidadAsignacionPeriodo(DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO)
            .antecedenteUltimoEmpleador(DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR)
            .antecedenteSalarioNeto(DEFAULT_ANTECEDENTE_SALARIO_NETO)
            .notas(DEFAULT_NOTAS)
            .porcentajeIngles(DEFAULT_PORCENTAJE_INGLES)
            .curp(DEFAULT_CURP)
            .rfc(DEFAULT_RFC)
            .nss(DEFAULT_NSS)
            .sexo(DEFAULT_SEXO)
            .estadoCivil(DEFAULT_ESTADO_CIVIL)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaUltimoSeguimiento(DEFAULT_FECHA_ULTIMO_SEGUIMIENTO)
            .foto(DEFAULT_FOTO);
        return candidato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidato createUpdatedEntity(EntityManager em) {
        Candidato candidato = new Candidato()
            .anosExperiencia(UPDATED_ANOS_EXPERIENCIA)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .edad(UPDATED_EDAD)
            .emailPrincipal(UPDATED_EMAIL_PRINCIPAL)
            .emailAdicional(UPDATED_EMAIL_ADICIONAL)
            .emailAsignacion(UPDATED_EMAIL_ASIGNACION)
            .emailKode(UPDATED_EMAIL_KODE)
            .web(UPDATED_WEB)
            .telefonoCasa(UPDATED_TELEFONO_CASA)
            .telefonoTrabajo(UPDATED_TELEFONO_TRABAJO)
            .telefonoCelular(UPDATED_TELEFONO_CELULAR)
            .telefonoAdicional(UPDATED_TELEFONO_ADICIONAL)
            .coorLat(UPDATED_COOR_LAT)
            .coorLong(UPDATED_COOR_LONG)
            .dirCodigoPostal(UPDATED_DIR_CODIGO_POSTAL)
            .dirCalle(UPDATED_DIR_CALLE)
            .noExt(UPDATED_NO_EXT)
            .noInt(UPDATED_NO_INT)
            .salarioNeto(UPDATED_SALARIO_NETO)
            .costoTotal(UPDATED_COSTO_TOTAL)
            .contatoDuracionMinima(UPDATED_CONTATO_DURACION_MINIMA)
            .disponibilidadEntrevistaFecha(UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA)
            .disponibilidadEntrevistaPeriodo(UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO)
            .disponibilidadAsignacionFecha(UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA)
            .disponibilidadAsignacionPeriodo(UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO)
            .antecedenteUltimoEmpleador(UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR)
            .antecedenteSalarioNeto(UPDATED_ANTECEDENTE_SALARIO_NETO)
            .notas(UPDATED_NOTAS)
            .porcentajeIngles(UPDATED_PORCENTAJE_INGLES)
            .curp(UPDATED_CURP)
            .rfc(UPDATED_RFC)
            .nss(UPDATED_NSS)
            .sexo(UPDATED_SEXO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaUltimoSeguimiento(UPDATED_FECHA_ULTIMO_SEGUIMIENTO)
            .foto(UPDATED_FOTO);
        return candidato;
    }

    @BeforeEach
    public void initTest() {
        candidato = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidato() throws Exception {
        int databaseSizeBeforeCreate = candidatoRepository.findAll().size();

        // Create the Candidato
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);
        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isCreated());

        // Validate the Candidato in the database
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeCreate + 1);
        Candidato testCandidato = candidatoList.get(candidatoList.size() - 1);
        assertThat(testCandidato.getAnosExperiencia()).isEqualTo(DEFAULT_ANOS_EXPERIENCIA);
        assertThat(testCandidato.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCandidato.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testCandidato.getApellidoMaterno()).isEqualTo(DEFAULT_APELLIDO_MATERNO);
        assertThat(testCandidato.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testCandidato.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testCandidato.getEmailPrincipal()).isEqualTo(DEFAULT_EMAIL_PRINCIPAL);
        assertThat(testCandidato.getEmailAdicional()).isEqualTo(DEFAULT_EMAIL_ADICIONAL);
        assertThat(testCandidato.getEmailAsignacion()).isEqualTo(DEFAULT_EMAIL_ASIGNACION);
        assertThat(testCandidato.getEmailKode()).isEqualTo(DEFAULT_EMAIL_KODE);
        assertThat(testCandidato.getWeb()).isEqualTo(DEFAULT_WEB);
        assertThat(testCandidato.getTelefonoCasa()).isEqualTo(DEFAULT_TELEFONO_CASA);
        assertThat(testCandidato.getTelefonoTrabajo()).isEqualTo(DEFAULT_TELEFONO_TRABAJO);
        assertThat(testCandidato.getTelefonoCelular()).isEqualTo(DEFAULT_TELEFONO_CELULAR);
        assertThat(testCandidato.getTelefonoAdicional()).isEqualTo(DEFAULT_TELEFONO_ADICIONAL);
        assertThat(testCandidato.getCoorLat()).isEqualTo(DEFAULT_COOR_LAT);
        assertThat(testCandidato.getCoorLong()).isEqualTo(DEFAULT_COOR_LONG);
        assertThat(testCandidato.getDirCodigoPostal()).isEqualTo(DEFAULT_DIR_CODIGO_POSTAL);
        assertThat(testCandidato.getDirCalle()).isEqualTo(DEFAULT_DIR_CALLE);
        assertThat(testCandidato.getNoExt()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testCandidato.getNoInt()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testCandidato.getSalarioNeto()).isEqualTo(DEFAULT_SALARIO_NETO);
        assertThat(testCandidato.getCostoTotal()).isEqualTo(DEFAULT_COSTO_TOTAL);
        assertThat(testCandidato.getContatoDuracionMinima()).isEqualTo(DEFAULT_CONTATO_DURACION_MINIMA);
        assertThat(testCandidato.getDisponibilidadEntrevistaFecha()).isEqualTo(DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA);
        assertThat(testCandidato.getDisponibilidadEntrevistaPeriodo()).isEqualTo(DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO);
        assertThat(testCandidato.getDisponibilidadAsignacionFecha()).isEqualTo(DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA);
        assertThat(testCandidato.getDisponibilidadAsignacionPeriodo()).isEqualTo(DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO);
        assertThat(testCandidato.getAntecedenteUltimoEmpleador()).isEqualTo(DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR);
        assertThat(testCandidato.getAntecedenteSalarioNeto()).isEqualTo(DEFAULT_ANTECEDENTE_SALARIO_NETO);
        assertThat(testCandidato.getNotas()).isEqualTo(DEFAULT_NOTAS);
        assertThat(testCandidato.getPorcentajeIngles()).isEqualTo(DEFAULT_PORCENTAJE_INGLES);
        assertThat(testCandidato.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testCandidato.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCandidato.getNss()).isEqualTo(DEFAULT_NSS);
        assertThat(testCandidato.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testCandidato.getEstadoCivil()).isEqualTo(DEFAULT_ESTADO_CIVIL);
        assertThat(testCandidato.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testCandidato.getFechaUltimoSeguimiento()).isEqualTo(DEFAULT_FECHA_ULTIMO_SEGUIMIENTO);
        assertThat(testCandidato.getFoto()).isEqualTo(DEFAULT_FOTO);
    }

    @Test
    @Transactional
    public void createCandidatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatoRepository.findAll().size();

        // Create the Candidato with an existing ID
        candidato.setId(1L);
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidato in the database
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatoRepository.findAll().size();
        // set the field null
        candidato.setNombre(null);

        // Create the Candidato, which fails.
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoPaternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatoRepository.findAll().size();
        // set the field null
        candidato.setApellidoPaterno(null);

        // Create the Candidato, which fails.
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailPrincipalIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatoRepository.findAll().size();
        // set the field null
        candidato.setEmailPrincipal(null);

        // Create the Candidato, which fails.
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        restCandidatoMockMvc.perform(post("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCandidatoes() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList
        restCandidatoMockMvc.perform(get("/api/candidatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidato.getId().intValue())))
            .andExpect(jsonPath("$.[*].anosExperiencia").value(hasItem(DEFAULT_ANOS_EXPERIENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO.toString())))
            .andExpect(jsonPath("$.[*].apellidoMaterno").value(hasItem(DEFAULT_APELLIDO_MATERNO.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].emailPrincipal").value(hasItem(DEFAULT_EMAIL_PRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].emailAdicional").value(hasItem(DEFAULT_EMAIL_ADICIONAL.toString())))
            .andExpect(jsonPath("$.[*].emailAsignacion").value(hasItem(DEFAULT_EMAIL_ASIGNACION.toString())))
            .andExpect(jsonPath("$.[*].emailKode").value(hasItem(DEFAULT_EMAIL_KODE.toString())))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())))
            .andExpect(jsonPath("$.[*].telefonoCasa").value(hasItem(DEFAULT_TELEFONO_CASA.toString())))
            .andExpect(jsonPath("$.[*].telefonoTrabajo").value(hasItem(DEFAULT_TELEFONO_TRABAJO.toString())))
            .andExpect(jsonPath("$.[*].telefonoCelular").value(hasItem(DEFAULT_TELEFONO_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].telefonoAdicional").value(hasItem(DEFAULT_TELEFONO_ADICIONAL.toString())))
            .andExpect(jsonPath("$.[*].coorLat").value(hasItem(DEFAULT_COOR_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].coorLong").value(hasItem(DEFAULT_COOR_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].dirCodigoPostal").value(hasItem(DEFAULT_DIR_CODIGO_POSTAL.toString())))
            .andExpect(jsonPath("$.[*].dirCalle").value(hasItem(DEFAULT_DIR_CALLE.toString())))
            .andExpect(jsonPath("$.[*].noExt").value(hasItem(DEFAULT_NO_EXT.toString())))
            .andExpect(jsonPath("$.[*].noInt").value(hasItem(DEFAULT_NO_INT.toString())))
            .andExpect(jsonPath("$.[*].salarioNeto").value(hasItem(DEFAULT_SALARIO_NETO.doubleValue())))
            .andExpect(jsonPath("$.[*].costoTotal").value(hasItem(DEFAULT_COSTO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].contatoDuracionMinima").value(hasItem(DEFAULT_CONTATO_DURACION_MINIMA)))
            .andExpect(jsonPath("$.[*].disponibilidadEntrevistaFecha").value(hasItem(DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA.toString())))
            .andExpect(jsonPath("$.[*].disponibilidadEntrevistaPeriodo").value(hasItem(DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO)))
            .andExpect(jsonPath("$.[*].disponibilidadAsignacionFecha").value(hasItem(DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA.toString())))
            .andExpect(jsonPath("$.[*].disponibilidadAsignacionPeriodo").value(hasItem(DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO.toString())))
            .andExpect(jsonPath("$.[*].antecedenteUltimoEmpleador").value(hasItem(DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR.toString())))
            .andExpect(jsonPath("$.[*].antecedenteSalarioNeto").value(hasItem(DEFAULT_ANTECEDENTE_SALARIO_NETO)))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())))
            .andExpect(jsonPath("$.[*].porcentajeIngles").value(hasItem(DEFAULT_PORCENTAJE_INGLES)))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
            .andExpect(jsonPath("$.[*].nss").value(hasItem(DEFAULT_NSS.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaUltimoSeguimiento").value(hasItem(DEFAULT_FECHA_ULTIMO_SEGUIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCandidatoesWithEagerRelationshipsIsEnabled() throws Exception {
        CandidatoResource candidatoResource = new CandidatoResource(candidatoServiceMock, candidatoQueryService);
        when(candidatoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCandidatoMockMvc = MockMvcBuilders.standaloneSetup(candidatoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCandidatoMockMvc.perform(get("/api/candidatoes?eagerload=true"))
        .andExpect(status().isOk());

        verify(candidatoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCandidatoesWithEagerRelationshipsIsNotEnabled() throws Exception {
        CandidatoResource candidatoResource = new CandidatoResource(candidatoServiceMock, candidatoQueryService);
            when(candidatoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCandidatoMockMvc = MockMvcBuilders.standaloneSetup(candidatoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCandidatoMockMvc.perform(get("/api/candidatoes?eagerload=true"))
        .andExpect(status().isOk());

            verify(candidatoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCandidato() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get the candidato
        restCandidatoMockMvc.perform(get("/api/candidatoes/{id}", candidato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidato.getId().intValue()))
            .andExpect(jsonPath("$.anosExperiencia").value(DEFAULT_ANOS_EXPERIENCIA.doubleValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellidoPaterno").value(DEFAULT_APELLIDO_PATERNO.toString()))
            .andExpect(jsonPath("$.apellidoMaterno").value(DEFAULT_APELLIDO_MATERNO.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.emailPrincipal").value(DEFAULT_EMAIL_PRINCIPAL.toString()))
            .andExpect(jsonPath("$.emailAdicional").value(DEFAULT_EMAIL_ADICIONAL.toString()))
            .andExpect(jsonPath("$.emailAsignacion").value(DEFAULT_EMAIL_ASIGNACION.toString()))
            .andExpect(jsonPath("$.emailKode").value(DEFAULT_EMAIL_KODE.toString()))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()))
            .andExpect(jsonPath("$.telefonoCasa").value(DEFAULT_TELEFONO_CASA.toString()))
            .andExpect(jsonPath("$.telefonoTrabajo").value(DEFAULT_TELEFONO_TRABAJO.toString()))
            .andExpect(jsonPath("$.telefonoCelular").value(DEFAULT_TELEFONO_CELULAR.toString()))
            .andExpect(jsonPath("$.telefonoAdicional").value(DEFAULT_TELEFONO_ADICIONAL.toString()))
            .andExpect(jsonPath("$.coorLat").value(DEFAULT_COOR_LAT.doubleValue()))
            .andExpect(jsonPath("$.coorLong").value(DEFAULT_COOR_LONG.doubleValue()))
            .andExpect(jsonPath("$.dirCodigoPostal").value(DEFAULT_DIR_CODIGO_POSTAL.toString()))
            .andExpect(jsonPath("$.dirCalle").value(DEFAULT_DIR_CALLE.toString()))
            .andExpect(jsonPath("$.noExt").value(DEFAULT_NO_EXT.toString()))
            .andExpect(jsonPath("$.noInt").value(DEFAULT_NO_INT.toString()))
            .andExpect(jsonPath("$.salarioNeto").value(DEFAULT_SALARIO_NETO.doubleValue()))
            .andExpect(jsonPath("$.costoTotal").value(DEFAULT_COSTO_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.contatoDuracionMinima").value(DEFAULT_CONTATO_DURACION_MINIMA))
            .andExpect(jsonPath("$.disponibilidadEntrevistaFecha").value(DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA.toString()))
            .andExpect(jsonPath("$.disponibilidadEntrevistaPeriodo").value(DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO))
            .andExpect(jsonPath("$.disponibilidadAsignacionFecha").value(DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA.toString()))
            .andExpect(jsonPath("$.disponibilidadAsignacionPeriodo").value(DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO.toString()))
            .andExpect(jsonPath("$.antecedenteUltimoEmpleador").value(DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR.toString()))
            .andExpect(jsonPath("$.antecedenteSalarioNeto").value(DEFAULT_ANTECEDENTE_SALARIO_NETO))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()))
            .andExpect(jsonPath("$.porcentajeIngles").value(DEFAULT_PORCENTAJE_INGLES))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.nss").value(DEFAULT_NSS.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaUltimoSeguimiento").value(DEFAULT_FECHA_ULTIMO_SEGUIMIENTO.toString()))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO.toString()));
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAnosExperienciaIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where anosExperiencia equals to DEFAULT_ANOS_EXPERIENCIA
        defaultCandidatoShouldBeFound("anosExperiencia.equals=" + DEFAULT_ANOS_EXPERIENCIA);

        // Get all the candidatoList where anosExperiencia equals to UPDATED_ANOS_EXPERIENCIA
        defaultCandidatoShouldNotBeFound("anosExperiencia.equals=" + UPDATED_ANOS_EXPERIENCIA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAnosExperienciaIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where anosExperiencia in DEFAULT_ANOS_EXPERIENCIA or UPDATED_ANOS_EXPERIENCIA
        defaultCandidatoShouldBeFound("anosExperiencia.in=" + DEFAULT_ANOS_EXPERIENCIA + "," + UPDATED_ANOS_EXPERIENCIA);

        // Get all the candidatoList where anosExperiencia equals to UPDATED_ANOS_EXPERIENCIA
        defaultCandidatoShouldNotBeFound("anosExperiencia.in=" + UPDATED_ANOS_EXPERIENCIA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAnosExperienciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where anosExperiencia is not null
        defaultCandidatoShouldBeFound("anosExperiencia.specified=true");

        // Get all the candidatoList where anosExperiencia is null
        defaultCandidatoShouldNotBeFound("anosExperiencia.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where nombre equals to DEFAULT_NOMBRE
        defaultCandidatoShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the candidatoList where nombre equals to UPDATED_NOMBRE
        defaultCandidatoShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCandidatoShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the candidatoList where nombre equals to UPDATED_NOMBRE
        defaultCandidatoShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where nombre is not null
        defaultCandidatoShouldBeFound("nombre.specified=true");

        // Get all the candidatoList where nombre is null
        defaultCandidatoShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByApellidoPaternoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where apellidoPaterno equals to DEFAULT_APELLIDO_PATERNO
        defaultCandidatoShouldBeFound("apellidoPaterno.equals=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the candidatoList where apellidoPaterno equals to UPDATED_APELLIDO_PATERNO
        defaultCandidatoShouldNotBeFound("apellidoPaterno.equals=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByApellidoPaternoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where apellidoPaterno in DEFAULT_APELLIDO_PATERNO or UPDATED_APELLIDO_PATERNO
        defaultCandidatoShouldBeFound("apellidoPaterno.in=" + DEFAULT_APELLIDO_PATERNO + "," + UPDATED_APELLIDO_PATERNO);

        // Get all the candidatoList where apellidoPaterno equals to UPDATED_APELLIDO_PATERNO
        defaultCandidatoShouldNotBeFound("apellidoPaterno.in=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByApellidoPaternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where apellidoPaterno is not null
        defaultCandidatoShouldBeFound("apellidoPaterno.specified=true");

        // Get all the candidatoList where apellidoPaterno is null
        defaultCandidatoShouldNotBeFound("apellidoPaterno.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByApellidoMaternoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where apellidoMaterno equals to DEFAULT_APELLIDO_MATERNO
        defaultCandidatoShouldBeFound("apellidoMaterno.equals=" + DEFAULT_APELLIDO_MATERNO);

        // Get all the candidatoList where apellidoMaterno equals to UPDATED_APELLIDO_MATERNO
        defaultCandidatoShouldNotBeFound("apellidoMaterno.equals=" + UPDATED_APELLIDO_MATERNO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByApellidoMaternoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where apellidoMaterno in DEFAULT_APELLIDO_MATERNO or UPDATED_APELLIDO_MATERNO
        defaultCandidatoShouldBeFound("apellidoMaterno.in=" + DEFAULT_APELLIDO_MATERNO + "," + UPDATED_APELLIDO_MATERNO);

        // Get all the candidatoList where apellidoMaterno equals to UPDATED_APELLIDO_MATERNO
        defaultCandidatoShouldNotBeFound("apellidoMaterno.in=" + UPDATED_APELLIDO_MATERNO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByApellidoMaternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where apellidoMaterno is not null
        defaultCandidatoShouldBeFound("apellidoMaterno.specified=true");

        // Get all the candidatoList where apellidoMaterno is null
        defaultCandidatoShouldNotBeFound("apellidoMaterno.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaNacimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaNacimiento equals to DEFAULT_FECHA_NACIMIENTO
        defaultCandidatoShouldBeFound("fechaNacimiento.equals=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the candidatoList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultCandidatoShouldNotBeFound("fechaNacimiento.equals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaNacimientoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaNacimiento in DEFAULT_FECHA_NACIMIENTO or UPDATED_FECHA_NACIMIENTO
        defaultCandidatoShouldBeFound("fechaNacimiento.in=" + DEFAULT_FECHA_NACIMIENTO + "," + UPDATED_FECHA_NACIMIENTO);

        // Get all the candidatoList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultCandidatoShouldNotBeFound("fechaNacimiento.in=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaNacimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaNacimiento is not null
        defaultCandidatoShouldBeFound("fechaNacimiento.specified=true");

        // Get all the candidatoList where fechaNacimiento is null
        defaultCandidatoShouldNotBeFound("fechaNacimiento.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaNacimientoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaNacimiento greater than or equals to DEFAULT_FECHA_NACIMIENTO
        defaultCandidatoShouldBeFound("fechaNacimiento.greaterOrEqualThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the candidatoList where fechaNacimiento greater than or equals to UPDATED_FECHA_NACIMIENTO
        defaultCandidatoShouldNotBeFound("fechaNacimiento.greaterOrEqualThan=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaNacimientoIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaNacimiento less than or equals to DEFAULT_FECHA_NACIMIENTO
        defaultCandidatoShouldNotBeFound("fechaNacimiento.lessThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the candidatoList where fechaNacimiento less than or equals to UPDATED_FECHA_NACIMIENTO
        defaultCandidatoShouldBeFound("fechaNacimiento.lessThan=" + UPDATED_FECHA_NACIMIENTO);
    }


    @Test
    @Transactional
    public void getAllCandidatoesByEdadIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where edad equals to DEFAULT_EDAD
        defaultCandidatoShouldBeFound("edad.equals=" + DEFAULT_EDAD);

        // Get all the candidatoList where edad equals to UPDATED_EDAD
        defaultCandidatoShouldNotBeFound("edad.equals=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEdadIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where edad in DEFAULT_EDAD or UPDATED_EDAD
        defaultCandidatoShouldBeFound("edad.in=" + DEFAULT_EDAD + "," + UPDATED_EDAD);

        // Get all the candidatoList where edad equals to UPDATED_EDAD
        defaultCandidatoShouldNotBeFound("edad.in=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEdadIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where edad is not null
        defaultCandidatoShouldBeFound("edad.specified=true");

        // Get all the candidatoList where edad is null
        defaultCandidatoShouldNotBeFound("edad.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEdadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where edad greater than or equals to DEFAULT_EDAD
        defaultCandidatoShouldBeFound("edad.greaterOrEqualThan=" + DEFAULT_EDAD);

        // Get all the candidatoList where edad greater than or equals to UPDATED_EDAD
        defaultCandidatoShouldNotBeFound("edad.greaterOrEqualThan=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEdadIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where edad less than or equals to DEFAULT_EDAD
        defaultCandidatoShouldNotBeFound("edad.lessThan=" + DEFAULT_EDAD);

        // Get all the candidatoList where edad less than or equals to UPDATED_EDAD
        defaultCandidatoShouldBeFound("edad.lessThan=" + UPDATED_EDAD);
    }


    @Test
    @Transactional
    public void getAllCandidatoesByEmailPrincipalIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailPrincipal equals to DEFAULT_EMAIL_PRINCIPAL
        defaultCandidatoShouldBeFound("emailPrincipal.equals=" + DEFAULT_EMAIL_PRINCIPAL);

        // Get all the candidatoList where emailPrincipal equals to UPDATED_EMAIL_PRINCIPAL
        defaultCandidatoShouldNotBeFound("emailPrincipal.equals=" + UPDATED_EMAIL_PRINCIPAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailPrincipalIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailPrincipal in DEFAULT_EMAIL_PRINCIPAL or UPDATED_EMAIL_PRINCIPAL
        defaultCandidatoShouldBeFound("emailPrincipal.in=" + DEFAULT_EMAIL_PRINCIPAL + "," + UPDATED_EMAIL_PRINCIPAL);

        // Get all the candidatoList where emailPrincipal equals to UPDATED_EMAIL_PRINCIPAL
        defaultCandidatoShouldNotBeFound("emailPrincipal.in=" + UPDATED_EMAIL_PRINCIPAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailPrincipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailPrincipal is not null
        defaultCandidatoShouldBeFound("emailPrincipal.specified=true");

        // Get all the candidatoList where emailPrincipal is null
        defaultCandidatoShouldNotBeFound("emailPrincipal.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailAdicionalIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailAdicional equals to DEFAULT_EMAIL_ADICIONAL
        defaultCandidatoShouldBeFound("emailAdicional.equals=" + DEFAULT_EMAIL_ADICIONAL);

        // Get all the candidatoList where emailAdicional equals to UPDATED_EMAIL_ADICIONAL
        defaultCandidatoShouldNotBeFound("emailAdicional.equals=" + UPDATED_EMAIL_ADICIONAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailAdicionalIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailAdicional in DEFAULT_EMAIL_ADICIONAL or UPDATED_EMAIL_ADICIONAL
        defaultCandidatoShouldBeFound("emailAdicional.in=" + DEFAULT_EMAIL_ADICIONAL + "," + UPDATED_EMAIL_ADICIONAL);

        // Get all the candidatoList where emailAdicional equals to UPDATED_EMAIL_ADICIONAL
        defaultCandidatoShouldNotBeFound("emailAdicional.in=" + UPDATED_EMAIL_ADICIONAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailAdicionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailAdicional is not null
        defaultCandidatoShouldBeFound("emailAdicional.specified=true");

        // Get all the candidatoList where emailAdicional is null
        defaultCandidatoShouldNotBeFound("emailAdicional.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailAsignacionIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailAsignacion equals to DEFAULT_EMAIL_ASIGNACION
        defaultCandidatoShouldBeFound("emailAsignacion.equals=" + DEFAULT_EMAIL_ASIGNACION);

        // Get all the candidatoList where emailAsignacion equals to UPDATED_EMAIL_ASIGNACION
        defaultCandidatoShouldNotBeFound("emailAsignacion.equals=" + UPDATED_EMAIL_ASIGNACION);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailAsignacionIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailAsignacion in DEFAULT_EMAIL_ASIGNACION or UPDATED_EMAIL_ASIGNACION
        defaultCandidatoShouldBeFound("emailAsignacion.in=" + DEFAULT_EMAIL_ASIGNACION + "," + UPDATED_EMAIL_ASIGNACION);

        // Get all the candidatoList where emailAsignacion equals to UPDATED_EMAIL_ASIGNACION
        defaultCandidatoShouldNotBeFound("emailAsignacion.in=" + UPDATED_EMAIL_ASIGNACION);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailAsignacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailAsignacion is not null
        defaultCandidatoShouldBeFound("emailAsignacion.specified=true");

        // Get all the candidatoList where emailAsignacion is null
        defaultCandidatoShouldNotBeFound("emailAsignacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailKodeIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailKode equals to DEFAULT_EMAIL_KODE
        defaultCandidatoShouldBeFound("emailKode.equals=" + DEFAULT_EMAIL_KODE);

        // Get all the candidatoList where emailKode equals to UPDATED_EMAIL_KODE
        defaultCandidatoShouldNotBeFound("emailKode.equals=" + UPDATED_EMAIL_KODE);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailKodeIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailKode in DEFAULT_EMAIL_KODE or UPDATED_EMAIL_KODE
        defaultCandidatoShouldBeFound("emailKode.in=" + DEFAULT_EMAIL_KODE + "," + UPDATED_EMAIL_KODE);

        // Get all the candidatoList where emailKode equals to UPDATED_EMAIL_KODE
        defaultCandidatoShouldNotBeFound("emailKode.in=" + UPDATED_EMAIL_KODE);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEmailKodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where emailKode is not null
        defaultCandidatoShouldBeFound("emailKode.specified=true");

        // Get all the candidatoList where emailKode is null
        defaultCandidatoShouldNotBeFound("emailKode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByWebIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where web equals to DEFAULT_WEB
        defaultCandidatoShouldBeFound("web.equals=" + DEFAULT_WEB);

        // Get all the candidatoList where web equals to UPDATED_WEB
        defaultCandidatoShouldNotBeFound("web.equals=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByWebIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where web in DEFAULT_WEB or UPDATED_WEB
        defaultCandidatoShouldBeFound("web.in=" + DEFAULT_WEB + "," + UPDATED_WEB);

        // Get all the candidatoList where web equals to UPDATED_WEB
        defaultCandidatoShouldNotBeFound("web.in=" + UPDATED_WEB);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByWebIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where web is not null
        defaultCandidatoShouldBeFound("web.specified=true");

        // Get all the candidatoList where web is null
        defaultCandidatoShouldNotBeFound("web.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoCasaIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoCasa equals to DEFAULT_TELEFONO_CASA
        defaultCandidatoShouldBeFound("telefonoCasa.equals=" + DEFAULT_TELEFONO_CASA);

        // Get all the candidatoList where telefonoCasa equals to UPDATED_TELEFONO_CASA
        defaultCandidatoShouldNotBeFound("telefonoCasa.equals=" + UPDATED_TELEFONO_CASA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoCasaIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoCasa in DEFAULT_TELEFONO_CASA or UPDATED_TELEFONO_CASA
        defaultCandidatoShouldBeFound("telefonoCasa.in=" + DEFAULT_TELEFONO_CASA + "," + UPDATED_TELEFONO_CASA);

        // Get all the candidatoList where telefonoCasa equals to UPDATED_TELEFONO_CASA
        defaultCandidatoShouldNotBeFound("telefonoCasa.in=" + UPDATED_TELEFONO_CASA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoCasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoCasa is not null
        defaultCandidatoShouldBeFound("telefonoCasa.specified=true");

        // Get all the candidatoList where telefonoCasa is null
        defaultCandidatoShouldNotBeFound("telefonoCasa.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoTrabajoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoTrabajo equals to DEFAULT_TELEFONO_TRABAJO
        defaultCandidatoShouldBeFound("telefonoTrabajo.equals=" + DEFAULT_TELEFONO_TRABAJO);

        // Get all the candidatoList where telefonoTrabajo equals to UPDATED_TELEFONO_TRABAJO
        defaultCandidatoShouldNotBeFound("telefonoTrabajo.equals=" + UPDATED_TELEFONO_TRABAJO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoTrabajoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoTrabajo in DEFAULT_TELEFONO_TRABAJO or UPDATED_TELEFONO_TRABAJO
        defaultCandidatoShouldBeFound("telefonoTrabajo.in=" + DEFAULT_TELEFONO_TRABAJO + "," + UPDATED_TELEFONO_TRABAJO);

        // Get all the candidatoList where telefonoTrabajo equals to UPDATED_TELEFONO_TRABAJO
        defaultCandidatoShouldNotBeFound("telefonoTrabajo.in=" + UPDATED_TELEFONO_TRABAJO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoTrabajoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoTrabajo is not null
        defaultCandidatoShouldBeFound("telefonoTrabajo.specified=true");

        // Get all the candidatoList where telefonoTrabajo is null
        defaultCandidatoShouldNotBeFound("telefonoTrabajo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoCelularIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoCelular equals to DEFAULT_TELEFONO_CELULAR
        defaultCandidatoShouldBeFound("telefonoCelular.equals=" + DEFAULT_TELEFONO_CELULAR);

        // Get all the candidatoList where telefonoCelular equals to UPDATED_TELEFONO_CELULAR
        defaultCandidatoShouldNotBeFound("telefonoCelular.equals=" + UPDATED_TELEFONO_CELULAR);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoCelularIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoCelular in DEFAULT_TELEFONO_CELULAR or UPDATED_TELEFONO_CELULAR
        defaultCandidatoShouldBeFound("telefonoCelular.in=" + DEFAULT_TELEFONO_CELULAR + "," + UPDATED_TELEFONO_CELULAR);

        // Get all the candidatoList where telefonoCelular equals to UPDATED_TELEFONO_CELULAR
        defaultCandidatoShouldNotBeFound("telefonoCelular.in=" + UPDATED_TELEFONO_CELULAR);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoCelularIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoCelular is not null
        defaultCandidatoShouldBeFound("telefonoCelular.specified=true");

        // Get all the candidatoList where telefonoCelular is null
        defaultCandidatoShouldNotBeFound("telefonoCelular.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoAdicionalIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoAdicional equals to DEFAULT_TELEFONO_ADICIONAL
        defaultCandidatoShouldBeFound("telefonoAdicional.equals=" + DEFAULT_TELEFONO_ADICIONAL);

        // Get all the candidatoList where telefonoAdicional equals to UPDATED_TELEFONO_ADICIONAL
        defaultCandidatoShouldNotBeFound("telefonoAdicional.equals=" + UPDATED_TELEFONO_ADICIONAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoAdicionalIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoAdicional in DEFAULT_TELEFONO_ADICIONAL or UPDATED_TELEFONO_ADICIONAL
        defaultCandidatoShouldBeFound("telefonoAdicional.in=" + DEFAULT_TELEFONO_ADICIONAL + "," + UPDATED_TELEFONO_ADICIONAL);

        // Get all the candidatoList where telefonoAdicional equals to UPDATED_TELEFONO_ADICIONAL
        defaultCandidatoShouldNotBeFound("telefonoAdicional.in=" + UPDATED_TELEFONO_ADICIONAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByTelefonoAdicionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where telefonoAdicional is not null
        defaultCandidatoShouldBeFound("telefonoAdicional.specified=true");

        // Get all the candidatoList where telefonoAdicional is null
        defaultCandidatoShouldNotBeFound("telefonoAdicional.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCoorLatIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where coorLat equals to DEFAULT_COOR_LAT
        defaultCandidatoShouldBeFound("coorLat.equals=" + DEFAULT_COOR_LAT);

        // Get all the candidatoList where coorLat equals to UPDATED_COOR_LAT
        defaultCandidatoShouldNotBeFound("coorLat.equals=" + UPDATED_COOR_LAT);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCoorLatIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where coorLat in DEFAULT_COOR_LAT or UPDATED_COOR_LAT
        defaultCandidatoShouldBeFound("coorLat.in=" + DEFAULT_COOR_LAT + "," + UPDATED_COOR_LAT);

        // Get all the candidatoList where coorLat equals to UPDATED_COOR_LAT
        defaultCandidatoShouldNotBeFound("coorLat.in=" + UPDATED_COOR_LAT);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCoorLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where coorLat is not null
        defaultCandidatoShouldBeFound("coorLat.specified=true");

        // Get all the candidatoList where coorLat is null
        defaultCandidatoShouldNotBeFound("coorLat.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCoorLongIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where coorLong equals to DEFAULT_COOR_LONG
        defaultCandidatoShouldBeFound("coorLong.equals=" + DEFAULT_COOR_LONG);

        // Get all the candidatoList where coorLong equals to UPDATED_COOR_LONG
        defaultCandidatoShouldNotBeFound("coorLong.equals=" + UPDATED_COOR_LONG);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCoorLongIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where coorLong in DEFAULT_COOR_LONG or UPDATED_COOR_LONG
        defaultCandidatoShouldBeFound("coorLong.in=" + DEFAULT_COOR_LONG + "," + UPDATED_COOR_LONG);

        // Get all the candidatoList where coorLong equals to UPDATED_COOR_LONG
        defaultCandidatoShouldNotBeFound("coorLong.in=" + UPDATED_COOR_LONG);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCoorLongIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where coorLong is not null
        defaultCandidatoShouldBeFound("coorLong.specified=true");

        // Get all the candidatoList where coorLong is null
        defaultCandidatoShouldNotBeFound("coorLong.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDirCodigoPostalIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where dirCodigoPostal equals to DEFAULT_DIR_CODIGO_POSTAL
        defaultCandidatoShouldBeFound("dirCodigoPostal.equals=" + DEFAULT_DIR_CODIGO_POSTAL);

        // Get all the candidatoList where dirCodigoPostal equals to UPDATED_DIR_CODIGO_POSTAL
        defaultCandidatoShouldNotBeFound("dirCodigoPostal.equals=" + UPDATED_DIR_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDirCodigoPostalIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where dirCodigoPostal in DEFAULT_DIR_CODIGO_POSTAL or UPDATED_DIR_CODIGO_POSTAL
        defaultCandidatoShouldBeFound("dirCodigoPostal.in=" + DEFAULT_DIR_CODIGO_POSTAL + "," + UPDATED_DIR_CODIGO_POSTAL);

        // Get all the candidatoList where dirCodigoPostal equals to UPDATED_DIR_CODIGO_POSTAL
        defaultCandidatoShouldNotBeFound("dirCodigoPostal.in=" + UPDATED_DIR_CODIGO_POSTAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDirCodigoPostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where dirCodigoPostal is not null
        defaultCandidatoShouldBeFound("dirCodigoPostal.specified=true");

        // Get all the candidatoList where dirCodigoPostal is null
        defaultCandidatoShouldNotBeFound("dirCodigoPostal.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDirCalleIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where dirCalle equals to DEFAULT_DIR_CALLE
        defaultCandidatoShouldBeFound("dirCalle.equals=" + DEFAULT_DIR_CALLE);

        // Get all the candidatoList where dirCalle equals to UPDATED_DIR_CALLE
        defaultCandidatoShouldNotBeFound("dirCalle.equals=" + UPDATED_DIR_CALLE);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDirCalleIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where dirCalle in DEFAULT_DIR_CALLE or UPDATED_DIR_CALLE
        defaultCandidatoShouldBeFound("dirCalle.in=" + DEFAULT_DIR_CALLE + "," + UPDATED_DIR_CALLE);

        // Get all the candidatoList where dirCalle equals to UPDATED_DIR_CALLE
        defaultCandidatoShouldNotBeFound("dirCalle.in=" + UPDATED_DIR_CALLE);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDirCalleIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where dirCalle is not null
        defaultCandidatoShouldBeFound("dirCalle.specified=true");

        // Get all the candidatoList where dirCalle is null
        defaultCandidatoShouldNotBeFound("dirCalle.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNoExtIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where noExt equals to DEFAULT_NO_EXT
        defaultCandidatoShouldBeFound("noExt.equals=" + DEFAULT_NO_EXT);

        // Get all the candidatoList where noExt equals to UPDATED_NO_EXT
        defaultCandidatoShouldNotBeFound("noExt.equals=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNoExtIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where noExt in DEFAULT_NO_EXT or UPDATED_NO_EXT
        defaultCandidatoShouldBeFound("noExt.in=" + DEFAULT_NO_EXT + "," + UPDATED_NO_EXT);

        // Get all the candidatoList where noExt equals to UPDATED_NO_EXT
        defaultCandidatoShouldNotBeFound("noExt.in=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNoExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where noExt is not null
        defaultCandidatoShouldBeFound("noExt.specified=true");

        // Get all the candidatoList where noExt is null
        defaultCandidatoShouldNotBeFound("noExt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNoIntIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where noInt equals to DEFAULT_NO_INT
        defaultCandidatoShouldBeFound("noInt.equals=" + DEFAULT_NO_INT);

        // Get all the candidatoList where noInt equals to UPDATED_NO_INT
        defaultCandidatoShouldNotBeFound("noInt.equals=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNoIntIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where noInt in DEFAULT_NO_INT or UPDATED_NO_INT
        defaultCandidatoShouldBeFound("noInt.in=" + DEFAULT_NO_INT + "," + UPDATED_NO_INT);

        // Get all the candidatoList where noInt equals to UPDATED_NO_INT
        defaultCandidatoShouldNotBeFound("noInt.in=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNoIntIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where noInt is not null
        defaultCandidatoShouldBeFound("noInt.specified=true");

        // Get all the candidatoList where noInt is null
        defaultCandidatoShouldNotBeFound("noInt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesBySalarioNetoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where salarioNeto equals to DEFAULT_SALARIO_NETO
        defaultCandidatoShouldBeFound("salarioNeto.equals=" + DEFAULT_SALARIO_NETO);

        // Get all the candidatoList where salarioNeto equals to UPDATED_SALARIO_NETO
        defaultCandidatoShouldNotBeFound("salarioNeto.equals=" + UPDATED_SALARIO_NETO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesBySalarioNetoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where salarioNeto in DEFAULT_SALARIO_NETO or UPDATED_SALARIO_NETO
        defaultCandidatoShouldBeFound("salarioNeto.in=" + DEFAULT_SALARIO_NETO + "," + UPDATED_SALARIO_NETO);

        // Get all the candidatoList where salarioNeto equals to UPDATED_SALARIO_NETO
        defaultCandidatoShouldNotBeFound("salarioNeto.in=" + UPDATED_SALARIO_NETO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesBySalarioNetoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where salarioNeto is not null
        defaultCandidatoShouldBeFound("salarioNeto.specified=true");

        // Get all the candidatoList where salarioNeto is null
        defaultCandidatoShouldNotBeFound("salarioNeto.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCostoTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where costoTotal equals to DEFAULT_COSTO_TOTAL
        defaultCandidatoShouldBeFound("costoTotal.equals=" + DEFAULT_COSTO_TOTAL);

        // Get all the candidatoList where costoTotal equals to UPDATED_COSTO_TOTAL
        defaultCandidatoShouldNotBeFound("costoTotal.equals=" + UPDATED_COSTO_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCostoTotalIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where costoTotal in DEFAULT_COSTO_TOTAL or UPDATED_COSTO_TOTAL
        defaultCandidatoShouldBeFound("costoTotal.in=" + DEFAULT_COSTO_TOTAL + "," + UPDATED_COSTO_TOTAL);

        // Get all the candidatoList where costoTotal equals to UPDATED_COSTO_TOTAL
        defaultCandidatoShouldNotBeFound("costoTotal.in=" + UPDATED_COSTO_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCostoTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where costoTotal is not null
        defaultCandidatoShouldBeFound("costoTotal.specified=true");

        // Get all the candidatoList where costoTotal is null
        defaultCandidatoShouldNotBeFound("costoTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByContatoDuracionMinimaIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where contatoDuracionMinima equals to DEFAULT_CONTATO_DURACION_MINIMA
        defaultCandidatoShouldBeFound("contatoDuracionMinima.equals=" + DEFAULT_CONTATO_DURACION_MINIMA);

        // Get all the candidatoList where contatoDuracionMinima equals to UPDATED_CONTATO_DURACION_MINIMA
        defaultCandidatoShouldNotBeFound("contatoDuracionMinima.equals=" + UPDATED_CONTATO_DURACION_MINIMA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByContatoDuracionMinimaIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where contatoDuracionMinima in DEFAULT_CONTATO_DURACION_MINIMA or UPDATED_CONTATO_DURACION_MINIMA
        defaultCandidatoShouldBeFound("contatoDuracionMinima.in=" + DEFAULT_CONTATO_DURACION_MINIMA + "," + UPDATED_CONTATO_DURACION_MINIMA);

        // Get all the candidatoList where contatoDuracionMinima equals to UPDATED_CONTATO_DURACION_MINIMA
        defaultCandidatoShouldNotBeFound("contatoDuracionMinima.in=" + UPDATED_CONTATO_DURACION_MINIMA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByContatoDuracionMinimaIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where contatoDuracionMinima is not null
        defaultCandidatoShouldBeFound("contatoDuracionMinima.specified=true");

        // Get all the candidatoList where contatoDuracionMinima is null
        defaultCandidatoShouldNotBeFound("contatoDuracionMinima.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByContatoDuracionMinimaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where contatoDuracionMinima greater than or equals to DEFAULT_CONTATO_DURACION_MINIMA
        defaultCandidatoShouldBeFound("contatoDuracionMinima.greaterOrEqualThan=" + DEFAULT_CONTATO_DURACION_MINIMA);

        // Get all the candidatoList where contatoDuracionMinima greater than or equals to UPDATED_CONTATO_DURACION_MINIMA
        defaultCandidatoShouldNotBeFound("contatoDuracionMinima.greaterOrEqualThan=" + UPDATED_CONTATO_DURACION_MINIMA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByContatoDuracionMinimaIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where contatoDuracionMinima less than or equals to DEFAULT_CONTATO_DURACION_MINIMA
        defaultCandidatoShouldNotBeFound("contatoDuracionMinima.lessThan=" + DEFAULT_CONTATO_DURACION_MINIMA);

        // Get all the candidatoList where contatoDuracionMinima less than or equals to UPDATED_CONTATO_DURACION_MINIMA
        defaultCandidatoShouldBeFound("contatoDuracionMinima.lessThan=" + UPDATED_CONTATO_DURACION_MINIMA);
    }


    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaFecha equals to DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaFecha.equals=" + DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA);

        // Get all the candidatoList where disponibilidadEntrevistaFecha equals to UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaFecha.equals=" + UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaFechaIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaFecha in DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA or UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaFecha.in=" + DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA + "," + UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA);

        // Get all the candidatoList where disponibilidadEntrevistaFecha equals to UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaFecha.in=" + UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaFecha is not null
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaFecha.specified=true");

        // Get all the candidatoList where disponibilidadEntrevistaFecha is null
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaFecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaFecha greater than or equals to DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaFecha.greaterOrEqualThan=" + DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA);

        // Get all the candidatoList where disponibilidadEntrevistaFecha greater than or equals to UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaFecha.greaterOrEqualThan=" + UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaFecha less than or equals to DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaFecha.lessThan=" + DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA);

        // Get all the candidatoList where disponibilidadEntrevistaFecha less than or equals to UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaFecha.lessThan=" + UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA);
    }


    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo equals to DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaPeriodo.equals=" + DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo equals to UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaPeriodo.equals=" + UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo in DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO or UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaPeriodo.in=" + DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO + "," + UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo equals to UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaPeriodo.in=" + UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo is not null
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaPeriodo.specified=true");

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo is null
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaPeriodo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaPeriodoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo greater than or equals to DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaPeriodo.greaterOrEqualThan=" + DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo greater than or equals to UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaPeriodo.greaterOrEqualThan=" + UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaPeriodoIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo less than or equals to DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaPeriodo.lessThan=" + DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodo less than or equals to UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaPeriodo.lessThan=" + UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO);
    }


    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadAsignacionFecha equals to DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA
        defaultCandidatoShouldBeFound("disponibilidadAsignacionFecha.equals=" + DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA);

        // Get all the candidatoList where disponibilidadAsignacionFecha equals to UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionFecha.equals=" + UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionFechaIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadAsignacionFecha in DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA or UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA
        defaultCandidatoShouldBeFound("disponibilidadAsignacionFecha.in=" + DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA + "," + UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA);

        // Get all the candidatoList where disponibilidadAsignacionFecha equals to UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionFecha.in=" + UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadAsignacionFecha is not null
        defaultCandidatoShouldBeFound("disponibilidadAsignacionFecha.specified=true");

        // Get all the candidatoList where disponibilidadAsignacionFecha is null
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionFecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadAsignacionFecha greater than or equals to DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA
        defaultCandidatoShouldBeFound("disponibilidadAsignacionFecha.greaterOrEqualThan=" + DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA);

        // Get all the candidatoList where disponibilidadAsignacionFecha greater than or equals to UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionFecha.greaterOrEqualThan=" + UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadAsignacionFecha less than or equals to DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionFecha.lessThan=" + DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA);

        // Get all the candidatoList where disponibilidadAsignacionFecha less than or equals to UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA
        defaultCandidatoShouldBeFound("disponibilidadAsignacionFecha.lessThan=" + UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA);
    }


    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadAsignacionPeriodo equals to DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO
        defaultCandidatoShouldBeFound("disponibilidadAsignacionPeriodo.equals=" + DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO);

        // Get all the candidatoList where disponibilidadAsignacionPeriodo equals to UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionPeriodo.equals=" + UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadAsignacionPeriodo in DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO or UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO
        defaultCandidatoShouldBeFound("disponibilidadAsignacionPeriodo.in=" + DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO + "," + UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO);

        // Get all the candidatoList where disponibilidadAsignacionPeriodo equals to UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionPeriodo.in=" + UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where disponibilidadAsignacionPeriodo is not null
        defaultCandidatoShouldBeFound("disponibilidadAsignacionPeriodo.specified=true");

        // Get all the candidatoList where disponibilidadAsignacionPeriodo is null
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionPeriodo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAntecedenteUltimoEmpleadorIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where antecedenteUltimoEmpleador equals to DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR
        defaultCandidatoShouldBeFound("antecedenteUltimoEmpleador.equals=" + DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR);

        // Get all the candidatoList where antecedenteUltimoEmpleador equals to UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR
        defaultCandidatoShouldNotBeFound("antecedenteUltimoEmpleador.equals=" + UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAntecedenteUltimoEmpleadorIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where antecedenteUltimoEmpleador in DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR or UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR
        defaultCandidatoShouldBeFound("antecedenteUltimoEmpleador.in=" + DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR + "," + UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR);

        // Get all the candidatoList where antecedenteUltimoEmpleador equals to UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR
        defaultCandidatoShouldNotBeFound("antecedenteUltimoEmpleador.in=" + UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAntecedenteUltimoEmpleadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where antecedenteUltimoEmpleador is not null
        defaultCandidatoShouldBeFound("antecedenteUltimoEmpleador.specified=true");

        // Get all the candidatoList where antecedenteUltimoEmpleador is null
        defaultCandidatoShouldNotBeFound("antecedenteUltimoEmpleador.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAntecedenteSalarioNetoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where antecedenteSalarioNeto equals to DEFAULT_ANTECEDENTE_SALARIO_NETO
        defaultCandidatoShouldBeFound("antecedenteSalarioNeto.equals=" + DEFAULT_ANTECEDENTE_SALARIO_NETO);

        // Get all the candidatoList where antecedenteSalarioNeto equals to UPDATED_ANTECEDENTE_SALARIO_NETO
        defaultCandidatoShouldNotBeFound("antecedenteSalarioNeto.equals=" + UPDATED_ANTECEDENTE_SALARIO_NETO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAntecedenteSalarioNetoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where antecedenteSalarioNeto in DEFAULT_ANTECEDENTE_SALARIO_NETO or UPDATED_ANTECEDENTE_SALARIO_NETO
        defaultCandidatoShouldBeFound("antecedenteSalarioNeto.in=" + DEFAULT_ANTECEDENTE_SALARIO_NETO + "," + UPDATED_ANTECEDENTE_SALARIO_NETO);

        // Get all the candidatoList where antecedenteSalarioNeto equals to UPDATED_ANTECEDENTE_SALARIO_NETO
        defaultCandidatoShouldNotBeFound("antecedenteSalarioNeto.in=" + UPDATED_ANTECEDENTE_SALARIO_NETO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAntecedenteSalarioNetoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where antecedenteSalarioNeto is not null
        defaultCandidatoShouldBeFound("antecedenteSalarioNeto.specified=true");

        // Get all the candidatoList where antecedenteSalarioNeto is null
        defaultCandidatoShouldNotBeFound("antecedenteSalarioNeto.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAntecedenteSalarioNetoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where antecedenteSalarioNeto greater than or equals to DEFAULT_ANTECEDENTE_SALARIO_NETO
        defaultCandidatoShouldBeFound("antecedenteSalarioNeto.greaterOrEqualThan=" + DEFAULT_ANTECEDENTE_SALARIO_NETO);

        // Get all the candidatoList where antecedenteSalarioNeto greater than or equals to UPDATED_ANTECEDENTE_SALARIO_NETO
        defaultCandidatoShouldNotBeFound("antecedenteSalarioNeto.greaterOrEqualThan=" + UPDATED_ANTECEDENTE_SALARIO_NETO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByAntecedenteSalarioNetoIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where antecedenteSalarioNeto less than or equals to DEFAULT_ANTECEDENTE_SALARIO_NETO
        defaultCandidatoShouldNotBeFound("antecedenteSalarioNeto.lessThan=" + DEFAULT_ANTECEDENTE_SALARIO_NETO);

        // Get all the candidatoList where antecedenteSalarioNeto less than or equals to UPDATED_ANTECEDENTE_SALARIO_NETO
        defaultCandidatoShouldBeFound("antecedenteSalarioNeto.lessThan=" + UPDATED_ANTECEDENTE_SALARIO_NETO);
    }


    @Test
    @Transactional
    public void getAllCandidatoesByNotasIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where notas equals to DEFAULT_NOTAS
        defaultCandidatoShouldBeFound("notas.equals=" + DEFAULT_NOTAS);

        // Get all the candidatoList where notas equals to UPDATED_NOTAS
        defaultCandidatoShouldNotBeFound("notas.equals=" + UPDATED_NOTAS);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNotasIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where notas in DEFAULT_NOTAS or UPDATED_NOTAS
        defaultCandidatoShouldBeFound("notas.in=" + DEFAULT_NOTAS + "," + UPDATED_NOTAS);

        // Get all the candidatoList where notas equals to UPDATED_NOTAS
        defaultCandidatoShouldNotBeFound("notas.in=" + UPDATED_NOTAS);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNotasIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where notas is not null
        defaultCandidatoShouldBeFound("notas.specified=true");

        // Get all the candidatoList where notas is null
        defaultCandidatoShouldNotBeFound("notas.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByPorcentajeInglesIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where porcentajeIngles equals to DEFAULT_PORCENTAJE_INGLES
        defaultCandidatoShouldBeFound("porcentajeIngles.equals=" + DEFAULT_PORCENTAJE_INGLES);

        // Get all the candidatoList where porcentajeIngles equals to UPDATED_PORCENTAJE_INGLES
        defaultCandidatoShouldNotBeFound("porcentajeIngles.equals=" + UPDATED_PORCENTAJE_INGLES);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByPorcentajeInglesIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where porcentajeIngles in DEFAULT_PORCENTAJE_INGLES or UPDATED_PORCENTAJE_INGLES
        defaultCandidatoShouldBeFound("porcentajeIngles.in=" + DEFAULT_PORCENTAJE_INGLES + "," + UPDATED_PORCENTAJE_INGLES);

        // Get all the candidatoList where porcentajeIngles equals to UPDATED_PORCENTAJE_INGLES
        defaultCandidatoShouldNotBeFound("porcentajeIngles.in=" + UPDATED_PORCENTAJE_INGLES);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByPorcentajeInglesIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where porcentajeIngles is not null
        defaultCandidatoShouldBeFound("porcentajeIngles.specified=true");

        // Get all the candidatoList where porcentajeIngles is null
        defaultCandidatoShouldNotBeFound("porcentajeIngles.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByPorcentajeInglesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where porcentajeIngles greater than or equals to DEFAULT_PORCENTAJE_INGLES
        defaultCandidatoShouldBeFound("porcentajeIngles.greaterOrEqualThan=" + DEFAULT_PORCENTAJE_INGLES);

        // Get all the candidatoList where porcentajeIngles greater than or equals to UPDATED_PORCENTAJE_INGLES
        defaultCandidatoShouldNotBeFound("porcentajeIngles.greaterOrEqualThan=" + UPDATED_PORCENTAJE_INGLES);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByPorcentajeInglesIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where porcentajeIngles less than or equals to DEFAULT_PORCENTAJE_INGLES
        defaultCandidatoShouldNotBeFound("porcentajeIngles.lessThan=" + DEFAULT_PORCENTAJE_INGLES);

        // Get all the candidatoList where porcentajeIngles less than or equals to UPDATED_PORCENTAJE_INGLES
        defaultCandidatoShouldBeFound("porcentajeIngles.lessThan=" + UPDATED_PORCENTAJE_INGLES);
    }


    @Test
    @Transactional
    public void getAllCandidatoesByCurpIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where curp equals to DEFAULT_CURP
        defaultCandidatoShouldBeFound("curp.equals=" + DEFAULT_CURP);

        // Get all the candidatoList where curp equals to UPDATED_CURP
        defaultCandidatoShouldNotBeFound("curp.equals=" + UPDATED_CURP);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCurpIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where curp in DEFAULT_CURP or UPDATED_CURP
        defaultCandidatoShouldBeFound("curp.in=" + DEFAULT_CURP + "," + UPDATED_CURP);

        // Get all the candidatoList where curp equals to UPDATED_CURP
        defaultCandidatoShouldNotBeFound("curp.in=" + UPDATED_CURP);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByCurpIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where curp is not null
        defaultCandidatoShouldBeFound("curp.specified=true");

        // Get all the candidatoList where curp is null
        defaultCandidatoShouldNotBeFound("curp.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByRfcIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where rfc equals to DEFAULT_RFC
        defaultCandidatoShouldBeFound("rfc.equals=" + DEFAULT_RFC);

        // Get all the candidatoList where rfc equals to UPDATED_RFC
        defaultCandidatoShouldNotBeFound("rfc.equals=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByRfcIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where rfc in DEFAULT_RFC or UPDATED_RFC
        defaultCandidatoShouldBeFound("rfc.in=" + DEFAULT_RFC + "," + UPDATED_RFC);

        // Get all the candidatoList where rfc equals to UPDATED_RFC
        defaultCandidatoShouldNotBeFound("rfc.in=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByRfcIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where rfc is not null
        defaultCandidatoShouldBeFound("rfc.specified=true");

        // Get all the candidatoList where rfc is null
        defaultCandidatoShouldNotBeFound("rfc.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNssIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where nss equals to DEFAULT_NSS
        defaultCandidatoShouldBeFound("nss.equals=" + DEFAULT_NSS);

        // Get all the candidatoList where nss equals to UPDATED_NSS
        defaultCandidatoShouldNotBeFound("nss.equals=" + UPDATED_NSS);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNssIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where nss in DEFAULT_NSS or UPDATED_NSS
        defaultCandidatoShouldBeFound("nss.in=" + DEFAULT_NSS + "," + UPDATED_NSS);

        // Get all the candidatoList where nss equals to UPDATED_NSS
        defaultCandidatoShouldNotBeFound("nss.in=" + UPDATED_NSS);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByNssIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where nss is not null
        defaultCandidatoShouldBeFound("nss.specified=true");

        // Get all the candidatoList where nss is null
        defaultCandidatoShouldNotBeFound("nss.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where sexo equals to DEFAULT_SEXO
        defaultCandidatoShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the candidatoList where sexo equals to UPDATED_SEXO
        defaultCandidatoShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultCandidatoShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the candidatoList where sexo equals to UPDATED_SEXO
        defaultCandidatoShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where sexo is not null
        defaultCandidatoShouldBeFound("sexo.specified=true");

        // Get all the candidatoList where sexo is null
        defaultCandidatoShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEstadoCivilIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where estadoCivil equals to DEFAULT_ESTADO_CIVIL
        defaultCandidatoShouldBeFound("estadoCivil.equals=" + DEFAULT_ESTADO_CIVIL);

        // Get all the candidatoList where estadoCivil equals to UPDATED_ESTADO_CIVIL
        defaultCandidatoShouldNotBeFound("estadoCivil.equals=" + UPDATED_ESTADO_CIVIL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEstadoCivilIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where estadoCivil in DEFAULT_ESTADO_CIVIL or UPDATED_ESTADO_CIVIL
        defaultCandidatoShouldBeFound("estadoCivil.in=" + DEFAULT_ESTADO_CIVIL + "," + UPDATED_ESTADO_CIVIL);

        // Get all the candidatoList where estadoCivil equals to UPDATED_ESTADO_CIVIL
        defaultCandidatoShouldNotBeFound("estadoCivil.in=" + UPDATED_ESTADO_CIVIL);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByEstadoCivilIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where estadoCivil is not null
        defaultCandidatoShouldBeFound("estadoCivil.specified=true");

        // Get all the candidatoList where estadoCivil is null
        defaultCandidatoShouldNotBeFound("estadoCivil.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaAltaIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaAlta equals to DEFAULT_FECHA_ALTA
        defaultCandidatoShouldBeFound("fechaAlta.equals=" + DEFAULT_FECHA_ALTA);

        // Get all the candidatoList where fechaAlta equals to UPDATED_FECHA_ALTA
        defaultCandidatoShouldNotBeFound("fechaAlta.equals=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaAltaIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaAlta in DEFAULT_FECHA_ALTA or UPDATED_FECHA_ALTA
        defaultCandidatoShouldBeFound("fechaAlta.in=" + DEFAULT_FECHA_ALTA + "," + UPDATED_FECHA_ALTA);

        // Get all the candidatoList where fechaAlta equals to UPDATED_FECHA_ALTA
        defaultCandidatoShouldNotBeFound("fechaAlta.in=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaAltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaAlta is not null
        defaultCandidatoShouldBeFound("fechaAlta.specified=true");

        // Get all the candidatoList where fechaAlta is null
        defaultCandidatoShouldNotBeFound("fechaAlta.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaUltimoSeguimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaUltimoSeguimiento equals to DEFAULT_FECHA_ULTIMO_SEGUIMIENTO
        defaultCandidatoShouldBeFound("fechaUltimoSeguimiento.equals=" + DEFAULT_FECHA_ULTIMO_SEGUIMIENTO);

        // Get all the candidatoList where fechaUltimoSeguimiento equals to UPDATED_FECHA_ULTIMO_SEGUIMIENTO
        defaultCandidatoShouldNotBeFound("fechaUltimoSeguimiento.equals=" + UPDATED_FECHA_ULTIMO_SEGUIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaUltimoSeguimientoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaUltimoSeguimiento in DEFAULT_FECHA_ULTIMO_SEGUIMIENTO or UPDATED_FECHA_ULTIMO_SEGUIMIENTO
        defaultCandidatoShouldBeFound("fechaUltimoSeguimiento.in=" + DEFAULT_FECHA_ULTIMO_SEGUIMIENTO + "," + UPDATED_FECHA_ULTIMO_SEGUIMIENTO);

        // Get all the candidatoList where fechaUltimoSeguimiento equals to UPDATED_FECHA_ULTIMO_SEGUIMIENTO
        defaultCandidatoShouldNotBeFound("fechaUltimoSeguimiento.in=" + UPDATED_FECHA_ULTIMO_SEGUIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFechaUltimoSeguimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where fechaUltimoSeguimiento is not null
        defaultCandidatoShouldBeFound("fechaUltimoSeguimiento.specified=true");

        // Get all the candidatoList where fechaUltimoSeguimiento is null
        defaultCandidatoShouldNotBeFound("fechaUltimoSeguimiento.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFotoIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where foto equals to DEFAULT_FOTO
        defaultCandidatoShouldBeFound("foto.equals=" + DEFAULT_FOTO);

        // Get all the candidatoList where foto equals to UPDATED_FOTO
        defaultCandidatoShouldNotBeFound("foto.equals=" + UPDATED_FOTO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFotoIsInShouldWork() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where foto in DEFAULT_FOTO or UPDATED_FOTO
        defaultCandidatoShouldBeFound("foto.in=" + DEFAULT_FOTO + "," + UPDATED_FOTO);

        // Get all the candidatoList where foto equals to UPDATED_FOTO
        defaultCandidatoShouldNotBeFound("foto.in=" + UPDATED_FOTO);
    }

    @Test
    @Transactional
    public void getAllCandidatoesByFotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        // Get all the candidatoList where foto is not null
        defaultCandidatoShouldBeFound("foto.specified=true");

        // Get all the candidatoList where foto is null
        defaultCandidatoShouldNotBeFound("foto.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatoesByReferenciasLaboralesIsEqualToSomething() throws Exception {
        // Initialize the database
        ReferenciasLaborales referenciasLaborales = ReferenciasLaboralesResourceIT.createEntity(em);
        em.persist(referenciasLaborales);
        em.flush();
        candidato.addReferenciasLaborales(referenciasLaborales);
        candidatoRepository.saveAndFlush(candidato);
        Long referenciasLaboralesId = referenciasLaborales.getId();

        // Get all the candidatoList where referenciasLaborales equals to referenciasLaboralesId
        defaultCandidatoShouldBeFound("referenciasLaboralesId.equals=" + referenciasLaboralesId);

        // Get all the candidatoList where referenciasLaborales equals to referenciasLaboralesId + 1
        defaultCandidatoShouldNotBeFound("referenciasLaboralesId.equals=" + (referenciasLaboralesId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesBySkillCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillCandidato skillCandidato = SkillCandidatoResourceIT.createEntity(em);
        em.persist(skillCandidato);
        em.flush();
        candidato.addSkillCandidato(skillCandidato);
        candidatoRepository.saveAndFlush(candidato);
        Long skillCandidatoId = skillCandidato.getId();

        // Get all the candidatoList where skillCandidato equals to skillCandidatoId
        defaultCandidatoShouldBeFound("skillCandidatoId.equals=" + skillCandidatoId);

        // Get all the candidatoList where skillCandidato equals to skillCandidatoId + 1
        defaultCandidatoShouldNotBeFound("skillCandidatoId.equals=" + (skillCandidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByTareaIsEqualToSomething() throws Exception {
        // Initialize the database
        Tarea tarea = TareaResourceIT.createEntity(em);
        em.persist(tarea);
        em.flush();
        candidato.addTarea(tarea);
        candidatoRepository.saveAndFlush(candidato);
        Long tareaId = tarea.getId();

        // Get all the candidatoList where tarea equals to tareaId
        defaultCandidatoShouldBeFound("tareaId.equals=" + tareaId);

        // Get all the candidatoList where tarea equals to tareaId + 1
        defaultCandidatoShouldNotBeFound("tareaId.equals=" + (tareaId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByBitacoraIsEqualToSomething() throws Exception {
        // Initialize the database
        Bitacora bitacora = BitacoraResourceIT.createEntity(em);
        em.persist(bitacora);
        em.flush();
        candidato.addBitacora(bitacora);
        candidatoRepository.saveAndFlush(candidato);
        Long bitacoraId = bitacora.getId();

        // Get all the candidatoList where bitacora equals to bitacoraId
        defaultCandidatoShouldBeFound("bitacoraId.equals=" + bitacoraId);

        // Get all the candidatoList where bitacora equals to bitacoraId + 1
        defaultCandidatoShouldNotBeFound("bitacoraId.equals=" + (bitacoraId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadEntrevistaPeriodoTiempoIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoPeriodo disponibilidadEntrevistaPeriodoTiempo = TipoPeriodoResourceIT.createEntity(em);
        em.persist(disponibilidadEntrevistaPeriodoTiempo);
        em.flush();
        candidato.setDisponibilidadEntrevistaPeriodoTiempo(disponibilidadEntrevistaPeriodoTiempo);
        candidatoRepository.saveAndFlush(candidato);
        Long disponibilidadEntrevistaPeriodoTiempoId = disponibilidadEntrevistaPeriodoTiempo.getId();

        // Get all the candidatoList where disponibilidadEntrevistaPeriodoTiempo equals to disponibilidadEntrevistaPeriodoTiempoId
        defaultCandidatoShouldBeFound("disponibilidadEntrevistaPeriodoTiempoId.equals=" + disponibilidadEntrevistaPeriodoTiempoId);

        // Get all the candidatoList where disponibilidadEntrevistaPeriodoTiempo equals to disponibilidadEntrevistaPeriodoTiempoId + 1
        defaultCandidatoShouldNotBeFound("disponibilidadEntrevistaPeriodoTiempoId.equals=" + (disponibilidadEntrevistaPeriodoTiempoId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByDisponibilidadAsignacionPeriodoTiempoIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoPeriodo disponibilidadAsignacionPeriodoTiempo = TipoPeriodoResourceIT.createEntity(em);
        em.persist(disponibilidadAsignacionPeriodoTiempo);
        em.flush();
        candidato.setDisponibilidadAsignacionPeriodoTiempo(disponibilidadAsignacionPeriodoTiempo);
        candidatoRepository.saveAndFlush(candidato);
        Long disponibilidadAsignacionPeriodoTiempoId = disponibilidadAsignacionPeriodoTiempo.getId();

        // Get all the candidatoList where disponibilidadAsignacionPeriodoTiempo equals to disponibilidadAsignacionPeriodoTiempoId
        defaultCandidatoShouldBeFound("disponibilidadAsignacionPeriodoTiempoId.equals=" + disponibilidadAsignacionPeriodoTiempoId);

        // Get all the candidatoList where disponibilidadAsignacionPeriodoTiempo equals to disponibilidadAsignacionPeriodoTiempoId + 1
        defaultCandidatoShouldNotBeFound("disponibilidadAsignacionPeriodoTiempoId.equals=" + (disponibilidadAsignacionPeriodoTiempoId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByUsuarioCreadorIsEqualToSomething() throws Exception {
        // Initialize the database
        User usuarioCreador = UserResourceIT.createEntity(em);
        em.persist(usuarioCreador);
        em.flush();
        candidato.setUsuarioCreador(usuarioCreador);
        candidatoRepository.saveAndFlush(candidato);
        Long usuarioCreadorId = usuarioCreador.getId();

        // Get all the candidatoList where usuarioCreador equals to usuarioCreadorId
        defaultCandidatoShouldBeFound("usuarioCreadorId.equals=" + usuarioCreadorId);

        // Get all the candidatoList where usuarioCreador equals to usuarioCreadorId + 1
        defaultCandidatoShouldNotBeFound("usuarioCreadorId.equals=" + (usuarioCreadorId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByUsuarioAsignadoIsEqualToSomething() throws Exception {
        // Initialize the database
        User usuarioAsignado = UserResourceIT.createEntity(em);
        em.persist(usuarioAsignado);
        em.flush();
        candidato.setUsuarioAsignado(usuarioAsignado);
        candidatoRepository.saveAndFlush(candidato);
        Long usuarioAsignadoId = usuarioAsignado.getId();

        // Get all the candidatoList where usuarioAsignado equals to usuarioAsignadoId
        defaultCandidatoShouldBeFound("usuarioAsignadoId.equals=" + usuarioAsignadoId);

        // Get all the candidatoList where usuarioAsignado equals to usuarioAsignadoId + 1
        defaultCandidatoShouldNotBeFound("usuarioAsignadoId.equals=" + (usuarioAsignadoId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        Documento documento = DocumentoResourceIT.createEntity(em);
        em.persist(documento);
        em.flush();
        candidato.setDocumento(documento);
        candidatoRepository.saveAndFlush(candidato);
        Long documentoId = documento.getId();

        // Get all the candidatoList where documento equals to documentoId
        defaultCandidatoShouldBeFound("documentoId.equals=" + documentoId);

        // Get all the candidatoList where documento equals to documentoId + 1
        defaultCandidatoShouldNotBeFound("documentoId.equals=" + (documentoId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByCuentaInteresIsEqualToSomething() throws Exception {
        // Initialize the database
        Cuenta cuentaInteres = CuentaResourceIT.createEntity(em);
        em.persist(cuentaInteres);
        em.flush();
        candidato.addCuentaInteres(cuentaInteres);
        candidatoRepository.saveAndFlush(candidato);
        Long cuentaInteresId = cuentaInteres.getId();

        // Get all the candidatoList where cuentaInteres equals to cuentaInteresId
        defaultCandidatoShouldBeFound("cuentaInteresId.equals=" + cuentaInteresId);

        // Get all the candidatoList where cuentaInteres equals to cuentaInteresId + 1
        defaultCandidatoShouldNotBeFound("cuentaInteresId.equals=" + (cuentaInteresId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByCuentaRechazadasIsEqualToSomething() throws Exception {
        // Initialize the database
        Cuenta cuentaRechazadas = CuentaResourceIT.createEntity(em);
        em.persist(cuentaRechazadas);
        em.flush();
        candidato.addCuentaRechazadas(cuentaRechazadas);
        candidatoRepository.saveAndFlush(candidato);
        Long cuentaRechazadasId = cuentaRechazadas.getId();

        // Get all the candidatoList where cuentaRechazadas equals to cuentaRechazadasId
        defaultCandidatoShouldBeFound("cuentaRechazadasId.equals=" + cuentaRechazadasId);

        // Get all the candidatoList where cuentaRechazadas equals to cuentaRechazadasId + 1
        defaultCandidatoShouldNotBeFound("cuentaRechazadasId.equals=" + (cuentaRechazadasId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByFuenteReclutamientoIsEqualToSomething() throws Exception {
        // Initialize the database
        FuenteReclutamiento fuenteReclutamiento = FuenteReclutamientoResourceIT.createEntity(em);
        em.persist(fuenteReclutamiento);
        em.flush();
        candidato.setFuenteReclutamiento(fuenteReclutamiento);
        candidatoRepository.saveAndFlush(candidato);
        Long fuenteReclutamientoId = fuenteReclutamiento.getId();

        // Get all the candidatoList where fuenteReclutamiento equals to fuenteReclutamientoId
        defaultCandidatoShouldBeFound("fuenteReclutamientoId.equals=" + fuenteReclutamientoId);

        // Get all the candidatoList where fuenteReclutamiento equals to fuenteReclutamientoId + 1
        defaultCandidatoShouldNotBeFound("fuenteReclutamientoId.equals=" + (fuenteReclutamientoId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByEstatusCandidatoIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusCandidato estatusCandidato = EstatusCandidatoResourceIT.createEntity(em);
        em.persist(estatusCandidato);
        em.flush();
        candidato.setEstatusCandidato(estatusCandidato);
        candidatoRepository.saveAndFlush(candidato);
        Long estatusCandidatoId = estatusCandidato.getId();

        // Get all the candidatoList where estatusCandidato equals to estatusCandidatoId
        defaultCandidatoShouldBeFound("estatusCandidatoId.equals=" + estatusCandidatoId);

        // Get all the candidatoList where estatusCandidato equals to estatusCandidatoId + 1
        defaultCandidatoShouldNotBeFound("estatusCandidatoId.equals=" + (estatusCandidatoId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        Perfil perfil = PerfilResourceIT.createEntity(em);
        em.persist(perfil);
        em.flush();
        candidato.setPerfil(perfil);
        candidatoRepository.saveAndFlush(candidato);
        Long perfilId = perfil.getId();

        // Get all the candidatoList where perfil equals to perfilId
        defaultCandidatoShouldBeFound("perfilId.equals=" + perfilId);

        // Get all the candidatoList where perfil equals to perfilId + 1
        defaultCandidatoShouldNotBeFound("perfilId.equals=" + (perfilId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByNivelPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        NivelPerfil nivelPerfil = NivelPerfilResourceIT.createEntity(em);
        em.persist(nivelPerfil);
        em.flush();
        candidato.setNivelPerfil(nivelPerfil);
        candidatoRepository.saveAndFlush(candidato);
        Long nivelPerfilId = nivelPerfil.getId();

        // Get all the candidatoList where nivelPerfil equals to nivelPerfilId
        defaultCandidatoShouldBeFound("nivelPerfilId.equals=" + nivelPerfilId);

        // Get all the candidatoList where nivelPerfil equals to nivelPerfilId + 1
        defaultCandidatoShouldNotBeFound("nivelPerfilId.equals=" + (nivelPerfilId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByInstitucionAcademicaIsEqualToSomething() throws Exception {
        // Initialize the database
        InstitucionAcademica institucionAcademica = InstitucionAcademicaResourceIT.createEntity(em);
        em.persist(institucionAcademica);
        em.flush();
        candidato.setInstitucionAcademica(institucionAcademica);
        candidatoRepository.saveAndFlush(candidato);
        Long institucionAcademicaId = institucionAcademica.getId();

        // Get all the candidatoList where institucionAcademica equals to institucionAcademicaId
        defaultCandidatoShouldBeFound("institucionAcademicaId.equals=" + institucionAcademicaId);

        // Get all the candidatoList where institucionAcademica equals to institucionAcademicaId + 1
        defaultCandidatoShouldNotBeFound("institucionAcademicaId.equals=" + (institucionAcademicaId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByEstatusAcademicoIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusAcademico estatusAcademico = EstatusAcademicoResourceIT.createEntity(em);
        em.persist(estatusAcademico);
        em.flush();
        candidato.setEstatusAcademico(estatusAcademico);
        candidatoRepository.saveAndFlush(candidato);
        Long estatusAcademicoId = estatusAcademico.getId();

        // Get all the candidatoList where estatusAcademico equals to estatusAcademicoId
        defaultCandidatoShouldBeFound("estatusAcademicoId.equals=" + estatusAcademicoId);

        // Get all the candidatoList where estatusAcademico equals to estatusAcademicoId + 1
        defaultCandidatoShouldNotBeFound("estatusAcademicoId.equals=" + (estatusAcademicoId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByEsquemaContratacionKodeIsEqualToSomething() throws Exception {
        // Initialize the database
        EsquemaContratacionKode esquemaContratacionKode = EsquemaContratacionKodeResourceIT.createEntity(em);
        em.persist(esquemaContratacionKode);
        em.flush();
        candidato.setEsquemaContratacionKode(esquemaContratacionKode);
        candidatoRepository.saveAndFlush(candidato);
        Long esquemaContratacionKodeId = esquemaContratacionKode.getId();

        // Get all the candidatoList where esquemaContratacionKode equals to esquemaContratacionKodeId
        defaultCandidatoShouldBeFound("esquemaContratacionKodeId.equals=" + esquemaContratacionKodeId);

        // Get all the candidatoList where esquemaContratacionKode equals to esquemaContratacionKodeId + 1
        defaultCandidatoShouldNotBeFound("esquemaContratacionKodeId.equals=" + (esquemaContratacionKodeId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByEstatusLaboralIsEqualToSomething() throws Exception {
        // Initialize the database
        EstatusLaboral estatusLaboral = EstatusLaboralResourceIT.createEntity(em);
        em.persist(estatusLaboral);
        em.flush();
        candidato.setEstatusLaboral(estatusLaboral);
        candidatoRepository.saveAndFlush(candidato);
        Long estatusLaboralId = estatusLaboral.getId();

        // Get all the candidatoList where estatusLaboral equals to estatusLaboralId
        defaultCandidatoShouldBeFound("estatusLaboralId.equals=" + estatusLaboralId);

        // Get all the candidatoList where estatusLaboral equals to estatusLaboralId + 1
        defaultCandidatoShouldNotBeFound("estatusLaboralId.equals=" + (estatusLaboralId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByColoniaIsEqualToSomething() throws Exception {
        // Initialize the database
        Colonia colonia = ColoniaResourceIT.createEntity(em);
        em.persist(colonia);
        em.flush();
        candidato.setColonia(colonia);
        candidatoRepository.saveAndFlush(candidato);
        Long coloniaId = colonia.getId();

        // Get all the candidatoList where colonia equals to coloniaId
        defaultCandidatoShouldBeFound("coloniaId.equals=" + coloniaId);

        // Get all the candidatoList where colonia equals to coloniaId + 1
        defaultCandidatoShouldNotBeFound("coloniaId.equals=" + (coloniaId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByAntecedentesEsquemaContratacionIsEqualToSomething() throws Exception {
        // Initialize the database
        EsqContRec antecedentesEsquemaContratacion = EsqContRecResourceIT.createEntity(em);
        em.persist(antecedentesEsquemaContratacion);
        em.flush();
        candidato.setAntecedentesEsquemaContratacion(antecedentesEsquemaContratacion);
        candidatoRepository.saveAndFlush(candidato);
        Long antecedentesEsquemaContratacionId = antecedentesEsquemaContratacion.getId();

        // Get all the candidatoList where antecedentesEsquemaContratacion equals to antecedentesEsquemaContratacionId
        defaultCandidatoShouldBeFound("antecedentesEsquemaContratacionId.equals=" + antecedentesEsquemaContratacionId);

        // Get all the candidatoList where antecedentesEsquemaContratacion equals to antecedentesEsquemaContratacionId + 1
        defaultCandidatoShouldNotBeFound("antecedentesEsquemaContratacionId.equals=" + (antecedentesEsquemaContratacionId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByEstudiosIsEqualToSomething() throws Exception {
        // Initialize the database
        FormacionAcademica estudios = FormacionAcademicaResourceIT.createEntity(em);
        em.persist(estudios);
        em.flush();
        candidato.setEstudios(estudios);
        candidatoRepository.saveAndFlush(candidato);
        Long estudiosId = estudios.getId();

        // Get all the candidatoList where estudios equals to estudiosId
        defaultCandidatoShouldBeFound("estudiosId.equals=" + estudiosId);

        // Get all the candidatoList where estudios equals to estudiosId + 1
        defaultCandidatoShouldNotBeFound("estudiosId.equals=" + (estudiosId + 1));
    }


    @Test
    @Transactional
    public void getAllCandidatoesByEstCanInactivoIsEqualToSomething() throws Exception {
        // Initialize the database
        EstCanInactivo estCanInactivo = EstCanInactivoResourceIT.createEntity(em);
        em.persist(estCanInactivo);
        em.flush();
        candidato.setEstCanInactivo(estCanInactivo);
        candidatoRepository.saveAndFlush(candidato);
        Long estCanInactivoId = estCanInactivo.getId();

        // Get all the candidatoList where estCanInactivo equals to estCanInactivoId
        defaultCandidatoShouldBeFound("estCanInactivoId.equals=" + estCanInactivoId);

        // Get all the candidatoList where estCanInactivo equals to estCanInactivoId + 1
        defaultCandidatoShouldNotBeFound("estCanInactivoId.equals=" + (estCanInactivoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCandidatoShouldBeFound(String filter) throws Exception {
        restCandidatoMockMvc.perform(get("/api/candidatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidato.getId().intValue())))
            .andExpect(jsonPath("$.[*].anosExperiencia").value(hasItem(DEFAULT_ANOS_EXPERIENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].apellidoMaterno").value(hasItem(DEFAULT_APELLIDO_MATERNO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].emailPrincipal").value(hasItem(DEFAULT_EMAIL_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].emailAdicional").value(hasItem(DEFAULT_EMAIL_ADICIONAL)))
            .andExpect(jsonPath("$.[*].emailAsignacion").value(hasItem(DEFAULT_EMAIL_ASIGNACION)))
            .andExpect(jsonPath("$.[*].emailKode").value(hasItem(DEFAULT_EMAIL_KODE)))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB)))
            .andExpect(jsonPath("$.[*].telefonoCasa").value(hasItem(DEFAULT_TELEFONO_CASA)))
            .andExpect(jsonPath("$.[*].telefonoTrabajo").value(hasItem(DEFAULT_TELEFONO_TRABAJO)))
            .andExpect(jsonPath("$.[*].telefonoCelular").value(hasItem(DEFAULT_TELEFONO_CELULAR)))
            .andExpect(jsonPath("$.[*].telefonoAdicional").value(hasItem(DEFAULT_TELEFONO_ADICIONAL)))
            .andExpect(jsonPath("$.[*].coorLat").value(hasItem(DEFAULT_COOR_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].coorLong").value(hasItem(DEFAULT_COOR_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].dirCodigoPostal").value(hasItem(DEFAULT_DIR_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].dirCalle").value(hasItem(DEFAULT_DIR_CALLE)))
            .andExpect(jsonPath("$.[*].noExt").value(hasItem(DEFAULT_NO_EXT)))
            .andExpect(jsonPath("$.[*].noInt").value(hasItem(DEFAULT_NO_INT)))
            .andExpect(jsonPath("$.[*].salarioNeto").value(hasItem(DEFAULT_SALARIO_NETO.doubleValue())))
            .andExpect(jsonPath("$.[*].costoTotal").value(hasItem(DEFAULT_COSTO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].contatoDuracionMinima").value(hasItem(DEFAULT_CONTATO_DURACION_MINIMA)))
            .andExpect(jsonPath("$.[*].disponibilidadEntrevistaFecha").value(hasItem(DEFAULT_DISPONIBILIDAD_ENTREVISTA_FECHA.toString())))
            .andExpect(jsonPath("$.[*].disponibilidadEntrevistaPeriodo").value(hasItem(DEFAULT_DISPONIBILIDAD_ENTREVISTA_PERIODO)))
            .andExpect(jsonPath("$.[*].disponibilidadAsignacionFecha").value(hasItem(DEFAULT_DISPONIBILIDAD_ASIGNACION_FECHA.toString())))
            .andExpect(jsonPath("$.[*].disponibilidadAsignacionPeriodo").value(hasItem(DEFAULT_DISPONIBILIDAD_ASIGNACION_PERIODO)))
            .andExpect(jsonPath("$.[*].antecedenteUltimoEmpleador").value(hasItem(DEFAULT_ANTECEDENTE_ULTIMO_EMPLEADOR)))
            .andExpect(jsonPath("$.[*].antecedenteSalarioNeto").value(hasItem(DEFAULT_ANTECEDENTE_SALARIO_NETO)))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS)))
            .andExpect(jsonPath("$.[*].porcentajeIngles").value(hasItem(DEFAULT_PORCENTAJE_INGLES)))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP)))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].nss").value(hasItem(DEFAULT_NSS)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaUltimoSeguimiento").value(hasItem(DEFAULT_FECHA_ULTIMO_SEGUIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO)));

        // Check, that the count call also returns 1
        restCandidatoMockMvc.perform(get("/api/candidatoes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCandidatoShouldNotBeFound(String filter) throws Exception {
        restCandidatoMockMvc.perform(get("/api/candidatoes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCandidatoMockMvc.perform(get("/api/candidatoes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCandidato() throws Exception {
        // Get the candidato
        restCandidatoMockMvc.perform(get("/api/candidatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidato() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        int databaseSizeBeforeUpdate = candidatoRepository.findAll().size();

        // Update the candidato
        Candidato updatedCandidato = candidatoRepository.findById(candidato.getId()).get();
        // Disconnect from session so that the updates on updatedCandidato are not directly saved in db
        em.detach(updatedCandidato);
        updatedCandidato
            .anosExperiencia(UPDATED_ANOS_EXPERIENCIA)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .edad(UPDATED_EDAD)
            .emailPrincipal(UPDATED_EMAIL_PRINCIPAL)
            .emailAdicional(UPDATED_EMAIL_ADICIONAL)
            .emailAsignacion(UPDATED_EMAIL_ASIGNACION)
            .emailKode(UPDATED_EMAIL_KODE)
            .web(UPDATED_WEB)
            .telefonoCasa(UPDATED_TELEFONO_CASA)
            .telefonoTrabajo(UPDATED_TELEFONO_TRABAJO)
            .telefonoCelular(UPDATED_TELEFONO_CELULAR)
            .telefonoAdicional(UPDATED_TELEFONO_ADICIONAL)
            .coorLat(UPDATED_COOR_LAT)
            .coorLong(UPDATED_COOR_LONG)
            .dirCodigoPostal(UPDATED_DIR_CODIGO_POSTAL)
            .dirCalle(UPDATED_DIR_CALLE)
            .noExt(UPDATED_NO_EXT)
            .noInt(UPDATED_NO_INT)
            .salarioNeto(UPDATED_SALARIO_NETO)
            .costoTotal(UPDATED_COSTO_TOTAL)
            .contatoDuracionMinima(UPDATED_CONTATO_DURACION_MINIMA)
            .disponibilidadEntrevistaFecha(UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA)
            .disponibilidadEntrevistaPeriodo(UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO)
            .disponibilidadAsignacionFecha(UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA)
            .disponibilidadAsignacionPeriodo(UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO)
            .antecedenteUltimoEmpleador(UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR)
            .antecedenteSalarioNeto(UPDATED_ANTECEDENTE_SALARIO_NETO)
            .notas(UPDATED_NOTAS)
            .porcentajeIngles(UPDATED_PORCENTAJE_INGLES)
            .curp(UPDATED_CURP)
            .rfc(UPDATED_RFC)
            .nss(UPDATED_NSS)
            .sexo(UPDATED_SEXO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaUltimoSeguimiento(UPDATED_FECHA_ULTIMO_SEGUIMIENTO)
            .foto(UPDATED_FOTO);
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(updatedCandidato);

        restCandidatoMockMvc.perform(put("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isOk());

        // Validate the Candidato in the database
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeUpdate);
        Candidato testCandidato = candidatoList.get(candidatoList.size() - 1);
        assertThat(testCandidato.getAnosExperiencia()).isEqualTo(UPDATED_ANOS_EXPERIENCIA);
        assertThat(testCandidato.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCandidato.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testCandidato.getApellidoMaterno()).isEqualTo(UPDATED_APELLIDO_MATERNO);
        assertThat(testCandidato.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testCandidato.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testCandidato.getEmailPrincipal()).isEqualTo(UPDATED_EMAIL_PRINCIPAL);
        assertThat(testCandidato.getEmailAdicional()).isEqualTo(UPDATED_EMAIL_ADICIONAL);
        assertThat(testCandidato.getEmailAsignacion()).isEqualTo(UPDATED_EMAIL_ASIGNACION);
        assertThat(testCandidato.getEmailKode()).isEqualTo(UPDATED_EMAIL_KODE);
        assertThat(testCandidato.getWeb()).isEqualTo(UPDATED_WEB);
        assertThat(testCandidato.getTelefonoCasa()).isEqualTo(UPDATED_TELEFONO_CASA);
        assertThat(testCandidato.getTelefonoTrabajo()).isEqualTo(UPDATED_TELEFONO_TRABAJO);
        assertThat(testCandidato.getTelefonoCelular()).isEqualTo(UPDATED_TELEFONO_CELULAR);
        assertThat(testCandidato.getTelefonoAdicional()).isEqualTo(UPDATED_TELEFONO_ADICIONAL);
        assertThat(testCandidato.getCoorLat()).isEqualTo(UPDATED_COOR_LAT);
        assertThat(testCandidato.getCoorLong()).isEqualTo(UPDATED_COOR_LONG);
        assertThat(testCandidato.getDirCodigoPostal()).isEqualTo(UPDATED_DIR_CODIGO_POSTAL);
        assertThat(testCandidato.getDirCalle()).isEqualTo(UPDATED_DIR_CALLE);
        assertThat(testCandidato.getNoExt()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testCandidato.getNoInt()).isEqualTo(UPDATED_NO_INT);
        assertThat(testCandidato.getSalarioNeto()).isEqualTo(UPDATED_SALARIO_NETO);
        assertThat(testCandidato.getCostoTotal()).isEqualTo(UPDATED_COSTO_TOTAL);
        assertThat(testCandidato.getContatoDuracionMinima()).isEqualTo(UPDATED_CONTATO_DURACION_MINIMA);
        assertThat(testCandidato.getDisponibilidadEntrevistaFecha()).isEqualTo(UPDATED_DISPONIBILIDAD_ENTREVISTA_FECHA);
        assertThat(testCandidato.getDisponibilidadEntrevistaPeriodo()).isEqualTo(UPDATED_DISPONIBILIDAD_ENTREVISTA_PERIODO);
        assertThat(testCandidato.getDisponibilidadAsignacionFecha()).isEqualTo(UPDATED_DISPONIBILIDAD_ASIGNACION_FECHA);
        assertThat(testCandidato.getDisponibilidadAsignacionPeriodo()).isEqualTo(UPDATED_DISPONIBILIDAD_ASIGNACION_PERIODO);
        assertThat(testCandidato.getAntecedenteUltimoEmpleador()).isEqualTo(UPDATED_ANTECEDENTE_ULTIMO_EMPLEADOR);
        assertThat(testCandidato.getAntecedenteSalarioNeto()).isEqualTo(UPDATED_ANTECEDENTE_SALARIO_NETO);
        assertThat(testCandidato.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testCandidato.getPorcentajeIngles()).isEqualTo(UPDATED_PORCENTAJE_INGLES);
        assertThat(testCandidato.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testCandidato.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCandidato.getNss()).isEqualTo(UPDATED_NSS);
        assertThat(testCandidato.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testCandidato.getEstadoCivil()).isEqualTo(UPDATED_ESTADO_CIVIL);
        assertThat(testCandidato.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testCandidato.getFechaUltimoSeguimiento()).isEqualTo(UPDATED_FECHA_ULTIMO_SEGUIMIENTO);
        assertThat(testCandidato.getFoto()).isEqualTo(UPDATED_FOTO);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidato() throws Exception {
        int databaseSizeBeforeUpdate = candidatoRepository.findAll().size();

        // Create the Candidato
        CandidatoDTO candidatoDTO = candidatoMapper.toDto(candidato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatoMockMvc.perform(put("/api/candidatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidato in the database
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidato() throws Exception {
        // Initialize the database
        candidatoRepository.saveAndFlush(candidato);

        int databaseSizeBeforeDelete = candidatoRepository.findAll().size();

        // Delete the candidato
        restCandidatoMockMvc.perform(delete("/api/candidatoes/{id}", candidato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Candidato> candidatoList = candidatoRepository.findAll();
        assertThat(candidatoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidato.class);
        Candidato candidato1 = new Candidato();
        candidato1.setId(1L);
        Candidato candidato2 = new Candidato();
        candidato2.setId(candidato1.getId());
        assertThat(candidato1).isEqualTo(candidato2);
        candidato2.setId(2L);
        assertThat(candidato1).isNotEqualTo(candidato2);
        candidato1.setId(null);
        assertThat(candidato1).isNotEqualTo(candidato2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidatoDTO.class);
        CandidatoDTO candidatoDTO1 = new CandidatoDTO();
        candidatoDTO1.setId(1L);
        CandidatoDTO candidatoDTO2 = new CandidatoDTO();
        assertThat(candidatoDTO1).isNotEqualTo(candidatoDTO2);
        candidatoDTO2.setId(candidatoDTO1.getId());
        assertThat(candidatoDTO1).isEqualTo(candidatoDTO2);
        candidatoDTO2.setId(2L);
        assertThat(candidatoDTO1).isNotEqualTo(candidatoDTO2);
        candidatoDTO1.setId(null);
        assertThat(candidatoDTO1).isNotEqualTo(candidatoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(candidatoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(candidatoMapper.fromId(null)).isNull();
    }
}
