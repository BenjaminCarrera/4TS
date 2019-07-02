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
 * Criteria class for the {@link com.kode.ts.domain.DominioSkill} entity. This class is used
 * in {@link com.kode.ts.web.rest.DominioSkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dominio-skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DominioSkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dominio;

    private LongFilter skillCandidatoId;

    public DominioSkillCriteria(){
    }

    public DominioSkillCriteria(DominioSkillCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dominio = other.dominio == null ? null : other.dominio.copy();
        this.skillCandidatoId = other.skillCandidatoId == null ? null : other.skillCandidatoId.copy();
    }

    @Override
    public DominioSkillCriteria copy() {
        return new DominioSkillCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDominio() {
        return dominio;
    }

    public void setDominio(StringFilter dominio) {
        this.dominio = dominio;
    }

    public LongFilter getSkillCandidatoId() {
        return skillCandidatoId;
    }

    public void setSkillCandidatoId(LongFilter skillCandidatoId) {
        this.skillCandidatoId = skillCandidatoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DominioSkillCriteria that = (DominioSkillCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dominio, that.dominio) &&
            Objects.equals(skillCandidatoId, that.skillCandidatoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dominio,
        skillCandidatoId
        );
    }

    @Override
    public String toString() {
        return "DominioSkillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dominio != null ? "dominio=" + dominio + ", " : "") +
                (skillCandidatoId != null ? "skillCandidatoId=" + skillCandidatoId + ", " : "") +
            "}";
    }

}
