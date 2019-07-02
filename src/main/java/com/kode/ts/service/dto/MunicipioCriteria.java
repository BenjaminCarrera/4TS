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
 * Criteria class for the {@link com.kode.ts.domain.Municipio} entity. This class is used
 * in {@link com.kode.ts.web.rest.MunicipioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /municipios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MunicipioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter municipio;

    private LongFilter coloniaId;

    private LongFilter estadoId;

    private LongFilter codigoPostalId;

    public MunicipioCriteria(){
    }

    public MunicipioCriteria(MunicipioCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.municipio = other.municipio == null ? null : other.municipio.copy();
        this.coloniaId = other.coloniaId == null ? null : other.coloniaId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.codigoPostalId = other.codigoPostalId == null ? null : other.codigoPostalId.copy();
    }

    @Override
    public MunicipioCriteria copy() {
        return new MunicipioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMunicipio() {
        return municipio;
    }

    public void setMunicipio(StringFilter municipio) {
        this.municipio = municipio;
    }

    public LongFilter getColoniaId() {
        return coloniaId;
    }

    public void setColoniaId(LongFilter coloniaId) {
        this.coloniaId = coloniaId;
    }

    public LongFilter getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(LongFilter estadoId) {
        this.estadoId = estadoId;
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
        final MunicipioCriteria that = (MunicipioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(municipio, that.municipio) &&
            Objects.equals(coloniaId, that.coloniaId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(codigoPostalId, that.codigoPostalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        municipio,
        coloniaId,
        estadoId,
        codigoPostalId
        );
    }

    @Override
    public String toString() {
        return "MunicipioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (municipio != null ? "municipio=" + municipio + ", " : "") +
                (coloniaId != null ? "coloniaId=" + coloniaId + ", " : "") +
                (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
                (codigoPostalId != null ? "codigoPostalId=" + codigoPostalId + ", " : "") +
            "}";
    }

}
