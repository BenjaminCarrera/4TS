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

import com.kode.ts.domain.EstatusRequerimiento;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstatusRequerimientoRepository;
import com.kode.ts.service.dto.EstatusRequerimientoCriteria;
import com.kode.ts.service.dto.EstatusRequerimientoDTO;
import com.kode.ts.service.mapper.EstatusRequerimientoMapper;

/**
 * Service for executing complex queries for {@link EstatusRequerimiento} entities in the database.
 * The main input is a {@link EstatusRequerimientoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstatusRequerimientoDTO} or a {@link Page} of {@link EstatusRequerimientoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstatusRequerimientoQueryService extends QueryService<EstatusRequerimiento> {

    private final Logger log = LoggerFactory.getLogger(EstatusRequerimientoQueryService.class);

    private final EstatusRequerimientoRepository estatusRequerimientoRepository;

    private final EstatusRequerimientoMapper estatusRequerimientoMapper;

    public EstatusRequerimientoQueryService(EstatusRequerimientoRepository estatusRequerimientoRepository, EstatusRequerimientoMapper estatusRequerimientoMapper) {
        this.estatusRequerimientoRepository = estatusRequerimientoRepository;
        this.estatusRequerimientoMapper = estatusRequerimientoMapper;
    }

    /**
     * Return a {@link List} of {@link EstatusRequerimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstatusRequerimientoDTO> findByCriteria(EstatusRequerimientoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstatusRequerimiento> specification = createSpecification(criteria);
        return estatusRequerimientoMapper.toDto(estatusRequerimientoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstatusRequerimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstatusRequerimientoDTO> findByCriteria(EstatusRequerimientoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstatusRequerimiento> specification = createSpecification(criteria);
        return estatusRequerimientoRepository.findAll(specification, page)
            .map(estatusRequerimientoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstatusRequerimientoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstatusRequerimiento> specification = createSpecification(criteria);
        return estatusRequerimientoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<EstatusRequerimiento> createSpecification(EstatusRequerimientoCriteria criteria) {
        Specification<EstatusRequerimiento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstatusRequerimiento_.id));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstatus(), EstatusRequerimiento_.estatus));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(EstatusRequerimiento_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
            if (criteria.getEstReqCerradoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstReqCerradoId(),
                    root -> root.join(EstatusRequerimiento_.estReqCerrados, JoinType.LEFT).get(EstReqCerrado_.id)));
            }
        }
        return specification;
    }
}
