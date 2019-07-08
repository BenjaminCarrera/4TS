package com.kode.ts.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.ReqCan} entity.
 */
public class ReqCanDTO implements Serializable {

    private Long id;


    private Long candidatoId;

    private String candidatoNombre;

    private Long requerimientoId;

    private String requerimientoProyecto;

    private Long estatusReqCanId;

    private String estatusReqCanEstatus;

    private Long estatusReqCanRecId;

    private String estatusReqCanRecMotivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(Long candidatoId) {
        this.candidatoId = candidatoId;
    }

    public String getCandidatoNombre() {
        return candidatoNombre;
    }

    public void setCandidatoNombre(String candidatoNombre) {
        this.candidatoNombre = candidatoNombre;
    }

    public Long getRequerimientoId() {
        return requerimientoId;
    }

    public void setRequerimientoId(Long requerimientoId) {
        this.requerimientoId = requerimientoId;
    }

    public String getRequerimientoProyecto() {
        return requerimientoProyecto;
    }

    public void setRequerimientoProyecto(String requerimientoProyecto) {
        this.requerimientoProyecto = requerimientoProyecto;
    }

    public Long getEstatusReqCanId() {
        return estatusReqCanId;
    }

    public void setEstatusReqCanId(Long estatusReqCanId) {
        this.estatusReqCanId = estatusReqCanId;
    }

    public String getEstatusReqCanEstatus() {
        return estatusReqCanEstatus;
    }

    public void setEstatusReqCanEstatus(String estatusReqCanEstatus) {
        this.estatusReqCanEstatus = estatusReqCanEstatus;
    }

    public Long getEstatusReqCanRecId() {
        return estatusReqCanRecId;
    }

    public void setEstatusReqCanRecId(Long estatusReqCanRecId) {
        this.estatusReqCanRecId = estatusReqCanRecId;
    }

    public String getEstatusReqCanRecMotivo() {
        return estatusReqCanRecMotivo;
    }

    public void setEstatusReqCanRecMotivo(String estatusReqCanRecMotivo) {
        this.estatusReqCanRecMotivo = estatusReqCanRecMotivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReqCanDTO reqCanDTO = (ReqCanDTO) o;
        if (reqCanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reqCanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReqCanDTO{" +
            "id=" + getId() +
            ", candidato=" + getCandidatoId() +
            ", candidato='" + getCandidatoNombre() + "'" +
            ", requerimiento=" + getRequerimientoId() +
            ", requerimiento='" + getRequerimientoProyecto() + "'" +
            ", estatusReqCan=" + getEstatusReqCanId() +
            ", estatusReqCan='" + getEstatusReqCanEstatus() + "'" +
            ", estatusReqCanRec=" + getEstatusReqCanRecId() +
            ", estatusReqCanRec='" + getEstatusReqCanRecMotivo() + "'" +
            "}";
    }
}
