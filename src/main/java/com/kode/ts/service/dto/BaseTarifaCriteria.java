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
 * Criteria class for the {@link com.kode.ts.domain.BaseTarifa} entity. This class is used
 * in {@link com.kode.ts.web.rest.BaseTarifaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /base-tarifas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BaseTarifaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter base;

    private LongFilter requerimientoId;

    public BaseTarifaCriteria(){
    }

    public BaseTarifaCriteria(BaseTarifaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.base = other.base == null ? null : other.base.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
    }

    @Override
    public BaseTarifaCriteria copy() {
        return new BaseTarifaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBase() {
        return base;
    }

    public void setBase(StringFilter base) {
        this.base = base;
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
        final BaseTarifaCriteria that = (BaseTarifaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(base, that.base) &&
            Objects.equals(requerimientoId, that.requerimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        base,
        requerimientoId
        );
    }

    @Override
    public String toString() {
        return "BaseTarifaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (base != null ? "base=" + base + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
            "}";
    }

}
