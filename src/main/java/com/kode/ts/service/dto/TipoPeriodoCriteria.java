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
 * Criteria class for the {@link com.kode.ts.domain.TipoPeriodo} entity. This class is used
 * in {@link com.kode.ts.web.rest.TipoPeriodoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipo-periodos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipoPeriodoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter periodo;

    private LongFilter requerimientoId;

    public TipoPeriodoCriteria(){
    }

    public TipoPeriodoCriteria(TipoPeriodoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.periodo = other.periodo == null ? null : other.periodo.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
    }

    @Override
    public TipoPeriodoCriteria copy() {
        return new TipoPeriodoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPeriodo() {
        return periodo;
    }

    public void setPeriodo(StringFilter periodo) {
        this.periodo = periodo;
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
        final TipoPeriodoCriteria that = (TipoPeriodoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(periodo, that.periodo) &&
            Objects.equals(requerimientoId, that.requerimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        periodo,
        requerimientoId
        );
    }

    @Override
    public String toString() {
        return "TipoPeriodoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (periodo != null ? "periodo=" + periodo + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
            "}";
    }

}
