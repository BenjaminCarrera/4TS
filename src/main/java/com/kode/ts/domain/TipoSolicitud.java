package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoSolicitud.
 */
@Entity
@Table(name = "tipo_solicitud")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "solicitud", length = 100)
    private String solicitud;

    @OneToMany(mappedBy = "tipoSolicitud")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimiento> requerimientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public TipoSolicitud solicitud(String solicitud) {
        this.solicitud = solicitud;
        return this;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public TipoSolicitud requerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
        return this;
    }

    public TipoSolicitud addRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.add(requerimiento);
        requerimiento.setTipoSolicitud(this);
        return this;
    }

    public TipoSolicitud removeRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.remove(requerimiento);
        requerimiento.setTipoSolicitud(null);
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
        if (!(o instanceof TipoSolicitud)) {
            return false;
        }
        return id != null && id.equals(((TipoSolicitud) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoSolicitud{" +
            "id=" + getId() +
            ", solicitud='" + getSolicitud() + "'" +
            "}";
    }
}
