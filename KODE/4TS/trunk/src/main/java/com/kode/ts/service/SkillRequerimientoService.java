package com.kode.ts.service;

import com.kode.ts.service.dto.ListaSkillRequerimientoDTO;
import com.kode.ts.service.dto.SkillRequerimientoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kode.ts.domain.SkillRequerimiento}.
 */
public interface SkillRequerimientoService {

    /**
     * Save a skillRequerimiento.
     *
     * @param skillRequerimientoDTO the entity to save.
     * @return the persisted entity.
     */
    SkillRequerimientoDTO save(SkillRequerimientoDTO skillRequerimientoDTO);
    
    /**
     * Save a list of skillRequerimiento.
     *
     * @param listaSkillRequerimientoDTO the entity to save.
     * @return the persisted entity.
     */
    ListaSkillRequerimientoDTO saveLista(ListaSkillRequerimientoDTO listaSkillRequerimientoDTO);

    /**
     * Get all the skillRequerimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SkillRequerimientoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" skillRequerimiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkillRequerimientoDTO> findOne(Long id);

    /**
     * Delete the "id" skillRequerimiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
