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
 * Criteria class for the {@link com.kode.ts.domain.Colonia} entity. This class is used
 * in {@link com.kode.ts.web.rest.ColoniaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /colonias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ColoniaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter colonia;

    private LongFilter candidatoId;

    private LongFilter municipioId;

    private LongFilter codigoPostalId;

    public ColoniaCriteria(){
    }

    public ColoniaCriteria(ColoniaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.colonia = other.colonia == null ? null : other.colonia.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
        this.codigoPostalId = other.codigoPostalId == null ? null : other.codigoPostalId.copy();
    }

    @Override
    public ColoniaCriteria copy() {
        return new ColoniaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getColonia() {
        return colonia;
    }

    public void setColonia(StringFilter colonia) {
        this.colonia = colonia;
    }

    public LongFilter getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(LongFilter candidatoId) {
        this.candidatoId = candidatoId;
    }

    public LongFilter getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(LongFilter municipioId) {
        this.municipioId = municipioId;
    }

    public LongFilter getCodigoPostalId() {
        return codigoPostalId;
    }

    public void setCodigoPostalId(LongFilter codigoPostalId) {
        this.codigoPostalId = codigoPostalId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ColoniaCriteria that = (ColoniaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(colonia, that.colonia) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(municipioId, that.municipioId) &&
            Objects.equals(codigoPostalId, that.codigoPostalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        colonia,
        candidatoId,
        municipioId,
        codigoPostalId
        );
    }

    @Override
    public String toString() {
        return "ColoniaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (colonia != null ? "colonia=" + colonia + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
                (codigoPostalId != null ? "codigoPostalId=" + codigoPostalId + ", " : "") +
            "}";
    }

}
