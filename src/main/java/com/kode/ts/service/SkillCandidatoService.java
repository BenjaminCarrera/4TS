package com.kode.ts.service;

import com.kode.ts.service.dto.SkillCandidatoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.SkillCandidato}.
 */
public interface SkillCandidatoService {

    /**
     * Save a skillCandidato.
     *
     * @param skillCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
    SkillCandidatoDTO save(SkillCandidatoDTO skillCandidatoDTO);

    /**
     * Get all the skillCandidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SkillCandidatoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" skillCandidato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkillCandidatoDTO> findOne(Long id);

    /**
     * Delete the "id" skillCandidato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
