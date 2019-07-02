package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstatusReqCanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstatusReqCan} and its DTO {@link EstatusReqCanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusReqCanMapper extends EntityMapper<EstatusReqCanDTO, EstatusReqCan> {


    @Mapping(target = "estatusReqCanRecs", ignore = true)
    EstatusReqCan toEntity(EstatusReqCanDTO estatusReqCanDTO);

    default EstatusReqCan fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusReqCan estatusReqCan = new EstatusReqCan();
        estatusReqCan.setId(id);
        return estatusReqCan;
    }
}
