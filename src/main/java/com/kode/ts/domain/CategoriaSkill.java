package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategoriaSkill.
 */
@Entity
@Table(name = "categoria_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoriaSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "categoria", length = 100)
    private String categoria;

    @OneToMany(mappedBy = "categoriaSkill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Skill> skills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public CategoriaSkill categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public CategoriaSkill skills(Set<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public CategoriaSkill addSkill(Skill skill) {
        this.skills.add(skill);
        skill.setCategoriaSkill(this);
        return this;
    }

    public CategoriaSkill removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.setCategoriaSkill(null);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaSkill)) {
            return false;
        }
        return id != null && id.equals(((CategoriaSkill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoriaSkill{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
