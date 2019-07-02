package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.BitacoraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bitacora} and its DTO {@link BitacoraDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RequerimientoMapper.class, CandidatoMapper.class, TareaMapper.class})
public interface BitacoraMapper extends EntityMapper<BitacoraDTO, Bitacora> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "requerimiento.id", target = "requerimientoId")
    @Mapping(source = "candidato.id", target = "candidatoId")
    @Mapping(source = "tarea.id", target = "tareaId")
    BitacoraDTO toDto(Bitacora bitacora);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "requerimientoId", target = "requerimiento")
    @Mapping(source = "candidatoId", target = "candidato")
    @Mapping(source = "tareaId", target = "tarea")
    Bitacora toEntity(BitacoraDTO bitacoraDTO);

    default Bitacora fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bitacora bitacora = new Bitacora();
        bitacora.setId(id);
        return bitacora;
    }
}
