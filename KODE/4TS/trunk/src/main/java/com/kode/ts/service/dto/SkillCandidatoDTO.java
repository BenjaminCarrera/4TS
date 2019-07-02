package com.kode.ts.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.SkillCandidato} entity.
 */
public class SkillCandidatoDTO implements Serializable {

    private Long id;

    private Float calificacionSkill;


    private Long idCandidatoId;

    private Long idSkillId;

    private Long nivelSkillId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCalificacionSkill() {
        return calificacionSkill;
    }

    public void setCalificacionSkill(Float calificacionSkill) {
        this.calificacionSkill = calificacionSkill;
    }

    public Long getIdCandidatoId() {
        return idCandidatoId;
    }

    public void setIdCandidatoId(Long candidatoId) {
        this.idCandidatoId = candidatoId;
    }

    public Long getIdSkillId() {
        return idSkillId;
    }

    public void setIdSkillId(Long skillId) {
        this.idSkillId = skillId;
    }

    public Long getNivelSkillId() {
        return nivelSkillId;
    }

    public void setNivelSkillId(Long dominioSkillId) {
        this.nivelSkillId = dominioSkillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillCandidatoDTO skillCandidatoDTO = (SkillCandidatoDTO) o;
        if (skillCandidatoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillCandidatoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillCandidatoDTO{" +
            "id=" + getId() +
            ", calificacionSkill=" + getCalificacionSkill() +
            ", idCandidato=" + getIdCandidatoId() +
            ", idSkill=" + getIdSkillId() +
            ", nivelSkill=" + getNivelSkillId() +
            "}";
    }
}
