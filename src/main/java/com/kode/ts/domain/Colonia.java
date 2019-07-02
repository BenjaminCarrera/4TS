package com.kode.ts.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Colonia.
 */
@Entity
@Table(name = "colonia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Colonia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "colonia", length = 100)
    private String colonia;

    @OneToMany(mappedBy = "colonia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("colonias")
    private Municipio municipio;

    @ManyToOne
    @JsonIgnoreProperties("colonias")
    private CodigoPostal codigoPostal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColonia() {
        return colonia;
    }

    public Colonia colonia(String colonia) {
        this.colonia = colonia;
        return this;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public Colonia candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public Colonia addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setColonia(this);
        return this;
    }

    public Colonia removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setColonia(null);
        return this;
    }

    public void setCandidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public Colonia municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public Colonia codigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colonia)) {
            return false;
        }
        return id != null && id.equals(((Colonia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Colonia{" +
            "id=" + getId() +
            ", colonia='" + getColonia() + "'" +
            "}";
    }
}
