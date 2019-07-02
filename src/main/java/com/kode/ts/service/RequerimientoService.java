package com.kode.ts.service;

import com.kode.ts.service.dto.RequerimientoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.Requerimiento}.
 */
public interface RequerimientoService {

    /**
     * Save a requerimiento.
     *
     * @param requerimientoDTO the entity to save.
     * @return the persisted entity.
     */
    RequerimientoDTO save(RequerimientoDTO requerimientoDTO);

    /**
     * Get all the requerimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequerimientoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" requerimiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequerimientoDTO> findOne(Long id);

    /**
     * Delete the "id" requerimiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
