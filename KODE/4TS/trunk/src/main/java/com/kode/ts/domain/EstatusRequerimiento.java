package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstatusRequerimiento.
 */
@Entity
@Table(name = "estatus_requerimiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstatusRequerimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "estatus", length = 100)
    private String estatus;

    @OneToMany(mappedBy = "estatusRequerimiento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimiento> requerimientos = new HashSet<>();

    @OneToMany(mappedBy = "estatusRequerimiento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EstReqCerrado> estReqCerrados = new HashSet<>();

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

    public EstatusRequerimiento estatus(String estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public EstatusRequerimiento requerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
        return this;
    }

    public EstatusRequerimiento addRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.add(requerimiento);
        requerimiento.setEstatusRequerimiento(this);
        return this;
    }

    public EstatusRequerimiento removeRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.remove(requerimiento);
        requerimiento.setEstatusRequerimiento(null);
        return this;
    }

    public void setRequerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }

    public Set<EstReqCerrado> getEstReqCerrados() {
        return estReqCerrados;
    }

    public EstatusRequerimiento estReqCerrados(Set<EstReqCerrado> estReqCerrados) {
        this.estReqCerrados = estReqCerrados;
        return this;
    }

    public EstatusRequerimiento addEstReqCerrado(EstReqCerrado estReqCerrado) {
        this.estReqCerrados.add(estReqCerrado);
        estReqCerrado.setEstatusRequerimiento(this);
        return this;
    }

    public EstatusRequerimiento removeEstReqCerrado(EstReqCerrado estReqCerrado) {
        this.estReqCerrados.remove(estReqCerrado);
        estReqCerrado.setEstatusRequerimiento(null);
        return this;
    }

    public void setEstReqCerrados(Set<EstReqCerrado> estReqCerrados) {
        this.estReqCerrados = estReqCerrados;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstatusRequerimiento)) {
            return false;
        }
        return id != null && id.equals(((EstatusRequerimiento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstatusRequerimiento{" +
            "id=" + getId() +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
