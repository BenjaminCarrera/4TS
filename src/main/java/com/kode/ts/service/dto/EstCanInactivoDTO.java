package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.EstCanInactivo} entity.
 */
public class EstCanInactivoDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String motivo;


    private Long estatusCandidatoId;

    private String estatusCandidatoEstatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstCanInactivoDTO estCanInactivoDTO = (EstCanInactivoDTO) o;
        if (estCanInactivoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estCanInactivoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstCanInactivoDTO{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            ", estatusCandidato=" + getEstatusCandidatoId() +
            ", estatusCandidato='" + getEstatusCandidatoEstatus() + "'" +
            "}";
    }
}
