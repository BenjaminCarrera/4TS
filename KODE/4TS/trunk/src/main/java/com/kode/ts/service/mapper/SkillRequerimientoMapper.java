package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.SkillRequerimientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SkillRequerimiento} and its DTO {@link SkillRequerimientoDTO}.
 */
@Mapper(componentModel = "spring", uses = {RequerimientoMapper.class, SkillMapper.class, TipoSkillMapper.class})
public interface SkillRequerimientoMapper extends EntityMapper<SkillRequerimientoDTO, SkillRequerimiento> {

    @Mapping(source = "idRequerimiento.id", target = "idRequerimientoId")
    @Mapping(source = "idRequerimiento.proyecto", target = "idRequerimientoProyecto")
    @Mapping(source = "idSkill.id", target = "idSkillId")
    @Mapping(source = "idSkill.nombre", target = "idSkillNombre")
    @Mapping(source = "tipoSkill.id", target = "tipoSkillId")
    @Mapping(source = "tipoSkill.tipo", target = "tipoSkillTipo")
    SkillRequerimientoDTO toDto(SkillRequerimiento skillRequerimiento);

    @Mapping(source = "idRequerimientoId", target = "idRequerimiento")
    @Mapping(source = "idSkillId", target = "idSkill")
    @Mapping(source = "tipoSkillId", target = "tipoSkill")
    SkillRequerimiento toEntity(SkillRequerimientoDTO skillRequerimientoDTO);

    default SkillRequerimiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        SkillRequerimiento skillRequerimiento = new SkillRequerimiento();
        skillRequerimiento.setId(id);
        return skillRequerimiento;
    }
}
