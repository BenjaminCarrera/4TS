package com.kode.ts.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Bitacora} entity.
 */
public class BitacoraDTO implements Serializable {

    private Long id;

    private ZonedDateTime fecha;

    @Size(max = 500)
    private String comentario;


    private Long usuarioId;

    private String usuarioFirstName;

    private Long requerimientoId;

    private String requerimientoProyecto;

    private Long candidatoId;

    private String candidatoNombre;

    private Long tareaId;

    private String tareaTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }

    public String getUsuarioFirstName() {
        return usuarioFirstName;
    }

    public void setUsuarioFirstName(String userFirstName) {
        this.usuarioFirstName = userFirstName;
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

    public Long getTareaId() {
        return tareaId;
    }

    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }

    public String getTareaTitulo() {
        return tareaTitulo;
    }

    public void setTareaTitulo(String tareaTitulo) {
        this.tareaTitulo = tareaTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BitacoraDTO bitacoraDTO = (BitacoraDTO) o;
        if (bitacoraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bitacoraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BitacoraDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", usuario=" + getUsuarioId() +
            ", usuario='" + getUsuarioFirstName() + "'" +
            ", requerimiento=" + getRequerimientoId() +
            ", requerimiento='" + getRequerimientoProyecto() + "'" +
            ", candidato=" + getCandidatoId() +
            ", candidato='" + getCandidatoNombre() + "'" +
            ", tarea=" + getTareaId() +
            ", tarea='" + getTareaTitulo() + "'" +
            "}";
    }
}
