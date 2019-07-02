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
 * Criteria class for the {@link com.kode.ts.domain.EstatusReqCanRec} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstatusReqCanRecResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estatus-req-can-recs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstatusReqCanRecCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter motivo;

    private LongFilter estatusReqCanId;

    public EstatusReqCanRecCriteria(){
    }

    public EstatusReqCanRecCriteria(EstatusReqCanRecCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.motivo = other.motivo == null ? null : other.motivo.copy();
        this.estatusReqCanId = other.estatusReqCanId == null ? null : other.estatusReqCanId.copy();
    }

    @Override
    public EstatusReqCanRecCriteria copy() {
        return new EstatusReqCanRecCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMotivo() {
        return motivo;
    }

    public void setMotivo(StringFilter motivo) {
        this.motivo = motivo;
    }

    public LongFilter getEstatusReqCanId() {
        return estatusReqCanId;
    }

    public void setEstatusReqCanId(LongFilter estatusReqCanId) {
        this.estatusReqCanId = estatusReqCanId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EstatusReqCanRecCriteria that = (EstatusReqCanRecCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(motivo, that.motivo) &&
            Objects.equals(estatusReqCanId, that.estatusReqCanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        motivo,
        estatusReqCanId
        );
    }

    @Override
    public String toString() {
        return "EstatusReqCanRecCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (motivo != null ? "motivo=" + motivo + ", " : "") +
                (estatusReqCanId != null ? "estatusReqCanId=" + estatusReqCanId + ", " : "") +
            "}";
    }

}
