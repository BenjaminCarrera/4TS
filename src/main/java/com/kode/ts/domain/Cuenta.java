package com.kode.ts.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cuenta.
 */
@Entity
@Table(name = "cuenta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "clave", length = 100)
    private String clave;

    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @ManyToMany(mappedBy = "cuentaInteres")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Candidato> candidatoInteres = new HashSet<>();

    @ManyToMany(mappedBy = "cuentaRechazadas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Candidato> candidatoRechazadas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public Cuenta clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public Cuenta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Candidato> getCandidatoInteres() {
        return candidatoInteres;
    }

    public Cuenta candidatoInteres(Set<Candidato> candidatoes) {
        this.candidatoInteres = candidatoes;
        return this;
    }

    public Cuenta addCandidatoInteres(Candidato candidato) {
        this.candidatoInteres.add(candidato);
        candidato.getCuentaInteres().add(this);
        return this;
    }

    public Cuenta removeCandidatoInteres(Candidato candidato) {
        this.candidatoInteres.remove(candidato);
        candidato.getCuentaInteres().remove(this);
        return this;
    }

    public void setCandidatoInteres(Set<Candidato> candidatoes) {
        this.candidatoInteres = candidatoes;
    }

    public Set<Candidato> getCandidatoRechazadas() {
        return candidatoRechazadas;
    }

    public Cuenta candidatoRechazadas(Set<Candidato> candidatoes) {
        this.candidatoRechazadas = candidatoes;
        return this;
    }

    public Cuenta addCandidatoRechazadas(Candidato candidato) {
        this.candidatoRechazadas.add(candidato);
        candidato.getCuentaRechazadas().add(this);
        return this;
    }

    public Cuenta removeCandidatoRechazadas(Candidato candidato) {
        this.candidatoRechazadas.remove(candidato);
        candidato.getCuentaRechazadas().remove(this);
        return this;
    }

    public void setCandidatoRechazadas(Set<Candidato> candidatoes) {
        this.candidatoRechazadas = candidatoes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cuenta)) {
            return false;
        }
        return id != null && id.equals(((Cuenta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
