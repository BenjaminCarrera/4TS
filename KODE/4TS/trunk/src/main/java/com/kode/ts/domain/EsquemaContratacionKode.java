package com.kode.ts.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EsquemaContratacionKode.
 */
@Entity
@Table(name = "esquema_contratacion_kode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EsquemaContratacionKode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "esquema", length = 100)
    private String esquema;

    @OneToMany(mappedBy = "esquemaContratacionKode")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidato> candidatoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEsquema() {
        return esquema;
    }

    public EsquemaContratacionKode esquema(String esquema) {
        this.esquema = esquema;
        return this;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    public Set<Candidato> getCandidatoes() {
        return candidatoes;
    }

    public EsquemaContratacionKode candidatoes(Set<Candidato> candidatoes) {
        this.candidatoes = candidatoes;
        return this;
    }

    public EsquemaContratacionKode addCandidato(Candidato candidato) {
        this.candidatoes.add(candidato);
        candidato.setEsquemaContratacionKode(this);
        return this;
    }

    public EsquemaContratacionKode removeCandidato(Candidato candidato) {
        this.candidatoes.remove(candidato);
        candidato.setEsquemaContratacionKode(null);
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
        if (!(o instanceof EsquemaContratacionKode)) {
            return false;
        }
        return id != null && id.equals(((EsquemaContratacionKode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EsquemaContratacionKode{" +
            "id=" + getId() +
            ", esquema='" + getEsquema() + "'" +
            "}";
    }
}
