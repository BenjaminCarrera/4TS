package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.TipoSolicitudDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoSolicitud} and its DTO {@link TipoSolicitudDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoSolicitudMapper extends EntityMapper<TipoSolicitudDTO, TipoSolicitud> {


    @Mapping(target = "requerimientos", ignore = true)
    TipoSolicitud toEntity(TipoSolicitudDTO tipoSolicitudDTO);

    default TipoSolicitud fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setId(id);
        return tipoSolicitud;
    }
}
