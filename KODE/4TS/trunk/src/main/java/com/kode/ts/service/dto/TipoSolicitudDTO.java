package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.TipoSolicitud} entity.
 */
public class TipoSolicitudDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String solicitud;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoSolicitudDTO tipoSolicitudDTO = (TipoSolicitudDTO) o;
        if (tipoSolicitudDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoSolicitudDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoSolicitudDTO{" +
            "id=" + getId() +
            ", solicitud='" + getSolicitud() + "'" +
            "}";
    }
}
