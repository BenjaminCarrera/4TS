package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstatusTareaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstatusTarea} and its DTO {@link EstatusTareaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusTareaMapper extends EntityMapper<EstatusTareaDTO, EstatusTarea> {


    @Mapping(target = "tareas", ignore = true)
    EstatusTarea toEntity(EstatusTareaDTO estatusTareaDTO);

    default EstatusTarea fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusTarea estatusTarea = new EstatusTarea();
        estatusTarea.setId(id);
        return estatusTarea;
    }
}
