package com.kode.ts.service.impl;

import com.kode.ts.service.EstatusTareaService;
import com.kode.ts.domain.EstatusTarea;
import com.kode.ts.repository.EstatusTareaRepository;
import com.kode.ts.service.dto.EstatusTareaDTO;
import com.kode.ts.service.mapper.EstatusTareaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstatusTarea}.
 */
@Service
@Transactional
public class EstatusTareaServiceImpl implements EstatusTareaService {

    private final Logger log = LoggerFactory.getLogger(EstatusTareaServiceImpl.class);

    private final EstatusTareaRepository estatusTareaRepository;

    private final EstatusTareaMapper estatusTareaMapper;

    public EstatusTareaServiceImpl(EstatusTareaRepository estatusTareaRepository, EstatusTareaMapper estatusTareaMapper) {
        this.estatusTareaRepository = estatusTareaRepository;
        this.estatusTareaMapper = estatusTareaMapper;
    }

    /**
     * Save a estatusTarea.
     *
     * @param estatusTareaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstatusTareaDTO save(EstatusTareaDTO estatusTareaDTO) {
        log.debug("Request to save EstatusTarea : {}", estatusTareaDTO);
        EstatusTarea estatusTarea = estatusTareaMapper.toEntity(estatusTareaDTO);
        estatusTarea = estatusTareaRepository.save(estatusTarea);
        return estatusTareaMapper.toDto(estatusTarea);
    }

    /**
     * Get all the estatusTareas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusTareaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusTareas");
        return estatusTareaRepository.findAll(pageable)
            .map(estatusTareaMapper::toDto);
    }


    /**
     * Get one estatusTarea by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusTareaDTO> findOne(Long id) {
        log.debug("Request to get EstatusTarea : {}", id);
        return estatusTareaRepository.findById(id)
            .map(estatusTareaMapper::toDto);
    }

    /**
     * Delete the estatusTarea by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusTarea : {}", id);
        estatusTareaRepository.deleteById(id);
    }
}
