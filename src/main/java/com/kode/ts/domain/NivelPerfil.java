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
 * A NivelPerfil.
 */
@Entity
@Table(name = "nivel_perfil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NivelPerfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "nivel", length = 200)
    private String nivel;

    @OneToMany(mappedBy = "nivelPerfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    @OneToMany(mappedBy = "nivelPerfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimiento> requerimientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public NivelPerfil nivel(String nivel) {
        this.nivel = nivel;
        return this;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public NivelPerfil candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public NivelPerfil addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setNivelPerfil(this);
        return this;
    }

    public NivelPerfil removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setNivelPerfil(null);
        return this;
    }

    public void setCandidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public NivelPerfil requerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
        return this;
    }

    public NivelPerfil addRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.add(requerimiento);
        requerimiento.setNivelPerfil(this);
        return this;
    }

    public NivelPerfil removeRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.remove(requerimiento);
        requerimiento.setNivelPerfil(null);
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
        if (!(o instanceof NivelPerfil)) {
            return false;
        }
        return id != null && id.equals(((NivelPerfil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NivelPerfil{" +
            "id=" + getId() +
            ", nivel='" + getNivel() + "'" +
            "}";
    }
}
