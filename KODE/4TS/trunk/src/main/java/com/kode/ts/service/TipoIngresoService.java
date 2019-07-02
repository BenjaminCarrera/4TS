package com.kode.ts.service;

import com.kode.ts.service.dto.TipoIngresoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.TipoIngreso}.
 */
public interface TipoIngresoService {

    /**
     * Save a tipoIngreso.
     *
     * @param tipoIngresoDTO the entity to save.
     * @return the persisted entity.
     */
    TipoIngresoDTO save(TipoIngresoDTO tipoIngresoDTO);

    /**
     * Get all the tipoIngresos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoIngresoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoIngreso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoIngresoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoIngreso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
