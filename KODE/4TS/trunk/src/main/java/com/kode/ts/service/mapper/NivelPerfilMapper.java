package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.NivelPerfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NivelPerfil} and its DTO {@link NivelPerfilDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NivelPerfilMapper extends EntityMapper<NivelPerfilDTO, NivelPerfil> {


    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(target = "removeCandidato", ignore = true)
    @Mapping(target = "requerimientos", ignore = true)
    @Mapping(target = "removeRequerimiento", ignore = true)
    NivelPerfil toEntity(NivelPerfilDTO nivelPerfilDTO);

    default NivelPerfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        NivelPerfil nivelPerfil = new NivelPerfil();
        nivelPerfil.setId(id);
        return nivelPerfil;
    }
}
