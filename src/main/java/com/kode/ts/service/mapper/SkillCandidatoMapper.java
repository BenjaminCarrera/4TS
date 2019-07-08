package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.SkillCandidatoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SkillCandidato} and its DTO {@link SkillCandidatoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidatoMapper.class, SkillMapper.class, DominioSkillMapper.class})
public interface SkillCandidatoMapper extends EntityMapper<SkillCandidatoDTO, SkillCandidato> {

    @Mapping(source = "idCandidato.id", target = "idCandidatoId")
    @Mapping(source = "idCandidato.nombre", target = "idCandidatoNombre")
    @Mapping(source = "idSkill.id", target = "idSkillId")
    @Mapping(source = "idSkill.nombre", target = "idSkillNombre")
    @Mapping(source = "nivelSkill.id", target = "nivelSkillId")
    @Mapping(source = "nivelSkill.dominio", target = "nivelSkillDominio")
    SkillCandidatoDTO toDto(SkillCandidato skillCandidato);

    @Mapping(source = "idCandidatoId", target = "idCandidato")
    @Mapping(source = "idSkillId", target = "idSkill")
    @Mapping(source = "nivelSkillId", target = "nivelSkill")
    SkillCandidato toEntity(SkillCandidatoDTO skillCandidatoDTO);

    default SkillCandidato fromId(Long id) {
        if (id == null) {
            return null;
        }
        SkillCandidato skillCandidato = new SkillCandidato();
        skillCandidato.setId(id);
        return skillCandidato;
    }
}
