package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.FuenteReclutamientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FuenteReclutamiento} and its DTO {@link FuenteReclutamientoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FuenteReclutamientoMapper extends EntityMapper<FuenteReclutamientoDTO, FuenteReclutamiento> {


    @Mapping(target = "candidatoes", ignore = true)
    FuenteReclutamiento toEntity(FuenteReclutamientoDTO fuenteReclutamientoDTO);

    default FuenteReclutamiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        FuenteReclutamiento fuenteReclutamiento = new FuenteReclutamiento();
        fuenteReclutamiento.setId(id);
        return fuenteReclutamiento;
    }
}
