package com.kode.ts.service.impl;

import com.kode.ts.service.RequerimientoService;
import com.kode.ts.domain.Requerimiento;
import com.kode.ts.repository.RequerimientoRepository;
import com.kode.ts.service.dto.RequerimientoDTO;
import com.kode.ts.service.mapper.RequerimientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Requerimiento}.
 */
@Service
@Transactional
public class RequerimientoServiceImpl implements RequerimientoService {

    private final Logger log = LoggerFactory.getLogger(RequerimientoServiceImpl.class);

    private final RequerimientoRepository requerimientoRepository;

    private final RequerimientoMapper requerimientoMapper;

    public RequerimientoServiceImpl(RequerimientoRepository requerimientoRepository, RequerimientoMapper requerimientoMapper) {
        this.requerimientoRepository = requerimientoRepository;
        this.requerimientoMapper = requerimientoMapper;
    }

    /**
     * Save a requerimiento.
     *
     * @param requerimientoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RequerimientoDTO save(RequerimientoDTO requerimientoDTO) {
        log.debug("Request to save Requerimiento : {}", requerimientoDTO);
        Requerimiento requerimiento = requerimientoMapper.toEntity(requerimientoDTO);
        requerimiento = requerimientoRepository.save(requerimiento);
        return requerimientoMapper.toDto(requerimiento);
    }

    /**
     * Get all the requerimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RequerimientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requerimientos");
        return requerimientoRepository.findAll(pageable)
            .map(requerimientoMapper::toDto);
    }


    /**
     * Get one requerimiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RequerimientoDTO> findOne(Long id) {
        log.debug("Request to get Requerimiento : {}", id);
        return requerimientoRepository.findById(id)
            .map(requerimientoMapper::toDto);
    }

    /**
     * Delete the requerimiento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Requerimiento : {}", id);
        requerimientoRepository.deleteById(id);
    }
}
