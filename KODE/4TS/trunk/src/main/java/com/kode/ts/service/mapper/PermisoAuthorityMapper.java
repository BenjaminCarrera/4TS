package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.PermisoAuthorityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PermisoAuthority} and its DTO {@link PermisoAuthorityDTO}.
 */
@Mapper(componentModel = "spring", uses = {PermisoMapper.class})
public interface PermisoAuthorityMapper extends EntityMapper<PermisoAuthorityDTO, PermisoAuthority> {

    @Mapping(source = "permiso.id", target = "permisoId")
    @Mapping(source = "permiso.nombre", target = "permisoNombre")
    PermisoAuthorityDTO toDto(PermisoAuthority permisoAuthority);

    @Mapping(source = "permisoId", target = "permiso")
    PermisoAuthority toEntity(PermisoAuthorityDTO permisoAuthorityDTO);

    default PermisoAuthority fromId(Long id) {
        if (id == null) {
            return null;
        }
        PermisoAuthority permisoAuthority = new PermisoAuthority();
        permisoAuthority.setId(id);
        return permisoAuthority;
    }
}
