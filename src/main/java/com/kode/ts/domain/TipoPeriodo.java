package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoPeriodo.
 */
@Entity
@Table(name = "tipo_periodo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoPeriodo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "periodo", length = 200)
    private String periodo;

    @OneToMany(mappedBy = "tipoPeriodo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimiento> requerimientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public TipoPeriodo periodo(String periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public TipoPeriodo requerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
        return this;
    }

    public TipoPeriodo addRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.add(requerimiento);
        requerimiento.setTipoPeriodo(this);
        return this;
    }

    public TipoPeriodo removeRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.remove(requerimiento);
        requerimiento.setTipoPeriodo(null);
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
        if (!(o instanceof TipoPeriodo)) {
            return false;
        }
        return id != null && id.equals(((TipoPeriodo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoPeriodo{" +
            "id=" + getId() +
            ", periodo='" + getPeriodo() + "'" +
            "}";
    }
}
