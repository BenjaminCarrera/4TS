package com.kode.ts.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EstCanInactivo.
 */
@Entity
@Table(name = "est_can_inactivo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstCanInactivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "motivo", length = 200)
    private String motivo;

    @OneToMany(mappedBy = "estCanInactivo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("estCanInactivos")
    private EstatusCandidato estatusCandidato;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public EstCanInactivo motivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public EstCanInactivo candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public EstCanInactivo addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setEstCanInactivo(this);
        return this;
    }

    public EstCanInactivo removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setEstCanInactivo(null);
        return this;
    }

    public void setCandidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
    }

    public EstatusCandidato getEstatusCandidato() {
        return estatusCandidato;
    }

    public EstCanInactivo estatusCandidato(EstatusCandidato estatusCandidato) {
        this.estatusCandidato = estatusCandidato;
        return this;
    }

    public void setEstatusCandidato(EstatusCandidato estatusCandidato) {
        this.estatusCandidato = estatusCandidato;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstCanInactivo)) {
            return false;
        }
        return id != null && id.equals(((EstCanInactivo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstCanInactivo{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            "}";
    }
}
