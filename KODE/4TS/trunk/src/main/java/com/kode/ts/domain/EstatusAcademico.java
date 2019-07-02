package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstatusAcademico.
 */
@Entity
@Table(name = "estatus_academico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstatusAcademico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "estatus", length = 100)
    private String estatus;

    @OneToMany(mappedBy = "estatusAcademico")
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

    public EstatusAcademico estatus(String estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public EstatusAcademico candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public EstatusAcademico addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setEstatusAcademico(this);
        return this;
    }

    public EstatusAcademico removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setEstatusAcademico(null);
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
        if (!(o instanceof EstatusAcademico)) {
            return false;
        }
        return id != null && id.equals(((EstatusAcademico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstatusAcademico{" +
            "id=" + getId() +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
