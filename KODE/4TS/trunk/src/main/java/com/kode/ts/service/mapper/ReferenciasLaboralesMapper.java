package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.ReferenciasLaboralesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferenciasLaborales} and its DTO {@link ReferenciasLaboralesDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidatoMapper.class})
public interface ReferenciasLaboralesMapper extends EntityMapper<ReferenciasLaboralesDTO, ReferenciasLaborales> {

    @Mapping(source = "candidato.id", target = "candidatoId")
    @Mapping(source = "candidato.nombre", target = "candidatoNombre")
    ReferenciasLaboralesDTO toDto(ReferenciasLaborales referenciasLaborales);

    @Mapping(source = "candidatoId", target = "candidato")
    ReferenciasLaborales toEntity(ReferenciasLaboralesDTO referenciasLaboralesDTO);

    default ReferenciasLaborales fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReferenciasLaborales referenciasLaborales = new ReferenciasLaborales();
        referenciasLaborales.setId(id);
        return referenciasLaborales;
    }
}
