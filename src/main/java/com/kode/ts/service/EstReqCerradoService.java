package com.kode.ts.service;

import com.kode.ts.service.dto.EstReqCerradoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EstReqCerrado}.
 */
public interface EstReqCerradoService {

    /**
     * Save a estReqCerrado.
     *
     * @param estReqCerradoDTO the entity to save.
     * @return the persisted entity.
     */
    EstReqCerradoDTO save(EstReqCerradoDTO estReqCerradoDTO);

    /**
     * Get all the estReqCerrados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstReqCerradoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estReqCerrado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstReqCerradoDTO> findOne(Long id);

    /**
     * Delete the "id" estReqCerrado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
