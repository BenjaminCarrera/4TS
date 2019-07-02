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
 * Criteria class for the {@link com.kode.ts.domain.ReferenciasLaborales} entity. This class is used
 * in {@link com.kode.ts.web.rest.ReferenciasLaboralesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /referencias-laborales?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReferenciasLaboralesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empresa;

    private StringFilter nombreContacto;

    private StringFilter puestoContacto;

    private StringFilter emailContaco;

    private StringFilter telefonoContacto;

    private LongFilter candidatoId;

    public ReferenciasLaboralesCriteria(){
    }

    public ReferenciasLaboralesCriteria(ReferenciasLaboralesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.empresa = other.empresa == null ? null : other.empresa.copy();
        this.nombreContacto = other.nombreContacto == null ? null : other.nombreContacto.copy();
        this.puestoContacto = other.puestoContacto == null ? null : other.puestoContacto.copy();
        this.emailContaco = other.emailContaco == null ? null : other.emailContaco.copy();
        this.telefonoContacto = other.telefonoContacto == null ? null : other.telefonoContacto.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
    }

    @Override
    public ReferenciasLaboralesCriteria copy() {
        return new ReferenciasLaboralesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmpresa() {
        return empresa;
    }

    public void setEmpresa(StringFilter empresa) {
        this.empresa = empresa;
    }

    public StringFilter getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(StringFilter nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public StringFilter getPuestoContacto() {
        return puestoContacto;
    }

    public void setPuestoContacto(StringFilter puestoContacto) {
        this.puestoContacto = puestoContacto;
    }

    public StringFilter getEmailContaco() {
        return emailContaco;
    }

    public void setEmailContaco(StringFilter emailContaco) {
        this.emailContaco = emailContaco;
    }

    public StringFilter getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(StringFilter telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
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
        final ReferenciasLaboralesCriteria that = (ReferenciasLaboralesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(empresa, that.empresa) &&
            Objects.equals(nombreContacto, that.nombreContacto) &&
            Objects.equals(puestoContacto, that.puestoContacto) &&
            Objects.equals(emailContaco, that.emailContaco) &&
            Objects.equals(telefonoContacto, that.telefonoContacto) &&
            Objects.equals(candidatoId, that.candidatoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        empresa,
        nombreContacto,
        puestoContacto,
        emailContaco,
        telefonoContacto,
        candidatoId
        );
    }

    @Override
    public String toString() {
        return "ReferenciasLaboralesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (empresa != null ? "empresa=" + empresa + ", " : "") +
                (nombreContacto != null ? "nombreContacto=" + nombreContacto + ", " : "") +
                (puestoContacto != null ? "puestoContacto=" + puestoContacto + ", " : "") +
                (emailContaco != null ? "emailContaco=" + emailContaco + ", " : "") +
                (telefonoContacto != null ? "telefonoContacto=" + telefonoContacto + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
            "}";
    }

}
