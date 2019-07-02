package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.TipoTareaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoTarea} and its DTO {@link TipoTareaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoTareaMapper extends EntityMapper<TipoTareaDTO, TipoTarea> {


    @Mapping(target = "tareas", ignore = true)
    TipoTarea toEntity(TipoTareaDTO tipoTareaDTO);

    default TipoTarea fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoTarea tipoTarea = new TipoTarea();
        tipoTarea.setId(id);
        return tipoTarea;
    }
}
