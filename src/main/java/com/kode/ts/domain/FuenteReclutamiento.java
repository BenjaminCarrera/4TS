package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FuenteReclutamiento.
 */
@Entity
@Table(name = "fuente_reclutamiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FuenteReclutamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "fuente", length = 100)
    private String fuente;

    @OneToMany(mappedBy = "fuenteReclutamiento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuente() {
        return fuente;
    }

    public FuenteReclutamiento fuente(String fuente) {
        this.fuente = fuente;
        return this;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public FuenteReclutamiento candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public FuenteReclutamiento addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setFuenteReclutamiento(this);
        return this;
    }

    public FuenteReclutamiento removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setFuenteReclutamiento(null);
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
        if (!(o instanceof FuenteReclutamiento)) {
            return false;
        }
        return id != null && id.equals(((FuenteReclutamiento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FuenteReclutamiento{" +
            "id=" + getId() +
            ", fuente='" + getFuente() + "'" +
            "}";
    }
}
