package com.kode.ts.service.impl;

import com.kode.ts.service.TipoIngresoService;
import com.kode.ts.domain.TipoIngreso;
import com.kode.ts.repository.TipoIngresoRepository;
import com.kode.ts.service.dto.TipoIngresoDTO;
import com.kode.ts.service.mapper.TipoIngresoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoIngreso}.
 */
@Service
@Transactional
public class TipoIngresoServiceImpl implements TipoIngresoService {

    private final Logger log = LoggerFactory.getLogger(TipoIngresoServiceImpl.class);

    private final TipoIngresoRepository tipoIngresoRepository;

    private final TipoIngresoMapper tipoIngresoMapper;

    public TipoIngresoServiceImpl(TipoIngresoRepository tipoIngresoRepository, TipoIngresoMapper tipoIngresoMapper) {
        this.tipoIngresoRepository = tipoIngresoRepository;
        this.tipoIngresoMapper = tipoIngresoMapper;
    }

    /**
     * Save a tipoIngreso.
     *
     * @param tipoIngresoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoIngresoDTO save(TipoIngresoDTO tipoIngresoDTO) {
        log.debug("Request to save TipoIngreso : {}", tipoIngresoDTO);
        TipoIngreso tipoIngreso = tipoIngresoMapper.toEntity(tipoIngresoDTO);
        tipoIngreso = tipoIngresoRepository.save(tipoIngreso);
        return tipoIngresoMapper.toDto(tipoIngreso);
    }

    /**
     * Get all the tipoIngresos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoIngresoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoIngresos");
        return tipoIngresoRepository.findAll(pageable)
            .map(tipoIngresoMapper::toDto);
    }


    /**
     * Get one tipoIngreso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoIngresoDTO> findOne(Long id) {
        log.debug("Request to get TipoIngreso : {}", id);
        return tipoIngresoRepository.findById(id)
            .map(tipoIngresoMapper::toDto);
    }

    /**
     * Delete the tipoIngreso by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoIngreso : {}", id);
        tipoIngresoRepository.deleteById(id);
    }
}
