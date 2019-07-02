package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.BaseTarifa} entity.
 */
public class BaseTarifaDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String base;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseTarifaDTO baseTarifaDTO = (BaseTarifaDTO) o;
        if (baseTarifaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseTarifaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseTarifaDTO{" +
            "id=" + getId() +
            ", base='" + getBase() + "'" +
            "}";
    }
}
