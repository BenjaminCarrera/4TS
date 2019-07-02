package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstatusLaboralDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstatusLaboral} and its DTO {@link EstatusLaboralDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusLaboralMapper extends EntityMapper<EstatusLaboralDTO, EstatusLaboral> {


    @Mapping(target = "candidatoes", ignore = true)
    EstatusLaboral toEntity(EstatusLaboralDTO estatusLaboralDTO);

    default EstatusLaboral fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusLaboral estatusLaboral = new EstatusLaboral();
        estatusLaboral.setId(id);
        return estatusLaboral;
    }
}
