package com.kode.ts.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.kode.ts.domain.enumeration.Sexo;

import com.kode.ts.domain.enumeration.EstadoCivil;

/**
 * A Candidato.
 */
@Entity
@Table(name = "candidato")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "anos_experiencia")
    private Float anosExperiencia;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 100)
    @Column(name = "apellido_paterno", length = 100, nullable = false)
    private String apellidoPaterno;

    @Size(max = 100)
    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "edad")
    private Integer edad;

    @NotNull
    @Size(max = 100)
    @Column(name = "email_principal", length = 100, nullable = false)
    private String emailPrincipal;

    @Size(max = 100)
    @Column(name = "email_adicional", length = 100)
    private String emailAdicional;

    @Size(max = 100)
    @Column(name = "email_asignacion", length = 100)
    private String emailAsignacion;

    @Size(max = 100)
    @Column(name = "email_kode", length = 100)
    private String emailKode;

    @Size(max = 100)
    @Column(name = "web", length = 100)
    private String web;

    @Size(max = 20)
    @Column(name = "telefono_casa", length = 20)
    private String telefonoCasa;

    @Size(max = 20)
    @Column(name = "telefono_trabajo", length = 20)
    private String telefonoTrabajo;

    @Size(max = 20)
    @Column(name = "telefono_celular", length = 20)
    private String telefonoCelular;

    @Size(max = 20)
    @Column(name = "telefono_adicional", length = 20)
    private String telefonoAdicional;

    @Column(name = "coor_lat")
    private Float coorLat;

    @Column(name = "coor_long")
    private Float coorLong;

    @Size(max = 5)
    @Column(name = "dir_codigo_postal", length = 5)
    private String dirCodigoPostal;

    @Size(max = 100)
    @Column(name = "dir_calle", length = 100)
    private String dirCalle;

    @Size(max = 100)
    @Column(name = "no_ext", length = 100)
    private String noExt;

    @Size(max = 100)
    @Column(name = "no_int", length = 100)
    private String noInt;

    @Column(name = "salario_neto")
    private Float salarioNeto;

    @Column(name = "costo_total")
    private Float costoTotal;

    @Column(name = "contato_duracion_minima")
    private Integer contatoDuracionMinima;

    @Column(name = "disponibilidad_entrevista_fecha")
    private LocalDate disponibilidadEntrevistaFecha;

    @Column(name = "disponibilidad_entrevista_periodo")
    private Integer disponibilidadEntrevistaPeriodo;

    @Column(name = "disponibilidad_asignacion_fecha")
    private LocalDate disponibilidadAsignacionFecha;

    @Size(max = 100)
    @Column(name = "disponibilidad_asignacion_periodo", length = 100)
    private String disponibilidadAsignacionPeriodo;

    @Size(max = 100)
    @Column(name = "antecedente_ultimo_empleador", length = 100)
    private String antecedenteUltimoEmpleador;

    @Column(name = "antecedente_salario_neto")
    private Integer antecedenteSalarioNeto;

    @Size(max = 1000)
    @Column(name = "notas", length = 1000)
    private String notas;

    @Column(name = "porcentaje_ingles")
    private Integer porcentajeIngles;

    @Size(max = 50)
    @Column(name = "curp", length = 50)
    private String curp;

    @Size(max = 50)
    @Column(name = "rfc", length = 50)
    private String rfc;

    @Size(max = 50)
    @Column(name = "nss", length = 50)
    private String nss;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @Column(name = "fecha_ultimo_seguimiento")
    private Instant fechaUltimoSeguimiento;

    @Size(max = 1000)
    @Column(name = "foto", length = 1000)
    private String foto;

    @OneToMany(mappedBy = "candidato")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReferenciasLaborales> referenciasLaborales = new HashSet<>();

    @OneToMany(mappedBy = "idCandidato")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillCandidato> skillCandidatoes = new HashSet<>();

    @OneToMany(mappedBy = "candidato")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarea> tareas = new HashSet<>();

    @OneToMany(mappedBy = "candidato")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bitacora> bitacoras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private TipoPeriodo disponibilidadEntrevistaPeriodoTiempo;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private TipoPeriodo disponibilidadAsignacionPeriodoTiempo;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private User usuarioCreador;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private User usuarioAsignado;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private Documento documento;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "candidato_cuenta_interes",
               joinColumns = @JoinColumn(name = "candidato_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cuenta_interes_id", referencedColumnName = "id"))
    private Set<Cuenta> cuentaInteres = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "candidato_cuenta_rechazadas",
               joinColumns = @JoinColumn(name = "candidato_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cuenta_rechazadas_id", referencedColumnName = "id"))
    private Set<Cuenta> cuentaRechazadas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private FuenteReclutamiento fuenteReclutamiento;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private EstatusCandidato estatusCandidato;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private Perfil perfil;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private NivelPerfil nivelPerfil;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private InstitucionAcademica institucionAcademica;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private EstatusAcademico estatusAcademico;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private EsquemaContratacionKode esquemaContratacionKode;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private EstatusLaboral estatusLaboral;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private Colonia colonia;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private EsqContRec antecedentesEsquemaContratacion;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private FormacionAcademica estudios;

    @ManyToOne
    @JsonIgnoreProperties("candidatoes")
    private EstCanInactivo estCanInactivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAnosExperiencia() {
        return anosExperiencia;
    }

    public Candidato anosExperiencia(Float anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
        return this;
    }

    public void setAnosExperiencia(Float anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public String getNombre() {
        return nombre;
    }

    public Candidato nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public Candidato apellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
        return this;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public Candidato apellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
        return this;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Candidato fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public Candidato edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEmailPrincipal() {
        return emailPrincipal;
    }

    public Candidato emailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
        return this;
    }

    public void setEmailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }

    public String getEmailAdicional() {
        return emailAdicional;
    }

    public Candidato emailAdicional(String emailAdicional) {
        this.emailAdicional = emailAdicional;
        return this;
    }

    public void setEmailAdicional(String emailAdicional) {
        this.emailAdicional = emailAdicional;
    }

    public String getEmailAsignacion() {
        return emailAsignacion;
    }

    public Candidato emailAsignacion(String emailAsignacion) {
        this.emailAsignacion = emailAsignacion;
        return this;
    }

    public void setEmailAsignacion(String emailAsignacion) {
        this.emailAsignacion = emailAsignacion;
    }

    public String getEmailKode() {
        return emailKode;
    }

    public Candidato emailKode(String emailKode) {
        this.emailKode = emailKode;
        return this;
    }

    public void setEmailKode(String emailKode) {
        this.emailKode = emailKode;
    }

    public String getWeb() {
        return web;
    }

    public Candidato web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public Candidato telefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
        return this;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public Candidato telefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
        return this;
    }

    public void setTelefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public Candidato telefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
        return this;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoAdicional() {
        return telefonoAdicional;
    }

    public Candidato telefonoAdicional(String telefonoAdicional) {
        this.telefonoAdicional = telefonoAdicional;
        return this;
    }

    public void setTelefonoAdicional(String telefonoAdicional) {
        this.telefonoAdicional = telefonoAdicional;
    }

    public Float getCoorLat() {
        return coorLat;
    }

    public Candidato coorLat(Float coorLat) {
        this.coorLat = coorLat;
        return this;
    }

    public void setCoorLat(Float coorLat) {
        this.coorLat = coorLat;
    }

    public Float getCoorLong() {
        return coorLong;
    }

    public Candidato coorLong(Float coorLong) {
        this.coorLong = coorLong;
        return this;
    }

    public void setCoorLong(Float coorLong) {
        this.coorLong = coorLong;
    }

    public String getDirCodigoPostal() {
        return dirCodigoPostal;
    }

    public Candidato dirCodigoPostal(String dirCodigoPostal) {
        this.dirCodigoPostal = dirCodigoPostal;
        return this;
    }

    public void setDirCodigoPostal(String dirCodigoPostal) {
        this.dirCodigoPostal = dirCodigoPostal;
    }

    public String getDirCalle() {
        return dirCalle;
    }

    public Candidato dirCalle(String dirCalle) {
        this.dirCalle = dirCalle;
        return this;
    }

    public void setDirCalle(String dirCalle) {
        this.dirCalle = dirCalle;
    }

    public String getNoExt() {
        return noExt;
    }

    public Candidato noExt(String noExt) {
        this.noExt = noExt;
        return this;
    }

    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getNoInt() {
        return noInt;
    }

    public Candidato noInt(String noInt) {
        this.noInt = noInt;
        return this;
    }

    public void setNoInt(String noInt) {
        this.noInt = noInt;
    }

    public Float getSalarioNeto() {
        return salarioNeto;
    }

    public Candidato salarioNeto(Float salarioNeto) {
        this.salarioNeto = salarioNeto;
        return this;
    }

    public void setSalarioNeto(Float salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public Float getCostoTotal() {
        return costoTotal;
    }

    public Candidato costoTotal(Float costoTotal) {
        this.costoTotal = costoTotal;
        return this;
    }

    public void setCostoTotal(Float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Integer getContatoDuracionMinima() {
        return contatoDuracionMinima;
    }

    public Candidato contatoDuracionMinima(Integer contatoDuracionMinima) {
        this.contatoDuracionMinima = contatoDuracionMinima;
        return this;
    }

    public void setContatoDuracionMinima(Integer contatoDuracionMinima) {
        this.contatoDuracionMinima = contatoDuracionMinima;
    }

    public LocalDate getDisponibilidadEntrevistaFecha() {
        return disponibilidadEntrevistaFecha;
    }

    public Candidato disponibilidadEntrevistaFecha(LocalDate disponibilidadEntrevistaFecha) {
        this.disponibilidadEntrevistaFecha = disponibilidadEntrevistaFecha;
        return this;
    }

    public void setDisponibilidadEntrevistaFecha(LocalDate disponibilidadEntrevistaFecha) {
        this.disponibilidadEntrevistaFecha = disponibilidadEntrevistaFecha;
    }

    public Integer getDisponibilidadEntrevistaPeriodo() {
        return disponibilidadEntrevistaPeriodo;
    }

    public Candidato disponibilidadEntrevistaPeriodo(Integer disponibilidadEntrevistaPeriodo) {
        this.disponibilidadEntrevistaPeriodo = disponibilidadEntrevistaPeriodo;
        return this;
    }

    public void setDisponibilidadEntrevistaPeriodo(Integer disponibilidadEntrevistaPeriodo) {
        this.disponibilidadEntrevistaPeriodo = disponibilidadEntrevistaPeriodo;
    }

    public LocalDate getDisponibilidadAsignacionFecha() {
        return disponibilidadAsignacionFecha;
    }

    public Candidato disponibilidadAsignacionFecha(LocalDate disponibilidadAsignacionFecha) {
        this.disponibilidadAsignacionFecha = disponibilidadAsignacionFecha;
        return this;
    }

    public void setDisponibilidadAsignacionFecha(LocalDate disponibilidadAsignacionFecha) {
        this.disponibilidadAsignacionFecha = disponibilidadAsignacionFecha;
    }

    public String getDisponibilidadAsignacionPeriodo() {
        return disponibilidadAsignacionPeriodo;
    }

    public Candidato disponibilidadAsignacionPeriodo(String disponibilidadAsignacionPeriodo) {
        this.disponibilidadAsignacionPeriodo = disponibilidadAsignacionPeriodo;
        return this;
    }

    public void setDisponibilidadAsignacionPeriodo(String disponibilidadAsignacionPeriodo) {
        this.disponibilidadAsignacionPeriodo = disponibilidadAsignacionPeriodo;
    }

    public String getAntecedenteUltimoEmpleador() {
        return antecedenteUltimoEmpleador;
    }

    public Candidato antecedenteUltimoEmpleador(String antecedenteUltimoEmpleador) {
        this.antecedenteUltimoEmpleador = antecedenteUltimoEmpleador;
        return this;
    }

    public void setAntecedenteUltimoEmpleador(String antecedenteUltimoEmpleador) {
        this.antecedenteUltimoEmpleador = antecedenteUltimoEmpleador;
    }

    public Integer getAntecedenteSalarioNeto() {
        return antecedenteSalarioNeto;
    }

    public Candidato antecedenteSalarioNeto(Integer antecedenteSalarioNeto) {
        this.antecedenteSalarioNeto = antecedenteSalarioNeto;
        return this;
    }

    public void setAntecedenteSalarioNeto(Integer antecedenteSalarioNeto) {
        this.antecedenteSalarioNeto = antecedenteSalarioNeto;
    }

    public String getNotas() {
        return notas;
    }

    public Candidato notas(String notas) {
        this.notas = notas;
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getPorcentajeIngles() {
        return porcentajeIngles;
    }

    public Candidato porcentajeIngles(Integer porcentajeIngles) {
        this.porcentajeIngles = porcentajeIngles;
        return this;
    }

    public void setPorcentajeIngles(Integer porcentajeIngles) {
        this.porcentajeIngles = porcentajeIngles;
    }

    public String getCurp() {
        return curp;
    }

    public Candidato curp(String curp) {
        this.curp = curp;
        return this;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public Candidato rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNss() {
        return nss;
    }

    public Candidato nss(String nss) {
        this.nss = nss;
        return this;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Candidato sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public Candidato estadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
        return this;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public Candidato fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Instant getFechaUltimoSeguimiento() {
        return fechaUltimoSeguimiento;
    }

    public Candidato fechaUltimoSeguimiento(Instant fechaUltimoSeguimiento) {
        this.fechaUltimoSeguimiento = fechaUltimoSeguimiento;
        return this;
    }

    public void setFechaUltimoSeguimiento(Instant fechaUltimoSeguimiento) {
        this.fechaUltimoSeguimiento = fechaUltimoSeguimiento;
    }

    public String getFoto() {
        return foto;
    }

    public Candidato foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Set<ReferenciasLaborales> getReferenciasLaborales() {
        return referenciasLaborales;
    }

    public Candidato referenciasLaborales(Set<ReferenciasLaborales> referenciasLaborales) {
        this.referenciasLaborales = referenciasLaborales;
        return this;
    }

    public Candidato addReferenciasLaborales(ReferenciasLaborales referenciasLaborales) {
        this.referenciasLaborales.add(referenciasLaborales);
        referenciasLaborales.setCandidato(this);
        return this;
    }

    public Candidato removeReferenciasLaborales(ReferenciasLaborales referenciasLaborales) {
        this.referenciasLaborales.remove(referenciasLaborales);
        referenciasLaborales.setCandidato(null);
        return this;
    }

    public void setReferenciasLaborales(Set<ReferenciasLaborales> referenciasLaborales) {
        this.referenciasLaborales = referenciasLaborales;
    }

    public Set<SkillCandidato> getSkillCandidatoes() {
        return skillCandidatoes;
    }

    public Candidato skillCandidatoes(Set<SkillCandidato> skillCandidatoes) {
        this.skillCandidatoes = skillCandidatoes;
        return this;
    }

    public Candidato addSkillCandidato(SkillCandidato skillCandidato) {
        this.skillCandidatoes.add(skillCandidato);
        skillCandidato.setIdCandidato(this);
        return this;
    }

    public Candidato removeSkillCandidato(SkillCandidato skillCandidato) {
        this.skillCandidatoes.remove(skillCandidato);
        skillCandidato.setIdCandidato(null);
        return this;
    }

    public void setSkillCandidatoes(Set<SkillCandidato> skillCandidatoes) {
        this.skillCandidatoes = skillCandidatoes;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public Candidato tareas(Set<Tarea> tareas) {
        this.tareas = tareas;
        return this;
    }

    public Candidato addTarea(Tarea tarea) {
        this.tareas.add(tarea);
        tarea.setCandidato(this);
        return this;
    }

    public Candidato removeTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.setCandidato(null);
        return this;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Set<Bitacora> getBitacoras() {
        return bitacoras;
    }

    public Candidato bitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
        return this;
    }

    public Candidato addBitacora(Bitacora bitacora) {
        this.bitacoras.add(bitacora);
        bitacora.setCandidato(this);
        return this;
    }

    public Candidato removeBitacora(Bitacora bitacora) {
        this.bitacoras.remove(bitacora);
        bitacora.setCandidato(null);
        return this;
    }

    public void setBitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
    }

    public TipoPeriodo getDisponibilidadEntrevistaPeriodoTiempo() {
        return disponibilidadEntrevistaPeriodoTiempo;
    }

    public Candidato disponibilidadEntrevistaPeriodoTiempo(TipoPeriodo tipoPeriodo) {
        this.disponibilidadEntrevistaPeriodoTiempo = tipoPeriodo;
        return this;
    }

    public void setDisponibilidadEntrevistaPeriodoTiempo(TipoPeriodo tipoPeriodo) {
        this.disponibilidadEntrevistaPeriodoTiempo = tipoPeriodo;
    }

    public TipoPeriodo getDisponibilidadAsignacionPeriodoTiempo() {
        return disponibilidadAsignacionPeriodoTiempo;
    }

    public Candidato disponibilidadAsignacionPeriodoTiempo(TipoPeriodo tipoPeriodo) {
        this.disponibilidadAsignacionPeriodoTiempo = tipoPeriodo;
        return this;
    }

    public void setDisponibilidadAsignacionPeriodoTiempo(TipoPeriodo tipoPeriodo) {
        this.disponibilidadAsignacionPeriodoTiempo = tipoPeriodo;
    }

    public User getUsuarioCreador() {
        return usuarioCreador;
    }

    public Candidato usuarioCreador(User user) {
        this.usuarioCreador = user;
        return this;
    }

    public void setUsuarioCreador(User user) {
        this.usuarioCreador = user;
    }

    public User getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public Candidato usuarioAsignado(User user) {
        this.usuarioAsignado = user;
        return this;
    }

    public void setUsuarioAsignado(User user) {
        this.usuarioAsignado = user;
    }

    public Documento getDocumento() {
        return documento;
    }

    public Candidato documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Set<Cuenta> getCuentaInteres() {
        return cuentaInteres;
    }

    public Candidato cuentaInteres(Set<Cuenta> cuentas) {
        this.cuentaInteres = cuentas;
        return this;
    }

    public Candidato addCuentaInteres(Cuenta cuenta) {
        this.cuentaInteres.add(cuenta);
        cuenta.getCandidatoInteres().add(this);
        return this;
    }

    public Candidato removeCuentaInteres(Cuenta cuenta) {
        this.cuentaInteres.remove(cuenta);
        cuenta.getCandidatoInteres().remove(this);
        return this;
    }

    public void setCuentaInteres(Set<Cuenta> cuentas) {
        this.cuentaInteres = cuentas;
    }

    public Set<Cuenta> getCuentaRechazadas() {
        return cuentaRechazadas;
    }

    public Candidato cuentaRechazadas(Set<Cuenta> cuentas) {
        this.cuentaRechazadas = cuentas;
        return this;
    }

    public Candidato addCuentaRechazadas(Cuenta cuenta) {
        this.cuentaRechazadas.add(cuenta);
        cuenta.getCandidatoRechazadas().add(this);
        return this;
    }

    public Candidato removeCuentaRechazadas(Cuenta cuenta) {
        this.cuentaRechazadas.remove(cuenta);
        cuenta.getCandidatoRechazadas().remove(this);
        return this;
    }

    public void setCuentaRechazadas(Set<Cuenta> cuentas) {
        this.cuentaRechazadas = cuentas;
    }

    public FuenteReclutamiento getFuenteReclutamiento() {
        return fuenteReclutamiento;
    }

    public Candidato fuenteReclutamiento(FuenteReclutamiento fuenteReclutamiento) {
        this.fuenteReclutamiento = fuenteReclutamiento;
        return this;
    }

    public void setFuenteReclutamiento(FuenteReclutamiento fuenteReclutamiento) {
        this.fuenteReclutamiento = fuenteReclutamiento;
    }

    public EstatusCandidato getEstatusCandidato() {
        return estatusCandidato;
    }

    public Candidato estatusCandidato(EstatusCandidato estatusCandidato) {
        this.estatusCandidato = estatusCandidato;
        return this;
    }

    public void setEstatusCandidato(EstatusCandidato estatusCandidato) {
        this.estatusCandidato = estatusCandidato;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public Candidato perfil(Perfil perfil) {
        this.perfil = perfil;
        return this;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public NivelPerfil getNivelPerfil() {
        return nivelPerfil;
    }

    public Candidato nivelPerfil(NivelPerfil nivelPerfil) {
        this.nivelPerfil = nivelPerfil;
        return this;
    }

    public void setNivelPerfil(NivelPerfil nivelPerfil) {
        this.nivelPerfil = nivelPerfil;
    }

    public InstitucionAcademica getInstitucionAcademica() {
        return institucionAcademica;
    }

    public Candidato institucionAcademica(InstitucionAcademica institucionAcademica) {
        this.institucionAcademica = institucionAcademica;
        return this;
    }

    public void setInstitucionAcademica(InstitucionAcademica institucionAcademica) {
        this.institucionAcademica = institucionAcademica;
    }

    public EstatusAcademico getEstatusAcademico() {
        return estatusAcademico;
    }

    public Candidato estatusAcademico(EstatusAcademico estatusAcademico) {
        this.estatusAcademico = estatusAcademico;
        return this;
    }

    public void setEstatusAcademico(EstatusAcademico estatusAcademico) {
        this.estatusAcademico = estatusAcademico;
    }

    public EsquemaContratacionKode getEsquemaContratacionKode() {
        return esquemaContratacionKode;
    }

    public Candidato esquemaContratacionKode(EsquemaContratacionKode esquemaContratacionKode) {
        this.esquemaContratacionKode = esquemaContratacionKode;
        return this;
    }

    public void setEsquemaContratacionKode(EsquemaContratacionKode esquemaContratacionKode) {
        this.esquemaContratacionKode = esquemaContratacionKode;
    }

    public EstatusLaboral getEstatusLaboral() {
        return estatusLaboral;
    }

    public Candidato estatusLaboral(EstatusLaboral estatusLaboral) {
        this.estatusLaboral = estatusLaboral;
        return this;
    }

    public void setEstatusLaboral(EstatusLaboral estatusLaboral) {
        this.estatusLaboral = estatusLaboral;
    }

    public Colonia getColonia() {
        return colonia;
    }

    public Candidato colonia(Colonia colonia) {
        this.colonia = colonia;
        return this;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }

    public EsqContRec getAntecedentesEsquemaContratacion() {
        return antecedentesEsquemaContratacion;
    }

    public Candidato antecedentesEsquemaContratacion(EsqContRec esqContRec) {
        this.antecedentesEsquemaContratacion = esqContRec;
        return this;
    }

    public void setAntecedentesEsquemaContratacion(EsqContRec esqContRec) {
        this.antecedentesEsquemaContratacion = esqContRec;
    }

    public FormacionAcademica getEstudios() {
        return estudios;
    }

    public Candidato estudios(FormacionAcademica formacionAcademica) {
        this.estudios = formacionAcademica;
        return this;
    }

    public void setEstudios(FormacionAcademica formacionAcademica) {
        this.estudios = formacionAcademica;
    }

    public EstCanInactivo getEstCanInactivo() {
        return estCanInactivo;
    }

    public Candidato estCanInactivo(EstCanInactivo estCanInactivo) {
        this.estCanInactivo = estCanInactivo;
        return this;
    }

    public void setEstCanInactivo(EstCanInactivo estCanInactivo) {
        this.estCanInactivo = estCanInactivo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidato)) {
            return false;
        }
        return id != null && id.equals(((Candidato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Candidato{" +
            "id=" + getId() +
            ", anosExperiencia=" + getAnosExperiencia() +
            ", nombre='" + getNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", apellidoMaterno='" + getApellidoMaterno() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", edad=" + getEdad() +
            ", emailPrincipal='" + getEmailPrincipal() + "'" +
            ", emailAdicional='" + getEmailAdicional() + "'" +
            ", emailAsignacion='" + getEmailAsignacion() + "'" +
            ", emailKode='" + getEmailKode() + "'" +
            ", web='" + getWeb() + "'" +
            ", telefonoCasa='" + getTelefonoCasa() + "'" +
            ", telefonoTrabajo='" + getTelefonoTrabajo() + "'" +
            ", telefonoCelular='" + getTelefonoCelular() + "'" +
            ", telefonoAdicional='" + getTelefonoAdicional() + "'" +
            ", coorLat=" + getCoorLat() +
            ", coorLong=" + getCoorLong() +
            ", dirCodigoPostal='" + getDirCodigoPostal() + "'" +
            ", dirCalle='" + getDirCalle() + "'" +
            ", noExt='" + getNoExt() + "'" +
            ", noInt='" + getNoInt() + "'" +
            ", salarioNeto=" + getSalarioNeto() +
            ", costoTotal=" + getCostoTotal() +
            ", contatoDuracionMinima=" + getContatoDuracionMinima() +
            ", disponibilidadEntrevistaFecha='" + getDisponibilidadEntrevistaFecha() + "'" +
            ", disponibilidadEntrevistaPeriodo=" + getDisponibilidadEntrevistaPeriodo() +
            ", disponibilidadAsignacionFecha='" + getDisponibilidadAsignacionFecha() + "'" +
            ", disponibilidadAsignacionPeriodo='" + getDisponibilidadAsignacionPeriodo() + "'" +
            ", antecedenteUltimoEmpleador='" + getAntecedenteUltimoEmpleador() + "'" +
            ", antecedenteSalarioNeto=" + getAntecedenteSalarioNeto() +
            ", notas='" + getNotas() + "'" +
            ", porcentajeIngles=" + getPorcentajeIngles() +
            ", curp='" + getCurp() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", nss='" + getNss() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaUltimoSeguimiento='" + getFechaUltimoSeguimiento() + "'" +
            ", foto='" + getFoto() + "'" +
            "}";
    }
}
