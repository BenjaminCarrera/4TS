package com.kode.ts.service.impl;

import com.kode.ts.service.FuenteReclutamientoService;
import com.kode.ts.domain.FuenteReclutamiento;
import com.kode.ts.repository.FuenteReclutamientoRepository;
import com.kode.ts.service.dto.FuenteReclutamientoDTO;
import com.kode.ts.service.mapper.FuenteReclutamientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FuenteReclutamiento}.
 */
@Service
@Transactional
public class FuenteReclutamientoServiceImpl implements FuenteReclutamientoService {

    private final Logger log = LoggerFactory.getLogger(FuenteReclutamientoServiceImpl.class);

    private final FuenteReclutamientoRepository fuenteReclutamientoRepository;

    private final FuenteReclutamientoMapper fuenteReclutamientoMapper;

    public FuenteReclutamientoServiceImpl(FuenteReclutamientoRepository fuenteReclutamientoRepository, FuenteReclutamientoMapper fuenteReclutamientoMapper) {
        this.fuenteReclutamientoRepository = fuenteReclutamientoRepository;
        this.fuenteReclutamientoMapper = fuenteReclutamientoMapper;
    }

    /**
     * Save a fuenteReclutamiento.
     *
     * @param fuenteReclutamientoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FuenteReclutamientoDTO save(FuenteReclutamientoDTO fuenteReclutamientoDTO) {
        log.debug("Request to save FuenteReclutamiento : {}", fuenteReclutamientoDTO);
        FuenteReclutamiento fuenteReclutamiento = fuenteReclutamientoMapper.toEntity(fuenteReclutamientoDTO);
        fuenteReclutamiento = fuenteReclutamientoRepository.save(fuenteReclutamiento);
        return fuenteReclutamientoMapper.toDto(fuenteReclutamiento);
    }

    /**
     * Get all the fuenteReclutamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FuenteReclutamientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FuenteReclutamientos");
        return fuenteReclutamientoRepository.findAll(pageable)
            .map(fuenteReclutamientoMapper::toDto);
    }


    /**
     * Get one fuenteReclutamiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FuenteReclutamientoDTO> findOne(Long id) {
        log.debug("Request to get FuenteReclutamiento : {}", id);
        return fuenteReclutamientoRepository.findById(id)
            .map(fuenteReclutamientoMapper::toDto);
    }

    /**
     * Delete the fuenteReclutamiento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FuenteReclutamiento : {}", id);
        fuenteReclutamientoRepository.deleteById(id);
    }
}
