package com.kode.ts.service;

import com.kode.ts.service.dto.EstatusCandidatoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EstatusCandidato}.
 */
public interface EstatusCandidatoService {

    /**
     * Save a estatusCandidato.
     *
     * @param estatusCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusCandidatoDTO save(EstatusCandidatoDTO estatusCandidatoDTO);

    /**
     * Get all the estatusCandidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstatusCandidatoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estatusCandidato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusCandidatoDTO> findOne(Long id);

    /**
     * Delete the "id" estatusCandidato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
