package com.kode.ts.service;

import com.kode.ts.service.dto.EstatusRequerimientoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EstatusRequerimiento}.
 */
public interface EstatusRequerimientoService {

    /**
     * Save a estatusRequerimiento.
     *
     * @param estatusRequerimientoDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusRequerimientoDTO save(EstatusRequerimientoDTO estatusRequerimientoDTO);

    /**
     * Get all the estatusRequerimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstatusRequerimientoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estatusRequerimiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusRequerimientoDTO> findOne(Long id);

    /**
     * Delete the "id" estatusRequerimiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
