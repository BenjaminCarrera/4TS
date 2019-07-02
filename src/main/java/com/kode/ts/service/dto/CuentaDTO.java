package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Cuenta} entity.
 */
public class CuentaDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String clave;

    @Size(max = 100)
    private String nombre;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CuentaDTO cuentaDTO = (CuentaDTO) o;
        if (cuentaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cuentaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CuentaDTO{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
