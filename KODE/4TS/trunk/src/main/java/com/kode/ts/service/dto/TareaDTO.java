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

    private Long usuarioEjecutorId;

    private Long requerimientoId;

    private Long candidatoId;

    private Long estatusTareaId;

    private Long tipoTareaId;

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

    public Long getUsuarioEjecutorId() {
        return usuarioEjecutorId;
    }

    public void setUsuarioEjecutorId(Long userId) {
        this.usuarioEjecutorId = userId;
    }

    public Long getRequerimientoId() {
        return requerimientoId;
    }

    public void setRequerimientoId(Long requerimientoId) {
        this.requerimientoId = requerimientoId;
    }

    public Long getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(Long candidatoId) {
        this.candidatoId = candidatoId;
    }

    public Long getEstatusTareaId() {
        return estatusTareaId;
    }

    public void setEstatusTareaId(Long estatusTareaId) {
        this.estatusTareaId = estatusTareaId;
    }

    public Long getTipoTareaId() {
        return tipoTareaId;
    }

    public void setTipoTareaId(Long tipoTareaId) {
        this.tipoTareaId = tipoTareaId;
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
            ", usuarioEjecutor=" + getUsuarioEjecutorId() +
            ", requerimiento=" + getRequerimientoId() +
            ", candidato=" + getCandidatoId() +
            ", estatusTarea=" + getEstatusTareaId() +
            ", tipoTarea=" + getTipoTareaId() +
            "}";
    }
}
