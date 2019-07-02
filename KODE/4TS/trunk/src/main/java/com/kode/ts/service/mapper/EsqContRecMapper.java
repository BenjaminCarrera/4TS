package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.EsqContRecDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EsqContRec} and its DTO {@link EsqContRecDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EsqContRecMapper extends EntityMapper<EsqContRecDTO, EsqContRec> {


    @Mapping(target = "candidatoes", ignore = true)
    @Mapping(target = "requerimientos", ignore = true)
    EsqContRec toEntity(EsqContRecDTO esqContRecDTO);

    default EsqContRec fromId(Long id) {
        if (id == null) {
            return null;
        }
        EsqContRec esqContRec = new EsqContRec();
        esqContRec.setId(id);
        return esqContRec;
    }
}
