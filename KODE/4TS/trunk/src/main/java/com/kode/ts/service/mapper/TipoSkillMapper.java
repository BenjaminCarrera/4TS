package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.TipoSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoSkill} and its DTO {@link TipoSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoSkillMapper extends EntityMapper<TipoSkillDTO, TipoSkill> {


    @Mapping(target = "skillRequerimientos", ignore = true)
    TipoSkill toEntity(TipoSkillDTO tipoSkillDTO);

    default TipoSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoSkill tipoSkill = new TipoSkill();
        tipoSkill.setId(id);
        return tipoSkill;
    }
}
