package com.kode.ts.service;

import com.kode.ts.service.dto.BitacoraDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.Bitacora}.
 */
public interface BitacoraService {

    /**
     * Save a bitacora.
     *
     * @param bitacoraDTO the entity to save.
     * @return the persisted entity.
     */
    BitacoraDTO save(BitacoraDTO bitacoraDTO);

    /**
     * Get all the bitacoras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BitacoraDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bitacora.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BitacoraDTO> findOne(Long id);

    /**
     * Delete the "id" bitacora.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
