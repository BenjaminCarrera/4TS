package com.kode.ts.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.kode.ts.domain.EstatusReqCan} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstatusReqCanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estatus-req-cans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstatusReqCanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter estatus;

    private LongFilter estatusReqCanRecId;

    public EstatusReqCanCriteria(){
    }

    public EstatusReqCanCriteria(EstatusReqCanCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.estatusReqCanRecId = other.estatusReqCanRecId == null ? null : other.estatusReqCanRecId.copy();
    }

    @Override
    public EstatusReqCanCriteria copy() {
        return new EstatusReqCanCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEstatus() {
        return estatus;
    }

    public void setEstatus(StringFilter estatus) {
        this.estatus = estatus;
    }

    public LongFilter getEstatusReqCanRecId() {
        return estatusReqCanRecId;
    }

    public void setEstatusReqCanRecId(LongFilter estatusReqCanRecId) {
        this.estatusReqCanRecId = estatusReqCanRecId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EstatusReqCanCriteria that = (EstatusReqCanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(estatusReqCanRecId, that.estatusReqCanRecId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        estatus,
        estatusReqCanRecId
        );
    }

    @Override
    public String toString() {
        return "EstatusReqCanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (estatusReqCanRecId != null ? "estatusReqCanRecId=" + estatusReqCanRecId + ", " : "") +
            "}";
    }

}
