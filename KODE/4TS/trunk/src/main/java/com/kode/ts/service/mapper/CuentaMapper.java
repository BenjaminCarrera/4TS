package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.CuentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cuenta} and its DTO {@link CuentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CuentaMapper extends EntityMapper<CuentaDTO, Cuenta> {


    @Mapping(target = "candidatoInteres", ignore = true)
    @Mapping(target = "candidatoRechazadas", ignore = true)
    Cuenta toEntity(CuentaDTO cuentaDTO);

    default Cuenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cuenta cuenta = new Cuenta();
        cuenta.setId(id);
        return cuenta;
    }
}
