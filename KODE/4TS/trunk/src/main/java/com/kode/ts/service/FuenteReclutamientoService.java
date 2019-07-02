package com.kode.ts.service;

import com.kode.ts.service.dto.FuenteReclutamientoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.FuenteReclutamiento}.
 */
public interface FuenteReclutamientoService {

    /**
     * Save a fuenteReclutamiento.
     *
     * @param fuenteReclutamientoDTO the entity to save.
     * @return the persisted entity.
     */
    FuenteReclutamientoDTO save(FuenteReclutamientoDTO fuenteReclutamientoDTO);

    /**
     * Get all the fuenteReclutamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuenteReclutamientoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fuenteReclutamiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuenteReclutamientoDTO> findOne(Long id);

    /**
     * Delete the "id" fuenteReclutamiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
