package com.kode.ts.service;

import com.kode.ts.service.dto.CategoriaSkillDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.CategoriaSkill}.
 */
public interface CategoriaSkillService {

    /**
     * Save a categoriaSkill.
     *
     * @param categoriaSkillDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriaSkillDTO save(CategoriaSkillDTO categoriaSkillDTO);

    /**
     * Get all the categoriaSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaSkillDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoriaSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaSkillDTO> findOne(Long id);

    /**
     * Delete the "id" categoriaSkill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
