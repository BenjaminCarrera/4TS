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

import com.kode.ts.domain.CategoriaSkill;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.CategoriaSkillRepository;
import com.kode.ts.service.dto.CategoriaSkillCriteria;
import com.kode.ts.service.dto.CategoriaSkillDTO;
import com.kode.ts.service.mapper.CategoriaSkillMapper;

/**
 * Service for executing complex queries for {@link CategoriaSkill} entities in the database.
 * The main input is a {@link CategoriaSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaSkillDTO} or a {@link Page} of {@link CategoriaSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaSkillQueryService extends QueryService<CategoriaSkill> {

    private final Logger log = LoggerFactory.getLogger(CategoriaSkillQueryService.class);

    private final CategoriaSkillRepository categoriaSkillRepository;

    private final CategoriaSkillMapper categoriaSkillMapper;

    public CategoriaSkillQueryService(CategoriaSkillRepository categoriaSkillRepository, CategoriaSkillMapper categoriaSkillMapper) {
        this.categoriaSkillRepository = categoriaSkillRepository;
        this.categoriaSkillMapper = categoriaSkillMapper;
    }

    /**
     * Return a {@link List} of {@link CategoriaSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaSkillDTO> findByCriteria(CategoriaSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaSkill> specification = createSpecification(criteria);
        return categoriaSkillMapper.toDto(categoriaSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoriaSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaSkillDTO> findByCriteria(CategoriaSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaSkill> specification = createSpecification(criteria);
        return categoriaSkillRepository.findAll(specification, page)
            .map(categoriaSkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaSkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaSkill> specification = createSpecification(criteria);
        return categoriaSkillRepository.count(specification);
    }

    /**
     * Function to convert CategoriaSkillCriteria to a {@link Specification}.
     */
    private Specification<CategoriaSkill> createSpecification(CategoriaSkillCriteria criteria) {
        Specification<CategoriaSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CategoriaSkill_.id));
            }
            if (criteria.getCategoria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoria(), CategoriaSkill_.categoria));
            }
            if (criteria.getSkillId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillId(),
                    root -> root.join(CategoriaSkill_.skills, JoinType.LEFT).get(Skill_.id)));
            }
        }
        return specification;
    }
}
