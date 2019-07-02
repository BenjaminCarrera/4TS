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

import com.kode.ts.domain.EstatusLaboral;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstatusLaboralRepository;
import com.kode.ts.service.dto.EstatusLaboralCriteria;
import com.kode.ts.service.dto.EstatusLaboralDTO;
import com.kode.ts.service.mapper.EstatusLaboralMapper;

/**
 * Service for executing complex queries for {@link EstatusLaboral} entities in the database.
 * The main input is a {@link EstatusLaboralCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstatusLaboralDTO} or a {@link Page} of {@link EstatusLaboralDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstatusLaboralQueryService extends QueryService<EstatusLaboral> {

    private final Logger log = LoggerFactory.getLogger(EstatusLaboralQueryService.class);

    private final EstatusLaboralRepository estatusLaboralRepository;

    private final EstatusLaboralMapper estatusLaboralMapper;

    public EstatusLaboralQueryService(EstatusLaboralRepository estatusLaboralRepository, EstatusLaboralMapper estatusLaboralMapper) {
        this.estatusLaboralRepository = estatusLaboralRepository;
        this.estatusLaboralMapper = estatusLaboralMapper;
    }

    /**
     * Return a {@link List} of {@link EstatusLaboralDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstatusLaboralDTO> findByCriteria(EstatusLaboralCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstatusLaboral> specification = createSpecification(criteria);
        return estatusLaboralMapper.toDto(estatusLaboralRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstatusLaboralDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstatusLaboralDTO> findByCriteria(EstatusLaboralCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstatusLaboral> specification = createSpecification(criteria);
        return estatusLaboralRepository.findAll(specification, page)
            .map(estatusLaboralMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstatusLaboralCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstatusLaboral> specification = createSpecification(criteria);
        return estatusLaboralRepository.count(specification);
    }

    /**
     * Function to convert EstatusLaboralCriteria to a {@link Specification}.
     */
    private Specification<EstatusLaboral> createSpecification(EstatusLaboralCriteria criteria) {
        Specification<EstatusLaboral> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstatusLaboral_.id));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstatus(), EstatusLaboral_.estatus));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(EstatusLaboral_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
        }
        return specification;
    }
}
