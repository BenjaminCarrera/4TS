package com.kode.ts.service;

import com.kode.ts.service.dto.PermisoAuthorityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.PermisoAuthority}.
 */
public interface PermisoAuthorityService {

    /**
     * Save a permisoAuthority.
     *
     * @param permisoAuthorityDTO the entity to save.
     * @return the persisted entity.
     */
    PermisoAuthorityDTO save(PermisoAuthorityDTO permisoAuthorityDTO);

    /**
     * Get all the permisoAuthorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PermisoAuthorityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" permisoAuthority.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PermisoAuthorityDTO> findOne(Long id);

    /**
     * Delete the "id" permisoAuthority.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
