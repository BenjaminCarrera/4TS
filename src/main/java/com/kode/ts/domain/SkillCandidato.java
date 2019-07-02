package com.kode.ts.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SkillCandidato.
 */
@Entity
@Table(name = "skill_candidato")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SkillCandidato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calificacion_skill")
    private Float calificacionSkill;

    @ManyToOne
    @JsonIgnoreProperties("skillCandidatoes")
    private Candidato idCandidato;

    @ManyToOne
    @JsonIgnoreProperties("skillCandidatoes")
    private Skill idSkill;

    @ManyToOne
    @JsonIgnoreProperties("skillCandidatoes")
    private DominioSkill nivelSkill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCalificacionSkill() {
        return calificacionSkill;
    }

    public SkillCandidato calificacionSkill(Float calificacionSkill) {
        this.calificacionSkill = calificacionSkill;
        return this;
    }

    public void setCalificacionSkill(Float calificacionSkill) {
        this.calificacionSkill = calificacionSkill;
    }

    public Candidato getIdCandidato() {
        return idCandidato;
    }

    public SkillCandidato idCandidato(Candidato candidato) {
        this.idCandidato = candidato;
        return this;
    }

    public void setIdCandidato(Candidato candidato) {
        this.idCandidato = candidato;
    }

    public Skill getIdSkill() {
        return idSkill;
    }

    public SkillCandidato idSkill(Skill skill) {
        this.idSkill = skill;
        return this;
    }

    public void setIdSkill(Skill skill) {
        this.idSkill = skill;
    }

    public DominioSkill getNivelSkill() {
        return nivelSkill;
    }

    public SkillCandidato nivelSkill(DominioSkill dominioSkill) {
        this.nivelSkill = dominioSkill;
        return this;
    }

    public void setNivelSkill(DominioSkill dominioSkill) {
        this.nivelSkill = dominioSkill;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillCandidato)) {
            return false;
        }
        return id != null && id.equals(((SkillCandidato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SkillCandidato{" +
            "id=" + getId() +
            ", calificacionSkill=" + getCalificacionSkill() +
            "}";
    }
}
