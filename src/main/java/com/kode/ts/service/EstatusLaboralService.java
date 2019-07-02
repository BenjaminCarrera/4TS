package com.kode.ts.service;

import com.kode.ts.service.dto.EstatusLaboralDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.EstatusLaboral}.
 */
public interface EstatusLaboralService {

    /**
     * Save a estatusLaboral.
     *
     * @param estatusLaboralDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusLaboralDTO save(EstatusLaboralDTO estatusLaboralDTO);

    /**
     * Get all the estatusLaborals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstatusLaboralDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estatusLaboral.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusLaboralDTO> findOne(Long id);

    /**
     * Delete the "id" estatusLaboral.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
