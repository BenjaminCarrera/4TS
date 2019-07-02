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
 * A EstReqCerrado.
 */
@Entity
@Table(name = "est_req_cerrado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstReqCerrado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "motivo", length = 100)
    private String motivo;

    @OneToMany(mappedBy = "estatusReqCan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimiento> requerimientos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("estReqCerrados")
    private EstatusRequerimiento estatusRequerimiento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public EstReqCerrado motivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Set<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public EstReqCerrado requerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
        return this;
    }

    public EstReqCerrado addRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.add(requerimiento);
        requerimiento.setEstatusReqCan(this);
        return this;
    }

    public EstReqCerrado removeRequerimiento(Requerimiento requerimiento) {
        this.requerimientos.remove(requerimiento);
        requerimiento.setEstatusReqCan(null);
        return this;
    }

    public void setRequerimientos(Set<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }

    public EstatusRequerimiento getEstatusRequerimiento() {
        return estatusRequerimiento;
    }

    public EstReqCerrado estatusRequerimiento(EstatusRequerimiento estatusRequerimiento) {
        this.estatusRequerimiento = estatusRequerimiento;
        return this;
    }

    public void setEstatusRequerimiento(EstatusRequerimiento estatusRequerimiento) {
        this.estatusRequerimiento = estatusRequerimiento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstReqCerrado)) {
            return false;
        }
        return id != null && id.equals(((EstReqCerrado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstReqCerrado{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            "}";
    }
}
