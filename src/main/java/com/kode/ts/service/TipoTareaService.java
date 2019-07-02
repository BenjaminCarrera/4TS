package com.kode.ts.service;

import com.kode.ts.service.dto.TipoTareaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.TipoTarea}.
 */
public interface TipoTareaService {

    /**
     * Save a tipoTarea.
     *
     * @param tipoTareaDTO the entity to save.
     * @return the persisted entity.
     */
    TipoTareaDTO save(TipoTareaDTO tipoTareaDTO);

    /**
     * Get all the tipoTareas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoTareaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoTarea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoTareaDTO> findOne(Long id);

    /**
     * Delete the "id" tipoTarea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
