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

import com.kode.ts.domain.TipoTarea;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.TipoTareaRepository;
import com.kode.ts.service.dto.TipoTareaCriteria;
import com.kode.ts.service.dto.TipoTareaDTO;
import com.kode.ts.service.mapper.TipoTareaMapper;

/**
 * Service for executing complex queries for {@link TipoTarea} entities in the database.
 * The main input is a {@link TipoTareaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoTareaDTO} or a {@link Page} of {@link TipoTareaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoTareaQueryService extends QueryService<TipoTarea> {

    private final Logger log = LoggerFactory.getLogger(TipoTareaQueryService.class);

    private final TipoTareaRepository tipoTareaRepository;

    private final TipoTareaMapper tipoTareaMapper;

    public TipoTareaQueryService(TipoTareaRepository tipoTareaRepository, TipoTareaMapper tipoTareaMapper) {
        this.tipoTareaRepository = tipoTareaRepository;
        this.tipoTareaMapper = tipoTareaMapper;
    }

    /**
     * Return a {@link List} of {@link TipoTareaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoTareaDTO> findByCriteria(TipoTareaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoTarea> specification = createSpecification(criteria);
        return tipoTareaMapper.toDto(tipoTareaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoTareaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoTareaDTO> findByCriteria(TipoTareaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoTarea> specification = createSpecification(criteria);
        return tipoTareaRepository.findAll(specification, page)
            .map(tipoTareaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoTareaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoTarea> specification = createSpecification(criteria);
        return tipoTareaRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<TipoTarea> createSpecification(TipoTareaCriteria criteria) {
        Specification<TipoTarea> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoTarea_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), TipoTarea_.tipo));
            }
            if (criteria.getTareaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTareaId(),
                    root -> root.join(TipoTarea_.tareas, JoinType.LEFT).get(Tarea_.id)));
            }
        }
        return specification;
    }
}
