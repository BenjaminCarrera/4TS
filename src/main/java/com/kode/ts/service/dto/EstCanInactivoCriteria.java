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
 * Criteria class for the {@link com.kode.ts.domain.EstCanInactivo} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstCanInactivoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /est-can-inactivos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstCanInactivoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter motivo;

    private LongFilter candidatoId;

    private LongFilter estatusCandidatoId;

    public EstCanInactivoCriteria(){
    }

    public EstCanInactivoCriteria(EstCanInactivoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.motivo = other.motivo == null ? null : other.motivo.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.estatusCandidatoId = other.estatusCandidatoId == null ? null : other.estatusCandidatoId.copy();
    }

    @Override
    public EstCanInactivoCriteria copy() {
        return new EstCanInactivoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMotivo() {
        return motivo;
    }

    public void setMotivo(StringFilter motivo) {
        this.motivo = motivo;
    }

    public LongFilter getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(LongFilter candidatoId) {
        this.candidatoId = candidatoId;
    }

    public LongFilter getEstatusCandidatoId() {
        return estatusCandidatoId;
    }

    public void setEstatusCandidatoId(LongFilter estatusCandidatoId) {
        this.estatusCandidatoId = estatusCandidatoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EstCanInactivoCriteria that = (EstCanInactivoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(motivo, that.motivo) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(estatusCandidatoId, that.estatusCandidatoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        motivo,
        candidatoId,
        estatusCandidatoId
        );
    }

    @Override
    public String toString() {
        return "EstCanInactivoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (motivo != null ? "motivo=" + motivo + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (estatusCandidatoId != null ? "estatusCandidatoId=" + estatusCandidatoId + ", " : "") +
            "}";
    }

}
