package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.InstitucionAcademicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InstitucionAcademica} and its DTO {@link InstitucionAcademicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstitucionAcademicaMapper extends EntityMapper<InstitucionAcademicaDTO, InstitucionAcademica> {


    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(target = "removeCandidato", ignore = true)
    InstitucionAcademica toEntity(InstitucionAcademicaDTO institucionAcademicaDTO);

    default InstitucionAcademica fromId(Long id) {
        if (id == null) {
            return null;
        }
        InstitucionAcademica institucionAcademica = new InstitucionAcademica();
        institucionAcademica.setId(id);
        return institucionAcademica;
    }
}
