package com.kode.ts.service;

import com.kode.ts.service.dto.ColoniaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.Colonia}.
 */
public interface ColoniaService {

    /**
     * Save a colonia.
     *
     * @param coloniaDTO the entity to save.
     * @return the persisted entity.
     */
    ColoniaDTO save(ColoniaDTO coloniaDTO);

    /**
     * Get all the colonias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ColoniaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" colonia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ColoniaDTO> findOne(Long id);

    /**
     * Delete the "id" colonia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
