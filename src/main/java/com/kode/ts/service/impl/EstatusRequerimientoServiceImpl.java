package com.kode.ts.service.impl;

import com.kode.ts.service.EstatusRequerimientoService;
import com.kode.ts.domain.EstatusRequerimiento;
import com.kode.ts.repository.EstatusRequerimientoRepository;
import com.kode.ts.service.dto.EstatusRequerimientoDTO;
import com.kode.ts.service.mapper.EstatusRequerimientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstatusRequerimiento}.
 */
@Service
@Transactional
public class EstatusRequerimientoServiceImpl implements EstatusRequerimientoService {

    private final Logger log = LoggerFactory.getLogger(EstatusRequerimientoServiceImpl.class);

    private final EstatusRequerimientoRepository estatusRequerimientoRepository;

    private final EstatusRequerimientoMapper estatusRequerimientoMapper;

    public EstatusRequerimientoServiceImpl(EstatusRequerimientoRepository estatusRequerimientoRepository, EstatusRequerimientoMapper estatusRequerimientoMapper) {
        this.estatusRequerimientoRepository = estatusRequerimientoRepository;
        this.estatusRequerimientoMapper = estatusRequerimientoMapper;
    }

    /**
     * Save a estatusRequerimiento.
     *
     * @param estatusRequerimientoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstatusRequerimientoDTO save(EstatusRequerimientoDTO estatusRequerimientoDTO) {
        log.debug("Request to save EstatusRequerimiento : {}", estatusRequerimientoDTO);
        EstatusRequerimiento estatusRequerimiento = estatusRequerimientoMapper.toEntity(estatusRequerimientoDTO);
        estatusRequerimiento = estatusRequerimientoRepository.save(estatusRequerimiento);
        return estatusRequerimientoMapper.toDto(estatusRequerimiento);
    }

    /**
     * Get all the estatusRequerimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusRequerimientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusRequerimientos");
        return estatusRequerimientoRepository.findAll(pageable)
            .map(estatusRequerimientoMapper::toDto);
    }


    /**
     * Get one estatusRequerimiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusRequerimientoDTO> findOne(Long id) {
        log.debug("Request to get EstatusRequerimiento : {}", id);
        return estatusRequerimientoRepository.findById(id)
            .map(estatusRequerimientoMapper::toDto);
    }

    /**
     * Delete the estatusRequerimiento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusRequerimiento : {}", id);
        estatusRequerimientoRepository.deleteById(id);
    }
}
