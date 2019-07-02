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
 * Criteria class for the {@link com.kode.ts.domain.EsqContRec} entity. This class is used
 * in {@link com.kode.ts.web.rest.EsqContRecResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /esq-cont-recs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EsqContRecCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter esquema;

    private LongFilter candidatoId;

    private LongFilter requerimientoId;

    public EsqContRecCriteria(){
    }

    public EsqContRecCriteria(EsqContRecCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.esquema = other.esquema == null ? null : other.esquema.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
    }

    @Override
    public EsqContRecCriteria copy() {
        return new EsqContRecCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEsquema() {
        return esquema;
    }

    public void setEsquema(StringFilter esquema) {
        this.esquema = esquema;
    }

    public LongFilter getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(LongFilter candidatoId) {
        this.candidatoId = candidatoId;
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
        final EsqContRecCriteria that = (EsqContRecCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(esquema, that.esquema) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(requerimientoId, that.requerimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        esquema,
        candidatoId,
        requerimientoId
        );
    }

    @Override
    public String toString() {
        return "EsqContRecCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (esquema != null ? "esquema=" + esquema + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
            "}";
    }

}
