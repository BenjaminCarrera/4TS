package com.kode.ts.service;

import com.kode.ts.service.dto.PermisoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.Permiso}.
 */
public interface PermisoService {

    /**
     * Save a permiso.
     *
     * @param permisoDTO the entity to save.
     * @return the persisted entity.
     */
    PermisoDTO save(PermisoDTO permisoDTO);

    /**
     * Get all the permisos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PermisoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" permiso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PermisoDTO> findOne(Long id);

    /**
     * Delete the "id" permiso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
