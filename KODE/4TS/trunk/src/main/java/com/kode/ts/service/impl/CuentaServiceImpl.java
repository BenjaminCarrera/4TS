package com.kode.ts.service.impl;

import com.kode.ts.service.CuentaService;
import com.kode.ts.domain.Cuenta;
import com.kode.ts.repository.CuentaRepository;
import com.kode.ts.service.dto.CuentaDTO;
import com.kode.ts.service.mapper.CuentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cuenta}.
 */
@Service
@Transactional
public class CuentaServiceImpl implements CuentaService {

    private final Logger log = LoggerFactory.getLogger(CuentaServiceImpl.class);

    private final CuentaRepository cuentaRepository;

    private final CuentaMapper cuentaMapper;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, CuentaMapper cuentaMapper) {
        this.cuentaRepository = cuentaRepository;
        this.cuentaMapper = cuentaMapper;
    }

    /**
     * Save a cuenta.
     *
     * @param cuentaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CuentaDTO save(CuentaDTO cuentaDTO) {
        log.debug("Request to save Cuenta : {}", cuentaDTO);
        Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        cuenta = cuentaRepository.save(cuenta);
        return cuentaMapper.toDto(cuenta);
    }

    /**
     * Get all the cuentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CuentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cuentas");
        return cuentaRepository.findAll(pageable)
            .map(cuentaMapper::toDto);
    }


    /**
     * Get one cuenta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaDTO> findOne(Long id) {
        log.debug("Request to get Cuenta : {}", id);
        return cuentaRepository.findById(id)
            .map(cuentaMapper::toDto);
    }

    /**
     * Delete the cuenta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cuenta : {}", id);
        cuentaRepository.deleteById(id);
    }
}
