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
 * Criteria class for the {@link com.kode.ts.domain.EstatusTarea} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstatusTareaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estatus-tareas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstatusTareaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter estatus;

    private LongFilter tareaId;

    public EstatusTareaCriteria(){
    }

    public EstatusTareaCriteria(EstatusTareaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.tareaId = other.tareaId == null ? null : other.tareaId.copy();
    }

    @Override
    public EstatusTareaCriteria copy() {
        return new EstatusTareaCriteria(this);
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

    public LongFilter getTareaId() {
        return tareaId;
    }

    public void setTareaId(LongFilter tareaId) {
        this.tareaId = tareaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EstatusTareaCriteria that = (EstatusTareaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(tareaId, that.tareaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        estatus,
        tareaId
        );
    }

    @Override
    public String toString() {
        return "EstatusTareaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (tareaId != null ? "tareaId=" + tareaId + ", " : "") +
            "}";
    }

}
