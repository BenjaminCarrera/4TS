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
 * Criteria class for the {@link com.kode.ts.domain.CodigoPostal} entity. This class is used
 * in {@link com.kode.ts.web.rest.CodigoPostalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /codigo-postals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CodigoPostalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigoPostal;

    private LongFilter coloniaId;

    private LongFilter municipioId;

    public CodigoPostalCriteria(){
    }

    public CodigoPostalCriteria(CodigoPostalCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.codigoPostal = other.codigoPostal == null ? null : other.codigoPostal.copy();
        this.coloniaId = other.coloniaId == null ? null : other.coloniaId.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
    }

    @Override
    public CodigoPostalCriteria copy() {
        return new CodigoPostalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(StringFilter codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public LongFilter getColoniaId() {
        return coloniaId;
    }

    public void setColoniaId(LongFilter coloniaId) {
        this.coloniaId = coloniaId;
    }

    public LongFilter getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(LongFilter municipioId) {
        this.municipioId = municipioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CodigoPostalCriteria that = (CodigoPostalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(codigoPostal, that.codigoPostal) &&
            Objects.equals(coloniaId, that.coloniaId) &&
            Objects.equals(municipioId, that.municipioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        codigoPostal,
        coloniaId,
        municipioId
        );
    }

    @Override
    public String toString() {
        return "CodigoPostalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codigoPostal != null ? "codigoPostal=" + codigoPostal + ", " : "") +
                (coloniaId != null ? "coloniaId=" + coloniaId + ", " : "") +
                (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
            "}";
    }

}
