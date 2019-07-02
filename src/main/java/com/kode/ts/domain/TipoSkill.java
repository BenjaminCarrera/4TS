package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoSkill.
 */
@Entity
@Table(name = "tipo_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "tipo", length = 100)
    private String tipo;

    @OneToMany(mappedBy = "tipoSkill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillRequerimiento> skillRequerimientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public TipoSkill tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<SkillRequerimiento> getSkillRequerimientos() {
        return skillRequerimientos;
    }

    public TipoSkill skillRequerimientos(Set<SkillRequerimiento> skillRequerimientos) {
        this.skillRequerimientos = skillRequerimientos;
        return this;
    }

    public TipoSkill addSkillRequerimiento(SkillRequerimiento skillRequerimiento) {
        this.skillRequerimientos.add(skillRequerimiento);
        skillRequerimiento.setTipoSkill(this);
        return this;
    }

    public TipoSkill removeSkillRequerimiento(SkillRequerimiento skillRequerimiento) {
        this.skillRequerimientos.remove(skillRequerimiento);
        skillRequerimiento.setTipoSkill(null);
        return this;
    }

    public void setSkillRequerimientos(Set<SkillRequerimiento> skillRequerimientos) {
        this.skillRequerimientos = skillRequerimientos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoSkill)) {
            return false;
        }
        return id != null && id.equals(((TipoSkill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoSkill{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
