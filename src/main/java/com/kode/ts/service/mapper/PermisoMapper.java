package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.PermisoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Permiso} and its DTO {@link PermisoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PermisoMapper extends EntityMapper<PermisoDTO, Permiso> {



    default Permiso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Permiso permiso = new Permiso();
        permiso.setId(id);
        return permiso;
    }
}
