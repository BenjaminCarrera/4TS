package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoTarea.
 */
@Entity
@Table(name = "tipo_tarea")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoTarea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "tipo", length = 100)
    private String tipo;

    @OneToMany(mappedBy = "tipoTarea")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarea> tareas = new HashSet<>();

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

    public TipoTarea tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public TipoTarea tareas(Set<Tarea> tareas) {
        this.tareas = tareas;
        return this;
    }

    public TipoTarea addTarea(Tarea tarea) {
        this.tareas.add(tarea);
        tarea.setTipoTarea(this);
        return this;
    }

    public TipoTarea removeTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.setTipoTarea(null);
        return this;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoTarea)) {
            return false;
        }
        return id != null && id.equals(((TipoTarea) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoTarea{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
