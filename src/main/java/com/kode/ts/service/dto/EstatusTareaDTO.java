package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.EstatusTarea} entity.
 */
public class EstatusTareaDTO implements Serializable {

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

        EstatusTareaDTO estatusTareaDTO = (EstatusTareaDTO) o;
        if (estatusTareaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estatusTareaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstatusTareaDTO{" +
            "id=" + getId() +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
