package com.kode.ts.service.impl;

import com.kode.ts.service.TipoPeriodoService;
import com.kode.ts.domain.TipoPeriodo;
import com.kode.ts.repository.TipoPeriodoRepository;
import com.kode.ts.service.dto.TipoPeriodoDTO;
import com.kode.ts.service.mapper.TipoPeriodoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoPeriodo}.
 */
@Service
@Transactional
public class TipoPeriodoServiceImpl implements TipoPeriodoService {

    private final Logger log = LoggerFactory.getLogger(TipoPeriodoServiceImpl.class);

    private final TipoPeriodoRepository tipoPeriodoRepository;

    private final TipoPeriodoMapper tipoPeriodoMapper;

    public TipoPeriodoServiceImpl(TipoPeriodoRepository tipoPeriodoRepository, TipoPeriodoMapper tipoPeriodoMapper) {
        this.tipoPeriodoRepository = tipoPeriodoRepository;
        this.tipoPeriodoMapper = tipoPeriodoMapper;
    }

    /**
     * Save a tipoPeriodo.
     *
     * @param tipoPeriodoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoPeriodoDTO save(TipoPeriodoDTO tipoPeriodoDTO) {
        log.debug("Request to save TipoPeriodo : {}", tipoPeriodoDTO);
        TipoPeriodo tipoPeriodo = tipoPeriodoMapper.toEntity(tipoPeriodoDTO);
        tipoPeriodo = tipoPeriodoRepository.save(tipoPeriodo);
        return tipoPeriodoMapper.toDto(tipoPeriodo);
    }

    /**
     * Get all the tipoPeriodos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoPeriodoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoPeriodos");
        return tipoPeriodoRepository.findAll(pageable)
            .map(tipoPeriodoMapper::toDto);
    }


    /**
     * Get one tipoPeriodo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPeriodoDTO> findOne(Long id) {
        log.debug("Request to get TipoPeriodo : {}", id);
        return tipoPeriodoRepository.findById(id)
            .map(tipoPeriodoMapper::toDto);
    }

    /**
     * Delete the tipoPeriodo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPeriodo : {}", id);
        tipoPeriodoRepository.deleteById(id);
    }
}
