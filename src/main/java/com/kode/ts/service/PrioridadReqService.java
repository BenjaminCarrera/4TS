package com.kode.ts.service;

import com.kode.ts.service.dto.PrioridadReqDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.PrioridadReq}.
 */
public interface PrioridadReqService {

    /**
     * Save a prioridadReq.
     *
     * @param prioridadReqDTO the entity to save.
     * @return the persisted entity.
     */
    PrioridadReqDTO save(PrioridadReqDTO prioridadReqDTO);

    /**
     * Get all the prioridadReqs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrioridadReqDTO> findAll(Pageable pageable);


    /**
     * Get the "id" prioridadReq.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrioridadReqDTO> findOne(Long id);

    /**
     * Delete the "id" prioridadReq.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
