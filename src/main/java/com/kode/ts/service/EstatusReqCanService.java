package com.kode.ts.service;

import com.kode.ts.service.dto.EstatusReqCanDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EstatusReqCan}.
 */
public interface EstatusReqCanService {

    /**
     * Save a estatusReqCan.
     *
     * @param estatusReqCanDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusReqCanDTO save(EstatusReqCanDTO estatusReqCanDTO);

    /**
     * Get all the estatusReqCans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstatusReqCanDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estatusReqCan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusReqCanDTO> findOne(Long id);

    /**
     * Delete the "id" estatusReqCan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
