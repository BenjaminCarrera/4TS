package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.EsqContRec} entity.
 */
public class EsqContRecDTO implements Serializable {

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

        EsqContRecDTO esqContRecDTO = (EsqContRecDTO) o;
        if (esqContRecDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), esqContRecDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EsqContRecDTO{" +
            "id=" + getId() +
            ", esquema='" + getEsquema() + "'" +
            "}";
    }
}
