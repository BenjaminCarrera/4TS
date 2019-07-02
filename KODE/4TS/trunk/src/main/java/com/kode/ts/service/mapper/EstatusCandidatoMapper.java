package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstatusCandidatoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstatusCandidato} and its DTO {@link EstatusCandidatoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusCandidatoMapper extends EntityMapper<EstatusCandidatoDTO, EstatusCandidato> {


    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(target = "estCanInactivos", ignore = true)
    EstatusCandidato toEntity(EstatusCandidatoDTO estatusCandidatoDTO);

    default EstatusCandidato fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusCandidato estatusCandidato = new EstatusCandidato();
        estatusCandidato.setId(id);
        return estatusCandidato;
    }
}
