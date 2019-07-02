package com.kode.ts.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Documento} entity.
 */
public class DocumentoDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentoDTO documentoDTO = (DocumentoDTO) o;
        if (documentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentoDTO{" +
            "id=" + getId() +
            "}";
    }
}
