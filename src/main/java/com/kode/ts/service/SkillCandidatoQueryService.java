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

import com.kode.ts.domain.SkillCandidato;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.SkillCandidatoRepository;
import com.kode.ts.service.dto.SkillCandidatoCriteria;
import com.kode.ts.service.dto.SkillCandidatoDTO;
import com.kode.ts.service.mapper.SkillCandidatoMapper;

/**
 * Service for executing complex queries for {@link SkillCandidato} entities in the database.
 * The main input is a {@link SkillCandidatoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillCandidatoDTO} or a {@link Page} of {@link SkillCandidatoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillCandidatoQueryService extends QueryService<SkillCandidato> {

    private final Logger log = LoggerFactory.getLogger(SkillCandidatoQueryService.class);

    private final SkillCandidatoRepository skillCandidatoRepository;

    private final SkillCandidatoMapper skillCandidatoMapper;

    public SkillCandidatoQueryService(SkillCandidatoRepository skillCandidatoRepository, SkillCandidatoMapper skillCandidatoMapper) {
        this.skillCandidatoRepository = skillCandidatoRepository;
        this.skillCandidatoMapper = skillCandidatoMapper;
    }

    /**
     * Return a {@link List} of {@link SkillCandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillCandidatoDTO> findByCriteria(SkillCandidatoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SkillCandidato> specification = createSpecification(criteria);
        return skillCandidatoMapper.toDto(skillCandidatoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SkillCandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillCandidatoDTO> findByCriteria(SkillCandidatoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SkillCandidato> specification = createSpecification(criteria);
        return skillCandidatoRepository.findAll(specification, page)
            .map(skillCandidatoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillCandidatoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SkillCandidato> specification = createSpecification(criteria);
        return skillCandidatoRepository.count(specification);
    }

    /**
     * Function to convert SkillCandidatoCriteria to a {@link Specification}.
     */
    private Specification<SkillCandidato> createSpecification(SkillCandidatoCriteria criteria) {
        Specification<SkillCandidato> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SkillCandidato_.id));
            }
            if (criteria.getCalificacionSkill() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalificacionSkill(), SkillCandidato_.calificacionSkill));
            }
            if (criteria.getIdCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdCandidatoId(),
                    root -> root.join(SkillCandidato_.idCandidato, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getIdSkillId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdSkillId(),
                    root -> root.join(SkillCandidato_.idSkill, JoinType.LEFT).get(Skill_.id)));
            }
            if (criteria.getNivelSkillId() != null) {
                specification = specification.and(buildSpecification(criteria.getNivelSkillId(),
                    root -> root.join(SkillCandidato_.nivelSkill, JoinType.LEFT).get(DominioSkill_.id)));
            }
        }
        return specification;
    }
}
