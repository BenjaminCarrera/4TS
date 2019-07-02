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

    @ManyToOne
    @JsonIgnoreProperties("codigoPostals")
    private Municipio municipio;

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

    public Municipio getMunicipio() {
        return municipio;
    }

    public CodigoPostal municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
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
