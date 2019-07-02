package com.kode.ts.service.impl;

import com.kode.ts.service.EstatusAcademicoService;
import com.kode.ts.domain.EstatusAcademico;
import com.kode.ts.repository.EstatusAcademicoRepository;
import com.kode.ts.service.dto.EstatusAcademicoDTO;
import com.kode.ts.service.mapper.EstatusAcademicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstatusAcademico}.
 */
@Service
@Transactional
public class EstatusAcademicoServiceImpl implements EstatusAcademicoService {

    private final Logger log = LoggerFactory.getLogger(EstatusAcademicoServiceImpl.class);

    private final EstatusAcademicoRepository estatusAcademicoRepository;

    private final EstatusAcademicoMapper estatusAcademicoMapper;

    public EstatusAcademicoServiceImpl(EstatusAcademicoRepository estatusAcademicoRepository, EstatusAcademicoMapper estatusAcademicoMapper) {
        this.estatusAcademicoRepository = estatusAcademicoRepository;
        this.estatusAcademicoMapper = estatusAcademicoMapper;
    }

    /**
     * Save a estatusAcademico.
     *
     * @param estatusAcademicoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstatusAcademicoDTO save(EstatusAcademicoDTO estatusAcademicoDTO) {
        log.debug("Request to save EstatusAcademico : {}", estatusAcademicoDTO);
        EstatusAcademico estatusAcademico = estatusAcademicoMapper.toEntity(estatusAcademicoDTO);
        estatusAcademico = estatusAcademicoRepository.save(estatusAcademico);
        return estatusAcademicoMapper.toDto(estatusAcademico);
    }

    /**
     * Get all the estatusAcademicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusAcademicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusAcademicos");
        return estatusAcademicoRepository.findAll(pageable)
            .map(estatusAcademicoMapper::toDto);
    }


    /**
     * Get one estatusAcademico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusAcademicoDTO> findOne(Long id) {
        log.debug("Request to get EstatusAcademico : {}", id);
        return estatusAcademicoRepository.findById(id)
            .map(estatusAcademicoMapper::toDto);
    }

    /**
     * Delete the estatusAcademico by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusAcademico : {}", id);
        estatusAcademicoRepository.deleteById(id);
    }
}
