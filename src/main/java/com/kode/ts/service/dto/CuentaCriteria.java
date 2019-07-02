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
 * Criteria class for the {@link com.kode.ts.domain.Cuenta} entity. This class is used
 * in {@link com.kode.ts.web.rest.CuentaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cuentas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CuentaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter clave;

    private StringFilter nombre;

    private LongFilter candidatoInteresId;

    private LongFilter candidatoRechazadasId;

    public CuentaCriteria(){
    }

    public CuentaCriteria(CuentaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.clave = other.clave == null ? null : other.clave.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.candidatoInteresId = other.candidatoInteresId == null ? null : other.candidatoInteresId.copy();
        this.candidatoRechazadasId = other.candidatoRechazadasId == null ? null : other.candidatoRechazadasId.copy();
    }

    @Override
    public CuentaCriteria copy() {
        return new CuentaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClave() {
        return clave;
    }

    public void setClave(StringFilter clave) {
        this.clave = clave;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public LongFilter getCandidatoInteresId() {
        return candidatoInteresId;
    }

    public void setCandidatoInteresId(LongFilter candidatoInteresId) {
        this.candidatoInteresId = candidatoInteresId;
    }

    public LongFilter getCandidatoRechazadasId() {
        return candidatoRechazadasId;
    }

    public void setCandidatoRechazadasId(LongFilter candidatoRechazadasId) {
        this.candidatoRechazadasId = candidatoRechazadasId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CuentaCriteria that = (CuentaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(clave, that.clave) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(candidatoInteresId, that.candidatoInteresId) &&
            Objects.equals(candidatoRechazadasId, that.candidatoRechazadasId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        clave,
        nombre,
        candidatoInteresId,
        candidatoRechazadasId
        );
    }

    @Override
    public String toString() {
        return "CuentaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (clave != null ? "clave=" + clave + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (candidatoInteresId != null ? "candidatoInteresId=" + candidatoInteresId + ", " : "") +
                (candidatoRechazadasId != null ? "candidatoRechazadasId=" + candidatoRechazadasId + ", " : "") +
            "}";
    }

}
