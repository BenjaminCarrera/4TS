package com.kode.ts.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ReqCan.
 */
@Entity
@Table(name = "req_can")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReqCan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("reqCans")
    private Candidato candidato;

    @ManyToOne
    @JsonIgnoreProperties("reqCans")
    private Requerimiento requerimiento;

    @ManyToOne
    @JsonIgnoreProperties("reqCans")
    private EstatusReqCan estatusReqCan;

    @ManyToOne
    @JsonIgnoreProperties("reqCans")
    private EstatusReqCanRec estatusReqCanRec;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public ReqCan candidato(Candidato candidato) {
        this.candidato = candidato;
        return this;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public ReqCan requerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
        return this;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public EstatusReqCan getEstatusReqCan() {
        return estatusReqCan;
    }

    public ReqCan estatusReqCan(EstatusReqCan estatusReqCan) {
        this.estatusReqCan = estatusReqCan;
        return this;
    }

    public void setEstatusReqCan(EstatusReqCan estatusReqCan) {
        this.estatusReqCan = estatusReqCan;
    }

    public EstatusReqCanRec getEstatusReqCanRec() {
        return estatusReqCanRec;
    }

    public ReqCan estatusReqCanRec(EstatusReqCanRec estatusReqCanRec) {
        this.estatusReqCanRec = estatusReqCanRec;
        return this;
    }

    public void setEstatusReqCanRec(EstatusReqCanRec estatusReqCanRec) {
        this.estatusReqCanRec = estatusReqCanRec;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReqCan)) {
            return false;
        }
        return id != null && id.equals(((ReqCan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReqCan{" +
            "id=" + getId() +
            "}";
    }
}
