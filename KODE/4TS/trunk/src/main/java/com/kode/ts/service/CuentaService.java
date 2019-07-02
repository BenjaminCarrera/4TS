package com.kode.ts.service;

import com.kode.ts.service.dto.CuentaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.Cuenta}.
 */
public interface CuentaService {

    /**
     * Save a cuenta.
     *
     * @param cuentaDTO the entity to save.
     * @return the persisted entity.
     */
    CuentaDTO save(CuentaDTO cuentaDTO);

    /**
     * Get all the cuentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CuentaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cuenta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CuentaDTO> findOne(Long id);

    /**
     * Delete the "id" cuenta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
