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
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "municipio", length = 100)
    private String municipio;

    @OneToMany(mappedBy = "municipio")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Colonia> colonias = new HashSet<>();

    @OneToMany(mappedBy = "municipio")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CodigoPostal> codigoPostals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("municipios")
    private Estado estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Municipio municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Set<Colonia> getColonias() {
        return colonias;
    }

    public Municipio colonias(Set<Colonia> colonias) {
        this.colonias = colonias;
        return this;
    }

    public Municipio addColonia(Colonia colonia) {
        this.colonias.add(colonia);
        colonia.setMunicipio(this);
        return this;
    }

    public Municipio removeColonia(Colonia colonia) {
        this.colonias.remove(colonia);
        colonia.setMunicipio(null);
        return this;
    }

    public void setColonias(Set<Colonia> colonias) {
        this.colonias = colonias;
    }

    public Set<CodigoPostal> getCodigoPostals() {
        return codigoPostals;
    }

    public Municipio codigoPostals(Set<CodigoPostal> codigoPostals) {
        this.codigoPostals = codigoPostals;
        return this;
    }

    public Municipio addCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostals.add(codigoPostal);
        codigoPostal.setMunicipio(this);
        return this;
    }

    public Municipio removeCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostals.remove(codigoPostal);
        codigoPostal.setMunicipio(null);
        return this;
    }

    public void setCodigoPostals(Set<CodigoPostal> codigoPostals) {
        this.codigoPostals = codigoPostals;
    }

    public Estado getEstado() {
        return estado;
    }

    public Municipio estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Municipio)) {
            return false;
        }
        return id != null && id.equals(((Municipio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Municipio{" +
            "id=" + getId() +
            ", municipio='" + getMunicipio() + "'" +
            "}";
    }
}
