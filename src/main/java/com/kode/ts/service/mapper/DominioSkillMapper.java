package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.DominioSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DominioSkill} and its DTO {@link DominioSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DominioSkillMapper extends EntityMapper<DominioSkillDTO, DominioSkill> {


    @Mapping(target = "skillCandidatoes", ignore = true)
    DominioSkill toEntity(DominioSkillDTO dominioSkillDTO);

    default DominioSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        DominioSkill dominioSkill = new DominioSkill();
        dominioSkill.setId(id);
        return dominioSkill;
    }
}
