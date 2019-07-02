package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.EsquemaContratacionKode} entity.
 */
public class EsquemaContratacionKodeDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String esquema;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EsquemaContratacionKodeDTO esquemaContratacionKodeDTO = (EsquemaContratacionKodeDTO) o;
        if (esquemaContratacionKodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), esquemaContratacionKodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EsquemaContratacionKodeDTO{" +
            "id=" + getId() +
            ", esquema='" + getEsquema() + "'" +
            "}";
    }
}
