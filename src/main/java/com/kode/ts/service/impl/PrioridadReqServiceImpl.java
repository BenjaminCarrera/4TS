package com.kode.ts.service.impl;

import com.kode.ts.service.PrioridadReqService;
import com.kode.ts.domain.PrioridadReq;
import com.kode.ts.repository.PrioridadReqRepository;
import com.kode.ts.service.dto.PrioridadReqDTO;
import com.kode.ts.service.mapper.PrioridadReqMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PrioridadReq}.
 */
@Service
@Transactional
public class PrioridadReqServiceImpl implements PrioridadReqService {

    private final Logger log = LoggerFactory.getLogger(PrioridadReqServiceImpl.class);

    private final PrioridadReqRepository prioridadReqRepository;

    private final PrioridadReqMapper prioridadReqMapper;

    public PrioridadReqServiceImpl(PrioridadReqRepository prioridadReqRepository, PrioridadReqMapper prioridadReqMapper) {
        this.prioridadReqRepository = prioridadReqRepository;
        this.prioridadReqMapper = prioridadReqMapper;
    }

    /**
     * Save a prioridadReq.
     *
     * @param prioridadReqDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PrioridadReqDTO save(PrioridadReqDTO prioridadReqDTO) {
        log.debug("Request to save PrioridadReq : {}", prioridadReqDTO);
        PrioridadReq prioridadReq = prioridadReqMapper.toEntity(prioridadReqDTO);
        prioridadReq = prioridadReqRepository.save(prioridadReq);
        return prioridadReqMapper.toDto(prioridadReq);
    }

    /**
     * Get all the prioridadReqs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrioridadReqDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrioridadReqs");
        return prioridadReqRepository.findAll(pageable)
            .map(prioridadReqMapper::toDto);
    }


    /**
     * Get one prioridadReq by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PrioridadReqDTO> findOne(Long id) {
        log.debug("Request to get PrioridadReq : {}", id);
        return prioridadReqRepository.findById(id)
            .map(prioridadReqMapper::toDto);
    }

    /**
     * Delete the prioridadReq by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrioridadReq : {}", id);
        prioridadReqRepository.deleteById(id);
    }
}
