package com.kode.ts.service.impl;

import com.kode.ts.service.TipoSkillService;
import com.kode.ts.domain.TipoSkill;
import com.kode.ts.repository.TipoSkillRepository;
import com.kode.ts.service.dto.TipoSkillDTO;
import com.kode.ts.service.mapper.TipoSkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoSkill}.
 */
@Service
@Transactional
public class TipoSkillServiceImpl implements TipoSkillService {

    private final Logger log = LoggerFactory.getLogger(TipoSkillServiceImpl.class);

    private final TipoSkillRepository tipoSkillRepository;

    private final TipoSkillMapper tipoSkillMapper;

    public TipoSkillServiceImpl(TipoSkillRepository tipoSkillRepository, TipoSkillMapper tipoSkillMapper) {
        this.tipoSkillRepository = tipoSkillRepository;
        this.tipoSkillMapper = tipoSkillMapper;
    }

    /**
     * Save a tipoSkill.
     *
     * @param tipoSkillDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoSkillDTO save(TipoSkillDTO tipoSkillDTO) {
        log.debug("Request to save TipoSkill : {}", tipoSkillDTO);
        TipoSkill tipoSkill = tipoSkillMapper.toEntity(tipoSkillDTO);
        tipoSkill = tipoSkillRepository.save(tipoSkill);
        return tipoSkillMapper.toDto(tipoSkill);
    }

    /**
     * Get all the tipoSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoSkills");
        return tipoSkillRepository.findAll(pageable)
            .map(tipoSkillMapper::toDto);
    }


    /**
     * Get one tipoSkill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoSkillDTO> findOne(Long id) {
        log.debug("Request to get TipoSkill : {}", id);
        return tipoSkillRepository.findById(id)
            .map(tipoSkillMapper::toDto);
    }

    /**
     * Delete the tipoSkill by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoSkill : {}", id);
        tipoSkillRepository.deleteById(id);
    }
}
