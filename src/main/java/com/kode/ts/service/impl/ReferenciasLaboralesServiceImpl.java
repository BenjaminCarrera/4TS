package com.kode.ts.service.impl;

import com.kode.ts.service.ReferenciasLaboralesService;
import com.kode.ts.domain.ReferenciasLaborales;
import com.kode.ts.repository.ReferenciasLaboralesRepository;
import com.kode.ts.service.dto.ReferenciasLaboralesDTO;
import com.kode.ts.service.mapper.ReferenciasLaboralesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReferenciasLaborales}.
 */
@Service
@Transactional
public class ReferenciasLaboralesServiceImpl implements ReferenciasLaboralesService {

    private final Logger log = LoggerFactory.getLogger(ReferenciasLaboralesServiceImpl.class);

    private final ReferenciasLaboralesRepository referenciasLaboralesRepository;

    private final ReferenciasLaboralesMapper referenciasLaboralesMapper;

    public ReferenciasLaboralesServiceImpl(ReferenciasLaboralesRepository referenciasLaboralesRepository, ReferenciasLaboralesMapper referenciasLaboralesMapper) {
        this.referenciasLaboralesRepository = referenciasLaboralesRepository;
        this.referenciasLaboralesMapper = referenciasLaboralesMapper;
    }

    /**
     * Save a referenciasLaborales.
     *
     * @param referenciasLaboralesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReferenciasLaboralesDTO save(ReferenciasLaboralesDTO referenciasLaboralesDTO) {
        log.debug("Request to save ReferenciasLaborales : {}", referenciasLaboralesDTO);
        ReferenciasLaborales referenciasLaborales = referenciasLaboralesMapper.toEntity(referenciasLaboralesDTO);
        referenciasLaborales = referenciasLaboralesRepository.save(referenciasLaborales);
        return referenciasLaboralesMapper.toDto(referenciasLaborales);
    }

    /**
     * Get all the referenciasLaborales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReferenciasLaboralesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReferenciasLaborales");
        return referenciasLaboralesRepository.findAll(pageable)
            .map(referenciasLaboralesMapper::toDto);
    }


    /**
     * Get one referenciasLaborales by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReferenciasLaboralesDTO> findOne(Long id) {
        log.debug("Request to get ReferenciasLaborales : {}", id);
        return referenciasLaboralesRepository.findById(id)
            .map(referenciasLaboralesMapper::toDto);
    }

    /**
     * Delete the referenciasLaborales by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReferenciasLaborales : {}", id);
        referenciasLaboralesRepository.deleteById(id);
    }
}
