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

    private String idCandidatoNombre;

    private Long idSkillId;

    private String idSkillNombre;

    private Long nivelSkillId;

    private String nivelSkillDominio;

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

    public String getIdCandidatoNombre() {
        return idCandidatoNombre;
    }

    public void setIdCandidatoNombre(String candidatoNombre) {
        this.idCandidatoNombre = candidatoNombre;
    }

    public Long getIdSkillId() {
        return idSkillId;
    }

    public void setIdSkillId(Long skillId) {
        this.idSkillId = skillId;
    }

    public String getIdSkillNombre() {
        return idSkillNombre;
    }

    public void setIdSkillNombre(String skillNombre) {
        this.idSkillNombre = skillNombre;
    }

    public Long getNivelSkillId() {
        return nivelSkillId;
    }

    public void setNivelSkillId(Long dominioSkillId) {
        this.nivelSkillId = dominioSkillId;
    }

    public String getNivelSkillDominio() {
        return nivelSkillDominio;
    }

    public void setNivelSkillDominio(String dominioSkillDominio) {
        this.nivelSkillDominio = dominioSkillDominio;
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
            ", idCandidato='" + getIdCandidatoNombre() + "'" +
            ", idSkill=" + getIdSkillId() +
            ", idSkill='" + getIdSkillNombre() + "'" +
            ", nivelSkill=" + getNivelSkillId() +
            ", nivelSkill='" + getNivelSkillDominio() + "'" +
            "}";
    }
}
