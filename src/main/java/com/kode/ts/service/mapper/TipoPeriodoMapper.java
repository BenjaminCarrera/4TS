package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.TipoPeriodoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoPeriodo} and its DTO {@link TipoPeriodoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoPeriodoMapper extends EntityMapper<TipoPeriodoDTO, TipoPeriodo> {


    @Mapping(target = "requerimientos", ignore = true)
    TipoPeriodo toEntity(TipoPeriodoDTO tipoPeriodoDTO);

    default TipoPeriodo fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoPeriodo tipoPeriodo = new TipoPeriodo();
        tipoPeriodo.setId(id);
        return tipoPeriodo;
    }
}
