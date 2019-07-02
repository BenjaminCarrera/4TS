package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.EstatusReqCanRec} entity.
 */
public class EstatusReqCanRecDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String motivo;


    private Long estatusReqCanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getEstatusReqCanId() {
        return estatusReqCanId;
    }

    public void setEstatusReqCanId(Long estatusReqCanId) {
        this.estatusReqCanId = estatusReqCanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstatusReqCanRecDTO estatusReqCanRecDTO = (EstatusReqCanRecDTO) o;
        if (estatusReqCanRecDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estatusReqCanRecDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstatusReqCanRecDTO{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            ", estatusReqCan=" + getEstatusReqCanId() +
            "}";
    }
}
