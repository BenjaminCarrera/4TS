package com.kode.ts.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kode.ts.domain.Perfil} entity.
 */
public class PerfilDTO implements Serializable {

    private Long id;

    private String perfil;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PerfilDTO perfilDTO = (PerfilDTO) o;
        if (perfilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerfilDTO{" +
            "id=" + getId() +
            ", perfil='" + getPerfil() + "'" +
            "}";
    }
}
