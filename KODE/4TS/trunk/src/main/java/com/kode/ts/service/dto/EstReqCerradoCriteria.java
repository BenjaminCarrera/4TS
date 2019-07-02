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
 * Criteria class for the {@link com.kode.ts.domain.EstReqCerrado} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstReqCerradoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /est-req-cerrados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstReqCerradoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter motivo;

    private LongFilter requerimientoId;

    private LongFilter estatusRequerimientoId;

    public EstReqCerradoCriteria(){
    }

    public EstReqCerradoCriteria(EstReqCerradoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.motivo = other.motivo == null ? null : other.motivo.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
        this.estatusRequerimientoId = other.estatusRequerimientoId == null ? null : other.estatusRequerimientoId.copy();
    }

    @Override
    public EstReqCerradoCriteria copy() {
        return new EstReqCerradoCriteria(this);
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

    public LongFilter getRequerimientoId() {
        return requerimientoId;
    }

    public void setRequerimientoId(LongFilter requerimientoId) {
        this.requerimientoId = requerimientoId;
    }

    public LongFilter getEstatusRequerimientoId() {
        return estatusRequerimientoId;
    }

    public void setEstatusRequerimientoId(LongFilter estatusRequerimientoId) {
        this.estatusRequerimientoId = estatusRequerimientoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EstReqCerradoCriteria that = (EstReqCerradoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(motivo, that.motivo) &&
            Objects.equals(requerimientoId, that.requerimientoId) &&
            Objects.equals(estatusRequerimientoId, that.estatusRequerimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        motivo,
        requerimientoId,
        estatusRequerimientoId
        );
    }

    @Override
    public String toString() {
        return "EstReqCerradoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (motivo != null ? "motivo=" + motivo + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
                (estatusRequerimientoId != null ? "estatusRequerimientoId=" + estatusRequerimientoId + ", " : "") +
            "}";
    }

}
