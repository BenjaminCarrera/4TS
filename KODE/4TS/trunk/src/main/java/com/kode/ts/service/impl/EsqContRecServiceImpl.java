package com.kode.ts.service.impl;

import com.kode.ts.service.EsqContRecService;
import com.kode.ts.domain.EsqContRec;
import com.kode.ts.repository.EsqContRecRepository;
import com.kode.ts.service.dto.EsqContRecDTO;
import com.kode.ts.service.mapper.EsqContRecMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EsqContRec}.
 */
@Service
@Transactional
public class EsqContRecServiceImpl implements EsqContRecService {

    private final Logger log = LoggerFactory.getLogger(EsqContRecServiceImpl.class);

    private final EsqContRecRepository esqContRecRepository;

    private final EsqContRecMapper esqContRecMapper;

    public EsqContRecServiceImpl(EsqContRecRepository esqContRecRepository, EsqContRecMapper esqContRecMapper) {
        this.esqContRecRepository = esqContRecRepository;
        this.esqContRecMapper = esqContRecMapper;
    }

    /**
     * Save a esqContRec.
     *
     * @param esqContRecDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EsqContRecDTO save(EsqContRecDTO esqContRecDTO) {
        log.debug("Request to save EsqContRec : {}", esqContRecDTO);
        EsqContRec esqContRec = esqContRecMapper.toEntity(esqContRecDTO);
        esqContRec = esqContRecRepository.save(esqContRec);
        return esqContRecMapper.toDto(esqContRec);
    }

    /**
     * Get all the esqContRecs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EsqContRecDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EsqContRecs");
        return esqContRecRepository.findAll(pageable)
            .map(esqContRecMapper::toDto);
    }


    /**
     * Get one esqContRec by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EsqContRecDTO> findOne(Long id) {
        log.debug("Request to get EsqContRec : {}", id);
        return esqContRecRepository.findById(id)
            .map(esqContRecMapper::toDto);
    }

    /**
     * Delete the esqContRec by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EsqContRec : {}", id);
        esqContRecRepository.deleteById(id);
    }
}
