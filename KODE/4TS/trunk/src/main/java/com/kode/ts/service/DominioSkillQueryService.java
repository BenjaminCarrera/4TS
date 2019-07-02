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

import com.kode.ts.domain.DominioSkill;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.DominioSkillRepository;
import com.kode.ts.service.dto.DominioSkillCriteria;
import com.kode.ts.service.dto.DominioSkillDTO;
import com.kode.ts.service.mapper.DominioSkillMapper;

/**
 * Service for executing complex queries for {@link DominioSkill} entities in the database.
 * The main input is a {@link DominioSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DominioSkillDTO} or a {@link Page} of {@link DominioSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DominioSkillQueryService extends QueryService<DominioSkill> {

    private final Logger log = LoggerFactory.getLogger(DominioSkillQueryService.class);

    private final DominioSkillRepository dominioSkillRepository;

    private final DominioSkillMapper dominioSkillMapper;

    public DominioSkillQueryService(DominioSkillRepository dominioSkillRepository, DominioSkillMapper dominioSkillMapper) {
        this.dominioSkillRepository = dominioSkillRepository;
        this.dominioSkillMapper = dominioSkillMapper;
    }

    /**
     * Return a {@link List} of {@link DominioSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DominioSkillDTO> findByCriteria(DominioSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DominioSkill> specification = createSpecification(criteria);
        return dominioSkillMapper.toDto(dominioSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DominioSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DominioSkillDTO> findByCriteria(DominioSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DominioSkill> specification = createSpecification(criteria);
        return dominioSkillRepository.findAll(specification, page)
            .map(dominioSkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DominioSkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DominioSkill> specification = createSpecification(criteria);
        return dominioSkillRepository.count(specification);
    }

    /**
     * Function to convert DominioSkillCriteria to a {@link Specification}.
     */
    private Specification<DominioSkill> createSpecification(DominioSkillCriteria criteria) {
        Specification<DominioSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DominioSkill_.id));
            }
            if (criteria.getDominio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDominio(), DominioSkill_.dominio));
            }
            if (criteria.getSkillCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillCandidatoId(),
                    root -> root.join(DominioSkill_.skillCandidatoes, JoinType.LEFT).get(SkillCandidato_.id)));
            }
        }
        return specification;
    }
}
