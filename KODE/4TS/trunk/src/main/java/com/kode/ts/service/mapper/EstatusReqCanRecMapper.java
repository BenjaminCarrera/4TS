package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstatusReqCanRecDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstatusReqCanRec} and its DTO {@link EstatusReqCanRecDTO}.
 */
@Mapper(componentModel = "spring", uses = {EstatusReqCanMapper.class})
public interface EstatusReqCanRecMapper extends EntityMapper<EstatusReqCanRecDTO, EstatusReqCanRec> {

    @Mapping(source = "estatusReqCan.id", target = "estatusReqCanId")
    EstatusReqCanRecDTO toDto(EstatusReqCanRec estatusReqCanRec);

    @Mapping(source = "estatusReqCanId", target = "estatusReqCan")
    EstatusReqCanRec toEntity(EstatusReqCanRecDTO estatusReqCanRecDTO);

    default EstatusReqCanRec fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusReqCanRec estatusReqCanRec = new EstatusReqCanRec();
        estatusReqCanRec.setId(id);
        return estatusReqCanRec;
    }
}
