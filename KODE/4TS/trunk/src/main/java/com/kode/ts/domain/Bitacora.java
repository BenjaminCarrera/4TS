package com.kode.ts.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Bitacora.
 */
@Entity
@Table(name = "bitacora")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @Size(max = 500)
    @Column(name = "comentario", length = 500)
    private String comentario;

    @ManyToOne
    @JsonIgnoreProperties("bitacoras")
    private User usuario;

    @ManyToOne
    @JsonIgnoreProperties("bitacoras")
    private Requerimiento requerimiento;

    @ManyToOne
    @JsonIgnoreProperties("bitacoras")
    private Candidato candidato;

    @ManyToOne
    @JsonIgnoreProperties("bitacoras")
    private Tarea tarea;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Bitacora fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public Bitacora comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public User getUsuario() {
        return usuario;
    }

    public Bitacora usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public Bitacora requerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
        return this;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public Bitacora candidato(Candidato candidato) {
        this.candidato = candidato;
        return this;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public Bitacora tarea(Tarea tarea) {
        this.tarea = tarea;
        return this;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bitacora)) {
            return false;
        }
        return id != null && id.equals(((Bitacora) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bitacora{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}
