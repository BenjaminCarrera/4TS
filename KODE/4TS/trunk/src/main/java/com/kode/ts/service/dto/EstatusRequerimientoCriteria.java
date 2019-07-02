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
 * Criteria class for the {@link com.kode.ts.domain.EstatusRequerimiento} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstatusRequerimientoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estatus-requerimientos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstatusRequerimientoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter estatus;

    private LongFilter requerimientoId;

    private LongFilter estReqCerradoId;

    public EstatusRequerimientoCriteria(){
    }

    public EstatusRequerimientoCriteria(EstatusRequerimientoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
        this.estReqCerradoId = other.estReqCerradoId == null ? null : other.estReqCerradoId.copy();
    }

    @Override
    public EstatusRequerimientoCriteria copy() {
        return new EstatusRequerimientoCriteria(this);
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

    public LongFilter getRequerimientoId() {
        return requerimientoId;
    }

    public void setRequerimientoId(LongFilter requerimientoId) {
        this.requerimientoId = requerimientoId;
    }

    public LongFilter getEstReqCerradoId() {
        return estReqCerradoId;
    }

    public void setEstReqCerradoId(LongFilter estReqCerradoId) {
        this.estReqCerradoId = estReqCerradoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EstatusRequerimientoCriteria that = (EstatusRequerimientoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(requerimientoId, that.requerimientoId) &&
            Objects.equals(estReqCerradoId, that.estReqCerradoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        estatus,
        requerimientoId,
        estReqCerradoId
        );
    }

    @Override
    public String toString() {
        return "EstatusRequerimientoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
                (estReqCerradoId != null ? "estReqCerradoId=" + estReqCerradoId + ", " : "") +
            "}";
    }

}
