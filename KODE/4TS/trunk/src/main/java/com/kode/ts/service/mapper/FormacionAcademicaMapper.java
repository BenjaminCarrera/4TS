package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.FormacionAcademicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormacionAcademica} and its DTO {@link FormacionAcademicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormacionAcademicaMapper extends EntityMapper<FormacionAcademicaDTO, FormacionAcademica> {


    @Mapping(target = "candidatoes", ignore = true)
    FormacionAcademica toEntity(FormacionAcademicaDTO formacionAcademicaDTO);

    default FormacionAcademica fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormacionAcademica formacionAcademica = new FormacionAcademica();
        formacionAcademica.setId(id);
        return formacionAcademica;
    }
}
