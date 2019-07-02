package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.EstatusRequerimiento} entity.
 */
public class EstatusRequerimientoDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String estatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstatusRequerimientoDTO estatusRequerimientoDTO = (EstatusRequerimientoDTO) o;
        if (estatusRequerimientoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estatusRequerimientoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstatusRequerimientoDTO{" +
            "id=" + getId() +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
