package com.kode.ts.service;

import com.kode.ts.service.dto.BaseTarifaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.BaseTarifa}.
 */
public interface BaseTarifaService {

    /**
     * Save a baseTarifa.
     *
     * @param baseTarifaDTO the entity to save.
     * @return the persisted entity.
     */
    BaseTarifaDTO save(BaseTarifaDTO baseTarifaDTO);

    /**
     * Get all the baseTarifas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BaseTarifaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" baseTarifa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BaseTarifaDTO> findOne(Long id);

    /**
     * Delete the "id" baseTarifa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
