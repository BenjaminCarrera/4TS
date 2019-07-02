package com.kode.ts.service.impl;

import com.kode.ts.service.EstatusLaboralService;
import com.kode.ts.domain.EstatusLaboral;
import com.kode.ts.repository.EstatusLaboralRepository;
import com.kode.ts.service.dto.EstatusLaboralDTO;
import com.kode.ts.service.mapper.EstatusLaboralMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstatusLaboral}.
 */
@Service
@Transactional
public class EstatusLaboralServiceImpl implements EstatusLaboralService {

    private final Logger log = LoggerFactory.getLogger(EstatusLaboralServiceImpl.class);

    private final EstatusLaboralRepository estatusLaboralRepository;

    private final EstatusLaboralMapper estatusLaboralMapper;

    public EstatusLaboralServiceImpl(EstatusLaboralRepository estatusLaboralRepository, EstatusLaboralMapper estatusLaboralMapper) {
        this.estatusLaboralRepository = estatusLaboralRepository;
        this.estatusLaboralMapper = estatusLaboralMapper;
    }

    /**
     * Save a estatusLaboral.
     *
     * @param estatusLaboralDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstatusLaboralDTO save(EstatusLaboralDTO estatusLaboralDTO) {
        log.debug("Request to save EstatusLaboral : {}", estatusLaboralDTO);
        EstatusLaboral estatusLaboral = estatusLaboralMapper.toEntity(estatusLaboralDTO);
        estatusLaboral = estatusLaboralRepository.save(estatusLaboral);
        return estatusLaboralMapper.toDto(estatusLaboral);
    }

    /**
     * Get all the estatusLaborals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusLaboralDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusLaborals");
        return estatusLaboralRepository.findAll(pageable)
            .map(estatusLaboralMapper::toDto);
    }


    /**
     * Get one estatusLaboral by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusLaboralDTO> findOne(Long id) {
        log.debug("Request to get EstatusLaboral : {}", id);
        return estatusLaboralRepository.findById(id)
            .map(estatusLaboralMapper::toDto);
    }

    /**
     * Delete the estatusLaboral by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusLaboral : {}", id);
        estatusLaboralRepository.deleteById(id);
    }
}
