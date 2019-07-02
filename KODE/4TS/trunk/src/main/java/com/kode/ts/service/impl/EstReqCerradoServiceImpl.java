package com.kode.ts.service.impl;

import com.kode.ts.service.EstReqCerradoService;
import com.kode.ts.domain.EstReqCerrado;
import com.kode.ts.repository.EstReqCerradoRepository;
import com.kode.ts.service.dto.EstReqCerradoDTO;
import com.kode.ts.service.mapper.EstReqCerradoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstReqCerrado}.
 */
@Service
@Transactional
public class EstReqCerradoServiceImpl implements EstReqCerradoService {

    private final Logger log = LoggerFactory.getLogger(EstReqCerradoServiceImpl.class);

    private final EstReqCerradoRepository estReqCerradoRepository;

    private final EstReqCerradoMapper estReqCerradoMapper;

    public EstReqCerradoServiceImpl(EstReqCerradoRepository estReqCerradoRepository, EstReqCerradoMapper estReqCerradoMapper) {
        this.estReqCerradoRepository = estReqCerradoRepository;
        this.estReqCerradoMapper = estReqCerradoMapper;
    }

    /**
     * Save a estReqCerrado.
     *
     * @param estReqCerradoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstReqCerradoDTO save(EstReqCerradoDTO estReqCerradoDTO) {
        log.debug("Request to save EstReqCerrado : {}", estReqCerradoDTO);
        EstReqCerrado estReqCerrado = estReqCerradoMapper.toEntity(estReqCerradoDTO);
        estReqCerrado = estReqCerradoRepository.save(estReqCerrado);
        return estReqCerradoMapper.toDto(estReqCerrado);
    }

    /**
     * Get all the estReqCerrados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstReqCerradoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstReqCerrados");
        return estReqCerradoRepository.findAll(pageable)
            .map(estReqCerradoMapper::toDto);
    }


    /**
     * Get one estReqCerrado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstReqCerradoDTO> findOne(Long id) {
        log.debug("Request to get EstReqCerrado : {}", id);
        return estReqCerradoRepository.findById(id)
            .map(estReqCerradoMapper::toDto);
    }

    /**
     * Delete the estReqCerrado by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstReqCerrado : {}", id);
        estReqCerradoRepository.deleteById(id);
    }
}
