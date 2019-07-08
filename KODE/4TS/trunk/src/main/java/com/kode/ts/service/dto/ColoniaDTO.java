package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Colonia} entity.
 */
public class ColoniaDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String colonia;


    private Long municipioId;

    private Long codigoPostalId;

    private String codigoPostalCodigoPostal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    public Long getCodigoPostalId() {
        return codigoPostalId;
    }

    public void setCodigoPostalId(Long codigoPostalId) {
        this.codigoPostalId = codigoPostalId;
    }

    public String getCodigoPostalCodigoPostal() {
        return codigoPostalCodigoPostal;
    }

    public void setCodigoPostalCodigoPostal(String codigoPostalCodigoPostal) {
        this.codigoPostalCodigoPostal = codigoPostalCodigoPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ColoniaDTO coloniaDTO = (ColoniaDTO) o;
        if (coloniaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coloniaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ColoniaDTO{" +
            "id=" + getId() +
            ", colonia='" + getColonia() + "'" +
            ", municipio=" + getMunicipioId() +
            ", codigoPostal=" + getCodigoPostalId() +
            ", codigoPostal='" + getCodigoPostalCodigoPostal() + "'" +
            "}";
    }
}
