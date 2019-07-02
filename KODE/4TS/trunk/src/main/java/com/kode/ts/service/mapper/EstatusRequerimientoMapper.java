package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstatusRequerimientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstatusRequerimiento} and its DTO {@link EstatusRequerimientoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusRequerimientoMapper extends EntityMapper<EstatusRequerimientoDTO, EstatusRequerimiento> {


    @Mapping(target = "requerimientos", ignore = true)
    @Mapping(target = "estReqCerrados", ignore = true)
    EstatusRequerimiento toEntity(EstatusRequerimientoDTO estatusRequerimientoDTO);

    default EstatusRequerimiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusRequerimiento estatusRequerimiento = new EstatusRequerimiento();
        estatusRequerimiento.setId(id);
        return estatusRequerimiento;
    }
}
