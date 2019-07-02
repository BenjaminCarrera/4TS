package com.kode.ts.service.impl;

import com.kode.ts.service.EstCanInactivoService;
import com.kode.ts.domain.EstCanInactivo;
import com.kode.ts.repository.EstCanInactivoRepository;
import com.kode.ts.service.dto.EstCanInactivoDTO;
import com.kode.ts.service.mapper.EstCanInactivoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstCanInactivo}.
 */
@Service
@Transactional
public class EstCanInactivoServiceImpl implements EstCanInactivoService {

    private final Logger log = LoggerFactory.getLogger(EstCanInactivoServiceImpl.class);

    private final EstCanInactivoRepository estCanInactivoRepository;

    private final EstCanInactivoMapper estCanInactivoMapper;

    public EstCanInactivoServiceImpl(EstCanInactivoRepository estCanInactivoRepository, EstCanInactivoMapper estCanInactivoMapper) {
        this.estCanInactivoRepository = estCanInactivoRepository;
        this.estCanInactivoMapper = estCanInactivoMapper;
    }

    /**
     * Save a estCanInactivo.
     *
     * @param estCanInactivoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstCanInactivoDTO save(EstCanInactivoDTO estCanInactivoDTO) {
        log.debug("Request to save EstCanInactivo : {}", estCanInactivoDTO);
        EstCanInactivo estCanInactivo = estCanInactivoMapper.toEntity(estCanInactivoDTO);
        estCanInactivo = estCanInactivoRepository.save(estCanInactivo);
        return estCanInactivoMapper.toDto(estCanInactivo);
    }

    /**
     * Get all the estCanInactivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstCanInactivoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstCanInactivos");
        return estCanInactivoRepository.findAll(pageable)
            .map(estCanInactivoMapper::toDto);
    }


    /**
     * Get one estCanInactivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstCanInactivoDTO> findOne(Long id) {
        log.debug("Request to get EstCanInactivo : {}", id);
        return estCanInactivoRepository.findById(id)
            .map(estCanInactivoMapper::toDto);
    }

    /**
     * Delete the estCanInactivo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstCanInactivo : {}", id);
        estCanInactivoRepository.deleteById(id);
    }
}
