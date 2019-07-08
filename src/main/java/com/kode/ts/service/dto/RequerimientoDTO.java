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

    private String cuentaClave;

    private Long subCuentaId;

    private String subCuentaClave;

    private Long usuarioCreadorId;

    private String usuarioCreadorFirstName;

    private Long usuarioAsignadoId;

    private String usuarioAsignadoFirstName;

    private Long estatusRequerimientoId;

    private String estatusRequerimientoEstatus;

    private Long prioridadId;

    private String prioridadPrioridad;

    private Long tipoSolicitudId;

    private String tipoSolicitudSolicitud;

    private Long tipoIngresoId;

    private String tipoIngresoTipo;

    private Long baseTarifaId;

    private String baseTarifaBase;

    private Long esquemaContratacionId;

    private String esquemaContratacionEsquema;

    private Long perfilId;

    private String perfilPerfil;

    private Long nivelPerfilId;

    private String nivelPerfilNivel;

    private Long estatusReqCanId;

    private String estatusReqCanMotivo;

    private Long tipoPeriodoId;

    private String tipoPeriodoPeriodo;

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

    public String getCuentaClave() {
        return cuentaClave;
    }

    public void setCuentaClave(String cuentaClave) {
        this.cuentaClave = cuentaClave;
    }

    public Long getSubCuentaId() {
        return subCuentaId;
    }

    public void setSubCuentaId(Long cuentaId) {
        this.subCuentaId = cuentaId;
    }

    public String getSubCuentaClave() {
        return subCuentaClave;
    }

    public void setSubCuentaClave(String cuentaClave) {
        this.subCuentaClave = cuentaClave;
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

    public Long getEstatusRequerimientoId() {
        return estatusRequerimientoId;
    }

    public void setEstatusRequerimientoId(Long estatusRequerimientoId) {
        this.estatusRequerimientoId = estatusRequerimientoId;
    }

    public String getEstatusRequerimientoEstatus() {
        return estatusRequerimientoEstatus;
    }

    public void setEstatusRequerimientoEstatus(String estatusRequerimientoEstatus) {
        this.estatusRequerimientoEstatus = estatusRequerimientoEstatus;
    }

    public Long getPrioridadId() {
        return prioridadId;
    }

    public void setPrioridadId(Long prioridadReqId) {
        this.prioridadId = prioridadReqId;
    }

    public String getPrioridadPrioridad() {
        return prioridadPrioridad;
    }

    public void setPrioridadPrioridad(String prioridadReqPrioridad) {
        this.prioridadPrioridad = prioridadReqPrioridad;
    }

    public Long getTipoSolicitudId() {
        return tipoSolicitudId;
    }

    public void setTipoSolicitudId(Long tipoSolicitudId) {
        this.tipoSolicitudId = tipoSolicitudId;
    }

    public String getTipoSolicitudSolicitud() {
        return tipoSolicitudSolicitud;
    }

    public void setTipoSolicitudSolicitud(String tipoSolicitudSolicitud) {
        this.tipoSolicitudSolicitud = tipoSolicitudSolicitud;
    }

    public Long getTipoIngresoId() {
        return tipoIngresoId;
    }

    public void setTipoIngresoId(Long tipoIngresoId) {
        this.tipoIngresoId = tipoIngresoId;
    }

    public String getTipoIngresoTipo() {
        return tipoIngresoTipo;
    }

    public void setTipoIngresoTipo(String tipoIngresoTipo) {
        this.tipoIngresoTipo = tipoIngresoTipo;
    }

    public Long getBaseTarifaId() {
        return baseTarifaId;
    }

    public void setBaseTarifaId(Long baseTarifaId) {
        this.baseTarifaId = baseTarifaId;
    }

    public String getBaseTarifaBase() {
        return baseTarifaBase;
    }

    public void setBaseTarifaBase(String baseTarifaBase) {
        this.baseTarifaBase = baseTarifaBase;
    }

    public Long getEsquemaContratacionId() {
        return esquemaContratacionId;
    }

    public void setEsquemaContratacionId(Long esqContRecId) {
        this.esquemaContratacionId = esqContRecId;
    }

    public String getEsquemaContratacionEsquema() {
        return esquemaContratacionEsquema;
    }

    public void setEsquemaContratacionEsquema(String esqContRecEsquema) {
        this.esquemaContratacionEsquema = esqContRecEsquema;
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

    public Long getEstatusReqCanId() {
        return estatusReqCanId;
    }

    public void setEstatusReqCanId(Long estReqCerradoId) {
        this.estatusReqCanId = estReqCerradoId;
    }

    public String getEstatusReqCanMotivo() {
        return estatusReqCanMotivo;
    }

    public void setEstatusReqCanMotivo(String estReqCerradoMotivo) {
        this.estatusReqCanMotivo = estReqCerradoMotivo;
    }

    public Long getTipoPeriodoId() {
        return tipoPeriodoId;
    }

    public void setTipoPeriodoId(Long tipoPeriodoId) {
        this.tipoPeriodoId = tipoPeriodoId;
    }

    public String getTipoPeriodoPeriodo() {
        return tipoPeriodoPeriodo;
    }

    public void setTipoPeriodoPeriodo(String tipoPeriodoPeriodo) {
        this.tipoPeriodoPeriodo = tipoPeriodoPeriodo;
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
            ", cuenta='" + getCuentaClave() + "'" +
            ", subCuenta=" + getSubCuentaId() +
            ", subCuenta='" + getSubCuentaClave() + "'" +
            ", usuarioCreador=" + getUsuarioCreadorId() +
            ", usuarioCreador='" + getUsuarioCreadorFirstName() + "'" +
            ", usuarioAsignado=" + getUsuarioAsignadoId() +
            ", usuarioAsignado='" + getUsuarioAsignadoFirstName() + "'" +
            ", estatusRequerimiento=" + getEstatusRequerimientoId() +
            ", estatusRequerimiento='" + getEstatusRequerimientoEstatus() + "'" +
            ", prioridad=" + getPrioridadId() +
            ", prioridad='" + getPrioridadPrioridad() + "'" +
            ", tipoSolicitud=" + getTipoSolicitudId() +
            ", tipoSolicitud='" + getTipoSolicitudSolicitud() + "'" +
            ", tipoIngreso=" + getTipoIngresoId() +
            ", tipoIngreso='" + getTipoIngresoTipo() + "'" +
            ", baseTarifa=" + getBaseTarifaId() +
            ", baseTarifa='" + getBaseTarifaBase() + "'" +
            ", esquemaContratacion=" + getEsquemaContratacionId() +
            ", esquemaContratacion='" + getEsquemaContratacionEsquema() + "'" +
            ", perfil=" + getPerfilId() +
            ", perfil='" + getPerfilPerfil() + "'" +
            ", nivelPerfil=" + getNivelPerfilId() +
            ", nivelPerfil='" + getNivelPerfilNivel() + "'" +
            ", estatusReqCan=" + getEstatusReqCanId() +
            ", estatusReqCan='" + getEstatusReqCanMotivo() + "'" +
            ", tipoPeriodo=" + getTipoPeriodoId() +
            ", tipoPeriodo='" + getTipoPeriodoPeriodo() + "'" +
            "}";
    }
}
