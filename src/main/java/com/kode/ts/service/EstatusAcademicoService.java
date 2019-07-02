package com.kode.ts.service;

import com.kode.ts.service.dto.EstatusAcademicoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EstatusAcademico}.
 */
public interface EstatusAcademicoService {

    /**
     * Save a estatusAcademico.
     *
     * @param estatusAcademicoDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusAcademicoDTO save(EstatusAcademicoDTO estatusAcademicoDTO);

    /**
     * Get all the estatusAcademicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstatusAcademicoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estatusAcademico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusAcademicoDTO> findOne(Long id);

    /**
     * Delete the "id" estatusAcademico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
