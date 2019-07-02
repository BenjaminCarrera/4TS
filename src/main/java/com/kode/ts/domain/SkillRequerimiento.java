package com.kode.ts.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SkillRequerimiento.
 */
@Entity
@Table(name = "skill_requerimiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SkillRequerimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("skillRequerimientos")
    private Requerimiento idRequerimiento;

    @ManyToOne
    @JsonIgnoreProperties("skillRequerimientos")
    private Skill idSkill;

    @ManyToOne
    @JsonIgnoreProperties("skillRequerimientos")
    private TipoSkill tipoSkill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Requerimiento getIdRequerimiento() {
        return idRequerimiento;
    }

    public SkillRequerimiento idRequerimiento(Requerimiento requerimiento) {
        this.idRequerimiento = requerimiento;
        return this;
    }

    public void setIdRequerimiento(Requerimiento requerimiento) {
        this.idRequerimiento = requerimiento;
    }

    public Skill getIdSkill() {
        return idSkill;
    }

    public SkillRequerimiento idSkill(Skill skill) {
        this.idSkill = skill;
        return this;
    }

    public void setIdSkill(Skill skill) {
        this.idSkill = skill;
    }

    public TipoSkill getTipoSkill() {
        return tipoSkill;
    }

    public SkillRequerimiento tipoSkill(TipoSkill tipoSkill) {
        this.tipoSkill = tipoSkill;
        return this;
    }

    public void setTipoSkill(TipoSkill tipoSkill) {
        this.tipoSkill = tipoSkill;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillRequerimiento)) {
            return false;
        }
        return id != null && id.equals(((SkillRequerimiento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SkillRequerimiento{" +
            "id=" + getId() +
            "}";
    }
}
