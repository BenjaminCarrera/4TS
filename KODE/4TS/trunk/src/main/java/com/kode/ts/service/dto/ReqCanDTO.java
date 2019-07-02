package com.kode.ts.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.ReqCan} entity.
 */
public class ReqCanDTO implements Serializable {

    private Long id;


    private Long candidatoId;

    private Long requerimientoId;

    private Long estatusReqCanId;

    private Long estatusReqCanRecId;

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

    public Long getRequerimientoId() {
        return requerimientoId;
    }

    public void setRequerimientoId(Long requerimientoId) {
        this.requerimientoId = requerimientoId;
    }

    public Long getEstatusReqCanId() {
        return estatusReqCanId;
    }

    public void setEstatusReqCanId(Long estatusReqCanId) {
        this.estatusReqCanId = estatusReqCanId;
    }

    public Long getEstatusReqCanRecId() {
        return estatusReqCanRecId;
    }

    public void setEstatusReqCanRecId(Long estatusReqCanRecId) {
        this.estatusReqCanRecId = estatusReqCanRecId;
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
            ", requerimiento=" + getRequerimientoId() +
            ", estatusReqCan=" + getEstatusReqCanId() +
            ", estatusReqCanRec=" + getEstatusReqCanRecId() +
            "}";
    }
}
