package com.kode.ts.service;

import com.kode.ts.service.dto.ReferenciasLaboralesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.ReferenciasLaborales}.
 */
public interface ReferenciasLaboralesService {

    /**
     * Save a referenciasLaborales.
     *
     * @param referenciasLaboralesDTO the entity to save.
     * @return the persisted entity.
     */
    ReferenciasLaboralesDTO save(ReferenciasLaboralesDTO referenciasLaboralesDTO);

    /**
     * Get all the referenciasLaborales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReferenciasLaboralesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" referenciasLaborales.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReferenciasLaboralesDTO> findOne(Long id);

    /**
     * Delete the "id" referenciasLaborales.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
