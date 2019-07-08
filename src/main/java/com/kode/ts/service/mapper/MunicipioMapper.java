package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.MunicipioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Municipio} and its DTO {@link MunicipioDTO}.
 */
@Mapper(componentModel = "spring", uses = {EstadoMapper.class})
public interface MunicipioMapper extends EntityMapper<MunicipioDTO, Municipio> {

    @Mapping(source = "estado.id", target = "estadoId")
    @Mapping(source = "estado.estado", target = "estadoEstado")
    MunicipioDTO toDto(Municipio municipio);

    @Mapping(target = "colonias", ignore = true)
    @Mapping(target = "removeColonia", ignore = true)
    @Mapping(target = "codigoPostals", ignore = true)
    @Mapping(target = "removeCodigoPostal", ignore = true)
    @Mapping(source = "estadoId", target = "estado")
    Municipio toEntity(MunicipioDTO municipioDTO);

    default Municipio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Municipio municipio = new Municipio();
        municipio.setId(id);
        return municipio;
    }
}
