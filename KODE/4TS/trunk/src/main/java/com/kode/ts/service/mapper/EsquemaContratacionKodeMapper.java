package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EsquemaContratacionKodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EsquemaContratacionKode} and its DTO {@link EsquemaContratacionKodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EsquemaContratacionKodeMapper extends EntityMapper<EsquemaContratacionKodeDTO, EsquemaContratacionKode> {


    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(target = "removeCandidato", ignore = true)
    EsquemaContratacionKode toEntity(EsquemaContratacionKodeDTO esquemaContratacionKodeDTO);

    default EsquemaContratacionKode fromId(Long id) {
        if (id == null) {
            return null;
        }
        EsquemaContratacionKode esquemaContratacionKode = new EsquemaContratacionKode();
        esquemaContratacionKode.setId(id);
        return esquemaContratacionKode;
    }
}
