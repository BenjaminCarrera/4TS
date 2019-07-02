package com.kode.ts.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Requerimiento} entity.
 */
public class RequerimientoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fechaAlda;

    private Instant fechaResolucion;

    @Size(max = 500)
    private String remplazoDe;

    private Integer vacantesSolicitadas;

    @Size(max = 200)
    private String proyecto;

    @Size(max = 100)
    private String nombreContacto;

    private Float tarifaSueldoNet;

    @Size(max = 500)
    private String prestaciones;

    private Integer duracionAsignacion;

    @Size(max = 500)
    private String lugarTrabajo;

    private Float coorLat;

    private Float coorLong;

    @Size(max = 300)
    private String horario;

    @Size(max = 500)
    private String informacionExamen;

    @Size(max = 1000)
    private String informacionAdicional;


    private Long cuentaId;

    private Long subCuentaId;

    private Long usuarioCreadorId;

    private Long usuarioAsignadoId;

    private Long estatusRequerimientoId;

    private Long prioridadId;

    private Long tipoSolicitudId;

    private Long tipoIngresoId;

    private Long baseTarifaId;

    private Long esquemaContratacionId;

    private Long perfilId;

    private Long nivelPerfilId;

    private Long estatusReqCanId;

    private Long tipoPeriodoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaAlda() {
        return fechaAlda;
    }

    public void setFechaAlda(Instant fechaAlda) {
        this.fechaAlda = fechaAlda;
    }

    public Instant getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Instant fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getRemplazoDe() {
        return remplazoDe;
    }

    public void setRemplazoDe(String remplazoDe) {
        this.remplazoDe = remplazoDe;
    }

    public Integer getVacantesSolicitadas() {
        return vacantesSolicitadas;
    }

    public void setVacantesSolicitadas(Integer vacantesSolicitadas) {
        this.vacantesSolicitadas = vacantesSolicitadas;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public Float getTarifaSueldoNet() {
        return tarifaSueldoNet;
    }

    public void setTarifaSueldoNet(Float tarifaSueldoNet) {
        this.tarifaSueldoNet = tarifaSueldoNet;
    }

    public String getPrestaciones() {
        return prestaciones;
    }

    public void setPrestaciones(String prestaciones) {
        this.prestaciones = prestaciones;
    }

    public Integer getDuracionAsignacion() {
        return duracionAsignacion;
    }

    public void setDuracionAsignacion(Integer duracionAsignacion) {
        this.duracionAsignacion = duracionAsignacion;
    }

    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getInformacionExamen() {
        return informacionExamen;
    }

    public void setInformacionExamen(String informacionExamen) {
        this.informacionExamen = informacionExamen;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Long getSubCuentaId() {
        return subCuentaId;
    }

    public void setSubCuentaId(Long cuentaId) {
        this.subCuentaId = cuentaId;
    }

    public Long getUsuarioCreadorId() {
        return usuarioCreadorId;
    }

    public void setUsuarioCreadorId(Long userId) {
        this.usuarioCreadorId = userId;
    }

    public Long getUsuarioAsignadoId() {
        return usuarioAsignadoId;
    }

    public void setUsuarioAsignadoId(Long userId) {
        this.usuarioAsignadoId = userId;
    }

    public Long getEstatusRequerimientoId() {
        return estatusRequerimientoId;
    }

    public void setEstatusRequerimientoId(Long estatusRequerimientoId) {
        this.estatusRequerimientoId = estatusRequerimientoId;
    }

    public Long getPrioridadId() {
        return prioridadId;
    }

    public void setPrioridadId(Long prioridadReqId) {
        this.prioridadId = prioridadReqId;
    }

    public Long getTipoSolicitudId() {
        return tipoSolicitudId;
    }

    public void setTipoSolicitudId(Long tipoSolicitudId) {
        this.tipoSolicitudId = tipoSolicitudId;
    }

    public Long getTipoIngresoId() {
        return tipoIngresoId;
    }

    public void setTipoIngresoId(Long tipoIngresoId) {
        this.tipoIngresoId = tipoIngresoId;
    }

    public Long getBaseTarifaId() {
        return baseTarifaId;
    }

    public void setBaseTarifaId(Long baseTarifaId) {
        this.baseTarifaId = baseTarifaId;
    }

    public Long getEsquemaContratacionId() {
        return esquemaContratacionId;
    }

    public void setEsquemaContratacionId(Long esqContRecId) {
        this.esquemaContratacionId = esqContRecId;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public Long getNivelPerfilId() {
        return nivelPerfilId;
    }

    public void setNivelPerfilId(Long nivelPerfilId) {
        this.nivelPerfilId = nivelPerfilId;
    }

    public Long getEstatusReqCanId() {
        return estatusReqCanId;
    }

    public void setEstatusReqCanId(Long estReqCerradoId) {
        this.estatusReqCanId = estReqCerradoId;
    }

    public Long getTipoPeriodoId() {
        return tipoPeriodoId;
    }

    public void setTipoPeriodoId(Long tipoPeriodoId) {
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

        RequerimientoDTO requerimientoDTO = (RequerimientoDTO) o;
        if (requerimientoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requerimientoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequerimientoDTO{" +
            "id=" + getId() +
            ", fechaAlda='" + getFechaAlda() + "'" +
            ", fechaResolucion='" + getFechaResolucion() + "'" +
            ", remplazoDe='" + getRemplazoDe() + "'" +
            ", vacantesSolicitadas=" + getVacantesSolicitadas() +
            ", proyecto='" + getProyecto() + "'" +
            ", nombreContacto='" + getNombreContacto() + "'" +
            ", tarifaSueldoNet=" + getTarifaSueldoNet() +
            ", prestaciones='" + getPrestaciones() + "'" +
            ", duracionAsignacion=" + getDuracionAsignacion() +
            ", lugarTrabajo='" + getLugarTrabajo() + "'" +
            ", coorLat=" + getCoorLat() +
            ", coorLong=" + getCoorLong() +
            ", horario='" + getHorario() + "'" +
            ", informacionExamen='" + getInformacionExamen() + "'" +
            ", informacionAdicional='" + getInformacionAdicional() + "'" +
            ", cuenta=" + getCuentaId() +
            ", subCuenta=" + getSubCuentaId() +
            ", usuarioCreador=" + getUsuarioCreadorId() +
            ", usuarioAsignado=" + getUsuarioAsignadoId() +
            ", estatusRequerimiento=" + getEstatusRequerimientoId() +
            ", prioridad=" + getPrioridadId() +
            ", tipoSolicitud=" + getTipoSolicitudId() +
            ", tipoIngreso=" + getTipoIngresoId() +
            ", baseTarifa=" + getBaseTarifaId() +
            ", esquemaContratacion=" + getEsquemaContratacionId() +
            ", perfil=" + getPerfilId() +
            ", nivelPerfil=" + getNivelPerfilId() +
            ", estatusReqCan=" + getEstatusReqCanId() +
            ", tipoPeriodo=" + getTipoPeriodoId() +
            "}";
    }
}
