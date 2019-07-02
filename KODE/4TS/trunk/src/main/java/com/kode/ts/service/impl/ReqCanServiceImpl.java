package com.kode.ts.service.impl;

import com.kode.ts.service.ReqCanService;
import com.kode.ts.domain.ReqCan;
import com.kode.ts.repository.ReqCanRepository;
import com.kode.ts.service.dto.ReqCanDTO;
import com.kode.ts.service.mapper.ReqCanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReqCan}.
 */
@Service
@Transactional
public class ReqCanServiceImpl implements ReqCanService {

    private final Logger log = LoggerFactory.getLogger(ReqCanServiceImpl.class);

    private final ReqCanRepository reqCanRepository;

    private final ReqCanMapper reqCanMapper;

    public ReqCanServiceImpl(ReqCanRepository reqCanRepository, ReqCanMapper reqCanMapper) {
        this.reqCanRepository = reqCanRepository;
        this.reqCanMapper = reqCanMapper;
    }

    /**
     * Save a reqCan.
     *
     * @param reqCanDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReqCanDTO save(ReqCanDTO reqCanDTO) {
        log.debug("Request to save ReqCan : {}", reqCanDTO);
        ReqCan reqCan = reqCanMapper.toEntity(reqCanDTO);
        reqCan = reqCanRepository.save(reqCan);
        return reqCanMapper.toDto(reqCan);
    }

    /**
     * Get all the reqCans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReqCanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReqCans");
        return reqCanRepository.findAll(pageable)
            .map(reqCanMapper::toDto);
    }


    /**
     * Get one reqCan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReqCanDTO> findOne(Long id) {
        log.debug("Request to get ReqCan : {}", id);
        return reqCanRepository.findById(id)
            .map(reqCanMapper::toDto);
    }

    /**
     * Delete the reqCan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReqCan : {}", id);
        reqCanRepository.deleteById(id);
    }
}
