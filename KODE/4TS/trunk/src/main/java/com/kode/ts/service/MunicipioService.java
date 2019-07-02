package com.kode.ts.service;

import com.kode.ts.service.dto.MunicipioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.Municipio}.
 */
public interface MunicipioService {

    /**
     * Save a municipio.
     *
     * @param municipioDTO the entity to save.
     * @return the persisted entity.
     */
    MunicipioDTO save(MunicipioDTO municipioDTO);

    /**
     * Get all the municipios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MunicipioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" municipio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MunicipioDTO> findOne(Long id);

    /**
     * Delete the "id" municipio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
