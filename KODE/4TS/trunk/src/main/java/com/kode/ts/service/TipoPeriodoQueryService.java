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

import com.kode.ts.domain.TipoPeriodo;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.TipoPeriodoRepository;
import com.kode.ts.service.dto.TipoPeriodoCriteria;
import com.kode.ts.service.dto.TipoPeriodoDTO;
import com.kode.ts.service.mapper.TipoPeriodoMapper;

/**
 * Service for executing complex queries for {@link TipoPeriodo} entities in the database.
 * The main input is a {@link TipoPeriodoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoPeriodoDTO} or a {@link Page} of {@link TipoPeriodoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoPeriodoQueryService extends QueryService<TipoPeriodo> {

    private final Logger log = LoggerFactory.getLogger(TipoPeriodoQueryService.class);

    private final TipoPeriodoRepository tipoPeriodoRepository;

    private final TipoPeriodoMapper tipoPeriodoMapper;

    public TipoPeriodoQueryService(TipoPeriodoRepository tipoPeriodoRepository, TipoPeriodoMapper tipoPeriodoMapper) {
        this.tipoPeriodoRepository = tipoPeriodoRepository;
        this.tipoPeriodoMapper = tipoPeriodoMapper;
    }

    /**
     * Return a {@link List} of {@link TipoPeriodoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoPeriodoDTO> findByCriteria(TipoPeriodoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoPeriodo> specification = createSpecification(criteria);
        return tipoPeriodoMapper.toDto(tipoPeriodoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoPeriodoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoPeriodoDTO> findByCriteria(TipoPeriodoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoPeriodo> specification = createSpecification(criteria);
        return tipoPeriodoRepository.findAll(specification, page)
            .map(tipoPeriodoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoPeriodoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoPeriodo> specification = createSpecification(criteria);
        return tipoPeriodoRepository.count(specification);
    }

    /**
     * Function to convert TipoPeriodoCriteria to a {@link Specification}.
     */
    private Specification<TipoPeriodo> createSpecification(TipoPeriodoCriteria criteria) {
        Specification<TipoPeriodo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoPeriodo_.id));
            }
            if (criteria.getPeriodo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeriodo(), TipoPeriodo_.periodo));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(TipoPeriodo_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
        }
        return specification;
    }
}
