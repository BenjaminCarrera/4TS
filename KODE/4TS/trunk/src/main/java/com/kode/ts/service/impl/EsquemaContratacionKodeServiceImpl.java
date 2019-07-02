package com.kode.ts.service.impl;

import com.kode.ts.service.EsquemaContratacionKodeService;
import com.kode.ts.domain.EsquemaContratacionKode;
import com.kode.ts.repository.EsquemaContratacionKodeRepository;
import com.kode.ts.service.dto.EsquemaContratacionKodeDTO;
import com.kode.ts.service.mapper.EsquemaContratacionKodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EsquemaContratacionKode}.
 */
@Service
@Transactional
public class EsquemaContratacionKodeServiceImpl implements EsquemaContratacionKodeService {

    private final Logger log = LoggerFactory.getLogger(EsquemaContratacionKodeServiceImpl.class);

    private final EsquemaContratacionKodeRepository esquemaContratacionKodeRepository;

    private final EsquemaContratacionKodeMapper esquemaContratacionKodeMapper;

    public EsquemaContratacionKodeServiceImpl(EsquemaContratacionKodeRepository esquemaContratacionKodeRepository, EsquemaContratacionKodeMapper esquemaContratacionKodeMapper) {
        this.esquemaContratacionKodeRepository = esquemaContratacionKodeRepository;
        this.esquemaContratacionKodeMapper = esquemaContratacionKodeMapper;
    }

    /**
     * Save a esquemaContratacionKode.
     *
     * @param esquemaContratacionKodeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EsquemaContratacionKodeDTO save(EsquemaContratacionKodeDTO esquemaContratacionKodeDTO) {
        log.debug("Request to save EsquemaContratacionKode : {}", esquemaContratacionKodeDTO);
        EsquemaContratacionKode esquemaContratacionKode = esquemaContratacionKodeMapper.toEntity(esquemaContratacionKodeDTO);
        esquemaContratacionKode = esquemaContratacionKodeRepository.save(esquemaContratacionKode);
        return esquemaContratacionKodeMapper.toDto(esquemaContratacionKode);
    }

    /**
     * Get all the esquemaContratacionKodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EsquemaContratacionKodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EsquemaContratacionKodes");
        return esquemaContratacionKodeRepository.findAll(pageable)
            .map(esquemaContratacionKodeMapper::toDto);
    }


    /**
     * Get one esquemaContratacionKode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EsquemaContratacionKodeDTO> findOne(Long id) {
        log.debug("Request to get EsquemaContratacionKode : {}", id);
        return esquemaContratacionKodeRepository.findById(id)
            .map(esquemaContratacionKodeMapper::toDto);
    }

    /**
     * Delete the esquemaContratacionKode by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EsquemaContratacionKode : {}", id);
        esquemaContratacionKodeRepository.deleteById(id);
    }
}
