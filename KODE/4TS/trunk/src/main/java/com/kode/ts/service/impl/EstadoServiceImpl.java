package com.kode.ts.service.impl;

import com.kode.ts.service.EstadoService;
import com.kode.ts.domain.Estado;
import com.kode.ts.repository.EstadoRepository;
import com.kode.ts.service.dto.EstadoDTO;
import com.kode.ts.service.mapper.EstadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Estado}.
 */
@Service
@Transactional
public class EstadoServiceImpl implements EstadoService {

    private final Logger log = LoggerFactory.getLogger(EstadoServiceImpl.class);

    private final EstadoRepository estadoRepository;

    private final EstadoMapper estadoMapper;

    public EstadoServiceImpl(EstadoRepository estadoRepository, EstadoMapper estadoMapper) {
        this.estadoRepository = estadoRepository;
        this.estadoMapper = estadoMapper;
    }

    /**
     * Save a estado.
     *
     * @param estadoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstadoDTO save(EstadoDTO estadoDTO) {
        log.debug("Request to save Estado : {}", estadoDTO);
        Estado estado = estadoMapper.toEntity(estadoDTO);
        estado = estadoRepository.save(estado);
        return estadoMapper.toDto(estado);
    }

    /**
     * Get all the estados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Estados");
        return estadoRepository.findAll(pageable)
            .map(estadoMapper::toDto);
    }


    /**
     * Get one estado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoDTO> findOne(Long id) {
        log.debug("Request to get Estado : {}", id);
        return estadoRepository.findById(id)
            .map(estadoMapper::toDto);
    }

    /**
     * Delete the estado by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estado : {}", id);
        estadoRepository.deleteById(id);
    }
}
