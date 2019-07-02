package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.PrioridadReqDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrioridadReq} and its DTO {@link PrioridadReqDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrioridadReqMapper extends EntityMapper<PrioridadReqDTO, PrioridadReq> {


    @Mapping(target = "requerimientos", ignore = true)
    PrioridadReq toEntity(PrioridadReqDTO prioridadReqDTO);

    default PrioridadReq fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrioridadReq prioridadReq = new PrioridadReq();
        prioridadReq.setId(id);
        return prioridadReq;
    }
}
