package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoIngreso.
 */
@Entity
@Table(name = "tipo_ingreso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoIngreso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "tipo", length = 100)
    private String tipo;

    @OneToMany(mappedBy = "tipoIngreso")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimiento> requerimientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public TipoIngreso tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public TipoIngreso requerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
        return this;
    }

    public TipoIngreso addRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.add(requerimiento);
        requerimiento.setTipoIngreso(this);
        return this;
    }

    public TipoIngreso removeRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.remove(requerimiento);
        requerimiento.setTipoIngreso(null);
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
        if (!(o instanceof TipoIngreso)) {
            return false;
        }
        return id != null && id.equals(((TipoIngreso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoIngreso{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
