package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.DominioSkill} entity.
 */
public class DominioSkillDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String dominio;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DominioSkillDTO dominioSkillDTO = (DominioSkillDTO) o;
        if (dominioSkillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dominioSkillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DominioSkillDTO{" +
            "id=" + getId() +
            ", dominio='" + getDominio() + "'" +
            "}";
    }
}
