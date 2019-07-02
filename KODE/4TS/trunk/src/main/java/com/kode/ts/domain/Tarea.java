package com.kode.ts.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tarea.
 */
@Entity
@Table(name = "tarea")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 300)
    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Size(max = 100)
    @Column(name = "titulo", length = 100)
    private String titulo;

    @OneToMany(mappedBy = "tarea")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bitacora> bitacoras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tareas")
    private User usuarioCreador;

    @ManyToOne
    @JsonIgnoreProperties("tareas")
    private User usuarioEjecutor;

    @ManyToOne
    @JsonIgnoreProperties("tareas")
    private Requerimiento requerimiento;

    @ManyToOne
    @JsonIgnoreProperties("tareas")
    private Candidato candidato;

    @ManyToOne
    @JsonIgnoreProperties("tareas")
    private EstatusTarea estatusTarea;

    @ManyToOne
    @JsonIgnoreProperties("tareas")
    private TipoTarea tipoTarea;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Tarea descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public Tarea titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Bitacora> getBitacoras() {
        return bitacoras;
    }

    public Tarea bitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
        return this;
    }

    public Tarea addBitacora(Bitacora bitacora) {
        this.bitacoras.add(bitacora);
        bitacora.setTarea(this);
        return this;
    }

    public Tarea removeBitacora(Bitacora bitacora) {
        this.bitacoras.remove(bitacora);
        bitacora.setTarea(null);
        return this;
    }

    public void setBitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
    }

    public User getUsuarioCreador() {
        return usuarioCreador;
    }

    public Tarea usuarioCreador(User user) {
        this.usuarioCreador = user;
        return this;
    }

    public void setUsuarioCreador(User user) {
        this.usuarioCreador = user;
    }

    public User getUsuarioEjecutor() {
        return usuarioEjecutor;
    }

    public Tarea usuarioEjecutor(User user) {
        this.usuarioEjecutor = user;
        return this;
    }

    public void setUsuarioEjecutor(User user) {
        this.usuarioEjecutor = user;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public Tarea requerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
        return this;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public Tarea candidato(Candidato candidato) {
        this.candidato = candidato;
        return this;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public EstatusTarea getEstatusTarea() {
        return estatusTarea;
    }

    public Tarea estatusTarea(EstatusTarea estatusTarea) {
        this.estatusTarea = estatusTarea;
        return this;
    }

    public void setEstatusTarea(EstatusTarea estatusTarea) {
        this.estatusTarea = estatusTarea;
    }

    public TipoTarea getTipoTarea() {
        return tipoTarea;
    }

    public Tarea tipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
        return this;
    }

    public void setTipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarea)) {
            return false;
        }
        return id != null && id.equals(((Tarea) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tarea{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
