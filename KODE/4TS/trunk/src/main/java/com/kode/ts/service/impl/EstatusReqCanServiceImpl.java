package com.kode.ts.service.impl;

import com.kode.ts.service.EstatusReqCanService;
import com.kode.ts.domain.EstatusReqCan;
import com.kode.ts.repository.EstatusReqCanRepository;
import com.kode.ts.service.dto.EstatusReqCanDTO;
import com.kode.ts.service.mapper.EstatusReqCanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstatusReqCan}.
 */
@Service
@Transactional
public class EstatusReqCanServiceImpl implements EstatusReqCanService {

    private final Logger log = LoggerFactory.getLogger(EstatusReqCanServiceImpl.class);

    private final EstatusReqCanRepository estatusReqCanRepository;

    private final EstatusReqCanMapper estatusReqCanMapper;

    public EstatusReqCanServiceImpl(EstatusReqCanRepository estatusReqCanRepository, EstatusReqCanMapper estatusReqCanMapper) {
        this.estatusReqCanRepository = estatusReqCanRepository;
        this.estatusReqCanMapper = estatusReqCanMapper;
    }

    /**
     * Save a estatusReqCan.
     *
     * @param estatusReqCanDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstatusReqCanDTO save(EstatusReqCanDTO estatusReqCanDTO) {
        log.debug("Request to save EstatusReqCan : {}", estatusReqCanDTO);
        EstatusReqCan estatusReqCan = estatusReqCanMapper.toEntity(estatusReqCanDTO);
        estatusReqCan = estatusReqCanRepository.save(estatusReqCan);
        return estatusReqCanMapper.toDto(estatusReqCan);
    }

    /**
     * Get all the estatusReqCans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusReqCanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusReqCans");
        return estatusReqCanRepository.findAll(pageable)
            .map(estatusReqCanMapper::toDto);
    }


    /**
     * Get one estatusReqCan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusReqCanDTO> findOne(Long id) {
        log.debug("Request to get EstatusReqCan : {}", id);
        return estatusReqCanRepository.findById(id)
            .map(estatusReqCanMapper::toDto);
    }

    /**
     * Delete the estatusReqCan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusReqCan : {}", id);
        estatusReqCanRepository.deleteById(id);
    }
}
