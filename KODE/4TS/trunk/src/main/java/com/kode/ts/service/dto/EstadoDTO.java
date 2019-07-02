package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Estado} entity.
 */
public class EstadoDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String estado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstadoDTO estadoDTO = (EstadoDTO) o;
        if (estadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstadoDTO{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
