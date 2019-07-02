package com.kode.ts.service.impl;

import com.kode.ts.service.EstatusReqCanRecService;
import com.kode.ts.domain.EstatusReqCanRec;
import com.kode.ts.repository.EstatusReqCanRecRepository;
import com.kode.ts.service.dto.EstatusReqCanRecDTO;
import com.kode.ts.service.mapper.EstatusReqCanRecMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstatusReqCanRec}.
 */
@Service
@Transactional
public class EstatusReqCanRecServiceImpl implements EstatusReqCanRecService {

    private final Logger log = LoggerFactory.getLogger(EstatusReqCanRecServiceImpl.class);

    private final EstatusReqCanRecRepository estatusReqCanRecRepository;

    private final EstatusReqCanRecMapper estatusReqCanRecMapper;

    public EstatusReqCanRecServiceImpl(EstatusReqCanRecRepository estatusReqCanRecRepository, EstatusReqCanRecMapper estatusReqCanRecMapper) {
        this.estatusReqCanRecRepository = estatusReqCanRecRepository;
        this.estatusReqCanRecMapper = estatusReqCanRecMapper;
    }

    /**
     * Save a estatusReqCanRec.
     *
     * @param estatusReqCanRecDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstatusReqCanRecDTO save(EstatusReqCanRecDTO estatusReqCanRecDTO) {
        log.debug("Request to save EstatusReqCanRec : {}", estatusReqCanRecDTO);
        EstatusReqCanRec estatusReqCanRec = estatusReqCanRecMapper.toEntity(estatusReqCanRecDTO);
        estatusReqCanRec = estatusReqCanRecRepository.save(estatusReqCanRec);
        return estatusReqCanRecMapper.toDto(estatusReqCanRec);
    }

    /**
     * Get all the estatusReqCanRecs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusReqCanRecDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusReqCanRecs");
        return estatusReqCanRecRepository.findAll(pageable)
            .map(estatusReqCanRecMapper::toDto);
    }


    /**
     * Get one estatusReqCanRec by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusReqCanRecDTO> findOne(Long id) {
        log.debug("Request to get EstatusReqCanRec : {}", id);
        return estatusReqCanRecRepository.findById(id)
            .map(estatusReqCanRecMapper::toDto);
    }

    /**
     * Delete the estatusReqCanRec by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusReqCanRec : {}", id);
        estatusReqCanRecRepository.deleteById(id);
    }
}
