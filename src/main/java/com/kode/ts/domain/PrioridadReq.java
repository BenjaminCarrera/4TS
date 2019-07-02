package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PrioridadReq.
 */
@Entity
@Table(name = "prioridad_req")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrioridadReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "prioridad", length = 100)
    private String prioridad;

    @OneToMany(mappedBy = "prioridad")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimiento> requerimientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public PrioridadReq prioridad(String prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public PrioridadReq requerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
        return this;
    }

    public PrioridadReq addRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.add(requerimiento);
        requerimiento.setPrioridad(this);
        return this;
    }

    public PrioridadReq removeRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.remove(requerimiento);
        requerimiento.setPrioridad(null);
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
        if (!(o instanceof PrioridadReq)) {
            return false;
        }
        return id != null && id.equals(((PrioridadReq) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrioridadReq{" +
            "id=" + getId() +
            ", prioridad='" + getPrioridad() + "'" +
            "}";
    }
}
