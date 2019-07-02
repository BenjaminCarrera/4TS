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
 * A CodigoPostal.
 */
@Entity
@Table(name = "codigo_postal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CodigoPostal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 5)
    @Column(name = "codigo_postal", length = 5)
    private String codigoPostal;

    @OneToMany(mappedBy = "codigoPostal")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Colonia> colonias = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "codigo_postal_municipio",
               joinColumns = @JoinColumn(name = "codigo_postal_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "municipio_id", referencedColumnName = "id"))
    private Set<Municipio> municipios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public CodigoPostal codigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Set<Colonia> getColonias() {
        return colonias;
    }

    public CodigoPostal colonias(Set<Colonia> colonias) {
        this.colonias = colonias;
        return this;
    }

    public CodigoPostal addColonia(Colonia colonia) {
        this.colonias.add(colonia);
        colonia.setCodigoPostal(this);
        return this;
    }

    public CodigoPostal removeColonia(Colonia colonia) {
        this.colonias.remove(colonia);
        colonia.setCodigoPostal(null);
        return this;
    }

    public void setColonias(Set<Colonia> colonias) {
        this.colonias = colonias;
    }

    public Set<Municipio> getMunicipios() {
        return municipios;
    }

    public CodigoPostal municipios(Set<Municipio> municipios) {
        this.municipios = municipios;
        return this;
    }

    public CodigoPostal addMunicipio(Municipio municipio) {
        this.municipios.add(municipio);
        municipio.getCodigoPostals().add(this);
        return this;
    }

    public CodigoPostal removeMunicipio(Municipio municipio) {
        this.municipios.remove(municipio);
        municipio.getCodigoPostals().remove(this);
        return this;
    }

    public void setMunicipios(Set<Municipio> municipios) {
        this.municipios = municipios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodigoPostal)) {
            return false;
        }
        return id != null && id.equals(((CodigoPostal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CodigoPostal{" +
            "id=" + getId() +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            "}";
    }
}
