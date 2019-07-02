package com.kode.ts.service.impl;

import com.kode.ts.service.NivelPerfilService;
import com.kode.ts.domain.NivelPerfil;
import com.kode.ts.repository.NivelPerfilRepository;
import com.kode.ts.service.dto.NivelPerfilDTO;
import com.kode.ts.service.mapper.NivelPerfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NivelPerfil}.
 */
@Service
@Transactional
public class NivelPerfilServiceImpl implements NivelPerfilService {

    private final Logger log = LoggerFactory.getLogger(NivelPerfilServiceImpl.class);

    private final NivelPerfilRepository nivelPerfilRepository;

    private final NivelPerfilMapper nivelPerfilMapper;

    public NivelPerfilServiceImpl(NivelPerfilRepository nivelPerfilRepository, NivelPerfilMapper nivelPerfilMapper) {
        this.nivelPerfilRepository = nivelPerfilRepository;
        this.nivelPerfilMapper = nivelPerfilMapper;
    }

    /**
     * Save a nivelPerfil.
     *
     * @param nivelPerfilDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NivelPerfilDTO save(NivelPerfilDTO nivelPerfilDTO) {
        log.debug("Request to save NivelPerfil : {}", nivelPerfilDTO);
        NivelPerfil nivelPerfil = nivelPerfilMapper.toEntity(nivelPerfilDTO);
        nivelPerfil = nivelPerfilRepository.save(nivelPerfil);
        return nivelPerfilMapper.toDto(nivelPerfil);
    }

    /**
     * Get all the nivelPerfils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NivelPerfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NivelPerfils");
        return nivelPerfilRepository.findAll(pageable)
            .map(nivelPerfilMapper::toDto);
    }


    /**
     * Get one nivelPerfil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NivelPerfilDTO> findOne(Long id) {
        log.debug("Request to get NivelPerfil : {}", id);
        return nivelPerfilRepository.findById(id)
            .map(nivelPerfilMapper::toDto);
    }

    /**
     * Delete the nivelPerfil by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NivelPerfil : {}", id);
        nivelPerfilRepository.deleteById(id);
    }
}
