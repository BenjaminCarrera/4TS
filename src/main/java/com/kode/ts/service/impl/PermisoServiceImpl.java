package com.kode.ts.service.impl;

import com.kode.ts.service.PermisoService;
import com.kode.ts.domain.Permiso;
import com.kode.ts.repository.PermisoRepository;
import com.kode.ts.service.dto.PermisoDTO;
import com.kode.ts.service.mapper.PermisoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Permiso}.
 */
@Service
@Transactional
public class PermisoServiceImpl implements PermisoService {

    private final Logger log = LoggerFactory.getLogger(PermisoServiceImpl.class);

    private final PermisoRepository permisoRepository;

    private final PermisoMapper permisoMapper;

    public PermisoServiceImpl(PermisoRepository permisoRepository, PermisoMapper permisoMapper) {
        this.permisoRepository = permisoRepository;
        this.permisoMapper = permisoMapper;
    }

    /**
     * Save a permiso.
     *
     * @param permisoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PermisoDTO save(PermisoDTO permisoDTO) {
        log.debug("Request to save Permiso : {}", permisoDTO);
        Permiso permiso = permisoMapper.toEntity(permisoDTO);
        permiso = permisoRepository.save(permiso);
        return permisoMapper.toDto(permiso);
    }

    /**
     * Get all the permisos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PermisoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Permisos");
        return permisoRepository.findAll(pageable)
            .map(permisoMapper::toDto);
    }


    /**
     * Get one permiso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PermisoDTO> findOne(Long id) {
        log.debug("Request to get Permiso : {}", id);
        return permisoRepository.findById(id)
            .map(permisoMapper::toDto);
    }

    /**
     * Delete the permiso by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Permiso : {}", id);
        permisoRepository.deleteById(id);
    }
}
