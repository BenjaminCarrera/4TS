package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstatusReqCan.
 */
@Entity
@Table(name = "estatus_req_can")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstatusReqCan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "estatus", length = 100)
    private String estatus;

    @OneToMany(mappedBy = "estatusReqCan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EstatusReqCanRec> estatusReqCanRecs = new HashSet<>();

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

    public EstatusReqCan estatus(String estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Set<EstatusReqCanRec> getEstatusReqCanRecs() {
        return estatusReqCanRecs;
    }

    public EstatusReqCan estatusReqCanRecs(Set<EstatusReqCanRec> estatusReqCanRecs) {
        this.estatusReqCanRecs = estatusReqCanRecs;
        return this;
    }

    public EstatusReqCan addEstatusReqCanRec(EstatusReqCanRec estatusReqCanRec) {
        this.estatusReqCanRecs.add(estatusReqCanRec);
        estatusReqCanRec.setEstatusReqCan(this);
        return this;
    }

    public EstatusReqCan removeEstatusReqCanRec(EstatusReqCanRec estatusReqCanRec) {
        this.estatusReqCanRecs.remove(estatusReqCanRec);
        estatusReqCanRec.setEstatusReqCan(null);
        return this;
    }

    public void setEstatusReqCanRecs(Set<EstatusReqCanRec> estatusReqCanRecs) {
        this.estatusReqCanRecs = estatusReqCanRecs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstatusReqCan)) {
            return false;
        }
        return id != null && id.equals(((EstatusReqCan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstatusReqCan{" +
            "id=" + getId() +
            ", estatus='" + getEstatus() + "'" +
            "}";
    }
}
