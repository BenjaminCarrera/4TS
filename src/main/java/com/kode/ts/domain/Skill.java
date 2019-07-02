package com.kode.ts.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @OneToMany(mappedBy = "idSkill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillCandidato> skillCandidatoes = new HashSet<>();

    @OneToMany(mappedBy = "idSkill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillRequerimiento> skillRequerimientos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("skills")
    private CategoriaSkill categoriaSkill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Skill nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<SkillCandidato> getSkillCandidatoes() {
        return skillCandidatoes;
    }

    public Skill skillCandidatoes(Set<SkillCandidato> skillCandidatoes) {
        this.skillCandidatoes = skillCandidatoes;
        return this;
    }

    public Skill addSkillCandidato(SkillCandidato skillCandidato) {
        this.skillCandidatoes.add(skillCandidato);
        skillCandidato.setIdSkill(this);
        return this;
    }

    public Skill removeSkillCandidato(SkillCandidato skillCandidato) {
        this.skillCandidatoes.remove(skillCandidato);
        skillCandidato.setIdSkill(null);
        return this;
    }

    public void setSkillCandidatoes(Set<SkillCandidato> skillCandidatoes) {
        this.skillCandidatoes = skillCandidatoes;
    }

    public Set<SkillRequerimiento> getSkillRequerimientos() {
        return skillRequerimientos;
    }

    public Skill skillRequerimientos(Set<SkillRequerimiento> skillRequerimientos) {
        this.skillRequerimientos = skillRequerimientos;
        return this;
    }

    public Skill addSkillRequerimiento(SkillRequerimiento skillRequerimiento) {
        this.skillRequerimientos.add(skillRequerimiento);
        skillRequerimiento.setIdSkill(this);
        return this;
    }

    public Skill removeSkillRequerimiento(SkillRequerimiento skillRequerimiento) {
        this.skillRequerimientos.remove(skillRequerimiento);
        skillRequerimiento.setIdSkill(null);
        return this;
    }

    public void setSkillRequerimientos(Set<SkillRequerimiento> skillRequerimientos) {
        this.skillRequerimientos = skillRequerimientos;
    }

    public CategoriaSkill getCategoriaSkill() {
        return categoriaSkill;
    }

    public Skill categoriaSkill(CategoriaSkill categoriaSkill) {
        this.categoriaSkill = categoriaSkill;
        return this;
    }

    public void setCategoriaSkill(CategoriaSkill categoriaSkill) {
        this.categoriaSkill = categoriaSkill;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
