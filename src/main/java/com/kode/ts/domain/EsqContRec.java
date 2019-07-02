package com.kode.ts.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EsqContRec.
 */
@Entity
@Table(name = "esq_cont_rec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EsqContRec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "esquema", length = 100)
    private String esquema;

    @OneToMany(mappedBy = "antecedentesEsquemaContratacion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    @OneToMany(mappedBy = "esquemaContratacion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimiento> requerimientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEsquema() {
        return esquema;
    }

    public EsqContRec esquema(String esquema) {
        this.esquema = esquema;
        return this;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public EsqContRec candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public EsqContRec addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setAntecedentesEsquemaContratacion(this);
        return this;
    }

    public EsqContRec removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setAntecedentesEsquemaContratacion(null);
        return this;
    }

    public void setCandidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public EsqContRec requerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
        return this;
    }

    public EsqContRec addRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.add(requerimiento);
        requerimiento.setEsquemaContratacion(this);
        return this;
    }

    public EsqContRec removeRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.remove(requerimiento);
        requerimiento.setEsquemaContratacion(null);
        return this;
    }

    public void setRequerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EsqContRec)) {
            return false;
        }
        return id != null && id.equals(((EsqContRec) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EsqContRec{" +
            "id=" + getId() +
            ", esquema='" + getEsquema() + "'" +
            "}";
    }
}
