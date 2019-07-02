package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.PerfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Perfil} and its DTO {@link PerfilDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PerfilMapper extends EntityMapper<PerfilDTO, Perfil> {


    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(target = "removeCandidato", ignore = true)
    @Mapping(target = "requerimientos", ignore = true)
    @Mapping(target = "removeRequerimiento", ignore = true)
    Perfil toEntity(PerfilDTO perfilDTO);

    default Perfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        Perfil perfil = new Perfil();
        perfil.setId(id);
        return perfil;
    }
}
