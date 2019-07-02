package com.kode.ts.service;

import com.kode.ts.service.dto.TipoPeriodoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.TipoPeriodo}.
 */
public interface TipoPeriodoService {

    /**
     * Save a tipoPeriodo.
     *
     * @param tipoPeriodoDTO the entity to save.
     * @return the persisted entity.
     */
    TipoPeriodoDTO save(TipoPeriodoDTO tipoPeriodoDTO);

    /**
     * Get all the tipoPeriodos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoPeriodoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoPeriodo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoPeriodoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoPeriodo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
