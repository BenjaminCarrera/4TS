package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstCanInactivoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstCanInactivo} and its DTO {@link EstCanInactivoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EstatusCandidatoMapper.class})
public interface EstCanInactivoMapper extends EntityMapper<EstCanInactivoDTO, EstCanInactivo> {

    @Mapping(source = "estatusCandidato.id", target = "estatusCandidatoId")
    EstCanInactivoDTO toDto(EstCanInactivo estCanInactivo);

    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(target = "removeCandidato", ignore = true)
    @Mapping(source = "estatusCandidatoId", target = "estatusCandidato")
    EstCanInactivo toEntity(EstCanInactivoDTO estCanInactivoDTO);

    default EstCanInactivo fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstCanInactivo estCanInactivo = new EstCanInactivo();
        estCanInactivo.setId(id);
        return estCanInactivo;
    }
}
