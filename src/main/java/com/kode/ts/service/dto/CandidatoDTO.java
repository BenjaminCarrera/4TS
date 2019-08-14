package com.kode.ts.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.kode.ts.domain.enumeration.Sexo;
import com.kode.ts.domain.enumeration.EstadoCivil;

/**
 * A DTO for the {@link com.kode.ts.domain.Candidato} entity.
 */
public class CandidatoDTO implements Serializable {

    private Long id;

    private Float anosExperiencia;

    @NotNull
    @Size(min = 1, max = 100)
    private String nombre;

    @NotNull
    @Size(min = 1, max = 100)
    private String apellidoPaterno;

    @Size(max = 100)
    private String apellidoMaterno;

    private LocalDate fechaNacimiento;

    private Integer edad;

    @NotNull
    @Size(min = 1, max = 100)
    private String emailPrincipal;

    @Size(max = 100)
    private String emailAdicional;

    @Size(max = 100)
    private String emailAsignacion;

    @Size(max = 100)
    private String emailKode;

    @Size(max = 100)
    private String web;

    @Size(max = 20)
    private String telefonoCasa;

    @Size(max = 20)
    private String telefonoTrabajo;

    @Size(max = 20)
    private String telefonoCelular;

    @Size(max = 20)
    private String telefonoAdicional;

    private Float coorLat;

    private Float coorLong;

    @Size(max = 5)
    private String dirCodigoPostal;

    @Size(max = 100)
    private String dirCalle;

    @Size(max = 100)
    private String noExt;

    @Size(max = 100)
    private String noInt;

    private Float salarioNeto;

    private Float costoTotal;

    private Integer contatoDuracionMinima;

    private LocalDate disponibilidadEntrevistaFecha;

    private Integer disponibilidadEntrevistaPeriodo;

    private LocalDate disponibilidadAsignacionFecha;

    @Size(max = 100)
    private String disponibilidadAsignacionPeriodo;

    @Size(max = 100)
    private String antecedenteUltimoEmpleador;

    private Integer antecedenteSalarioNeto;

    @Size(max = 1000)
    private String notas;

    private Integer porcentajeIngles;

    @Size(max = 50)
    private String curp;

    @Size(max = 50)
    private String rfc;

    @Size(max = 50)
    private String nss;

	@NotNull
    private Sexo sexo;

    private EstadoCivil estadoCivil;

	@NotNull
    private Instant fechaAlta;

    private Instant fechaUltimoSeguimiento;

    @Size(max = 1000)
    private String foto;


    private Long disponibilidadEntrevistaPeriodoTiempoId;

    private String disponibilidadEntrevistaPeriodoTiempoPeriodo;

    private Long disponibilidadAsignacionPeriodoTiempoId;

    private String disponibilidadAsignacionPeriodoTiempoPeriodo;

	@NotNull
    private Long usuarioCreadorId;

    private String usuarioCreadorFirstName;

	@NotNull
    private Long usuarioAsignadoId;

    private String usuarioAsignadoFirstName;

    private Long documentoId;

    private String documentoDocumento;

    private Set<CuentaDTO> cuentaInteres = new HashSet<>();

    private Set<CuentaDTO> cuentaRechazadas = new HashSet<>();

    private Long fuenteReclutamientoId;

    private String fuenteReclutamientoFuente;

	@NotNull
    private Long estatusCandidatoId;

    private String estatusCandidatoEstatus;

    private Long perfilId;

    private String perfilPerfil;

    private Long nivelPerfilId;

    private String nivelPerfilNivel;

    private Long institucionAcademicaId;

    private String institucionAcademicaInstitucion;

    private Long estatusAcademicoId;

    private String estatusAcademicoEstatus;

    private Long esquemaContratacionKodeId;

    private String esquemaContratacionKodeEsquema;

	@NotNull
    private Long estatusLaboralId;

    private String estatusLaboralEstatus;

    private Long coloniaId;

    private String coloniaColonia;

    private Long antecedentesEsquemaContratacionId;

