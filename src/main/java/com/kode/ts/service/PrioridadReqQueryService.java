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

import com.kode.ts.domain.PrioridadReq;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.PrioridadReqRepository;
import com.kode.ts.service.dto.PrioridadReqCriteria;
import com.kode.ts.service.dto.PrioridadReqDTO;
import com.kode.ts.service.mapper.PrioridadReqMapper;

/**
 * Service for executing complex queries for {@link PrioridadReq} entities in the database.
 * The main input is a {@link PrioridadReqCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrioridadReqDTO} or a {@link Page} of {@link PrioridadReqDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrioridadReqQueryService extends QueryService<PrioridadReq> {

    private final Logger log = LoggerFactory.getLogger(PrioridadReqQueryService.class);

    private final PrioridadReqRepository prioridadReqRepository;

    private final PrioridadReqMapper prioridadReqMapper;

    public PrioridadReqQueryService(PrioridadReqRepository prioridadReqRepository, PrioridadReqMapper prioridadReqMapper) {
        this.prioridadReqRepository = prioridadReqRepository;
        this.prioridadReqMapper = prioridadReqMapper;
    }

    /**
     * Return a {@link List} of {@link PrioridadReqDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrioridadReqDTO> findByCriteria(PrioridadReqCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PrioridadReq> specification = createSpecification(criteria);
        return prioridadReqMapper.toDto(prioridadReqRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrioridadReqDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrioridadReqDTO> findByCriteria(PrioridadReqCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrioridadReq> specification = createSpecification(criteria);
        return prioridadReqRepository.findAll(specification, page)
            .map(prioridadReqMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrioridadReqCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PrioridadReq> specification = createSpecification(criteria);
        return prioridadReqRepository.count(specification);
    }

    /**
     * Function to convert PrioridadReqCriteria to a {@link Specification}.
     */
    private Specification<PrioridadReq> createSpecification(PrioridadReqCriteria criteria) {
        Specification<PrioridadReq> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrioridadReq_.id));
            }
            if (criteria.getPrioridad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrioridad(), PrioridadReq_.prioridad));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(PrioridadReq_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
        }
        return specification;
    }
}
