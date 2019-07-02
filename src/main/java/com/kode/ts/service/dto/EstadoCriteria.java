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
 * Criteria class for the {@link com.kode.ts.domain.Estado} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstadoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstadoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter estado;

    private LongFilter municipioId;

    public EstadoCriteria(){
    }

    public EstadoCriteria(EstadoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
    }

    @Override
    public EstadoCriteria copy() {
        return new EstadoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
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
        final EstadoCriteria that = (EstadoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(municipioId, that.municipioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        estado,
        municipioId
        );
    }

    @Override
    public String toString() {
        return "EstadoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
            "}";
    }

}
