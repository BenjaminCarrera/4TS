package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.InstitucionAcademica} entity.
 */
public class InstitucionAcademicaDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String institucion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstitucionAcademicaDTO institucionAcademicaDTO = (InstitucionAcademicaDTO) o;
        if (institucionAcademicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), institucionAcademicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstitucionAcademicaDTO{" +
            "id=" + getId() +
            ", institucion='" + getInstitucion() + "'" +
            "}";
    }
}
