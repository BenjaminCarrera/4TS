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
 * Criteria class for the {@link com.kode.ts.domain.SkillRequerimiento} entity. This class is used
 * in {@link com.kode.ts.web.rest.SkillRequerimientoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skill-requerimientos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkillRequerimientoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter idRequerimientoId;

    private LongFilter idSkillId;

    private LongFilter tipoSkillId;

    public SkillRequerimientoCriteria(){
    }

    public SkillRequerimientoCriteria(SkillRequerimientoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idRequerimientoId = other.idRequerimientoId == null ? null : other.idRequerimientoId.copy();
        this.idSkillId = other.idSkillId == null ? null : other.idSkillId.copy();
        this.tipoSkillId = other.tipoSkillId == null ? null : other.tipoSkillId.copy();
    }

    @Override
    public SkillRequerimientoCriteria copy() {
        return new SkillRequerimientoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdRequerimientoId() {
        return idRequerimientoId;
    }

    public void setIdRequerimientoId(LongFilter idRequerimientoId) {
        this.idRequerimientoId = idRequerimientoId;
    }

    public LongFilter getIdSkillId() {
        return idSkillId;
    }

    public void setIdSkillId(LongFilter idSkillId) {
        this.idSkillId = idSkillId;
    }

    public LongFilter getTipoSkillId() {
        return tipoSkillId;
    }

    public void setTipoSkillId(LongFilter tipoSkillId) {
        this.tipoSkillId = tipoSkillId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SkillRequerimientoCriteria that = (SkillRequerimientoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idRequerimientoId, that.idRequerimientoId) &&
            Objects.equals(idSkillId, that.idSkillId) &&
            Objects.equals(tipoSkillId, that.tipoSkillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idRequerimientoId,
        idSkillId,
        tipoSkillId
        );
    }

    @Override
    public String toString() {
        return "SkillRequerimientoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idRequerimientoId != null ? "idRequerimientoId=" + idRequerimientoId + ", " : "") +
                (idSkillId != null ? "idSkillId=" + idSkillId + ", " : "") +
                (tipoSkillId != null ? "tipoSkillId=" + tipoSkillId + ", " : "") +
            "}";
    }

}
