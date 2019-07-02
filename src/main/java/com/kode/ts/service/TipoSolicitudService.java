package com.kode.ts.service;

import com.kode.ts.service.dto.TipoSolicitudDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.TipoSolicitud}.
 */
public interface TipoSolicitudService {

    /**
     * Save a tipoSolicitud.
     *
     * @param tipoSolicitudDTO the entity to save.
     * @return the persisted entity.
     */
    TipoSolicitudDTO save(TipoSolicitudDTO tipoSolicitudDTO);

    /**
     * Get all the tipoSolicituds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoSolicitudDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoSolicitud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoSolicitudDTO> findOne(Long id);

    /**
     * Delete the "id" tipoSolicitud.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
