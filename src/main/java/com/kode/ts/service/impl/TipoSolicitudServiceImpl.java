package com.kode.ts.service.impl;

import com.kode.ts.service.TipoSolicitudService;
import com.kode.ts.domain.TipoSolicitud;
import com.kode.ts.repository.TipoSolicitudRepository;
import com.kode.ts.service.dto.TipoSolicitudDTO;
import com.kode.ts.service.mapper.TipoSolicitudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoSolicitud}.
 */
@Service
@Transactional
public class TipoSolicitudServiceImpl implements TipoSolicitudService {

    private final Logger log = LoggerFactory.getLogger(TipoSolicitudServiceImpl.class);

    private final TipoSolicitudRepository tipoSolicitudRepository;

    private final TipoSolicitudMapper tipoSolicitudMapper;

    public TipoSolicitudServiceImpl(TipoSolicitudRepository tipoSolicitudRepository, TipoSolicitudMapper tipoSolicitudMapper) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
        this.tipoSolicitudMapper = tipoSolicitudMapper;
    }

    /**
     * Save a tipoSolicitud.
     *
     * @param tipoSolicitudDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoSolicitudDTO save(TipoSolicitudDTO tipoSolicitudDTO) {
        log.debug("Request to save TipoSolicitud : {}", tipoSolicitudDTO);
        TipoSolicitud tipoSolicitud = tipoSolicitudMapper.toEntity(tipoSolicitudDTO);
        tipoSolicitud = tipoSolicitudRepository.save(tipoSolicitud);
        return tipoSolicitudMapper.toDto(tipoSolicitud);
    }

    /**
     * Get all the tipoSolicituds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoSolicitudDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoSolicituds");
        return tipoSolicitudRepository.findAll(pageable)
            .map(tipoSolicitudMapper::toDto);
    }


    /**
     * Get one tipoSolicitud by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoSolicitudDTO> findOne(Long id) {
        log.debug("Request to get TipoSolicitud : {}", id);
        return tipoSolicitudRepository.findById(id)
            .map(tipoSolicitudMapper::toDto);
    }

    /**
     * Delete the tipoSolicitud by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoSolicitud : {}", id);
        tipoSolicitudRepository.deleteById(id);
    }
}
