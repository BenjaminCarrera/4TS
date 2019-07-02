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
 * Criteria class for the {@link com.kode.ts.domain.Perfil} entity. This class is used
 * in {@link com.kode.ts.web.rest.PerfilResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /perfils?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PerfilCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter perfil;

    private LongFilter candidatoId;

    private LongFilter requerimientoId;

    public PerfilCriteria(){
    }

    public PerfilCriteria(PerfilCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.perfil = other.perfil == null ? null : other.perfil.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
    }

    @Override
    public PerfilCriteria copy() {
        return new PerfilCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPerfil() {
        return perfil;
    }

    public void setPerfil(StringFilter perfil) {
        this.perfil = perfil;
    }

    public LongFilter getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(LongFilter candidatoId) {
        this.candidatoId = candidatoId;
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
        final PerfilCriteria that = (PerfilCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(perfil, that.perfil) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(requerimientoId, that.requerimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        perfil,
        candidatoId,
        requerimientoId
        );
    }

    @Override
    public String toString() {
        return "PerfilCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (perfil != null ? "perfil=" + perfil + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
            "}";
    }

}
