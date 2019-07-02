package com.kode.ts.service.impl;

import com.kode.ts.service.PerfilService;
import com.kode.ts.domain.Perfil;
import com.kode.ts.repository.PerfilRepository;
import com.kode.ts.service.dto.PerfilDTO;
import com.kode.ts.service.mapper.PerfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Perfil}.
 */
@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private final Logger log = LoggerFactory.getLogger(PerfilServiceImpl.class);

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    public PerfilServiceImpl(PerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    /**
     * Save a perfil.
     *
     * @param perfilDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PerfilDTO save(PerfilDTO perfilDTO) {
        log.debug("Request to save Perfil : {}", perfilDTO);
        Perfil perfil = perfilMapper.toEntity(perfilDTO);
        perfil = perfilRepository.save(perfil);
        return perfilMapper.toDto(perfil);
    }

    /**
     * Get all the perfils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PerfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Perfils");
        return perfilRepository.findAll(pageable)
            .map(perfilMapper::toDto);
    }


    /**
     * Get one perfil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PerfilDTO> findOne(Long id) {
        log.debug("Request to get Perfil : {}", id);
        return perfilRepository.findById(id)
            .map(perfilMapper::toDto);
    }

    /**
     * Delete the perfil by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Perfil : {}", id);
        perfilRepository.deleteById(id);
    }
}
