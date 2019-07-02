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
 * Criteria class for the {@link com.kode.ts.domain.EstatusCandidato} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstatusCandidatoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estatus-candidatoes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstatusCandidatoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter estatus;

    private LongFilter candidatoId;

    private LongFilter estCanInactivoId;

    public EstatusCandidatoCriteria(){
    }

    public EstatusCandidatoCriteria(EstatusCandidatoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.estCanInactivoId = other.estCanInactivoId == null ? null : other.estCanInactivoId.copy();
    }

    @Override
    public EstatusCandidatoCriteria copy() {
        return new EstatusCandidatoCriteria(this);
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

    public LongFilter getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(LongFilter candidatoId) {
        this.candidatoId = candidatoId;
    }

    public LongFilter getEstCanInactivoId() {
        return estCanInactivoId;
    }

    public void setEstCanInactivoId(LongFilter estCanInactivoId) {
        this.estCanInactivoId = estCanInactivoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EstatusCandidatoCriteria that = (EstatusCandidatoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(estCanInactivoId, that.estCanInactivoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        estatus,
        candidatoId,
        estCanInactivoId
        );
    }

    @Override
    public String toString() {
        return "EstatusCandidatoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (estCanInactivoId != null ? "estCanInactivoId=" + estCanInactivoId + ", " : "") +
            "}";
    }

}
