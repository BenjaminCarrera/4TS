package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.PrioridadReq} entity.
 */
public class PrioridadReqDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String prioridad;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrioridadReqDTO prioridadReqDTO = (PrioridadReqDTO) o;
        if (prioridadReqDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prioridadReqDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrioridadReqDTO{" +
            "id=" + getId() +
            ", prioridad='" + getPrioridad() + "'" +
            "}";
    }
}
