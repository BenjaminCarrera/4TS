package com.kode.ts.service;

import com.kode.ts.service.dto.EsquemaContratacionKodeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EsquemaContratacionKode}.
 */
public interface EsquemaContratacionKodeService {

    /**
     * Save a esquemaContratacionKode.
     *
     * @param esquemaContratacionKodeDTO the entity to save.
     * @return the persisted entity.
     */
    EsquemaContratacionKodeDTO save(EsquemaContratacionKodeDTO esquemaContratacionKodeDTO);

    /**
     * Get all the esquemaContratacionKodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EsquemaContratacionKodeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" esquemaContratacionKode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EsquemaContratacionKodeDTO> findOne(Long id);

    /**
     * Delete the "id" esquemaContratacionKode.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
