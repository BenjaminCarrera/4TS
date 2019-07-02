package com.kode.ts.service.impl;

import com.kode.ts.service.InstitucionAcademicaService;
import com.kode.ts.domain.InstitucionAcademica;
import com.kode.ts.repository.InstitucionAcademicaRepository;
import com.kode.ts.service.dto.InstitucionAcademicaDTO;
import com.kode.ts.service.mapper.InstitucionAcademicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InstitucionAcademica}.
 */
@Service
@Transactional
public class InstitucionAcademicaServiceImpl implements InstitucionAcademicaService {

    private final Logger log = LoggerFactory.getLogger(InstitucionAcademicaServiceImpl.class);

    private final InstitucionAcademicaRepository institucionAcademicaRepository;

    private final InstitucionAcademicaMapper institucionAcademicaMapper;

    public InstitucionAcademicaServiceImpl(InstitucionAcademicaRepository institucionAcademicaRepository, InstitucionAcademicaMapper institucionAcademicaMapper) {
        this.institucionAcademicaRepository = institucionAcademicaRepository;
        this.institucionAcademicaMapper = institucionAcademicaMapper;
    }

    /**
     * Save a institucionAcademica.
     *
     * @param institucionAcademicaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InstitucionAcademicaDTO save(InstitucionAcademicaDTO institucionAcademicaDTO) {
        log.debug("Request to save InstitucionAcademica : {}", institucionAcademicaDTO);
        InstitucionAcademica institucionAcademica = institucionAcademicaMapper.toEntity(institucionAcademicaDTO);
        institucionAcademica = institucionAcademicaRepository.save(institucionAcademica);
        return institucionAcademicaMapper.toDto(institucionAcademica);
    }

    /**
     * Get all the institucionAcademicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InstitucionAcademicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InstitucionAcademicas");
        return institucionAcademicaRepository.findAll(pageable)
            .map(institucionAcademicaMapper::toDto);
    }


    /**
     * Get one institucionAcademica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InstitucionAcademicaDTO> findOne(Long id) {
        log.debug("Request to get InstitucionAcademica : {}", id);
        return institucionAcademicaRepository.findById(id)
            .map(institucionAcademicaMapper::toDto);
    }

    /**
     * Delete the institucionAcademica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InstitucionAcademica : {}", id);
        institucionAcademicaRepository.deleteById(id);
    }
}
