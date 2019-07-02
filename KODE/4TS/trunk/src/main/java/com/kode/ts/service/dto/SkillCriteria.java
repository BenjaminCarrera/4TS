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
 * Criteria class for the {@link com.kode.ts.domain.Skill} entity. This class is used
 * in {@link com.kode.ts.web.rest.SkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private LongFilter skillCandidatoId;

    private LongFilter skillRequerimientoId;

    private LongFilter categoriaSkillId;

    public SkillCriteria(){
    }

    public SkillCriteria(SkillCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.skillCandidatoId = other.skillCandidatoId == null ? null : other.skillCandidatoId.copy();
        this.skillRequerimientoId = other.skillRequerimientoId == null ? null : other.skillRequerimientoId.copy();
        this.categoriaSkillId = other.categoriaSkillId == null ? null : other.categoriaSkillId.copy();
    }

    @Override
    public SkillCriteria copy() {
        return new SkillCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public LongFilter getSkillCandidatoId() {
        return skillCandidatoId;
    }

    public void setSkillCandidatoId(LongFilter skillCandidatoId) {
        this.skillCandidatoId = skillCandidatoId;
    }

    public LongFilter getSkillRequerimientoId() {
        return skillRequerimientoId;
    }

    public void setSkillRequerimientoId(LongFilter skillRequerimientoId) {
        this.skillRequerimientoId = skillRequerimientoId;
    }

    public LongFilter getCategoriaSkillId() {
        return categoriaSkillId;
    }

    public void setCategoriaSkillId(LongFilter categoriaSkillId) {
        this.categoriaSkillId = categoriaSkillId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SkillCriteria that = (SkillCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(skillCandidatoId, that.skillCandidatoId) &&
            Objects.equals(skillRequerimientoId, that.skillRequerimientoId) &&
            Objects.equals(categoriaSkillId, that.categoriaSkillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        skillCandidatoId,
        skillRequerimientoId,
        categoriaSkillId
        );
    }

    @Override
    public String toString() {
        return "SkillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (skillCandidatoId != null ? "skillCandidatoId=" + skillCandidatoId + ", " : "") +
                (skillRequerimientoId != null ? "skillRequerimientoId=" + skillRequerimientoId + ", " : "") +
                (categoriaSkillId != null ? "categoriaSkillId=" + categoriaSkillId + ", " : "") +
            "}";
    }

}
