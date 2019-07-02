package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.CodigoPostal} entity.
 */
public class CodigoPostalDTO implements Serializable {

    private Long id;

    @Size(max = 5)
    private String codigoPostal;


    private Set<MunicipioDTO> municipios = new HashSet<>();

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

    public Set<MunicipioDTO> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(Set<MunicipioDTO> municipios) {
        this.municipios = municipios;
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
            "}";
    }
}
