package com.kode.ts.service.impl;

import com.kode.ts.service.EstatusCandidatoService;
import com.kode.ts.domain.EstatusCandidato;
import com.kode.ts.repository.EstatusCandidatoRepository;
import com.kode.ts.service.dto.EstatusCandidatoDTO;
import com.kode.ts.service.mapper.EstatusCandidatoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstatusCandidato}.
 */
@Service
@Transactional
public class EstatusCandidatoServiceImpl implements EstatusCandidatoService {

    private final Logger log = LoggerFactory.getLogger(EstatusCandidatoServiceImpl.class);

    private final EstatusCandidatoRepository estatusCandidatoRepository;

    private final EstatusCandidatoMapper estatusCandidatoMapper;

    public EstatusCandidatoServiceImpl(EstatusCandidatoRepository estatusCandidatoRepository, EstatusCandidatoMapper estatusCandidatoMapper) {
        this.estatusCandidatoRepository = estatusCandidatoRepository;
        this.estatusCandidatoMapper = estatusCandidatoMapper;
    }

    /**
     * Save a estatusCandidato.
     *
     * @param estatusCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstatusCandidatoDTO save(EstatusCandidatoDTO estatusCandidatoDTO) {
        log.debug("Request to save EstatusCandidato : {}", estatusCandidatoDTO);
        EstatusCandidato estatusCandidato = estatusCandidatoMapper.toEntity(estatusCandidatoDTO);
        estatusCandidato = estatusCandidatoRepository.save(estatusCandidato);
        return estatusCandidatoMapper.toDto(estatusCandidato);
    }

    /**
     * Get all the estatusCandidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusCandidatoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusCandidatoes");
        return estatusCandidatoRepository.findAll(pageable)
            .map(estatusCandidatoMapper::toDto);
    }


    /**
     * Get one estatusCandidato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusCandidatoDTO> findOne(Long id) {
        log.debug("Request to get EstatusCandidato : {}", id);
        return estatusCandidatoRepository.findById(id)
            .map(estatusCandidatoMapper::toDto);
    }

    /**
     * Delete the estatusCandidato by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusCandidato : {}", id);
        estatusCandidatoRepository.deleteById(id);
    }
}
