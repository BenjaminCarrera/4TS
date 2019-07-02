package com.kode.ts.service;

import com.kode.ts.service.dto.DominioSkillDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.DominioSkill}.
 */
public interface DominioSkillService {

    /**
     * Save a dominioSkill.
     *
     * @param dominioSkillDTO the entity to save.
     * @return the persisted entity.
     */
    DominioSkillDTO save(DominioSkillDTO dominioSkillDTO);

    /**
     * Get all the dominioSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DominioSkillDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dominioSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DominioSkillDTO> findOne(Long id);

    /**
     * Delete the "id" dominioSkill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
