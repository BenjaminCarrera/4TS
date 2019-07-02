package com.kode.ts.service.impl;

import com.kode.ts.service.BitacoraService;
import com.kode.ts.domain.Bitacora;
import com.kode.ts.repository.BitacoraRepository;
import com.kode.ts.service.dto.BitacoraDTO;
import com.kode.ts.service.mapper.BitacoraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bitacora}.
 */
@Service
@Transactional
public class BitacoraServiceImpl implements BitacoraService {

    private final Logger log = LoggerFactory.getLogger(BitacoraServiceImpl.class);

    private final BitacoraRepository bitacoraRepository;

    private final BitacoraMapper bitacoraMapper;

    public BitacoraServiceImpl(BitacoraRepository bitacoraRepository, BitacoraMapper bitacoraMapper) {
        this.bitacoraRepository = bitacoraRepository;
        this.bitacoraMapper = bitacoraMapper;
    }

    /**
     * Save a bitacora.
     *
     * @param bitacoraDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BitacoraDTO save(BitacoraDTO bitacoraDTO) {
        log.debug("Request to save Bitacora : {}", bitacoraDTO);
        Bitacora bitacora = bitacoraMapper.toEntity(bitacoraDTO);
        bitacora = bitacoraRepository.save(bitacora);
        return bitacoraMapper.toDto(bitacora);
    }

    /**
     * Get all the bitacoras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BitacoraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bitacoras");
        return bitacoraRepository.findAll(pageable)
            .map(bitacoraMapper::toDto);
    }


    /**
     * Get one bitacora by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BitacoraDTO> findOne(Long id) {
        log.debug("Request to get Bitacora : {}", id);
        return bitacoraRepository.findById(id)
            .map(bitacoraMapper::toDto);
    }

    /**
     * Delete the bitacora by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bitacora : {}", id);
        bitacoraRepository.deleteById(id);
    }
}
