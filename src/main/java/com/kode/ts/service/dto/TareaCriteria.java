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
 * Criteria class for the {@link com.kode.ts.domain.Tarea} entity. This class is used
 * in {@link com.kode.ts.web.rest.TareaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tareas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TareaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descripcion;

    private StringFilter titulo;

    private LongFilter bitacoraId;

    private LongFilter usuarioCreadorId;

    private LongFilter usuarioEjecutorId;

    private LongFilter requerimientoId;

    private LongFilter candidatoId;

    private LongFilter estatusTareaId;

    private LongFilter tipoTareaId;

    public TareaCriteria(){
    }

    public TareaCriteria(TareaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.bitacoraId = other.bitacoraId == null ? null : other.bitacoraId.copy();
        this.usuarioCreadorId = other.usuarioCreadorId == null ? null : other.usuarioCreadorId.copy();
        this.usuarioEjecutorId = other.usuarioEjecutorId == null ? null : other.usuarioEjecutorId.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.estatusTareaId = other.estatusTareaId == null ? null : other.estatusTareaId.copy();
        this.tipoTareaId = other.tipoTareaId == null ? null : other.tipoTareaId.copy();
    }

    @Override
    public TareaCriteria copy() {
        return new TareaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public LongFilter getBitacoraId() {
        return bitacoraId;
    }

    public void setBitacoraId(LongFilter bitacoraId) {
        this.bitacoraId = bitacoraId;
    }

    public LongFilter getUsuarioCreadorId() {
        return usuarioCreadorId;
    }

    public void setUsuarioCreadorId(LongFilter usuarioCreadorId) {
        this.usuarioCreadorId = usuarioCreadorId;
    }

    public LongFilter getUsuarioEjecutorId() {
        return usuarioEjecutorId;
    }

    public void setUsuarioEjecutorId(LongFilter usuarioEjecutorId) {
        this.usuarioEjecutorId = usuarioEjecutorId;
    }

    public LongFilter getRequerimientoId() {
        return requerimientoId;
    }

    public void setRequerimientoId(LongFilter requerimientoId) {
        this.requerimientoId = requerimientoId;
    }

    public LongFilter getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(LongFilter candidatoId) {
        this.candidatoId = candidatoId;
    }

    public LongFilter getEstatusTareaId() {
        return estatusTareaId;
    }

    public void setEstatusTareaId(LongFilter estatusTareaId) {
        this.estatusTareaId = estatusTareaId;
    }

    public LongFilter getTipoTareaId() {
        return tipoTareaId;
    }

    public void setTipoTareaId(LongFilter tipoTareaId) {
        this.tipoTareaId = tipoTareaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TareaCriteria that = (TareaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(bitacoraId, that.bitacoraId) &&
            Objects.equals(usuarioCreadorId, that.usuarioCreadorId) &&
            Objects.equals(usuarioEjecutorId, that.usuarioEjecutorId) &&
            Objects.equals(requerimientoId, that.requerimientoId) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(estatusTareaId, that.estatusTareaId) &&
            Objects.equals(tipoTareaId, that.tipoTareaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descripcion,
        titulo,
        bitacoraId,
        usuarioCreadorId,
        usuarioEjecutorId,
        requerimientoId,
        candidatoId,
        estatusTareaId,
        tipoTareaId
        );
    }

    @Override
    public String toString() {
        return "TareaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (bitacoraId != null ? "bitacoraId=" + bitacoraId + ", " : "") +
                (usuarioCreadorId != null ? "usuarioCreadorId=" + usuarioCreadorId + ", " : "") +
                (usuarioEjecutorId != null ? "usuarioEjecutorId=" + usuarioEjecutorId + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (estatusTareaId != null ? "estatusTareaId=" + estatusTareaId + ", " : "") +
                (tipoTareaId != null ? "tipoTareaId=" + tipoTareaId + ", " : "") +
            "}";
    }

}
