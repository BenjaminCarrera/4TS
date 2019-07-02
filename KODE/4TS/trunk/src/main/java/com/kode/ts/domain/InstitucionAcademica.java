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
 * A InstitucionAcademica.
 */
@Entity
@Table(name = "institucion_academica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InstitucionAcademica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "institucion", length = 200)
    private String institucion;

    @OneToMany(mappedBy = "institucionAcademica")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitucion() {
        return institucion;
    }

    public InstitucionAcademica institucion(String institucion) {
        this.institucion = institucion;
        return this;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public InstitucionAcademica candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public InstitucionAcademica addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setInstitucionAcademica(this);
        return this;
    }

    public InstitucionAcademica removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setInstitucionAcademica(null);
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
        if (!(o instanceof InstitucionAcademica)) {
            return false;
        }
        return id != null && id.equals(((InstitucionAcademica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InstitucionAcademica{" +
            "id=" + getId() +
            ", institucion='" + getInstitucion() + "'" +
            "}";
    }
}
