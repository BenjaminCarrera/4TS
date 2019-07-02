package com.kode.ts.service;

import com.kode.ts.service.dto.ReqCanDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.ReqCan}.
 */
public interface ReqCanService {

    /**
     * Save a reqCan.
     *
     * @param reqCanDTO the entity to save.
     * @return the persisted entity.
     */
    ReqCanDTO save(ReqCanDTO reqCanDTO);

    /**
     * Get all the reqCans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReqCanDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reqCan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReqCanDTO> findOne(Long id);

    /**
     * Delete the "id" reqCan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
