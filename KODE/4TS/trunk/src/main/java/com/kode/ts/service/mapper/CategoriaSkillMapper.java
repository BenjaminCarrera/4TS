package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.CategoriaSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaSkill} and its DTO {@link CategoriaSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaSkillMapper extends EntityMapper<CategoriaSkillDTO, CategoriaSkill> {


    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "removeSkill", ignore = true)
    CategoriaSkill toEntity(CategoriaSkillDTO categoriaSkillDTO);

    default CategoriaSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoriaSkill categoriaSkill = new CategoriaSkill();
        categoriaSkill.setId(id);
        return categoriaSkill;
    }
}
