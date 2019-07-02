package com.kode.ts.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A EstatusReqCanRec.
 */
@Entity
@Table(name = "estatus_req_can_rec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstatusReqCanRec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "motivo", length = 100)
    private String motivo;

    @ManyToOne
    @JsonIgnoreProperties("estatusReqCanRecs")
    private EstatusReqCan estatusReqCan;

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

    public EstatusReqCanRec motivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstatusReqCan getEstatusReqCan() {
        return estatusReqCan;
    }

    public EstatusReqCanRec estatusReqCan(EstatusReqCan estatusReqCan) {
        this.estatusReqCan = estatusReqCan;
        return this;
    }

    public void setEstatusReqCan(EstatusReqCan estatusReqCan) {
        this.estatusReqCan = estatusReqCan;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstatusReqCanRec)) {
            return false;
        }
        return id != null && id.equals(((EstatusReqCanRec) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstatusReqCanRec{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            "}";
    }
}
