package com.kode.ts.service.impl;

import com.kode.ts.service.PermisoAuthorityService;
import com.kode.ts.domain.PermisoAuthority;
import com.kode.ts.repository.PermisoAuthorityRepository;
import com.kode.ts.service.dto.PermisoAuthorityDTO;
import com.kode.ts.service.mapper.PermisoAuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PermisoAuthority}.
 */
@Service
@Transactional
public class PermisoAuthorityServiceImpl implements PermisoAuthorityService {

    private final Logger log = LoggerFactory.getLogger(PermisoAuthorityServiceImpl.class);

    private final PermisoAuthorityRepository permisoAuthorityRepository;

    private final PermisoAuthorityMapper permisoAuthorityMapper;

    public PermisoAuthorityServiceImpl(PermisoAuthorityRepository permisoAuthorityRepository, PermisoAuthorityMapper permisoAuthorityMapper) {
        this.permisoAuthorityRepository = permisoAuthorityRepository;
        this.permisoAuthorityMapper = permisoAuthorityMapper;
    }

    /**
     * Save a permisoAuthority.
     *
     * @param permisoAuthorityDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PermisoAuthorityDTO save(PermisoAuthorityDTO permisoAuthorityDTO) {
        log.debug("Request to save PermisoAuthority : {}", permisoAuthorityDTO);
        PermisoAuthority permisoAuthority = permisoAuthorityMapper.toEntity(permisoAuthorityDTO);
        permisoAuthority = permisoAuthorityRepository.save(permisoAuthority);
        return permisoAuthorityMapper.toDto(permisoAuthority);
    }

    /**
     * Get all the permisoAuthorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PermisoAuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PermisoAuthorities");
        return permisoAuthorityRepository.findAll(pageable)
            .map(permisoAuthorityMapper::toDto);
    }


    /**
     * Get one permisoAuthority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PermisoAuthorityDTO> findOne(Long id) {
        log.debug("Request to get PermisoAuthority : {}", id);
        return permisoAuthorityRepository.findById(id)
            .map(permisoAuthorityMapper::toDto);
    }

    /**
     * Delete the permisoAuthority by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PermisoAuthority : {}", id);
        permisoAuthorityRepository.deleteById(id);
    }
}
