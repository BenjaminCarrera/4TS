package com.kode.ts.service;

import com.kode.ts.service.dto.NivelPerfilDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.NivelPerfil}.
 */
public interface NivelPerfilService {

    /**
     * Save a nivelPerfil.
     *
     * @param nivelPerfilDTO the entity to save.
     * @return the persisted entity.
     */
    NivelPerfilDTO save(NivelPerfilDTO nivelPerfilDTO);

    /**
     * Get all the nivelPerfils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NivelPerfilDTO> findAll(Pageable pageable);


    /**
     * Get the "id" nivelPerfil.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NivelPerfilDTO> findOne(Long id);

    /**
     * Delete the "id" nivelPerfil.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
