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
 * A EstatusLaboral.
 */
@Entity
@Table(name = "estatus_laboral")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstatusLaboral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "estatus", length = 100)
    private String estatus;

    @OneToMany(mappedBy = "estatusLaboral")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstatus() {
        return estatus;
    }

    public EstatusLaboral estatus(String estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public EstatusLaboral candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public EstatusLaboral addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setEstatusLaboral(this);
        return this;
    }

    public EstatusLaboral removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setEstatusLaboral(null);
        return this;
    }

    public void setCandidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstatusLaboral)) {
            return false;
        }
        return id != null && id.equals(((EstatusLaboral) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstatusLaboral{" +
            "id=" + getId() +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
