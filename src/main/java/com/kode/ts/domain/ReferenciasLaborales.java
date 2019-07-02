package com.kode.ts.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ReferenciasLaborales.
 */
@Entity
@Table(name = "referencias_laborales")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReferenciasLaborales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "empresa", length = 200)
    private String empresa;

    @Size(max = 200)
    @Column(name = "nombre_contacto", length = 200)
    private String nombreContacto;

    @Size(max = 100)
    @Column(name = "puesto_contacto", length = 100)
    private String puestoContacto;

    @Size(max = 100)
    @Column(name = "email_contaco", length = 100)
    private String emailContaco;

    @Size(max = 20)
    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;

    @ManyToOne
    @JsonIgnoreProperties("referenciasLaborales")
    private Candidato candidato;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public ReferenciasLaborales empresa(String empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public ReferenciasLaborales nombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
        return this;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getPuestoContacto() {
        return puestoContacto;
    }

    public ReferenciasLaborales puestoContacto(String puestoContacto) {
        this.puestoContacto = puestoContacto;
        return this;
    }

    public void setPuestoContacto(String puestoContacto) {
        this.puestoContacto = puestoContacto;
    }

    public String getEmailContaco() {
        return emailContaco;
    }

    public ReferenciasLaborales emailContaco(String emailContaco) {
        this.emailContaco = emailContaco;
        return this;
    }

    public void setEmailContaco(String emailContaco) {
        this.emailContaco = emailContaco;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public ReferenciasLaborales telefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
        return this;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public ReferenciasLaborales candidato(Candidato candidato) {
        this.candidato = candidato;
        return this;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferenciasLaborales)) {
            return false;
        }
        return id != null && id.equals(((ReferenciasLaborales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReferenciasLaborales{" +
            "id=" + getId() +
            ", empresa='" + getEmpresa() + "'" +
            ", nombreContacto='" + getNombreContacto() + "'" +
            ", puestoContacto='" + getPuestoContacto() + "'" +
            ", emailContaco='" + getEmailContaco() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            "}";
    }
}
