package com.kode.ts.service.impl;

import com.kode.ts.service.FormacionAcademicaService;
import com.kode.ts.domain.FormacionAcademica;
import com.kode.ts.repository.FormacionAcademicaRepository;
import com.kode.ts.service.dto.FormacionAcademicaDTO;
import com.kode.ts.service.mapper.FormacionAcademicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FormacionAcademica}.
 */
@Service
@Transactional
public class FormacionAcademicaServiceImpl implements FormacionAcademicaService {

    private final Logger log = LoggerFactory.getLogger(FormacionAcademicaServiceImpl.class);

    private final FormacionAcademicaRepository formacionAcademicaRepository;

    private final FormacionAcademicaMapper formacionAcademicaMapper;

    public FormacionAcademicaServiceImpl(FormacionAcademicaRepository formacionAcademicaRepository, FormacionAcademicaMapper formacionAcademicaMapper) {
        this.formacionAcademicaRepository = formacionAcademicaRepository;
        this.formacionAcademicaMapper = formacionAcademicaMapper;
    }

    /**
     * Save a formacionAcademica.
     *
     * @param formacionAcademicaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FormacionAcademicaDTO save(FormacionAcademicaDTO formacionAcademicaDTO) {
        log.debug("Request to save FormacionAcademica : {}", formacionAcademicaDTO);
        FormacionAcademica formacionAcademica = formacionAcademicaMapper.toEntity(formacionAcademicaDTO);
        formacionAcademica = formacionAcademicaRepository.save(formacionAcademica);
        return formacionAcademicaMapper.toDto(formacionAcademica);
    }

    /**
     * Get all the formacionAcademicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormacionAcademicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormacionAcademicas");
        return formacionAcademicaRepository.findAll(pageable)
            .map(formacionAcademicaMapper::toDto);
    }


    /**
     * Get one formacionAcademica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FormacionAcademicaDTO> findOne(Long id) {
        log.debug("Request to get FormacionAcademica : {}", id);
        return formacionAcademicaRepository.findById(id)
            .map(formacionAcademicaMapper::toDto);
    }

    /**
     * Delete the formacionAcademica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormacionAcademica : {}", id);
        formacionAcademicaRepository.deleteById(id);
    }
}
