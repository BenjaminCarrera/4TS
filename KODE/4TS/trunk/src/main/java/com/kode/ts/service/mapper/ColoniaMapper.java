package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.ColoniaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Colonia} and its DTO {@link ColoniaDTO}.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class, CodigoPostalMapper.class})
public interface ColoniaMapper extends EntityMapper<ColoniaDTO, Colonia> {

    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "codigoPostal.id", target = "codigoPostalId")
    ColoniaDTO toDto(Colonia colonia);

    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(source = "codigoPostalId", target = "codigoPostal")
    Colonia toEntity(ColoniaDTO coloniaDTO);

    default Colonia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Colonia colonia = new Colonia();
        colonia.setId(id);
        return colonia;
    }
}
