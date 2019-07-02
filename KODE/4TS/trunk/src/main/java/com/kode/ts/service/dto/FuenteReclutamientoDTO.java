package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.FuenteReclutamiento} entity.
 */
public class FuenteReclutamientoDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String fuente;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FuenteReclutamientoDTO fuenteReclutamientoDTO = (FuenteReclutamientoDTO) o;
        if (fuenteReclutamientoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fuenteReclutamientoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FuenteReclutamientoDTO{" +
            "id=" + getId() +
            ", fuente='" + getFuente() + "'" +
            "}";
    }
}
