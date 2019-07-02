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
 * Criteria class for the {@link com.kode.ts.domain.NivelPerfil} entity. This class is used
 * in {@link com.kode.ts.web.rest.NivelPerfilResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nivel-perfils?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NivelPerfilCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nivel;

    private LongFilter candidatoId;

    private LongFilter requerimientoId;

    public NivelPerfilCriteria(){
    }

    public NivelPerfilCriteria(NivelPerfilCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nivel = other.nivel == null ? null : other.nivel.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
    }

    @Override
    public NivelPerfilCriteria copy() {
        return new NivelPerfilCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNivel() {
        return nivel;
    }

    public void setNivel(StringFilter nivel) {
        this.nivel = nivel;
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
        final NivelPerfilCriteria that = (NivelPerfilCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nivel, that.nivel) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(requerimientoId, that.requerimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nivel,
        candidatoId,
        requerimientoId
        );
    }

    @Override
    public String toString() {
        return "NivelPerfilCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nivel != null ? "nivel=" + nivel + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
            "}";
    }

}
