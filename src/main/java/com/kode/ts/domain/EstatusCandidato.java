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
 * A EstatusCandidato.
 */
@Entity
@Table(name = "estatus_candidato")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstatusCandidato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "estatus", length = 200)
    private String estatus;

    @OneToMany(mappedBy = "estatusCandidato")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    @OneToMany(mappedBy = "estatusCandidato")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EstCanInactivo> estCanInactivos = new HashSet<>();

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

    public EstatusCandidato estatus(String estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public EstatusCandidato candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public EstatusCandidato addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setEstatusCandidato(this);
        return this;
    }

    public EstatusCandidato removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setEstatusCandidato(null);
        return this;
    }

    public void setCandidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
    }

    public Set<EstCanInactivo> getEstCanInactivos() {
        return estCanInactivos;
    }

    public EstatusCandidato estCanInactivos(Set<EstCanInactivo> estCanInactivos) {
        this.estCanInactivos = estCanInactivos;
        return this;
    }

    public EstatusCandidato addEstCanInactivo(EstCanInactivo estCanInactivo) {
        this.estCanInactivos.add(estCanInactivo);
        estCanInactivo.setEstatusCandidato(this);
        return this;
    }

    public EstatusCandidato removeEstCanInactivo(EstCanInactivo estCanInactivo) {
        this.estCanInactivos.remove(estCanInactivo);
        estCanInactivo.setEstatusCandidato(null);
        return this;
    }

    public void setEstCanInactivos(Set<EstCanInactivo> estCanInactivos) {
        this.estCanInactivos = estCanInactivos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstatusCandidato)) {
            return false;
        }
        return id != null && id.equals(((EstatusCandidato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstatusCandidato{" +
            "id=" + getId() +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
