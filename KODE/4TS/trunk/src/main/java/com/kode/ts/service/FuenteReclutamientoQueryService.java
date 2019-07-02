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

import com.kode.ts.domain.FuenteReclutamiento;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.FuenteReclutamientoRepository;
import com.kode.ts.service.dto.FuenteReclutamientoCriteria;
import com.kode.ts.service.dto.FuenteReclutamientoDTO;
import com.kode.ts.service.mapper.FuenteReclutamientoMapper;

/**
 * Service for executing complex queries for {@link FuenteReclutamiento} entities in the database.
 * The main input is a {@link FuenteReclutamientoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuenteReclutamientoDTO} or a {@link Page} of {@link FuenteReclutamientoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuenteReclutamientoQueryService extends QueryService<FuenteReclutamiento> {

    private final Logger log = LoggerFactory.getLogger(FuenteReclutamientoQueryService.class);

    private final FuenteReclutamientoRepository fuenteReclutamientoRepository;

    private final FuenteReclutamientoMapper fuenteReclutamientoMapper;

    public FuenteReclutamientoQueryService(FuenteReclutamientoRepository fuenteReclutamientoRepository, FuenteReclutamientoMapper fuenteReclutamientoMapper) {
        this.fuenteReclutamientoRepository = fuenteReclutamientoRepository;
        this.fuenteReclutamientoMapper = fuenteReclutamientoMapper;
    }

    /**
     * Return a {@link List} of {@link FuenteReclutamientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuenteReclutamientoDTO> findByCriteria(FuenteReclutamientoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FuenteReclutamiento> specification = createSpecification(criteria);
        return fuenteReclutamientoMapper.toDto(fuenteReclutamientoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FuenteReclutamientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuenteReclutamientoDTO> findByCriteria(FuenteReclutamientoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FuenteReclutamiento> specification = createSpecification(criteria);
        return fuenteReclutamientoRepository.findAll(specification, page)
            .map(fuenteReclutamientoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuenteReclutamientoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FuenteReclutamiento> specification = createSpecification(criteria);
        return fuenteReclutamientoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<FuenteReclutamiento> createSpecification(FuenteReclutamientoCriteria criteria) {
        Specification<FuenteReclutamiento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FuenteReclutamiento_.id));
            }
            if (criteria.getFuente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFuente(), FuenteReclutamiento_.fuente));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(FuenteReclutamiento_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
        }
        return specification;
    }
}
