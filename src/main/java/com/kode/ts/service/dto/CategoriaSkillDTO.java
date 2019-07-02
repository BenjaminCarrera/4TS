package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.CategoriaSkill} entity.
 */
public class CategoriaSkillDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String categoria;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoriaSkillDTO categoriaSkillDTO = (CategoriaSkillDTO) o;
        if (categoriaSkillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriaSkillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoriaSkillDTO{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
