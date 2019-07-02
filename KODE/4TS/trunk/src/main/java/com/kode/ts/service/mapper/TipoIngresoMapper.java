package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.TipoIngresoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoIngreso} and its DTO {@link TipoIngresoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoIngresoMapper extends EntityMapper<TipoIngresoDTO, TipoIngreso> {


    @Mapping(target = "requerimientos", ignore = true)
    TipoIngreso toEntity(TipoIngresoDTO tipoIngresoDTO);

    default TipoIngreso fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoIngreso tipoIngreso = new TipoIngreso();
        tipoIngreso.setId(id);
        return tipoIngreso;
    }
}
