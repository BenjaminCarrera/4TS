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
 * Criteria class for the {@link com.kode.ts.domain.TipoSolicitud} entity. This class is used
 * in {@link com.kode.ts.web.rest.TipoSolicitudResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipo-solicituds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipoSolicitudCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter solicitud;

    private LongFilter requerimientoId;

    public TipoSolicitudCriteria(){
    }

    public TipoSolicitudCriteria(TipoSolicitudCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.solicitud = other.solicitud == null ? null : other.solicitud.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
    }

    @Override
    public TipoSolicitudCriteria copy() {
        return new TipoSolicitudCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(StringFilter solicitud) {
        this.solicitud = solicitud;
    }

    public LongFilter getRequerimientoId() {
        return requerimientoId;
    }

    public void setRequerimientoId(LongFilter requerimientoId) {
        this.requerimientoId = requerimientoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TipoSolicitudCriteria that = (TipoSolicitudCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(solicitud, that.solicitud) &&
            Objects.equals(requerimientoId, that.requerimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        solicitud,
        requerimientoId
        );
    }

    @Override
    public String toString() {
        return "TipoSolicitudCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (solicitud != null ? "solicitud=" + solicitud + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
            "}";
    }

}
