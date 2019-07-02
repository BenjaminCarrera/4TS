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

import com.kode.ts.domain.EstatusCandidato;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstatusCandidatoRepository;
import com.kode.ts.service.dto.EstatusCandidatoCriteria;
import com.kode.ts.service.dto.EstatusCandidatoDTO;
import com.kode.ts.service.mapper.EstatusCandidatoMapper;

/**
 * Service for executing complex queries for {@link EstatusCandidato} entities in the database.
 * The main input is a {@link EstatusCandidatoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstatusCandidatoDTO} or a {@link Page} of {@link EstatusCandidatoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstatusCandidatoQueryService extends QueryService<EstatusCandidato> {

    private final Logger log = LoggerFactory.getLogger(EstatusCandidatoQueryService.class);

    private final EstatusCandidatoRepository estatusCandidatoRepository;

    private final EstatusCandidatoMapper estatusCandidatoMapper;

    public EstatusCandidatoQueryService(EstatusCandidatoRepository estatusCandidatoRepository, EstatusCandidatoMapper estatusCandidatoMapper) {
        this.estatusCandidatoRepository = estatusCandidatoRepository;
        this.estatusCandidatoMapper = estatusCandidatoMapper;
    }

    /**
     * Return a {@link List} of {@link EstatusCandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstatusCandidatoDTO> findByCriteria(EstatusCandidatoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstatusCandidato> specification = createSpecification(criteria);
        return estatusCandidatoMapper.toDto(estatusCandidatoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstatusCandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstatusCandidatoDTO> findByCriteria(EstatusCandidatoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstatusCandidato> specification = createSpecification(criteria);
        return estatusCandidatoRepository.findAll(specification, page)
            .map(estatusCandidatoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstatusCandidatoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstatusCandidato> specification = createSpecification(criteria);
        return estatusCandidatoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<EstatusCandidato> createSpecification(EstatusCandidatoCriteria criteria) {
        Specification<EstatusCandidato> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstatusCandidato_.id));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstatus(), EstatusCandidato_.estatus));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(EstatusCandidato_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getEstCanInactivoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstCanInactivoId(),
                    root -> root.join(EstatusCandidato_.estCanInactivos, JoinType.LEFT).get(EstCanInactivo_.id)));
            }
        }
        return specification;
    }
}
