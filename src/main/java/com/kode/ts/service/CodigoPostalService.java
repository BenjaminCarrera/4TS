package com.kode.ts.service;

import com.kode.ts.service.dto.CodigoPostalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.CodigoPostal}.
 */
public interface CodigoPostalService {

    /**
     * Save a codigoPostal.
     *
     * @param codigoPostalDTO the entity to save.
     * @return the persisted entity.
     */
    CodigoPostalDTO save(CodigoPostalDTO codigoPostalDTO);

    /**
     * Get all the codigoPostals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CodigoPostalDTO> findAll(Pageable pageable);

    /**
     * Get all the codigoPostals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CodigoPostalDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" codigoPostal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CodigoPostalDTO> findOne(Long id);

    /**
     * Delete the "id" codigoPostal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
