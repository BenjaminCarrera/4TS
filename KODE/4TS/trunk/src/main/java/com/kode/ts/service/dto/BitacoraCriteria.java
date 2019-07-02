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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.kode.ts.domain.Bitacora} entity. This class is used
 * in {@link com.kode.ts.web.rest.BitacoraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bitacoras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BitacoraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter fecha;

    private StringFilter comentario;

    private LongFilter usuarioId;

    private LongFilter requerimientoId;

    private LongFilter candidatoId;

    private LongFilter tareaId;

    public BitacoraCriteria(){
    }

    public BitacoraCriteria(BitacoraCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.comentario = other.comentario == null ? null : other.comentario.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.requerimientoId = other.requerimientoId == null ? null : other.requerimientoId.copy();
        this.candidatoId = other.candidatoId == null ? null : other.candidatoId.copy();
        this.tareaId = other.tareaId == null ? null : other.tareaId.copy();
    }

    @Override
    public BitacoraCriteria copy() {
        return new BitacoraCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTimeFilter fecha) {
        this.fecha = fecha;
    }

    public StringFilter getComentario() {
        return comentario;
    }

    public void setComentario(StringFilter comentario) {
        this.comentario = comentario;
    }

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
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

    public LongFilter getTareaId() {
        return tareaId;
    }

    public void setTareaId(LongFilter tareaId) {
        this.tareaId = tareaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BitacoraCriteria that = (BitacoraCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(comentario, that.comentario) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(requerimientoId, that.requerimientoId) &&
            Objects.equals(candidatoId, that.candidatoId) &&
            Objects.equals(tareaId, that.tareaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fecha,
        comentario,
        usuarioId,
        requerimientoId,
        candidatoId,
        tareaId
        );
    }

    @Override
    public String toString() {
        return "BitacoraCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (comentario != null ? "comentario=" + comentario + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (requerimientoId != null ? "requerimientoId=" + requerimientoId + ", " : "") +
                (candidatoId != null ? "candidatoId=" + candidatoId + ", " : "") +
                (tareaId != null ? "tareaId=" + tareaId + ", " : "") +
            "}";
    }

}
