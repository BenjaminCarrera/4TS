package com.kode.ts.service;

import com.kode.ts.service.dto.InstitucionAcademicaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.InstitucionAcademica}.
 */
public interface InstitucionAcademicaService {

    /**
     * Save a institucionAcademica.
     *
     * @param institucionAcademicaDTO the entity to save.
     * @return the persisted entity.
     */
    InstitucionAcademicaDTO save(InstitucionAcademicaDTO institucionAcademicaDTO);

    /**
     * Get all the institucionAcademicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InstitucionAcademicaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" institucionAcademica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstitucionAcademicaDTO> findOne(Long id);

    /**
     * Delete the "id" institucionAcademica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
