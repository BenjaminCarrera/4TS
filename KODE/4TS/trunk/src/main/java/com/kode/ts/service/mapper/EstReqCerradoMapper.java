package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstReqCerradoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstReqCerrado} and its DTO {@link EstReqCerradoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EstatusRequerimientoMapper.class})
public interface EstReqCerradoMapper extends EntityMapper<EstReqCerradoDTO, EstReqCerrado> {

    @Mapping(source = "estatusRequerimiento.id", target = "estatusRequerimientoId")
    @Mapping(source = "estatusRequerimiento.estatus", target = "estatusRequerimientoEstatus")
    EstReqCerradoDTO toDto(EstReqCerrado estReqCerrado);

    @Mapping(target = "requerimientos", ignore = true)
    @Mapping(target = "removeRequerimiento", ignore = true)
    @Mapping(source = "estatusRequerimientoId", target = "estatusRequerimiento")
    EstReqCerrado toEntity(EstReqCerradoDTO estReqCerradoDTO);

    default EstReqCerrado fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstReqCerrado estReqCerrado = new EstReqCerrado();
        estReqCerrado.setId(id);
        return estReqCerrado;
    }
}
