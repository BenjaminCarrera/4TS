package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Tarea} entity.
 */
public class TareaDTO implements Serializable {

    private Long id;

    @Size(max = 300)
    private String descripcion;

    @Size(max = 100)
    private String titulo;


    private Long usuarioCreadorId;

    private String usuarioCreadorFirstName;

    private Long usuarioEjecutorId;

    private String usuarioEjecutorFirstName;

    private Long requerimientoId;

    private String requerimientoProyecto;

    private Long candidatoId;

    private String candidatoNombre;

    private Long estatusTareaId;

    private String estatusTareaEstatus;

    private Long tipoTareaId;

    private String tipoTareaTipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Long getUsuarioEjecutorId() {
        return usuarioEjecutorId;
    }

    public void setUsuarioEjecutorId(Long userId) {
        this.usuarioEjecutorId = userId;
    }

    public String getUsuarioEjecutorFirstName() {
        return usuarioEjecutorFirstName;
    }

    public void setUsuarioEjecutorFirstName(String userFirstName) {
        this.usuarioEjecutorFirstName = userFirstName;
    }

    public Long getRequerimientoId() {
        return requerimientoId;
    }

    public void setRequerimientoId(Long requerimientoId) {
        this.requerimientoId = requerimientoId;
    }

    public String getRequerimientoProyecto() {
        return requerimientoProyecto;
    }

    public void setRequerimientoProyecto(String requerimientoProyecto) {
        this.requerimientoProyecto = requerimientoProyecto;
    }

    public Long getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(Long candidatoId) {
        this.candidatoId = candidatoId;
    }

    public String getCandidatoNombre() {
        return candidatoNombre;
    }

    public void setCandidatoNombre(String candidatoNombre) {
        this.candidatoNombre = candidatoNombre;
    }

    public Long getEstatusTareaId() {
        return estatusTareaId;
    }

    public void setEstatusTareaId(Long estatusTareaId) {
        this.estatusTareaId = estatusTareaId;
    }

    public String getEstatusTareaEstatus() {
        return estatusTareaEstatus;
    }

    public void setEstatusTareaEstatus(String estatusTareaEstatus) {
        this.estatusTareaEstatus = estatusTareaEstatus;
    }

    public Long getTipoTareaId() {
        return tipoTareaId;
    }

    public void setTipoTareaId(Long tipoTareaId) {
        this.tipoTareaId = tipoTareaId;
    }

    public String getTipoTareaTipo() {
        return tipoTareaTipo;
    }

    public void setTipoTareaTipo(String tipoTareaTipo) {
        this.tipoTareaTipo = tipoTareaTipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TareaDTO tareaDTO = (TareaDTO) o;
        if (tareaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tareaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TareaDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", usuarioCreador=" + getUsuarioCreadorId() +
            ", usuarioCreador='" + getUsuarioCreadorFirstName() + "'" +
            ", usuarioEjecutor=" + getUsuarioEjecutorId() +
            ", usuarioEjecutor='" + getUsuarioEjecutorFirstName() + "'" +
            ", requerimiento=" + getRequerimientoId() +
            ", requerimiento='" + getRequerimientoProyecto() + "'" +
            ", candidato=" + getCandidatoId() +
            ", candidato='" + getCandidatoNombre() + "'" +
            ", estatusTarea=" + getEstatusTareaId() +
            ", estatusTarea='" + getEstatusTareaEstatus() + "'" +
            ", tipoTarea=" + getTipoTareaId() +
            ", tipoTarea='" + getTipoTareaTipo() + "'" +
            "}";
    }
}
