package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.ReferenciasLaborales} entity.
 */
public class ReferenciasLaboralesDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String empresa;

    @Size(max = 200)
    private String nombreContacto;

    @Size(max = 100)
    private String puestoContacto;

    @Size(max = 100)
    private String emailContaco;

    @Size(max = 20)
    private String telefonoContacto;


    private Long candidatoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getPuestoContacto() {
        return puestoContacto;
    }

    public void setPuestoContacto(String puestoContacto) {
        this.puestoContacto = puestoContacto;
    }

    public String getEmailContaco() {
        return emailContaco;
    }

    public void setEmailContaco(String emailContaco) {
        this.emailContaco = emailContaco;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Long getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(Long candidatoId) {
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

        ReferenciasLaboralesDTO referenciasLaboralesDTO = (ReferenciasLaboralesDTO) o;
        if (referenciasLaboralesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referenciasLaboralesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReferenciasLaboralesDTO{" +
            "id=" + getId() +
            ", empresa='" + getEmpresa() + "'" +
            ", nombreContacto='" + getNombreContacto() + "'" +
            ", puestoContacto='" + getPuestoContacto() + "'" +
            ", emailContaco='" + getEmailContaco() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", candidato=" + getCandidatoId() +
            "}";
    }
}
