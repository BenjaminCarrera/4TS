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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.kode.ts.domain.PermisoAuthority} entity. This class is used
 * in {@link com.kode.ts.web.rest.PermisoAuthorityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /permiso-authorities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PermisoAuthorityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter authority;

    private BooleanFilter activated;

    private BooleanFilter deleted;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter permisoId;

    public PermisoAuthorityCriteria(){
    }

    public PermisoAuthorityCriteria(PermisoAuthorityCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.authority = other.authority == null ? null : other.authority.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.permisoId = other.permisoId == null ? null : other.permisoId.copy();
    }

    @Override
    public PermisoAuthorityCriteria copy() {
        return new PermisoAuthorityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAuthority() {
        return authority;
    }

    public void setAuthority(StringFilter authority) {
        this.authority = authority;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
        this.deleted = deleted;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getPermisoId() {
        return permisoId;
    }

    public void setPermisoId(LongFilter permisoId) {
        this.permisoId = permisoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PermisoAuthorityCriteria that = (PermisoAuthorityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(authority, that.authority) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(permisoId, that.permisoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        authority,
        activated,
        deleted,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        permisoId
        );
    }

    @Override
    public String toString() {
        return "PermisoAuthorityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (authority != null ? "authority=" + authority + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (deleted != null ? "deleted=" + deleted + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (permisoId != null ? "permisoId=" + permisoId + ", " : "") +
            "}";
    }

}
