package com.kode.ts.service;

import com.kode.ts.service.dto.TipoSkillDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.TipoSkill}.
 */
public interface TipoSkillService {

    /**
     * Save a tipoSkill.
     *
     * @param tipoSkillDTO the entity to save.
     * @return the persisted entity.
     */
    TipoSkillDTO save(TipoSkillDTO tipoSkillDTO);

    /**
     * Get all the tipoSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoSkillDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoSkillDTO> findOne(Long id);

    /**
     * Delete the "id" tipoSkill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
