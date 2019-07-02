package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.TipoPeriodo} entity.
 */
public class TipoPeriodoDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String periodo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoPeriodoDTO tipoPeriodoDTO = (TipoPeriodoDTO) o;
        if (tipoPeriodoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoPeriodoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoPeriodoDTO{" +
            "id=" + getId() +
            ", periodo='" + getPeriodo() + "'" +
            "}";
    }
}
