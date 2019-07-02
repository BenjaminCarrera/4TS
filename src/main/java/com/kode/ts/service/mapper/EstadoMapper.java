package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EstadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estado} and its DTO {@link EstadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstadoMapper extends EntityMapper<EstadoDTO, Estado> {


    @Mapping(target = "municipios", ignore = true)
    Estado toEntity(EstadoDTO estadoDTO);

    default Estado fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estado estado = new Estado();
        estado.setId(id);
        return estado;
    }
}
