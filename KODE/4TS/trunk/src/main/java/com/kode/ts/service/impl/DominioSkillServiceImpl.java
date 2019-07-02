package com.kode.ts.service.impl;

import com.kode.ts.service.DominioSkillService;
import com.kode.ts.domain.DominioSkill;
import com.kode.ts.repository.DominioSkillRepository;
import com.kode.ts.service.dto.DominioSkillDTO;
import com.kode.ts.service.mapper.DominioSkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DominioSkill}.
 */
@Service
@Transactional
public class DominioSkillServiceImpl implements DominioSkillService {

    private final Logger log = LoggerFactory.getLogger(DominioSkillServiceImpl.class);

    private final DominioSkillRepository dominioSkillRepository;

    private final DominioSkillMapper dominioSkillMapper;

    public DominioSkillServiceImpl(DominioSkillRepository dominioSkillRepository, DominioSkillMapper dominioSkillMapper) {
        this.dominioSkillRepository = dominioSkillRepository;
        this.dominioSkillMapper = dominioSkillMapper;
    }

    /**
     * Save a dominioSkill.
     *
     * @param dominioSkillDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DominioSkillDTO save(DominioSkillDTO dominioSkillDTO) {
        log.debug("Request to save DominioSkill : {}", dominioSkillDTO);
        DominioSkill dominioSkill = dominioSkillMapper.toEntity(dominioSkillDTO);
        dominioSkill = dominioSkillRepository.save(dominioSkill);
        return dominioSkillMapper.toDto(dominioSkill);
    }

    /**
     * Get all the dominioSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DominioSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DominioSkills");
        return dominioSkillRepository.findAll(pageable)
            .map(dominioSkillMapper::toDto);
    }


    /**
     * Get one dominioSkill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DominioSkillDTO> findOne(Long id) {
        log.debug("Request to get DominioSkill : {}", id);
        return dominioSkillRepository.findById(id)
            .map(dominioSkillMapper::toDto);
    }

    /**
     * Delete the dominioSkill by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DominioSkill : {}", id);
        dominioSkillRepository.deleteById(id);
    }
}
