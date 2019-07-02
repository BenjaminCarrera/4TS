package com.kode.ts.service.impl;

import com.kode.ts.service.BaseTarifaService;
import com.kode.ts.domain.BaseTarifa;
import com.kode.ts.repository.BaseTarifaRepository;
import com.kode.ts.service.dto.BaseTarifaDTO;
import com.kode.ts.service.mapper.BaseTarifaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BaseTarifa}.
 */
@Service
@Transactional
public class BaseTarifaServiceImpl implements BaseTarifaService {

    private final Logger log = LoggerFactory.getLogger(BaseTarifaServiceImpl.class);

    private final BaseTarifaRepository baseTarifaRepository;

    private final BaseTarifaMapper baseTarifaMapper;

    public BaseTarifaServiceImpl(BaseTarifaRepository baseTarifaRepository, BaseTarifaMapper baseTarifaMapper) {
        this.baseTarifaRepository = baseTarifaRepository;
        this.baseTarifaMapper = baseTarifaMapper;
    }

    /**
     * Save a baseTarifa.
     *
     * @param baseTarifaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BaseTarifaDTO save(BaseTarifaDTO baseTarifaDTO) {
        log.debug("Request to save BaseTarifa : {}", baseTarifaDTO);
        BaseTarifa baseTarifa = baseTarifaMapper.toEntity(baseTarifaDTO);
        baseTarifa = baseTarifaRepository.save(baseTarifa);
        return baseTarifaMapper.toDto(baseTarifa);
    }

    /**
     * Get all the baseTarifas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BaseTarifaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BaseTarifas");
        return baseTarifaRepository.findAll(pageable)
            .map(baseTarifaMapper::toDto);
    }


    /**
     * Get one baseTarifa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BaseTarifaDTO> findOne(Long id) {
        log.debug("Request to get BaseTarifa : {}", id);
        return baseTarifaRepository.findById(id)
            .map(baseTarifaMapper::toDto);
    }

    /**
     * Delete the baseTarifa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BaseTarifa : {}", id);
        baseTarifaRepository.deleteById(id);
    }
}
