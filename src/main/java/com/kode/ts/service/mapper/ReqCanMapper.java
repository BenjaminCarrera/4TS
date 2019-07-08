package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.ReqCanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReqCan} and its DTO {@link ReqCanDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidatoMapper.class, RequerimientoMapper.class, EstatusReqCanMapper.class, EstatusReqCanRecMapper.class})
public interface ReqCanMapper extends EntityMapper<ReqCanDTO, ReqCan> {

    @Mapping(source = "candidato.id", target = "candidatoId")
    @Mapping(source = "candidato.nombre", target = "candidatoNombre")
    @Mapping(source = "requerimiento.id", target = "requerimientoId")
    @Mapping(source = "requerimiento.proyecto", target = "requerimientoProyecto")
    @Mapping(source = "estatusReqCan.id", target = "estatusReqCanId")
    @Mapping(source = "estatusReqCan.estatus", target = "estatusReqCanEstatus")
    @Mapping(source = "estatusReqCanRec.id", target = "estatusReqCanRecId")
    @Mapping(source = "estatusReqCanRec.motivo", target = "estatusReqCanRecMotivo")
    ReqCanDTO toDto(ReqCan reqCan);

    @Mapping(source = "candidatoId", target = "candidato")
    @Mapping(source = "requerimientoId", target = "requerimiento")
    @Mapping(source = "estatusReqCanId", target = "estatusReqCan")
    @Mapping(source = "estatusReqCanRecId", target = "estatusReqCanRec")
    ReqCan toEntity(ReqCanDTO reqCanDTO);

    default ReqCan fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReqCan reqCan = new ReqCan();
        reqCan.setId(id);
        return reqCan;
    }
}
