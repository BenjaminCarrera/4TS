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
 * Criteria class for the {@link com.kode.ts.domain.FormacionAcademica} entity. This class is used
 * in {@link com.kode.ts.web.rest.FormacionAcademicaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /formacion-academicas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FormacionAcademicaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter candidatoId;

    public FormacionAcademicaCriteria(){
    }

    public FormacionAcademicaCriteria(FormacionAcademicaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
    }

    @Override
    public FormacionAcademicaCriteria copy() {
        return new FormacionAcademicaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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
        final FormacionAcademicaCriteria that = (FormacionAcademicaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(candidatoId, that.candidatoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        candidatoId
        );
    }

    @Override
    public String toString() {
        return "FormacionAcademicaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
            "}";
    }

}
