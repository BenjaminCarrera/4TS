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
 * Criteria class for the {@link com.kode.ts.domain.TipoSkill} entity. This class is used
 * in {@link com.kode.ts.web.rest.TipoSkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipo-skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipoSkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipo;

    private LongFilter skillRequerimientoId;

    public TipoSkillCriteria(){
    }

    public TipoSkillCriteria(TipoSkillCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.skillRequerimientoId = other.skillRequerimientoId == null ? null : other.skillRequerimientoId.copy();
    }

    @Override
    public TipoSkillCriteria copy() {
        return new TipoSkillCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipo() {
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public LongFilter getSkillRequerimientoId() {
        return skillRequerimientoId;
    }

    public void setSkillRequerimientoId(LongFilter skillRequerimientoId) {
        this.skillRequerimientoId = skillRequerimientoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TipoSkillCriteria that = (TipoSkillCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(skillRequerimientoId, that.skillRequerimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipo,
        skillRequerimientoId
        );
    }

    @Override
    public String toString() {
        return "TipoSkillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (skillRequerimientoId != null ? "skillRequerimientoId=" + skillRequerimientoId + ", " : "") +
            "}";
    }

}
