package com.kode.ts.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.SkillRequerimiento} entity.
 */
public class SkillRequerimientoDTO implements Serializable {

    private Long id;


    private Long idRequerimientoId;

    private Long idSkillId;

    private Long tipoSkillId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRequerimientoId() {
        return idRequerimientoId;
    }

    public void setIdRequerimientoId(Long requerimientoId) {
        this.idRequerimientoId = requerimientoId;
    }

    public Long getIdSkillId() {
        return idSkillId;
    }

    public void setIdSkillId(Long skillId) {
        this.idSkillId = skillId;
    }

    public Long getTipoSkillId() {
        return tipoSkillId;
    }

    public void setTipoSkillId(Long tipoSkillId) {
        this.tipoSkillId = tipoSkillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillRequerimientoDTO skillRequerimientoDTO = (SkillRequerimientoDTO) o;
        if (skillRequerimientoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillRequerimientoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillRequerimientoDTO{" +
            "id=" + getId() +
            ", idRequerimiento=" + getIdRequerimientoId() +
            ", idSkill=" + getIdSkillId() +
            ", tipoSkill=" + getTipoSkillId() +
            "}";
    }
}
