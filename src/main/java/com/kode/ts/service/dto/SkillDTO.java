package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Skill} entity.
 */
public class SkillDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String nombre;


    private Long categoriaSkillId;

    private String categoriaSkillCategoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCategoriaSkillId() {
        return categoriaSkillId;
    }

    public void setCategoriaSkillId(Long categoriaSkillId) {
        this.categoriaSkillId = categoriaSkillId;
    }

    public String getCategoriaSkillCategoria() {
        return categoriaSkillCategoria;
    }

    public void setCategoriaSkillCategoria(String categoriaSkillCategoria) {
        this.categoriaSkillCategoria = categoriaSkillCategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillDTO skillDTO = (SkillDTO) o;
        if (skillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", categoriaSkill=" + getCategoriaSkillId() +
            ", categoriaSkill='" + getCategoriaSkillCategoria() + "'" +
            "}";
    }
}
