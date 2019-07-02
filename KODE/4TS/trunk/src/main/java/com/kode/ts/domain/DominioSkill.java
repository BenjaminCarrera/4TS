package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DominioSkill.
 */
@Entity
@Table(name = "dominio_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DominioSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "dominio", length = 100)
    private String dominio;

    @OneToMany(mappedBy = "nivelSkill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillCandidato> skillCandidatoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDominio() {
        return dominio;
    }

    public DominioSkill dominio(String dominio) {
        this.dominio = dominio;
        return this;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public Set<SkillCandidato> getSkillCandidatoes() {
        return skillCandidatoes;
    }

    public DominioSkill skillCandidatoes(Set<SkillCandidato> skillCandidatoes) {
        this.skillCandidatoes = skillCandidatoes;
        return this;
    }

    public DominioSkill addSkillCandidato(SkillCandidato skillCandidato) {
        this.skillCandidatoes.add(skillCandidato);
        skillCandidato.setNivelSkill(this);
        return this;
    }

    public DominioSkill removeSkillCandidato(SkillCandidato skillCandidato) {
        this.skillCandidatoes.remove(skillCandidato);
        skillCandidato.setNivelSkill(null);
        return this;
    }

    public void setSkillCandidatoes(Set<SkillCandidato> skillCandidatoes) {
        this.skillCandidatoes = skillCandidatoes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DominioSkill)) {
            return false;
        }
        return id != null && id.equals(((DominioSkill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DominioSkill{" +
            "id=" + getId() +
            ", dominio='" + getDominio() + "'" +
            "}";
    }
}
