package com.kode.ts.service;

import com.kode.ts.service.dto.EsqContRecDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EsqContRec}.
 */
public interface EsqContRecService {

    /**
     * Save a esqContRec.
     *
     * @param esqContRecDTO the entity to save.
     * @return the persisted entity.
     */
    EsqContRecDTO save(EsqContRecDTO esqContRecDTO);

    /**
     * Get all the esqContRecs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EsqContRecDTO> findAll(Pageable pageable);


    /**
     * Get the "id" esqContRec.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EsqContRecDTO> findOne(Long id);

    /**
     * Delete the "id" esqContRec.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
