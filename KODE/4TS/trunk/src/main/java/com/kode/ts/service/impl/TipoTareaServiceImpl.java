package com.kode.ts.service.impl;

import com.kode.ts.service.TipoTareaService;
import com.kode.ts.domain.TipoTarea;
import com.kode.ts.repository.TipoTareaRepository;
import com.kode.ts.service.dto.TipoTareaDTO;
import com.kode.ts.service.mapper.TipoTareaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoTarea}.
 */
@Service
@Transactional
public class TipoTareaServiceImpl implements TipoTareaService {

    private final Logger log = LoggerFactory.getLogger(TipoTareaServiceImpl.class);

    private final TipoTareaRepository tipoTareaRepository;

    private final TipoTareaMapper tipoTareaMapper;

    public TipoTareaServiceImpl(TipoTareaRepository tipoTareaRepository, TipoTareaMapper tipoTareaMapper) {
        this.tipoTareaRepository = tipoTareaRepository;
        this.tipoTareaMapper = tipoTareaMapper;
    }

    /**
     * Save a tipoTarea.
     *
     * @param tipoTareaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoTareaDTO save(TipoTareaDTO tipoTareaDTO) {
        log.debug("Request to save TipoTarea : {}", tipoTareaDTO);
        TipoTarea tipoTarea = tipoTareaMapper.toEntity(tipoTareaDTO);
        tipoTarea = tipoTareaRepository.save(tipoTarea);
        return tipoTareaMapper.toDto(tipoTarea);
    }

    /**
     * Get all the tipoTareas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoTareaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoTareas");
        return tipoTareaRepository.findAll(pageable)
            .map(tipoTareaMapper::toDto);
    }


    /**
     * Get one tipoTarea by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoTareaDTO> findOne(Long id) {
        log.debug("Request to get TipoTarea : {}", id);
        return tipoTareaRepository.findById(id)
            .map(tipoTareaMapper::toDto);
    }

    /**
     * Delete the tipoTarea by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoTarea : {}", id);
        tipoTareaRepository.deleteById(id);
    }
}
