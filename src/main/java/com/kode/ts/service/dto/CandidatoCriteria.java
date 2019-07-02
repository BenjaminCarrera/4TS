package com.kode.ts.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.kode.ts.domain.enumeration.Sexo;
import com.kode.ts.domain.enumeration.EstadoCivil;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.kode.ts.domain.Candidato} entity. This class is used
 * in {@link com.kode.ts.web.rest.CandidatoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /candidatoes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CandidatoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Sexo
     */
    public static class SexoFilter extends Filter<Sexo> {

        public SexoFilter() {
        }

        public SexoFilter(SexoFilter filter) {
            super(filter);
        }

        @Override
        public SexoFilter copy() {
            return new SexoFilter(this);
        }

    }
    /**
     * Class for filtering EstadoCivil
     */
    public static class EstadoCivilFilter extends Filter<EstadoCivil> {

        public EstadoCivilFilter() {
        }

        public EstadoCivilFilter(EstadoCivilFilter filter) {
            super(filter);
        }

        @Override
        public EstadoCivilFilter copy() {
            return new EstadoCivilFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter anosExperiencia;

    private StringFilter nombre;

    private StringFilter apellidoPaterno;

    private StringFilter apellidoMaterno;

    private LocalDateFilter fechaNacimiento;

    private IntegerFilter edad;

    private StringFilter emailPrincipal;

    private StringFilter emailAdicional;

    private StringFilter emailAsignacion;

    private StringFilter emailKode;

    private StringFilter web;

    private StringFilter telefonoCasa;

    private StringFilter telefonoTrabajo;

    private StringFilter telefonoCelular;

    private StringFilter telefonoAdicional;

    private FloatFilter coorLat;

    private FloatFilter coorLong;

    private StringFilter dirCodigoPostal;

    private StringFilter dirCalle;

    private StringFilter noExt;

    private StringFilter noInt;

    private FloatFilter salarioNeto;

    private FloatFilter costoTotal;

    private IntegerFilter contatoDuracionMinima;

    private LocalDateFilter disponibilidadEntrevistaFecha;

    private IntegerFilter disponibilidadEntrevistaPeriodo;

    private LocalDateFilter disponibilidadAsignacionFecha;

    private StringFilter disponibilidadAsignacionPeriodo;

    private StringFilter antecedenteUltimoEmpleador;

    private IntegerFilter antecedenteSalarioNeto;

    private StringFilter notas;

    private IntegerFilter porcentajeIngles;

    private StringFilter curp;

    private StringFilter rfc;

    private StringFilter nss;

    private SexoFilter sexo;

    private EstadoCivilFilter estadoCivil;

    private InstantFilter fechaAlta;

    private InstantFilter fechaUltimoSeguimiento;

    private StringFilter foto;

    private LongFilter referenciasLaboralesId;

    private LongFilter skillCandidatoId;

    private LongFilter tareaId;

    private LongFilter bitacoraId;

    private LongFilter disponibilidadEntrevistaPeriodoTiempoId;

    private LongFilter disponibilidadAsignacionPeriodoTiempoId;

    private LongFilter usuarioCreadorId;

    private LongFilter usuarioAsignadoId;

    private LongFilter documentoId;

    private LongFilter cuentaInteresId;

    private LongFilter cuentaRechazadasId;

    private LongFilter fuenteReclutamientoId;

    private LongFilter estatusCandidatoId;

    private LongFilter perfilId;

    private LongFilter nivelPerfilId;

    private LongFilter institucionAcademicaId;

    private LongFilter estatusAcademicoId;

    private LongFilter esquemaContratacionKodeId;

    private LongFilter estatusLaboralId;

    private LongFilter coloniaId;

    private LongFilter antecedentesEsquemaContratacionId;

    private LongFilter estudiosId;

    private LongFilter estCanInactivoId;

    public CandidatoCriteria(){
    }

    public CandidatoCriteria(CandidatoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.anosExperiencia = other.anosExperiencia == null ? null : other.anosExperiencia.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apellidoPaterno = other.apellidoPaterno == null ? null : other.apellidoPaterno.copy();
        this.apellidoMaterno = other.apellidoMaterno == null ? null : other.apellidoMaterno.copy();
        this.fechaNacimiento = other.fechaNacimiento == null ? null : other.fechaNacimiento.copy();
        this.edad = other.edad == null ? null : other.edad.copy();
        this.emailPrincipal = other.emailPrincipal == null ? null : other.emailPrincipal.copy();
        this.emailAdicional = other.emailAdicional == null ? null : other.emailAdicional.copy();
        this.emailAsignacion = other.emailAsignacion == null ? null : other.emailAsignacion.copy();
        this.emailKode = other.emailKode == null ? null : other.emailKode.copy();
        this.web = other.web == null ? null : other.web.copy();
        this.telefonoCasa = other.telefonoCasa == null ? null : other.telefonoCasa.copy();
        this.telefonoTrabajo = other.telefonoTrabajo == null ? null : other.telefonoTrabajo.copy();
        this.telefonoCelular = other.telefonoCelular == null ? null : other.telefonoCelular.copy();
        this.telefonoAdicional = other.telefonoAdicional == null ? null : other.telefonoAdicional.copy();
        this.coorLat = other.coorLat == null ? null : other.coorLat.copy();
        this.coorLong = other.coorLong == null ? null : other.coorLong.copy();
        this.dirCodigoPostal = other.dirCodigoPostal == null ? null : other.dirCodigoPostal.copy();
        this.dirCalle = other.dirCalle == null ? null : other.dirCalle.copy();
        this.noExt = other.noExt == null ? null : other.noExt.copy();
        this.noInt = other.noInt == null ? null : other.noInt.copy();
        this.salarioNeto = other.salarioNeto == null ? null : other.salarioNeto.copy();
        this.costoTotal = other.costoTotal == null ? null : other.costoTotal.copy();
        this.contatoDuracionMinima = other.contatoDuracionMinima == null ? null : other.contatoDuracionMinima.copy();
        this.disponibilidadEntrevistaFecha = other.disponibilidadEntrevistaFecha == null ? null : other.disponibilidadEntrevistaFecha.copy();
        this.disponibilidadEntrevistaPeriodo = other.disponibilidadEntrevistaPeriodo == null ? null : other.disponibilidadEntrevistaPeriodo.copy();
        this.disponibilidadAsignacionFecha = other.disponibilidadAsignacionFecha == null ? null : other.disponibilidadAsignacionFecha.copy();
        this.disponibilidadAsignacionPeriodo = other.disponibilidadAsignacionPeriodo == null ? null : other.disponibilidadAsignacionPeriodo.copy();
        this.antecedenteUltimoEmpleador = other.antecedenteUltimoEmpleador == null ? null : other.antecedenteUltimoEmpleador.copy();
        this.antecedenteSalarioNeto = other.antecedenteSalarioNeto == null ? null : other.antecedenteSalarioNeto.copy();
        this.notas = other.notas == null ? null : other.notas.copy();
        this.porcentajeIngles = other.porcentajeIngles == null ? null : other.porcentajeIngles.copy();
        this.curp = other.curp == null ? null : other.curp.copy();
        this.rfc = other.rfc == null ? null : other.rfc.copy();
        this.nss = other.nss == null ? null : other.nss.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.estadoCivil = other.estadoCivil == null ? null : other.estadoCivil.copy();
        this.fechaAlta = other.fechaAlta == null ? null : other.fechaAlta.copy();
        this.fechaUltimoSeguimiento = other.fechaUltimoSeguimiento == null ? null : other.fechaUltimoSeguimiento.copy();
        this.foto = other.foto == null ? null : other.foto.copy();
        this.referenciasLaboralesId = other.referenciasLaboralesId == null ? null : other.referenciasLaboralesId.copy();
        this.skillCandidatoId = other.skillCandidatoId == null ? null : other.skillCandidatoId.copy();
        this.tareaId = other.tareaId == null ? null : other.tareaId.copy();
        this.bitacoraId = other.bitacoraId == null ? null : other.bitacoraId.copy();
        this.disponibilidadEntrevistaPeriodoTiempoId = other.disponibilidadEntrevistaPeriodoTiempoId == null ? null : other.disponibilidadEntrevistaPeriodoTiempoId.copy();
        this.disponibilidadAsignacionPeriodoTiempoId = other.disponibilidadAsignacionPeriodoTiempoId == null ? null : other.disponibilidadAsignacionPeriodoTiempoId.copy();
        this.usuarioCreadorId = other.usuarioCreadorId == null ? null : other.usuarioCreadorId.copy();
        this.usuarioAsignadoId = other.usuarioAsignadoId == null ? null : other.usuarioAsignadoId.copy();
        this.documentoId = other.documentoId == null ? null : other.documentoId.copy();
        this.cuentaInteresId = other.cuentaInteresId == null ? null : other.cuentaInteresId.copy();
        this.cuentaRechazadasId = other.cuentaRechazadasId == null ? null : other.cuentaRechazadasId.copy();
        this.fuenteReclutamientoId = other.fuenteReclutamientoId == null ? null : other.fuenteReclutamientoId.copy();
        this.estatusCandidatoId = other.estatusCandidatoId == null ? null : other.estatusCandidatoId.copy();
        this.perfilId = other.perfilId == null ? null : other.perfilId.copy();
        this.nivelPerfilId = other.nivelPerfilId == null ? null : other.nivelPerfilId.copy();
        this.institucionAcademicaId = other.institucionAcademicaId == null ? null : other.institucionAcademicaId.copy();
        this.estatusAcademicoId = other.estatusAcademicoId == null ? null : other.estatusAcademicoId.copy();
        this.esquemaContratacionKodeId = other.esquemaContratacionKodeId == null ? null : other.esquemaContratacionKodeId.copy();
        this.estatusLaboralId = other.estatusLaboralId == null ? null : other.estatusLaboralId.copy();
        this.coloniaId = other.coloniaId == null ? null : other.coloniaId.copy();
        this.antecedentesEsquemaContratacionId = other.antecedentesEsquemaContratacionId == null ? null : other.antecedentesEsquemaContratacionId.copy();
        this.estudiosId = other.estudiosId == null ? null : other.estudiosId.copy();
        this.estCanInactivoId = other.estCanInactivoId == null ? null : other.estCanInactivoId.copy();
    }

    @Override
    public CandidatoCriteria copy() {
        return new CandidatoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(FloatFilter anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(StringFilter apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public StringFilter getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(StringFilter apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public LocalDateFilter getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateFilter fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public IntegerFilter getEdad() {
        return edad;
    }

    public void setEdad(IntegerFilter edad) {
        this.edad = edad;
    }

    public StringFilter getEmailPrincipal() {
        return emailPrincipal;
    }

    public void setEmailPrincipal(StringFilter emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }

    public StringFilter getEmailAdicional() {
        return emailAdicional;
    }

    public void setEmailAdicional(StringFilter emailAdicional) {
        this.emailAdicional = emailAdicional;
    }

    public StringFilter getEmailAsignacion() {
        return emailAsignacion;
    }

    public void setEmailAsignacion(StringFilter emailAsignacion) {
        this.emailAsignacion = emailAsignacion;
    }

    public StringFilter getEmailKode() {
        return emailKode;
    }

    public void setEmailKode(StringFilter emailKode) {
        this.emailKode = emailKode;
    }

    public StringFilter getWeb() {
        return web;
    }

    public void setWeb(StringFilter web) {
        this.web = web;
    }

    public StringFilter getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(StringFilter telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public StringFilter getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public void setTelefonoTrabajo(StringFilter telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public StringFilter getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(StringFilter telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public StringFilter getTelefonoAdicional() {
        return telefonoAdicional;
    }

    public void setTelefonoAdicional(StringFilter telefonoAdicional) {
        this.telefonoAdicional = telefonoAdicional;
    }

    public FloatFilter getCoorLat() {
        return coorLat;
    }

    public void setCoorLat(FloatFilter coorLat) {
        this.coorLat = coorLat;
    }

    public FloatFilter getCoorLong() {
        return coorLong;
    }

    public void setCoorLong(FloatFilter coorLong) {
        this.coorLong = coorLong;
    }

    public StringFilter getDirCodigoPostal() {
        return dirCodigoPostal;
    }

    public void setDirCodigoPostal(StringFilter dirCodigoPostal) {
        this.dirCodigoPostal = dirCodigoPostal;
    }

    public StringFilter getDirCalle() {
        return dirCalle;
    }

    public void setDirCalle(StringFilter dirCalle) {
        this.dirCalle = dirCalle;
    }

    public StringFilter getNoExt() {
        return noExt;
    }

    public void setNoExt(StringFilter noExt) {
        this.noExt = noExt;
    }

    public StringFilter getNoInt() {
        return noInt;
    }

    public void setNoInt(StringFilter noInt) {
        this.noInt = noInt;
    }

    public FloatFilter getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(FloatFilter salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public FloatFilter getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(FloatFilter costoTotal) {
        this.costoTotal = costoTotal;
    }

    public IntegerFilter getContatoDuracionMinima() {
        return contatoDuracionMinima;
    }

    public void setContatoDuracionMinima(IntegerFilter contatoDuracionMinima) {
        this.contatoDuracionMinima = contatoDuracionMinima;
    }

    public LocalDateFilter getDisponibilidadEntrevistaFecha() {
        return disponibilidadEntrevistaFecha;
    }

    public void setDisponibilidadEntrevistaFecha(LocalDateFilter disponibilidadEntrevistaFecha) {
        this.disponibilidadEntrevistaFecha = disponibilidadEntrevistaFecha;
    }

    public IntegerFilter getDisponibilidadEntrevistaPeriodo() {
        return disponibilidadEntrevistaPeriodo;
    }

    public void setDisponibilidadEntrevistaPeriodo(IntegerFilter disponibilidadEntrevistaPeriodo) {
        this.disponibilidadEntrevistaPeriodo = disponibilidadEntrevistaPeriodo;
    }

    public LocalDateFilter getDisponibilidadAsignacionFecha() {
        return disponibilidadAsignacionFecha;
    }

    public void setDisponibilidadAsignacionFecha(LocalDateFilter disponibilidadAsignacionFecha) {
        this.disponibilidadAsignacionFecha = disponibilidadAsignacionFecha;
    }

    public StringFilter getDisponibilidadAsignacionPeriodo() {
        return disponibilidadAsignacionPeriodo;
    }

    public void setDisponibilidadAsignacionPeriodo(StringFilter disponibilidadAsignacionPeriodo) {
        this.disponibilidadAsignacionPeriodo = disponibilidadAsignacionPeriodo;
    }

    public StringFilter getAntecedenteUltimoEmpleador() {
        return antecedenteUltimoEmpleador;
    }

    public void setAntecedenteUltimoEmpleador(StringFilter antecedenteUltimoEmpleador) {
        this.antecedenteUltimoEmpleador = antecedenteUltimoEmpleador;
    }

    public IntegerFilter getAntecedenteSalarioNeto() {
        return antecedenteSalarioNeto;
    }

    public void setAntecedenteSalarioNeto(IntegerFilter antecedenteSalarioNeto) {
        this.antecedenteSalarioNeto = antecedenteSalarioNeto;
    }

    public StringFilter getNotas() {
        return notas;
    }

    public void setNotas(StringFilter notas) {
        this.notas = notas;
    }

    public IntegerFilter getPorcentajeIngles() {
        return porcentajeIngles;
    }

    public void setPorcentajeIngles(IntegerFilter porcentajeIngles) {
        this.porcentajeIngles = porcentajeIngles;
    }

    public StringFilter getCurp() {
        return curp;
    }

    public void setCurp(StringFilter curp) {
        this.curp = curp;
    }

    public StringFilter getRfc() {
        return rfc;
    }

    public void setRfc(StringFilter rfc) {
        this.rfc = rfc;
    }

    public StringFilter getNss() {
        return nss;
    }

    public void setNss(StringFilter nss) {
        this.nss = nss;
    }

    public SexoFilter getSexo() {
        return sexo;
    }

    public void setSexo(SexoFilter sexo) {
        this.sexo = sexo;
    }

    public EstadoCivilFilter getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilFilter estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public InstantFilter getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(InstantFilter fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public InstantFilter getFechaUltimoSeguimiento() {
        return fechaUltimoSeguimiento;
    }

    public void setFechaUltimoSeguimiento(InstantFilter fechaUltimoSeguimiento) {
        this.fechaUltimoSeguimiento = fechaUltimoSeguimiento;
    }

    public StringFilter getFoto() {
        return foto;
    }

    public void setFoto(StringFilter foto) {
        this.foto = foto;
    }

    public LongFilter getReferenciasLaboralesId() {
        return referenciasLaboralesId;
    }

    public void setReferenciasLaboralesId(LongFilter referenciasLaboralesId) {
        this.referenciasLaboralesId = referenciasLaboralesId;
    }

    public LongFilter getSkillCandidatoId() {
        return skillCandidatoId;
    }

    public void setSkillCandidatoId(LongFilter skillCandidatoId) {
        this.skillCandidatoId = skillCandidatoId;
    }

    public LongFilter getTareaId() {
        return tareaId;
    }

    public void setTareaId(LongFilter tareaId) {
        this.tareaId = tareaId;
    }

    public LongFilter getBitacoraId() {
        return bitacoraId;
    }

    public void setBitacoraId(LongFilter bitacoraId) {
        this.bitacoraId = bitacoraId;
    }

    public LongFilter getDisponibilidadEntrevistaPeriodoTiempoId() {
        return disponibilidadEntrevistaPeriodoTiempoId;
    }

    public void setDisponibilidadEntrevistaPeriodoTiempoId(LongFilter disponibilidadEntrevistaPeriodoTiempoId) {
        this.disponibilidadEntrevistaPeriodoTiempoId = disponibilidadEntrevistaPeriodoTiempoId;
    }

    public LongFilter getDisponibilidadAsignacionPeriodoTiempoId() {
        return disponibilidadAsignacionPeriodoTiempoId;
    }

    public void setDisponibilidadAsignacionPeriodoTiempoId(LongFilter disponibilidadAsignacionPeriodoTiempoId) {
        this.disponibilidadAsignacionPeriodoTiempoId = disponibilidadAsignacionPeriodoTiempoId;
    }

    public LongFilter getUsuarioCreadorId() {
        return usuarioCreadorId;
    }

    public void setUsuarioCreadorId(LongFilter usuarioCreadorId) {
        this.usuarioCreadorId = usuarioCreadorId;
    }

    public LongFilter getUsuarioAsignadoId() {
        return usuarioAsignadoId;
    }

    public void setUsuarioAsignadoId(LongFilter usuarioAsignadoId) {
        this.usuarioAsignadoId = usuarioAsignadoId;
    }

    public LongFilter getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(LongFilter documentoId) {
        this.documentoId = documentoId;
    }

    public LongFilter getCuentaInteresId() {
        return cuentaInteresId;
    }

    public void setCuentaInteresId(LongFilter cuentaInteresId) {
        this.cuentaInteresId = cuentaInteresId;
    }

    public LongFilter getCuentaRechazadasId() {
        return cuentaRechazadasId;
    }

    public void setCuentaRechazadasId(LongFilter cuentaRechazadasId) {
        this.cuentaRechazadasId = cuentaRechazadasId;
    }

    public LongFilter getFuenteReclutamientoId() {
        return fuenteReclutamientoId;
    }

    public void setFuenteReclutamientoId(LongFilter fuenteReclutamientoId) {
        this.fuenteReclutamientoId = fuenteReclutamientoId;
    }

    public LongFilter getEstatusCandidatoId() {
        return estatusCandidatoId;
    }

    public void setEstatusCandidatoId(LongFilter estatusCandidatoId) {
        this.estatusCandidatoId = estatusCandidatoId;
    }

    public LongFilter getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(LongFilter perfilId) {
        this.perfilId = perfilId;
    }

    public LongFilter getNivelPerfilId() {
        return nivelPerfilId;
    }

    public void setNivelPerfilId(LongFilter nivelPerfilId) {
        this.nivelPerfilId = nivelPerfilId;
    }

    public LongFilter getInstitucionAcademicaId() {
        return institucionAcademicaId;
    }

    public void setInstitucionAcademicaId(LongFilter institucionAcademicaId) {
        this.institucionAcademicaId = institucionAcademicaId;
    }

    public LongFilter getEstatusAcademicoId() {
        return estatusAcademicoId;
    }

    public void setEstatusAcademicoId(LongFilter estatusAcademicoId) {
        this.estatusAcademicoId = estatusAcademicoId;
    }

    public LongFilter getEsquemaContratacionKodeId() {
        return esquemaContratacionKodeId;
    }

    public void setEsquemaContratacionKodeId(LongFilter esquemaContratacionKodeId) {
        this.esquemaContratacionKodeId = esquemaContratacionKodeId;
    }

    public LongFilter getEstatusLaboralId() {
        return estatusLaboralId;
    }

    public void setEstatusLaboralId(LongFilter estatusLaboralId) {
        this.estatusLaboralId = estatusLaboralId;
    }

    public LongFilter getColoniaId() {
        return coloniaId;
    }

    public void setColoniaId(LongFilter coloniaId) {
        this.coloniaId = coloniaId;
    }

    public LongFilter getAntecedentesEsquemaContratacionId() {
        return antecedentesEsquemaContratacionId;
    }

    public void setAntecedentesEsquemaContratacionId(LongFilter antecedentesEsquemaContratacionId) {
        this.antecedentesEsquemaContratacionId = antecedentesEsquemaContratacionId;
    }

    public LongFilter getEstudiosId() {
        return estudiosId;
    }

    public void setEstudiosId(LongFilter estudiosId) {
        this.estudiosId = estudiosId;
    }

    public LongFilter getEstCanInactivoId() {
        return estCanInactivoId;
    }

    public void setEstCanInactivoId(LongFilter estCanInactivoId) {
        this.estCanInactivoId = estCanInactivoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CandidatoCriteria that = (CandidatoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(anosExperiencia, that.anosExperiencia) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellidoPaterno, that.apellidoPaterno) &&
            Objects.equals(apellidoMaterno, that.apellidoMaterno) &&
            Objects.equals(fechaNacimiento, that.fechaNacimiento) &&
            Objects.equals(edad, that.edad) &&
            Objects.equals(emailPrincipal, that.emailPrincipal) &&
            Objects.equals(emailAdicional, that.emailAdicional) &&
            Objects.equals(emailAsignacion, that.emailAsignacion) &&
            Objects.equals(emailKode, that.emailKode) &&
            Objects.equals(web, that.web) &&
            Objects.equals(telefonoCasa, that.telefonoCasa) &&
            Objects.equals(telefonoTrabajo, that.telefonoTrabajo) &&
            Objects.equals(telefonoCelular, that.telefonoCelular) &&
            Objects.equals(telefonoAdicional, that.telefonoAdicional) &&
            Objects.equals(coorLat, that.coorLat) &&
            Objects.equals(coorLong, that.coorLong) &&
            Objects.equals(dirCodigoPostal, that.dirCodigoPostal) &&
            Objects.equals(dirCalle, that.dirCalle) &&
            Objects.equals(noExt, that.noExt) &&
            Objects.equals(noInt, that.noInt) &&
            Objects.equals(salarioNeto, that.salarioNeto) &&
            Objects.equals(costoTotal, that.costoTotal) &&
            Objects.equals(contatoDuracionMinima, that.contatoDuracionMinima) &&
            Objects.equals(disponibilidadEntrevistaFecha, that.disponibilidadEntrevistaFecha) &&
            Objects.equals(disponibilidadEntrevistaPeriodo, that.disponibilidadEntrevistaPeriodo) &&
            Objects.equals(disponibilidadAsignacionFecha, that.disponibilidadAsignacionFecha) &&
            Objects.equals(disponibilidadAsignacionPeriodo, that.disponibilidadAsignacionPeriodo) &&
            Objects.equals(antecedenteUltimoEmpleador, that.antecedenteUltimoEmpleador) &&
            Objects.equals(antecedenteSalarioNeto, that.antecedenteSalarioNeto) &&
            Objects.equals(notas, that.notas) &&
            Objects.equals(porcentajeIngles, that.porcentajeIngles) &&
            Objects.equals(curp, that.curp) &&
            Objects.equals(rfc, that.rfc) &&
            Objects.equals(nss, that.nss) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(estadoCivil, that.estadoCivil) &&
            Objects.equals(fechaAlta, that.fechaAlta) &&
            Objects.equals(fechaUltimoSeguimiento, that.fechaUltimoSeguimiento) &&
            Objects.equals(foto, that.foto) &&
            Objects.equals(referenciasLaboralesId, that.referenciasLaboralesId) &&
            Objects.equals(skillCandidatoId, that.skillCandidatoId) &&
            Objects.equals(tareaId, that.tareaId) &&
            Objects.equals(bitacoraId, that.bitacoraId) &&
            Objects.equals(disponibilidadEntrevistaPeriodoTiempoId, that.disponibilidadEntrevistaPeriodoTiempoId) &&
            Objects.equals(disponibilidadAsignacionPeriodoTiempoId, that.disponibilidadAsignacionPeriodoTiempoId) &&
            Objects.equals(usuarioCreadorId, that.usuarioCreadorId) &&
            Objects.equals(usuarioAsignadoId, that.usuarioAsignadoId) &&
            Objects.equals(documentoId, that.documentoId) &&
            Objects.equals(cuentaInteresId, that.cuentaInteresId) &&
            Objects.equals(cuentaRechazadasId, that.cuentaRechazadasId) &&
            Objects.equals(fuenteReclutamientoId, that.fuenteReclutamientoId) &&
            Objects.equals(estatusCandidatoId, that.estatusCandidatoId) &&
            Objects.equals(perfilId, that.perfilId) &&
            Objects.equals(nivelPerfilId, that.nivelPerfilId) &&
            Objects.equals(institucionAcademicaId, that.institucionAcademicaId) &&
            Objects.equals(estatusAcademicoId, that.estatusAcademicoId) &&
            Objects.equals(esquemaContratacionKodeId, that.esquemaContratacionKodeId) &&
            Objects.equals(estatusLaboralId, that.estatusLaboralId) &&
            Objects.equals(coloniaId, that.coloniaId) &&
            Objects.equals(antecedentesEsquemaContratacionId, that.antecedentesEsquemaContratacionId) &&
            Objects.equals(estudiosId, that.estudiosId) &&
            Objects.equals(estCanInactivoId, that.estCanInactivoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        anosExperiencia,
        nombre,
        apellidoPaterno,
        apellidoMaterno,
        fechaNacimiento,
        edad,
        emailPrincipal,
        emailAdicional,
        emailAsignacion,
        emailKode,
        web,
        telefonoCasa,
        telefonoTrabajo,
        telefonoCelular,
        telefonoAdicional,
        coorLat,
        coorLong,
        dirCodigoPostal,
        dirCalle,
        noExt,
        noInt,
        salarioNeto,
        costoTotal,
        contatoDuracionMinima,
        disponibilidadEntrevistaFecha,
        disponibilidadEntrevistaPeriodo,
        disponibilidadAsignacionFecha,
        disponibilidadAsignacionPeriodo,
        antecedenteUltimoEmpleador,
        antecedenteSalarioNeto,
        notas,
        porcentajeIngles,
        curp,
        rfc,
        nss,
        sexo,
        estadoCivil,
        fechaAlta,
        fechaUltimoSeguimiento,
        foto,
        referenciasLaboralesId,
        skillCandidatoId,
        tareaId,
        bitacoraId,
        disponibilidadEntrevistaPeriodoTiempoId,
        disponibilidadAsignacionPeriodoTiempoId,
        usuarioCreadorId,
        usuarioAsignadoId,
        documentoId,
        cuentaInteresId,
        cuentaRechazadasId,
        fuenteReclutamientoId,
        estatusCandidatoId,
        perfilId,
        nivelPerfilId,
        institucionAcademicaId,
        estatusAcademicoId,
        esquemaContratacionKodeId,
        estatusLaboralId,
        coloniaId,
        antecedentesEsquemaContratacionId,
        estudiosId,
        estCanInactivoId
        );
    }

    @Override
    public String toString() {
        return "CandidatoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (anosExperiencia != null ? "anosExperiencia=" + anosExperiencia + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (apellidoPaterno != null ? "apellidoPaterno=" + apellidoPaterno + ", " : "") +
                (apellidoMaterno != null ? "apellidoMaterno=" + apellidoMaterno + ", " : "") +
                (fechaNacimiento != null ? "fechaNacimiento=" + fechaNacimiento + ", " : "") +
                (edad != null ? "edad=" + edad + ", " : "") +
                (emailPrincipal != null ? "emailPrincipal=" + emailPrincipal + ", " : "") +
                (emailAdicional != null ? "emailAdicional=" + emailAdicional + ", " : "") +
                (emailAsignacion != null ? "emailAsignacion=" + emailAsignacion + ", " : "") +
                (emailKode != null ? "emailKode=" + emailKode + ", " : "") +
                (web != null ? "web=" + web + ", " : "") +
                (telefonoCasa != null ? "telefonoCasa=" + telefonoCasa + ", " : "") +
                (telefonoTrabajo != null ? "telefonoTrabajo=" + telefonoTrabajo + ", " : "") +
                (telefonoCelular != null ? "telefonoCelular=" + telefonoCelular + ", " : "") +
                (telefonoAdicional != null ? "telefonoAdicional=" + telefonoAdicional + ", " : "") +
                (coorLat != null ? "coorLat=" + coorLat + ", " : "") +
                (coorLong != null ? "coorLong=" + coorLong + ", " : "") +
                (dirCodigoPostal != null ? "dirCodigoPostal=" + dirCodigoPostal + ", " : "") +
                (dirCalle != null ? "dirCalle=" + dirCalle + ", " : "") +
                (noExt != null ? "noExt=" + noExt + ", " : "") +
                (noInt != null ? "noInt=" + noInt + ", " : "") +
                (salarioNeto != null ? "salarioNeto=" + salarioNeto + ", " : "") +
                (costoTotal != null ? "costoTotal=" + costoTotal + ", " : "") +
                (contatoDuracionMinima != null ? "contatoDuracionMinima=" + contatoDuracionMinima + ", " : "") +
                (disponibilidadEntrevistaFecha != null ? "disponibilidadEntrevistaFecha=" + disponibilidadEntrevistaFecha + ", " : "") +
                (disponibilidadEntrevistaPeriodo != null ? "disponibilidadEntrevistaPeriodo=" + disponibilidadEntrevistaPeriodo + ", " : "") +
                (disponibilidadAsignacionFecha != null ? "disponibilidadAsignacionFecha=" + disponibilidadAsignacionFecha + ", " : "") +
                (disponibilidadAsignacionPeriodo != null ? "disponibilidadAsignacionPeriodo=" + disponibilidadAsignacionPeriodo + ", " : "") +
                (antecedenteUltimoEmpleador != null ? "antecedenteUltimoEmpleador=" + antecedenteUltimoEmpleador + ", " : "") +
                (antecedenteSalarioNeto != null ? "antecedenteSalarioNeto=" + antecedenteSalarioNeto + ", " : "") +
                (notas != null ? "notas=" + notas + ", " : "") +
                (porcentajeIngles != null ? "porcentajeIngles=" + porcentajeIngles + ", " : "") +
                (curp != null ? "curp=" + curp + ", " : "") +
                (rfc != null ? "rfc=" + rfc + ", " : "") +
                (nss != null ? "nss=" + nss + ", " : "") +
                (sexo != null ? "sexo=" + sexo + ", " : "") +
                (estadoCivil != null ? "estadoCivil=" + estadoCivil + ", " : "") +
                (fechaAlta != null ? "fechaAlta=" + fechaAlta + ", " : "") +
                (fechaUltimoSeguimiento != null ? "fechaUltimoSeguimiento=" + fechaUltimoSeguimiento + ", " : "") +
                (foto != null ? "foto=" + foto + ", " : "") +
                (referenciasLaboralesId != null ? "referenciasLaboralesId=" + referenciasLaboralesId + ", " : "") +
                (skillCandidatoId != null ? "skillCandidatoId=" + skillCandidatoId + ", " : "") +
                (tareaId != null ? "tareaId=" + tareaId + ", " : "") +
                (bitacoraId != null ? "bitacoraId=" + bitacoraId + ", " : "") +
                (disponibilidadEntrevistaPeriodoTiempoId != null ? "disponibilidadEntrevistaPeriodoTiempoId=" + disponibilidadEntrevistaPeriodoTiempoId + ", " : "") +
                (disponibilidadAsignacionPeriodoTiempoId != null ? "disponibilidadAsignacionPeriodoTiempoId=" + disponibilidadAsignacionPeriodoTiempoId + ", " : "") +
                (usuarioCreadorId != null ? "usuarioCreadorId=" + usuarioCreadorId + ", " : "") +
                (usuarioAsignadoId != null ? "usuarioAsignadoId=" + usuarioAsignadoId + ", " : "") +
                (documentoId != null ? "documentoId=" + documentoId + ", " : "") +
                (cuentaInteresId != null ? "cuentaInteresId=" + cuentaInteresId + ", " : "") +
                (cuentaRechazadasId != null ? "cuentaRechazadasId=" + cuentaRechazadasId + ", " : "") +
                (fuenteReclutamientoId != null ? "fuenteReclutamientoId=" + fuenteReclutamientoId + ", " : "") +
                (estatusCandidatoId != null ? "estatusCandidatoId=" + estatusCandidatoId + ", " : "") +
                (perfilId != null ? "perfilId=" + perfilId + ", " : "") +
                (nivelPerfilId != null ? "nivelPerfilId=" + nivelPerfilId + ", " : "") +
                (institucionAcademicaId != null ? "institucionAcademicaId=" + institucionAcademicaId + ", " : "") +
                (estatusAcademicoId != null ? "estatusAcademicoId=" + estatusAcademicoId + ", " : "") +
                (esquemaContratacionKodeId != null ? "esquemaContratacionKodeId=" + esquemaContratacionKodeId + ", " : "") +
                (estatusLaboralId != null ? "estatusLaboralId=" + estatusLaboralId + ", " : "") +
                (coloniaId != null ? "coloniaId=" + coloniaId + ", " : "") +
                (antecedentesEsquemaContratacionId != null ? "antecedentesEsquemaContratacionId=" + antecedentesEsquemaContratacionId + ", " : "") +
                (estudiosId != null ? "estudiosId=" + estudiosId + ", " : "") +
                (estCanInactivoId != null ? "estCanInactivoId=" + estCanInactivoId + ", " : "") +
            "}";
    }

}
