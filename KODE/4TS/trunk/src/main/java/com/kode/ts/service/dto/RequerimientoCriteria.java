package com.kode.ts.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.kode.ts.domain.Requerimiento} entity. This class is used
 * in {@link com.kode.ts.web.rest.RequerimientoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /requerimientos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequerimientoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fechaAlda;

    private InstantFilter fechaResolucion;

    private StringFilter remplazoDe;

    private IntegerFilter vacantesSolicitadas;

    private StringFilter proyecto;

    private StringFilter nombreContacto;

    private FloatFilter tarifaSueldoNet;

    private StringFilter prestaciones;

    private IntegerFilter duracionAsignacion;

    private StringFilter lugarTrabajo;

    private FloatFilter coorLat;

    private FloatFilter coorLong;

    private StringFilter horario;

    private StringFilter informacionExamen;

    private StringFilter informacionAdicional;

    private LongFilter skillRequerimientoId;

    private LongFilter tareaId;

    private LongFilter bitacoraId;

    private LongFilter cuentaId;

    private LongFilter subCuentaId;

    private LongFilter usuarioCreadorId;

    private LongFilter usuarioAsignadoId;

    private LongFilter estatusRequerimientoId;

    private LongFilter prioridadId;

    private LongFilter tipoSolicitudId;

    private LongFilter tipoIngresoId;

    private LongFilter baseTarifaId;

    private LongFilter esquemaContratacionId;

    private LongFilter perfilId;

    private LongFilter nivelPerfilId;

    private LongFilter estatusReqCanId;

    private LongFilter tipoPeriodoId;

    public RequerimientoCriteria(){
    }

    public RequerimientoCriteria(RequerimientoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fechaAlda = other.fechaAlda == null ? null : other.fechaAlda.copy();
        this.fechaResolucion = other.fechaResolucion == null ? null : other.fechaResolucion.copy();
        this.remplazoDe = other.remplazoDe == null ? null : other.remplazoDe.copy();
        this.vacantesSolicitadas = other.vacantesSolicitadas == null ? null : other.vacantesSolicitadas.copy();
        this.proyecto = other.proyecto == null ? null : other.proyecto.copy();
        this.nombreContacto = other.nombreContacto == null ? null : other.nombreContacto.copy();
        this.tarifaSueldoNet = other.tarifaSueldoNet == null ? null : other.tarifaSueldoNet.copy();
        this.prestaciones = other.prestaciones == null ? null : other.prestaciones.copy();
        this.duracionAsignacion = other.duracionAsignacion == null ? null : other.duracionAsignacion.copy();
        this.lugarTrabajo = other.lugarTrabajo == null ? null : other.lugarTrabajo.copy();
        this.coorLat = other.coorLat == null ? null : other.coorLat.copy();
        this.coorLong = other.coorLong == null ? null : other.coorLong.copy();
        this.horario = other.horario == null ? null : other.horario.copy();
        this.informacionExamen = other.informacionExamen == null ? null : other.informacionExamen.copy();
        this.informacionAdicional = other.informacionAdicional == null ? null : other.informacionAdicional.copy();
        this.skillRequerimientoId = other.skillRequerimientoId == null ? null : other.skillRequerimientoId.copy();
        this.tareaId = other.tareaId == null ? null : other.tareaId.copy();
        this.bitacoraId = other.bitacoraId == null ? null : other.bitacoraId.copy();
        this.cuentaId = other.cuentaId == null ? null : other.cuentaId.copy();
        this.subCuentaId = other.subCuentaId == null ? null : other.subCuentaId.copy();
        this.usuarioCreadorId = other.usuarioCreadorId == null ? null : other.usuarioCreadorId.copy();
        this.usuarioAsignadoId = other.usuarioAsignadoId == null ? null : other.usuarioAsignadoId.copy();
        this.estatusRequerimientoId = other.estatusRequerimientoId == null ? null : other.estatusRequerimientoId.copy();
        this.prioridadId = other.prioridadId == null ? null : other.prioridadId.copy();
        this.tipoSolicitudId = other.tipoSolicitudId == null ? null : other.tipoSolicitudId.copy();
        this.tipoIngresoId = other.tipoIngresoId == null ? null : other.tipoIngresoId.copy();
        this.baseTarifaId = other.baseTarifaId == null ? null : other.baseTarifaId.copy();
        this.esquemaContratacionId = other.esquemaContratacionId == null ? null : other.esquemaContratacionId.copy();
        this.perfilId = other.perfilId == null ? null : other.perfilId.copy();
        this.nivelPerfilId = other.nivelPerfilId == null ? null : other.nivelPerfilId.copy();
        this.estatusReqCanId = other.estatusReqCanId == null ? null : other.estatusReqCanId.copy();
        this.tipoPeriodoId = other.tipoPeriodoId == null ? null : other.tipoPeriodoId.copy();
    }

    @Override
    public RequerimientoCriteria copy() {
        return new RequerimientoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getFechaAlda() {
        return fechaAlda;
    }

    public void setFechaAlda(InstantFilter fechaAlda) {
        this.fechaAlda = fechaAlda;
    }

    public InstantFilter getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(InstantFilter fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public StringFilter getRemplazoDe() {
        return remplazoDe;
    }

    public void setRemplazoDe(StringFilter remplazoDe) {
        this.remplazoDe = remplazoDe;
    }

    public IntegerFilter getVacantesSolicitadas() {
        return vacantesSolicitadas;
    }

    public void setVacantesSolicitadas(IntegerFilter vacantesSolicitadas) {
        this.vacantesSolicitadas = vacantesSolicitadas;
    }

    public StringFilter getProyecto() {
        return proyecto;
    }

    public void setProyecto(StringFilter proyecto) {
        this.proyecto = proyecto;
    }

    public StringFilter getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(StringFilter nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public FloatFilter getTarifaSueldoNet() {
        return tarifaSueldoNet;
    }

    public void setTarifaSueldoNet(FloatFilter tarifaSueldoNet) {
        this.tarifaSueldoNet = tarifaSueldoNet;
    }

    public StringFilter getPrestaciones() {
        return prestaciones;
    }

    public void setPrestaciones(StringFilter prestaciones) {
        this.prestaciones = prestaciones;
    }

    public IntegerFilter getDuracionAsignacion() {
        return duracionAsignacion;
    }

    public void setDuracionAsignacion(IntegerFilter duracionAsignacion) {
        this.duracionAsignacion = duracionAsignacion;
    }

    public StringFilter getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(StringFilter lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
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

    public StringFilter getHorario() {
        return horario;
    }

    public void setHorario(StringFilter horario) {
        this.horario = horario;
    }

    public StringFilter getInformacionExamen() {
        return informacionExamen;
    }

    public void setInformacionExamen(StringFilter informacionExamen) {
        this.informacionExamen = informacionExamen;
    }

    public StringFilter getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(StringFilter informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public LongFilter getSkillRequerimientoId() {
        return skillRequerimientoId;
    }

    public void setSkillRequerimientoId(LongFilter skillRequerimientoId) {
        this.skillRequerimientoId = skillRequerimientoId;
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

    public LongFilter getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(LongFilter cuentaId) {
        this.cuentaId = cuentaId;
    }

    public LongFilter getSubCuentaId() {
        return subCuentaId;
    }

    public void setSubCuentaId(LongFilter subCuentaId) {
        this.subCuentaId = subCuentaId;
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

    public LongFilter getEstatusRequerimientoId() {
        return estatusRequerimientoId;
    }

    public void setEstatusRequerimientoId(LongFilter estatusRequerimientoId) {
        this.estatusRequerimientoId = estatusRequerimientoId;
    }

    public LongFilter getPrioridadId() {
        return prioridadId;
    }

    public void setPrioridadId(LongFilter prioridadId) {
        this.prioridadId = prioridadId;
    }

    public LongFilter getTipoSolicitudId() {
        return tipoSolicitudId;
    }

    public void setTipoSolicitudId(LongFilter tipoSolicitudId) {
        this.tipoSolicitudId = tipoSolicitudId;
    }

    public LongFilter getTipoIngresoId() {
        return tipoIngresoId;
    }

    public void setTipoIngresoId(LongFilter tipoIngresoId) {
        this.tipoIngresoId = tipoIngresoId;
    }

    public LongFilter getBaseTarifaId() {
        return baseTarifaId;
    }

    public void setBaseTarifaId(LongFilter baseTarifaId) {
        this.baseTarifaId = baseTarifaId;
    }

    public LongFilter getEsquemaContratacionId() {
        return esquemaContratacionId;
    }

    public void setEsquemaContratacionId(LongFilter esquemaContratacionId) {
        this.esquemaContratacionId = esquemaContratacionId;
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

    public LongFilter getEstatusReqCanId() {
        return estatusReqCanId;
    }

    public void setEstatusReqCanId(LongFilter estatusReqCanId) {
        this.estatusReqCanId = estatusReqCanId;
    }

    public LongFilter getTipoPeriodoId() {
        return tipoPeriodoId;
    }

    public void setTipoPeriodoId(LongFilter tipoPeriodoId) {
        this.tipoPeriodoId = tipoPeriodoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequerimientoCriteria that = (RequerimientoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fechaAlda, that.fechaAlda) &&
            Objects.equals(fechaResolucion, that.fechaResolucion) &&
            Objects.equals(remplazoDe, that.remplazoDe) &&
            Objects.equals(vacantesSolicitadas, that.vacantesSolicitadas) &&
            Objects.equals(proyecto, that.proyecto) &&
            Objects.equals(nombreContacto, that.nombreContacto) &&
            Objects.equals(tarifaSueldoNet, that.tarifaSueldoNet) &&
            Objects.equals(prestaciones, that.prestaciones) &&
            Objects.equals(duracionAsignacion, that.duracionAsignacion) &&
            Objects.equals(lugarTrabajo, that.lugarTrabajo) &&
            Objects.equals(coorLat, that.coorLat) &&
            Objects.equals(coorLong, that.coorLong) &&
            Objects.equals(horario, that.horario) &&
            Objects.equals(informacionExamen, that.informacionExamen) &&
            Objects.equals(informacionAdicional, that.informacionAdicional) &&
            Objects.equals(skillRequerimientoId, that.skillRequerimientoId) &&
            Objects.equals(tareaId, that.tareaId) &&
            Objects.equals(bitacoraId, that.bitacoraId) &&
            Objects.equals(cuentaId, that.cuentaId) &&
            Objects.equals(subCuentaId, that.subCuentaId) &&
            Objects.equals(usuarioCreadorId, that.usuarioCreadorId) &&
            Objects.equals(usuarioAsignadoId, that.usuarioAsignadoId) &&
            Objects.equals(estatusRequerimientoId, that.estatusRequerimientoId) &&
            Objects.equals(prioridadId, that.prioridadId) &&
            Objects.equals(tipoSolicitudId, that.tipoSolicitudId) &&
            Objects.equals(tipoIngresoId, that.tipoIngresoId) &&
            Objects.equals(baseTarifaId, that.baseTarifaId) &&
            Objects.equals(esquemaContratacionId, that.esquemaContratacionId) &&
            Objects.equals(perfilId, that.perfilId) &&
            Objects.equals(nivelPerfilId, that.nivelPerfilId) &&
            Objects.equals(estatusReqCanId, that.estatusReqCanId) &&
            Objects.equals(tipoPeriodoId, that.tipoPeriodoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fechaAlda,
        fechaResolucion,
        remplazoDe,
        vacantesSolicitadas,
        proyecto,
        nombreContacto,
        tarifaSueldoNet,
        prestaciones,
        duracionAsignacion,
        lugarTrabajo,
        coorLat,
        coorLong,
        horario,
        informacionExamen,
        informacionAdicional,
        skillRequerimientoId,
        tareaId,
        bitacoraId,
        cuentaId,
        subCuentaId,
        usuarioCreadorId,
        usuarioAsignadoId,
        estatusRequerimientoId,
        prioridadId,
        tipoSolicitudId,
        tipoIngresoId,
        baseTarifaId,
        esquemaContratacionId,
        perfilId,
        nivelPerfilId,
        estatusReqCanId,
        tipoPeriodoId
        );
    }

    @Override
    public String toString() {
        return "RequerimientoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fechaAlda != null ? "fechaAlda=" + fechaAlda + ", " : "") +
                (fechaResolucion != null ? "fechaResolucion=" + fechaResolucion + ", " : "") +
                (remplazoDe != null ? "remplazoDe=" + remplazoDe + ", " : "") +
                (vacantesSolicitadas != null ? "vacantesSolicitadas=" + vacantesSolicitadas + ", " : "") +
                (proyecto != null ? "proyecto=" + proyecto + ", " : "") +
                (nombreContacto != null ? "nombreContacto=" + nombreContacto + ", " : "") +
                (tarifaSueldoNet != null ? "tarifaSueldoNet=" + tarifaSueldoNet + ", " : "") +
                (prestaciones != null ? "prestaciones=" + prestaciones + ", " : "") +
                (duracionAsignacion != null ? "duracionAsignacion=" + duracionAsignacion + ", " : "") +
                (lugarTrabajo != null ? "lugarTrabajo=" + lugarTrabajo + ", " : "") +
                (coorLat != null ? "coorLat=" + coorLat + ", " : "") +
                (coorLong != null ? "coorLong=" + coorLong + ", " : "") +
                (horario != null ? "horario=" + horario + ", " : "") +
                (informacionExamen != null ? "informacionExamen=" + informacionExamen + ", " : "") +
                (informacionAdicional != null ? "informacionAdicional=" + informacionAdicional + ", " : "") +
                (skillRequerimientoId != null ? "skillRequerimientoId=" + skillRequerimientoId + ", " : "") +
                (tareaId != null ? "tareaId=" + tareaId + ", " : "") +
                (bitacoraId != null ? "bitacoraId=" + bitacoraId + ", " : "") +
                (cuentaId != null ? "cuentaId=" + cuentaId + ", " : "") +
                (subCuentaId != null ? "subCuentaId=" + subCuentaId + ", " : "") +
                (usuarioCreadorId != null ? "usuarioCreadorId=" + usuarioCreadorId + ", " : "") +
                (usuarioAsignadoId != null ? "usuarioAsignadoId=" + usuarioAsignadoId + ", " : "") +
                (estatusRequerimientoId != null ? "estatusRequerimientoId=" + estatusRequerimientoId + ", " : "") +
                (prioridadId != null ? "prioridadId=" + prioridadId + ", " : "") +
                (tipoSolicitudId != null ? "tipoSolicitudId=" + tipoSolicitudId + ", " : "") +
                (tipoIngresoId != null ? "tipoIngresoId=" + tipoIngresoId + ", " : "") +
                (baseTarifaId != null ? "baseTarifaId=" + baseTarifaId + ", " : "") +
                (esquemaContratacionId != null ? "esquemaContratacionId=" + esquemaContratacionId + ", " : "") +
                (perfilId != null ? "perfilId=" + perfilId + ", " : "") +
                (nivelPerfilId != null ? "nivelPerfilId=" + nivelPerfilId + ", " : "") +
                (estatusReqCanId != null ? "estatusReqCanId=" + estatusReqCanId + ", " : "") +
                (tipoPeriodoId != null ? "tipoPeriodoId=" + tipoPeriodoId + ", " : "") +
            "}";
    }

}
