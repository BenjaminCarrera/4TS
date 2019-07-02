package com.kode.ts.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.NivelPerfil} entity.
 */
public class NivelPerfilDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String nivel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NivelPerfilDTO nivelPerfilDTO = (NivelPerfilDTO) o;
        if (nivelPerfilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nivelPerfilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NivelPerfilDTO{" +
            "id=" + getId() +
            ", nivel='" + getNivel() + "'" +
            "}";
    }
}
