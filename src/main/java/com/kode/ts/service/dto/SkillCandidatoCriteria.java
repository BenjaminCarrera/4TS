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
 * Criteria class for the {@link com.kode.ts.domain.SkillCandidato} entity. This class is used
 * in {@link com.kode.ts.web.rest.SkillCandidatoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skill-candidatoes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkillCandidatoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter calificacionSkill;

    private LongFilter idCandidatoId;

    private LongFilter idSkillId;

    private LongFilter nivelSkillId;

    public SkillCandidatoCriteria(){
    }

    public SkillCandidatoCriteria(SkillCandidatoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.calificacionSkill = other.calificacionSkill == null ? null : other.calificacionSkill.copy();
        this.idCandidatoId = other.idCandidatoId == null ? null : other.idCandidatoId.copy();
        this.idSkillId = other.idSkillId == null ? null : other.idSkillId.copy();
        this.nivelSkillId = other.nivelSkillId == null ? null : other.nivelSkillId.copy();
    }

    @Override
    public SkillCandidatoCriteria copy() {
        return new SkillCandidatoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getCalificacionSkill() {
        return calificacionSkill;
    }

    public void setCalificacionSkill(FloatFilter calificacionSkill) {
        this.calificacionSkill = calificacionSkill;
    }

    public LongFilter getIdCandidatoId() {
        return idCandidatoId;
    }

    public void setIdCandidatoId(LongFilter idCandidatoId) {
        this.idCandidatoId = idCandidatoId;
    }

    public LongFilter getIdSkillId() {
        return idSkillId;
    }

    public void setIdSkillId(LongFilter idSkillId) {
        this.idSkillId = idSkillId;
    }

    public LongFilter getNivelSkillId() {
        return nivelSkillId;
    }

    public void setNivelSkillId(LongFilter nivelSkillId) {
        this.nivelSkillId = nivelSkillId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SkillCandidatoCriteria that = (SkillCandidatoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(calificacionSkill, that.calificacionSkill) &&
            Objects.equals(idCandidatoId, that.idCandidatoId) &&
            Objects.equals(idSkillId, that.idSkillId) &&
            Objects.equals(nivelSkillId, that.nivelSkillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        calificacionSkill,
        idCandidatoId,
        idSkillId,
        nivelSkillId
        );
    }

    @Override
    public String toString() {
        return "SkillCandidatoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (calificacionSkill != null ? "calificacionSkill=" + calificacionSkill + ", " : "") +
                (idCandidatoId != null ? "idCandidatoId=" + idCandidatoId + ", " : "") +
                (idSkillId != null ? "idSkillId=" + idSkillId + ", " : "") +
                (nivelSkillId != null ? "nivelSkillId=" + nivelSkillId + ", " : "") +
            "}";
    }

}
