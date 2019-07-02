package com.kode.ts.service.impl;

import com.kode.ts.service.ColoniaService;
import com.kode.ts.domain.Colonia;
import com.kode.ts.repository.ColoniaRepository;
import com.kode.ts.service.dto.ColoniaDTO;
import com.kode.ts.service.mapper.ColoniaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Colonia}.
 */
@Service
@Transactional
public class ColoniaServiceImpl implements ColoniaService {

    private final Logger log = LoggerFactory.getLogger(ColoniaServiceImpl.class);

    private final ColoniaRepository coloniaRepository;

    private final ColoniaMapper coloniaMapper;

    public ColoniaServiceImpl(ColoniaRepository coloniaRepository, ColoniaMapper coloniaMapper) {
        this.coloniaRepository = coloniaRepository;
        this.coloniaMapper = coloniaMapper;
    }

    /**
     * Save a colonia.
     *
     * @param coloniaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ColoniaDTO save(ColoniaDTO coloniaDTO) {
        log.debug("Request to save Colonia : {}", coloniaDTO);
        Colonia colonia = coloniaMapper.toEntity(coloniaDTO);
        colonia = coloniaRepository.save(colonia);
        return coloniaMapper.toDto(colonia);
    }

    /**
     * Get all the colonias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ColoniaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Colonias");
        return coloniaRepository.findAll(pageable)
            .map(coloniaMapper::toDto);
    }


    /**
     * Get one colonia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ColoniaDTO> findOne(Long id) {
        log.debug("Request to get Colonia : {}", id);
        return coloniaRepository.findById(id)
            .map(coloniaMapper::toDto);
    }

    /**
     * Delete the colonia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Colonia : {}", id);
        coloniaRepository.deleteById(id);
    }
}
