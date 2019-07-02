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

import com.kode.ts.domain.SkillRequerimiento;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.SkillRequerimientoRepository;
import com.kode.ts.service.dto.SkillRequerimientoCriteria;
import com.kode.ts.service.dto.SkillRequerimientoDTO;
import com.kode.ts.service.mapper.SkillRequerimientoMapper;

/**
 * Service for executing complex queries for {@link SkillRequerimiento} entities in the database.
 * The main input is a {@link SkillRequerimientoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillRequerimientoDTO} or a {@link Page} of {@link SkillRequerimientoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillRequerimientoQueryService extends QueryService<SkillRequerimiento> {

    private final Logger log = LoggerFactory.getLogger(SkillRequerimientoQueryService.class);

    private final SkillRequerimientoRepository skillRequerimientoRepository;

    private final SkillRequerimientoMapper skillRequerimientoMapper;

    public SkillRequerimientoQueryService(SkillRequerimientoRepository skillRequerimientoRepository, SkillRequerimientoMapper skillRequerimientoMapper) {
        this.skillRequerimientoRepository = skillRequerimientoRepository;
        this.skillRequerimientoMapper = skillRequerimientoMapper;
    }

    /**
     * Return a {@link List} of {@link SkillRequerimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillRequerimientoDTO> findByCriteria(SkillRequerimientoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SkillRequerimiento> specification = createSpecification(criteria);
        return skillRequerimientoMapper.toDto(skillRequerimientoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SkillRequerimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillRequerimientoDTO> findByCriteria(SkillRequerimientoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SkillRequerimiento> specification = createSpecification(criteria);
        return skillRequerimientoRepository.findAll(specification, page)
            .map(skillRequerimientoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillRequerimientoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SkillRequerimiento> specification = createSpecification(criteria);
        return skillRequerimientoRepository.count(specification);
    }

    /**
     * Function to convert SkillRequerimientoCriteria to a {@link Specification}.
     */
    private Specification<SkillRequerimiento> createSpecification(SkillRequerimientoCriteria criteria) {
        Specification<SkillRequerimiento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SkillRequerimiento_.id));
            }
            if (criteria.getIdRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdRequerimientoId(),
                    root -> root.join(SkillRequerimiento_.idRequerimiento, JoinType.LEFT).get(Requerimiento_.id)));
            }
            if (criteria.getIdSkillId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdSkillId(),
                    root -> root.join(SkillRequerimiento_.idSkill, JoinType.LEFT).get(Skill_.id)));
            }
            if (criteria.getTipoSkillId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoSkillId(),
                    root -> root.join(SkillRequerimiento_.tipoSkill, JoinType.LEFT).get(TipoSkill_.id)));
            }
        }
        return specification;
    }
}
