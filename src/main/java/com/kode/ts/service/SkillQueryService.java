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

import com.kode.ts.domain.Skill;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.SkillRepository;
import com.kode.ts.service.dto.SkillCriteria;
import com.kode.ts.service.dto.SkillDTO;
import com.kode.ts.service.mapper.SkillMapper;

/**
 * Service for executing complex queries for {@link Skill} entities in the database.
 * The main input is a {@link SkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillDTO} or a {@link Page} of {@link SkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillQueryService extends QueryService<Skill> {

    private final Logger log = LoggerFactory.getLogger(SkillQueryService.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillQueryService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Return a {@link List} of {@link SkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillDTO> findByCriteria(SkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillMapper.toDto(skillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillDTO> findByCriteria(SkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillRepository.findAll(specification, page)
            .map(skillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Skill> createSpecification(SkillCriteria criteria) {
        Specification<Skill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Skill_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Skill_.nombre));
            }
            if (criteria.getSkillCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillCandidatoId(),
                    root -> root.join(Skill_.skillCandidatoes, JoinType.LEFT).get(SkillCandidato_.id)));
            }
            if (criteria.getSkillRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillRequerimientoId(),
                    root -> root.join(Skill_.skillRequerimientos, JoinType.LEFT).get(SkillRequerimiento_.id)));
            }
            if (criteria.getCategoriaSkillId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaSkillId(),
                    root -> root.join(Skill_.categoriaSkill, JoinType.LEFT).get(CategoriaSkill_.id)));
            }
        }
        return specification;
    }
}
