package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.SkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoriaSkillMapper.class})
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {

    @Mapping(source = "categoriaSkill.id", target = "categoriaSkillId")
    SkillDTO toDto(Skill skill);

    @Mapping(target = "skillCandidatoes", ignore = true)
    @Mapping(target = "removeSkillCandidato", ignore = true)
    @Mapping(target = "skillRequerimientos", ignore = true)
    @Mapping(target = "removeSkillRequerimiento", ignore = true)
    @Mapping(source = "categoriaSkillId", target = "categoriaSkill")
    Skill toEntity(SkillDTO skillDTO);

    default Skill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setId(id);
        return skill;
    }
}
