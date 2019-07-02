package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FormacionAcademica.
 */
@Entity
@Table(name = "formacion_academica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormacionAcademica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "formacion_academica")
    private String formacionAcademica;

    @OneToMany(mappedBy = "estudios")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormacionAcademica() {
        return formacionAcademica;
    }

    public FormacionAcademica formacionAcademica(String formacionAcademica) {
        this.formacionAcademica = formacionAcademica;
        return this;
    }

    public void setFormacionAcademica(String formacionAcademica) {
        this.formacionAcademica = formacionAcademica;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public FormacionAcademica candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public FormacionAcademica addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setEstudios(this);
        return this;
    }

    public FormacionAcademica removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setEstudios(null);
        return this;
    }

    public void setCandidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormacionAcademica)) {
            return false;
        }
        return id != null && id.equals(((FormacionAcademica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FormacionAcademica{" +
            "id=" + getId() +
            ", formacionAcademica='" + getFormacionAcademica() + "'" +
            "}";
    }
}
