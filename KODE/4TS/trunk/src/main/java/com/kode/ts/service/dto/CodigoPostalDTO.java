package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.CodigoPostal} entity.
 */
public class CodigoPostalDTO implements Serializable {

    private Long id;

    @Size(max = 5)
    private String codigoPostal;


    private Long municipioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CodigoPostalDTO codigoPostalDTO = (CodigoPostalDTO) o;
        if (codigoPostalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codigoPostalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CodigoPostalDTO{" +
            "id=" + getId() +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", municipio=" + getMunicipioId() +
            "}";
    }
}
