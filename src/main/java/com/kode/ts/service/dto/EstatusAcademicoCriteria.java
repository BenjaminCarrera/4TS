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
 * Criteria class for the {@link com.kode.ts.domain.EstatusAcademico} entity. This class is used
 * in {@link com.kode.ts.web.rest.EstatusAcademicoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estatus-academicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstatusAcademicoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter estatus;

    private LongFilter candidatoId;

    public EstatusAcademicoCriteria(){
    }

    public EstatusAcademicoCriteria(EstatusAcademicoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
    }

    @Override
    public EstatusAcademicoCriteria copy() {
        return new EstatusAcademicoCriteria(this);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EstatusAcademicoCriteria that = (EstatusAcademicoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(candidatoId, that.candidatoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        estatus,
        candidatoId
        );
    }

    @Override
    public String toString() {
        return "EstatusAcademicoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
            "}";
    }

}
