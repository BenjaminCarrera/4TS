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

import com.kode.ts.domain.EstatusTarea;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstatusTareaRepository;
import com.kode.ts.service.dto.EstatusTareaCriteria;
import com.kode.ts.service.dto.EstatusTareaDTO;
import com.kode.ts.service.mapper.EstatusTareaMapper;

/**
 * Service for executing complex queries for {@link EstatusTarea} entities in the database.
 * The main input is a {@link EstatusTareaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstatusTareaDTO} or a {@link Page} of {@link EstatusTareaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstatusTareaQueryService extends QueryService<EstatusTarea> {

    private final Logger log = LoggerFactory.getLogger(EstatusTareaQueryService.class);

    private final EstatusTareaRepository estatusTareaRepository;

    private final EstatusTareaMapper estatusTareaMapper;

    public EstatusTareaQueryService(EstatusTareaRepository estatusTareaRepository, EstatusTareaMapper estatusTareaMapper) {
        this.estatusTareaRepository = estatusTareaRepository;
        this.estatusTareaMapper = estatusTareaMapper;
    }

    /**
     * Return a {@link List} of {@link EstatusTareaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstatusTareaDTO> findByCriteria(EstatusTareaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstatusTarea> specification = createSpecification(criteria);
        return estatusTareaMapper.toDto(estatusTareaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstatusTareaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstatusTareaDTO> findByCriteria(EstatusTareaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstatusTarea> specification = createSpecification(criteria);
        return estatusTareaRepository.findAll(specification, page)
            .map(estatusTareaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstatusTareaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstatusTarea> specification = createSpecification(criteria);
        return estatusTareaRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<EstatusTarea> createSpecification(EstatusTareaCriteria criteria) {
        Specification<EstatusTarea> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstatusTarea_.id));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstatus(), EstatusTarea_.estatus));
            }
            if (criteria.getTareaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTareaId(),
                    root -> root.join(EstatusTarea_.tareas, JoinType.LEFT).get(Tarea_.id)));
            }
        }
        return specification;
    }
}
