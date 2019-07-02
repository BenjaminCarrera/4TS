package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.CodigoPostalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CodigoPostal} and its DTO {@link CodigoPostalDTO}.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class})
public interface CodigoPostalMapper extends EntityMapper<CodigoPostalDTO, CodigoPostal> {


    @Mapping(target = "colonias", ignore = true)
    CodigoPostal toEntity(CodigoPostalDTO codigoPostalDTO);

    default CodigoPostal fromId(Long id) {
        if (id == null) {
            return null;
        }
        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setId(id);
        return codigoPostal;
    }
}
