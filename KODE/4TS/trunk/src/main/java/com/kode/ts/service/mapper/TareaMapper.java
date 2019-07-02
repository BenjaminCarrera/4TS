package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.TareaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarea} and its DTO {@link TareaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RequerimientoMapper.class, CandidatoMapper.class, EstatusTareaMapper.class, TipoTareaMapper.class})
public interface TareaMapper extends EntityMapper<TareaDTO, Tarea> {

    @Mapping(source = "usuarioCreador.id", target = "usuarioCreadorId")
    @Mapping(source = "usuarioEjecutor.id", target = "usuarioEjecutorId")
    @Mapping(source = "requerimiento.id", target = "requerimientoId")
    @Mapping(source = "candidato.id", target = "candidatoId")
    @Mapping(source = "estatusTarea.id", target = "estatusTareaId")
    @Mapping(source = "tipoTarea.id", target = "tipoTareaId")
    TareaDTO toDto(Tarea tarea);

    @Mapping(target = "bitacoras", ignore = true)
    @Mapping(target = "removeBitacora", ignore = true)
    @Mapping(source = "usuarioCreadorId", target = "usuarioCreador")
    @Mapping(source = "usuarioEjecutorId", target = "usuarioEjecutor")
    @Mapping(source = "requerimientoId", target = "requerimiento")
    @Mapping(source = "candidatoId", target = "candidato")
    @Mapping(source = "estatusTareaId", target = "estatusTarea")
    @Mapping(source = "tipoTareaId", target = "tipoTarea")
    Tarea toEntity(TareaDTO tareaDTO);

    default Tarea fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tarea tarea = new Tarea();
        tarea.setId(id);
        return tarea;
    }
}
