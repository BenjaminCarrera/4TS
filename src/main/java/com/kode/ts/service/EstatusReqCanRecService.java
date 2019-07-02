package com.kode.ts.service;

import com.kode.ts.service.dto.EstatusReqCanRecDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EstatusReqCanRec}.
 */
public interface EstatusReqCanRecService {

    /**
     * Save a estatusReqCanRec.
     *
     * @param estatusReqCanRecDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusReqCanRecDTO save(EstatusReqCanRecDTO estatusReqCanRecDTO);

    /**
     * Get all the estatusReqCanRecs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstatusReqCanRecDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estatusReqCanRec.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusReqCanRecDTO> findOne(Long id);

    /**
     * Delete the "id" estatusReqCanRec.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