    private String antecedentesEsquemaContratacionEsquema;

    private Long estudiosId;

    private String estudiosFormacionAcademica;

    private Long estCanInactivoId;

    private String estCanInactivoMotivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(Float anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEmailPrincipal() {
        return emailPrincipal;
    }

    public void setEmailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }

    public String getEmailAdicional() {
        return emailAdicional;
    }

    public void setEmailAdicional(String emailAdicional) {
        this.emailAdicional = emailAdicional;
    }

    public String getEmailAsignacion() {
        return emailAsignacion;
    }

    public void setEmailAsignacion(String emailAsignacion) {
        this.emailAsignacion = emailAsignacion;
    }

    public String getEmailKode() {
        return emailKode;
    }

    public void setEmailKode(String emailKode) {
        this.emailKode = emailKode;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public void setTelefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoAdicional() {
        return telefonoAdicional;
    }

    public void setTelefonoAdicional(String telefonoAdicional) {
        this.telefonoAdicional = telefonoAdicional;
    }

    public Float getCoorLat() {
        return coorLat;
    }

    public void setCoorLat(Float coorLat) {
        this.coorLat = coorLat;
    }

    public Float getCoorLong() {
        return coorLong;
    }

    public void setCoorLong(Float coorLong) {
        this.coorLong = coorLong;
    }

    public String getDirCodigoPostal() {
        return dirCodigoPostal;
    }

    public void setDirCodigoPostal(String dirCodigoPostal) {
        this.dirCodigoPostal = dirCodigoPostal;
    }

    public String getDirCalle() {
        return dirCalle;
    }

    public void setDirCalle(String dirCalle) {
        this.dirCalle = dirCalle;
    }

    public String getNoExt() {
        return noExt;
    }

    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getNoInt() {
        return noInt;
    }

    public void setNoInt(String noInt) {
        this.noInt = noInt;
    }

    public Float getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(Float salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public Float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Integer getContatoDuracionMinima() {
        return contatoDuracionMinima;
    }

    public void setContatoDuracionMinima(Integer contatoDuracionMinima) {
        this.contatoDuracionMinima = contatoDuracionMinima;
    }

    public LocalDate getDisponibilidadEntrevistaFecha() {
        return disponibilidadEntrevistaFecha;
    }

    public void setDisponibilidadEntrevistaFecha(LocalDate disponibilidadEntrevistaFecha) {
        this.disponibilidadEntrevistaFecha = disponibilidadEntrevistaFecha;
    }

    public Integer getDisponibilidadEntrevistaPeriodo() {
        return disponibilidadEntrevistaPeriodo;
    }

    public void setDisponibilidadEntrevistaPeriodo(Integer disponibilidadEntrevistaPeriodo) {
        this.disponibilidadEntrevistaPeriodo = disponibilidadEntrevistaPeriodo;
    }

    public LocalDate getDisponibilidadAsignacionFecha() {
        return disponibilidadAsignacionFecha;
    }

    public void setDisponibilidadAsignacionFecha(LocalDate disponibilidadAsignacionFecha) {
        this.disponibilidadAsignacionFecha = disponibilidadAsignacionFecha;
    }

    public String getDisponibilidadAsignacionPeriodo() {
        return disponibilidadAsignacionPeriodo;
    }

    public void setDisponibilidadAsignacionPeriodo(String disponibilidadAsignacionPeriodo) {
        this.disponibilidadAsignacionPeriodo = disponibilidadAsignacionPeriodo;
    }

    public String getAntecedenteUltimoEmpleador() {
        return antecedenteUltimoEmpleador;
    }

    public void setAntecedenteUltimoEmpleador(String antecedenteUltimoEmpleador) {
        this.antecedenteUltimoEmpleador = antecedenteUltimoEmpleador;
    }

    public Integer getAntecedenteSalarioNeto() {
        return antecedenteSalarioNeto;
    }

    public void setAntecedenteSalarioNeto(Integer antecedenteSalarioNeto) {
        this.antecedenteSalarioNeto = antecedenteSalarioNeto;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getPorcentajeIngles() {
        return porcentajeIngles;
    }

    public void setPorcentajeIngles(Integer porcentajeIngles) {
        this.porcentajeIngles = porcentajeIngles;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Instant getFechaUltimoSeguimiento() {
        return fechaUltimoSeguimiento;
    }

    public void setFechaUltimoSeguimiento(Instant fechaUltimoSeguimiento) {
        this.fechaUltimoSeguimiento = fechaUltimoSeguimiento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getDisponibilidadEntrevistaPeriodoTiempoId() {
        return disponibilidadEntrevistaPeriodoTiempoId;
    }

    public void setDisponibilidadEntrevistaPeriodoTiempoId(Long tipoPeriodoId) {
        this.disponibilidadEntrevistaPeriodoTiempoId = tipoPeriodoId;
    }

    public String getDisponibilidadEntrevistaPeriodoTiempoPeriodo() {
        return disponibilidadEntrevistaPeriodoTiempoPeriodo;
    }

    public void setDisponibilidadEntrevistaPeriodoTiempoPeriodo(String tipoPeriodoPeriodo) {
        this.disponibilidadEntrevistaPeriodoTiempoPeriodo = tipoPeriodoPeriodo;
    }

    public Long getDisponibilidadAsignacionPeriodoTiempoId() {
        return disponibilidadAsignacionPeriodoTiempoId;
    }

    public void setDisponibilidadAsignacionPeriodoTiempoId(Long tipoPeriodoId) {
        this.disponibilidadAsignacionPeriodoTiempoId = tipoPeriodoId;
    }

    public String getDisponibilidadAsignacionPeriodoTiempoPeriodo() {
        return disponibilidadAsignacionPeriodoTiempoPeriodo;
    }

    public void setDisponibilidadAsignacionPeriodoTiempoPeriodo(String tipoPeriodoPeriodo) {
        this.disponibilidadAsignacionPeriodoTiempoPeriodo = tipoPeriodoPeriodo;
    }

    public Long getUsuarioCreadorId() {
        return usuarioCreadorId;
    }

    public void setUsuarioCreadorId(Long userId) {
        this.usuarioCreadorId = userId;
    }

    public String getUsuarioCreadorFirstName() {
        return usuarioCreadorFirstName;
    }

    public void setUsuarioCreadorFirstName(String userFirstName) {
        this.usuarioCreadorFirstName = userFirstName;
    }

    public Long getUsuarioAsignadoId() {
        return usuarioAsignadoId;
    }

    public void setUsuarioAsignadoId(Long userId) {
        this.usuarioAsignadoId = userId;
    }

    public String getUsuarioAsignadoFirstName() {
        return usuarioAsignadoFirstName;
    }

    public void setUsuarioAsignadoFirstName(String userFirstName) {
        this.usuarioAsignadoFirstName = userFirstName;
    }

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public String getDocumentoDocumento() {
        return documentoDocumento;
    }

    public void setDocumentoDocumento(String documentoDocumento) {
        this.documentoDocumento = documentoDocumento;
    }

    public Set<CuentaDTO> getCuentaInteres() {
        return cuentaInteres;
    }

    public void setCuentaInteres(Set<CuentaDTO> cuentas) {
        this.cuentaInteres = cuentas;
    }

    public Set<CuentaDTO> getCuentaRechazadas() {
        return cuentaRechazadas;
    }

    public void setCuentaRechazadas(Set<CuentaDTO> cuentas) {
        this.cuentaRechazadas = cuentas;
    }

    public Long getFuenteReclutamientoId() {
        return fuenteReclutamientoId;
    }

    public void setFuenteReclutamientoId(Long fuenteReclutamientoId) {
        this.fuenteReclutamientoId = fuenteReclutamientoId;
    }

    public String getFuenteReclutamientoFuente() {
        return fuenteReclutamientoFuente;
    }

    public void setFuenteReclutamientoFuente(String fuenteReclutamientoFuente) {
        this.fuenteReclutamientoFuente = fuenteReclutamientoFuente;
    }

    public Long getEstatusCandidatoId() {
        return estatusCandidatoId;
    }

    public void setEstatusCandidatoId(Long estatusCandidatoId) {
        this.estatusCandidatoId = estatusCandidatoId;
    }

    public String getEstatusCandidatoEstatus() {
        return estatusCandidatoEstatus;
    }

    public void setEstatusCandidatoEstatus(String estatusCandidatoEstatus) {
        this.estatusCandidatoEstatus = estatusCandidatoEstatus;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public String getPerfilPerfil() {
        return perfilPerfil;
    }

    public void setPerfilPerfil(String perfilPerfil) {
        this.perfilPerfil = perfilPerfil;
    }

    public Long getNivelPerfilId() {
        return nivelPerfilId;
    }

    public void setNivelPerfilId(Long nivelPerfilId) {
        this.nivelPerfilId = nivelPerfilId;
    }

    public String getNivelPerfilNivel() {
        return nivelPerfilNivel;
    }

    public void setNivelPerfilNivel(String nivelPerfilNivel) {
        this.nivelPerfilNivel = nivelPerfilNivel;
    }

    public Long getInstitucionAcademicaId() {
        return institucionAcademicaId;
    }

    public void setInstitucionAcademicaId(Long institucionAcademicaId) {
        this.institucionAcademicaId = institucionAcademicaId;
    }

    public String getInstitucionAcademicaInstitucion() {
        return institucionAcademicaInstitucion;
    }

    public void setInstitucionAcademicaInstitucion(String institucionAcademicaInstitucion) {
        this.institucionAcademicaInstitucion = institucionAcademicaInstitucion;
    }

    public Long getEstatusAcademicoId() {
        return estatusAcademicoId;
    }

    public void setEstatusAcademicoId(Long estatusAcademicoId) {
        this.estatusAcademicoId = estatusAcademicoId;
    }

    public String getEstatusAcademicoEstatus() {
        return estatusAcademicoEstatus;
    }

    public void setEstatusAcademicoEstatus(String estatusAcademicoEstatus) {
        this.estatusAcademicoEstatus = estatusAcademicoEstatus;
    }

    public Long getEsquemaContratacionKodeId() {
        return esquemaContratacionKodeId;
    }

    public void setEsquemaContratacionKodeId(Long esquemaContratacionKodeId) {
        this.esquemaContratacionKodeId = esquemaContratacionKodeId;
    }

    public String getEsquemaContratacionKodeEsquema() {
        return esquemaContratacionKodeEsquema;
    }

    public void setEsquemaContratacionKodeEsquema(String esquemaContratacionKodeEsquema) {
        this.esquemaContratacionKodeEsquema = esquemaContratacionKodeEsquema;
    }

    public Long getEstatusLaboralId() {
        return estatusLaboralId;
    }

    public void setEstatusLaboralId(Long estatusLaboralId) {
        this.estatusLaboralId = estatusLaboralId;
    }

    public String getEstatusLaboralEstatus() {
        return estatusLaboralEstatus;
    }

    public void setEstatusLaboralEstatus(String estatusLaboralEstatus) {
        this.estatusLaboralEstatus = estatusLaboralEstatus;
    }

    public Long getColoniaId() {
        return coloniaId;
    }

    public void setColoniaId(Long coloniaId) {
        this.coloniaId = coloniaId;
    }

    public String getColoniaColonia() {
        return coloniaColonia;
    }

    public void setColoniaColonia(String coloniaColonia) {
        this.coloniaColonia = coloniaColonia;
    }

    public Long getAntecedentesEsquemaContratacionId() {
        return antecedentesEsquemaContratacionId;
    }

    public void setAntecedentesEsquemaContratacionId(Long esqContRecId) {
        this.antecedentesEsquemaContratacionId = esqContRecId;
    }

    public String getAntecedentesEsquemaContratacionEsquema() {
        return antecedentesEsquemaContratacionEsquema;
    }

    public void setAntecedentesEsquemaContratacionEsquema(String esqContRecEsquema) {
        this.antecedentesEsquemaContratacionEsquema = esqContRecEsquema;
    }

    public Long getEstudiosId() {
        return estudiosId;
    }

    public void setEstudiosId(Long formacionAcademicaId) {
        this.estudiosId = formacionAcademicaId;
    }

    public String getEstudiosFormacionAcademica() {
        return estudiosFormacionAcademica;
    }

    public void setEstudiosFormacionAcademica(String formacionAcademicaFormacionAcademica) {
        this.estudiosFormacionAcademica = formacionAcademicaFormacionAcademica;
    }

    public Long getEstCanInactivoId() {
        return estCanInactivoId;
    }

    public void setEstCanInactivoId(Long estCanInactivoId) {
        this.estCanInactivoId = estCanInactivoId;
    }

    public String getEstCanInactivoMotivo() {
        return estCanInactivoMotivo;
    }

    public void setEstCanInactivoMotivo(String estCanInactivoMotivo) {
        this.estCanInactivoMotivo = estCanInactivoMotivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidatoDTO candidatoDTO = (CandidatoDTO) o;
        if (candidatoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidatoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidatoDTO{" +
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
            ", disponibilidadEntrevistaPeriodoTiempo=" + getDisponibilidadEntrevistaPeriodoTiempoId() +
            ", disponibilidadEntrevistaPeriodoTiempo='" + getDisponibilidadEntrevistaPeriodoTiempoPeriodo() + "'" +
            ", disponibilidadAsignacionPeriodoTiempo=" + getDisponibilidadAsignacionPeriodoTiempoId() +
            ", disponibilidadAsignacionPeriodoTiempo='" + getDisponibilidadAsignacionPeriodoTiempoPeriodo() + "'" +
            ", usuarioCreador=" + getUsuarioCreadorId() +
            ", usuarioCreador='" + getUsuarioCreadorFirstName() + "'" +
            ", usuarioAsignado=" + getUsuarioAsignadoId() +
            ", usuarioAsignado='" + getUsuarioAsignadoFirstName() + "'" +
            ", documento=" + getDocumentoId() +
            ", documento='" + getDocumentoDocumento() + "'" +
            ", fuenteReclutamiento=" + getFuenteReclutamientoId() +
            ", fuenteReclutamiento='" + getFuenteReclutamientoFuente() + "'" +
            ", estatusCandidato=" + getEstatusCandidatoId() +
            ", estatusCandidato='" + getEstatusCandidatoEstatus() + "'" +
            ", perfil=" + getPerfilId() +
            ", perfil='" + getPerfilPerfil() + "'" +
            ", nivelPerfil=" + getNivelPerfilId() +
            ", nivelPerfil='" + getNivelPerfilNivel() + "'" +
            ", institucionAcademica=" + getInstitucionAcademicaId() +
            ", institucionAcademica='" + getInstitucionAcademicaInstitucion() + "'" +
            ", estatusAcademico=" + getEstatusAcademicoId() +
            ", estatusAcademico='" + getEstatusAcademicoEstatus() + "'" +
            ", esquemaContratacionKode=" + getEsquemaContratacionKodeId() +
            ", esquemaContratacionKode='" + getEsquemaContratacionKodeEsquema() + "'" +
            ", estatusLaboral=" + getEstatusLaboralId() +
            ", estatusLaboral='" + getEstatusLaboralEstatus() + "'" +
            ", colonia=" + getColoniaId() +
            ", colonia='" + getColoniaColonia() + "'" +
            ", antecedentesEsquemaContratacion=" + getAntecedentesEsquemaContratacionId() +
            ", antecedentesEsquemaContratacion='" + getAntecedentesEsquemaContratacionEsquema() + "'" +
            ", estudios=" + getEstudiosId() +
            ", estudios='" + getEstudiosFormacionAcademica() + "'" +
            ", estCanInactivo=" + getEstCanInactivoId() +
            ", estCanInactivo='" + getEstCanInactivoMotivo() + "'" +
            "}";
    }
}
