package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstatusAcademicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstatusAcademico} and its DTO {@link EstatusAcademicoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusAcademicoMapper extends EntityMapper<EstatusAcademicoDTO, EstatusAcademico> {


    @Mapping(target = "candidatoes", ignore = true)
    EstatusAcademico toEntity(EstatusAcademicoDTO estatusAcademicoDTO);

    default EstatusAcademico fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusAcademico estatusAcademico = new EstatusAcademico();
        estatusAcademico.setId(id);
        return estatusAcademico;
    }
}
