package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.BaseTarifaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BaseTarifa} and its DTO {@link BaseTarifaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BaseTarifaMapper extends EntityMapper<BaseTarifaDTO, BaseTarifa> {


    @Mapping(target = "requerimientos", ignore = true)
    @Mapping(target = "removeRequerimiento", ignore = true)
    BaseTarifa toEntity(BaseTarifaDTO baseTarifaDTO);

    default BaseTarifa fromId(Long id) {
        if (id == null) {
            return null;
        }
        BaseTarifa baseTarifa = new BaseTarifa();
        baseTarifa.setId(id);
        return baseTarifa;
    }
}
