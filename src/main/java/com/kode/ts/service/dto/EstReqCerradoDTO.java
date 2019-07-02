package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.EstReqCerrado} entity.
 */
public class EstReqCerradoDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String motivo;


    private Long estatusRequerimientoId;

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

    public Long getEstatusRequerimientoId() {
        return estatusRequerimientoId;
    }

    public void setEstatusRequerimientoId(Long estatusRequerimientoId) {
        this.estatusRequerimientoId = estatusRequerimientoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstReqCerradoDTO estReqCerradoDTO = (EstReqCerradoDTO) o;
        if (estReqCerradoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estReqCerradoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstReqCerradoDTO{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            ", estatusRequerimiento=" + getEstatusRequerimientoId() +
            "}";
    }
}
