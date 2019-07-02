package com.kode.ts.service;

import com.kode.ts.service.dto.CandidatoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.Candidato}.
 */
public interface CandidatoService {

    /**
     * Save a candidato.
     *
     * @param candidatoDTO the entity to save.
     * @return the persisted entity.
     */
    CandidatoDTO save(CandidatoDTO candidatoDTO);

    /**
     * Get all the candidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidatoDTO> findAll(Pageable pageable);

    /**
     * Get all the candidatoes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CandidatoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" candidato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidatoDTO> findOne(Long id);

    /**
     * Delete the "id" candidato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
