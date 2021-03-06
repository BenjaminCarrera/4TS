package com.kode.ts.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.FormacionAcademica} entity.
 */
public class FormacionAcademicaDTO implements Serializable {

    private Long id;

    private String formacionAcademica;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormacionAcademica() {
        return formacionAcademica;
    }

    public void setFormacionAcademica(String formacionAcademica) {
        this.formacionAcademica = formacionAcademica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormacionAcademicaDTO formacionAcademicaDTO = (FormacionAcademicaDTO) o;
        if (formacionAcademicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formacionAcademicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormacionAcademicaDTO{" +
            "id=" + getId() +
            ", formacionAcademica='" + getFormacionAcademica() + "'" +
            "}";
    }
}
