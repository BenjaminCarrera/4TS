package com.kode.ts.service.impl;

import com.kode.ts.service.CategoriaSkillService;
import com.kode.ts.domain.CategoriaSkill;
import com.kode.ts.repository.CategoriaSkillRepository;
import com.kode.ts.service.dto.CategoriaSkillDTO;
import com.kode.ts.service.mapper.CategoriaSkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoriaSkill}.
 */
@Service
@Transactional
public class CategoriaSkillServiceImpl implements CategoriaSkillService {

    private final Logger log = LoggerFactory.getLogger(CategoriaSkillServiceImpl.class);

    private final CategoriaSkillRepository categoriaSkillRepository;

    private final CategoriaSkillMapper categoriaSkillMapper;

    public CategoriaSkillServiceImpl(CategoriaSkillRepository categoriaSkillRepository, CategoriaSkillMapper categoriaSkillMapper) {
        this.categoriaSkillRepository = categoriaSkillRepository;
        this.categoriaSkillMapper = categoriaSkillMapper;
    }

    /**
     * Save a categoriaSkill.
     *
     * @param categoriaSkillDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CategoriaSkillDTO save(CategoriaSkillDTO categoriaSkillDTO) {
        log.debug("Request to save CategoriaSkill : {}", categoriaSkillDTO);
        CategoriaSkill categoriaSkill = categoriaSkillMapper.toEntity(categoriaSkillDTO);
        categoriaSkill = categoriaSkillRepository.save(categoriaSkill);
        return categoriaSkillMapper.toDto(categoriaSkill);
    }

    /**
     * Get all the categoriaSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaSkills");
        return categoriaSkillRepository.findAll(pageable)
            .map(categoriaSkillMapper::toDto);
    }


    /**
     * Get one categoriaSkill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaSkillDTO> findOne(Long id) {
        log.debug("Request to get CategoriaSkill : {}", id);
        return categoriaSkillRepository.findById(id)
            .map(categoriaSkillMapper::toDto);
    }

    /**
     * Delete the categoriaSkill by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoriaSkill : {}", id);
        categoriaSkillRepository.deleteById(id);
    }
}
