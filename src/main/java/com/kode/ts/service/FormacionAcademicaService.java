package com.kode.ts.service;

import com.kode.ts.service.dto.FormacionAcademicaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.FormacionAcademica}.
 */
public interface FormacionAcademicaService {

    /**
     * Save a formacionAcademica.
     *
     * @param formacionAcademicaDTO the entity to save.
     * @return the persisted entity.
     */
    FormacionAcademicaDTO save(FormacionAcademicaDTO formacionAcademicaDTO);

    /**
     * Get all the formacionAcademicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormacionAcademicaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" formacionAcademica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormacionAcademicaDTO> findOne(Long id);

    /**
     * Delete the "id" formacionAcademica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
