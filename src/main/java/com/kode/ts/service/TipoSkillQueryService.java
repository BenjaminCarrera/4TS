package com.kode.ts.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.kode.ts.domain.TipoSkill;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.TipoSkillRepository;
import com.kode.ts.service.dto.TipoSkillCriteria;
import com.kode.ts.service.dto.TipoSkillDTO;
import com.kode.ts.service.mapper.TipoSkillMapper;

/**
 * Service for executing complex queries for {@link TipoSkill} entities in the database.
 * The main input is a {@link TipoSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoSkillDTO} or a {@link Page} of {@link TipoSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoSkillQueryService extends QueryService<TipoSkill> {

    private final Logger log = LoggerFactory.getLogger(TipoSkillQueryService.class);

    private final TipoSkillRepository tipoSkillRepository;

    private final TipoSkillMapper tipoSkillMapper;

    public TipoSkillQueryService(TipoSkillRepository tipoSkillRepository, TipoSkillMapper tipoSkillMapper) {
        this.tipoSkillRepository = tipoSkillRepository;
        this.tipoSkillMapper = tipoSkillMapper;
    }

    /**
     * Return a {@link List} of {@link TipoSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoSkillDTO> findByCriteria(TipoSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoSkill> specification = createSpecification(criteria);
        return tipoSkillMapper.toDto(tipoSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoSkillDTO> findByCriteria(TipoSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoSkill> specification = createSpecification(criteria);
        return tipoSkillRepository.findAll(specification, page)
            .map(tipoSkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoSkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoSkill> specification = createSpecification(criteria);
        return tipoSkillRepository.count(specification);
    }

    /**
     * Function to convert TipoSkillCriteria to a {@link Specification}.
     */
    private Specification<TipoSkill> createSpecification(TipoSkillCriteria criteria) {
        Specification<TipoSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoSkill_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), TipoSkill_.tipo));
            }
            if (criteria.getSkillRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillRequerimientoId(),
                    root -> root.join(TipoSkill_.skillRequerimientos, JoinType.LEFT).get(SkillRequerimiento_.id)));
            }
        }
        return specification;
    }
}
