package com.kode.ts.service;

import com.kode.ts.service.dto.EstCanInactivoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EstCanInactivo}.
 */
public interface EstCanInactivoService {

    /**
     * Save a estCanInactivo.
     *
     * @param estCanInactivoDTO the entity to save.
     * @return the persisted entity.
     */
    EstCanInactivoDTO save(EstCanInactivoDTO estCanInactivoDTO);

    /**
     * Get all the estCanInactivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstCanInactivoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estCanInactivo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstCanInactivoDTO> findOne(Long id);

    /**
     * Delete the "id" estCanInactivo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
