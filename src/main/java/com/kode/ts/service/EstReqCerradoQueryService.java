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

import com.kode.ts.domain.EstReqCerrado;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstReqCerradoRepository;
import com.kode.ts.service.dto.EstReqCerradoCriteria;
import com.kode.ts.service.dto.EstReqCerradoDTO;
import com.kode.ts.service.mapper.EstReqCerradoMapper;

/**
 * Service for executing complex queries for {@link EstReqCerrado} entities in the database.
 * The main input is a {@link EstReqCerradoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstReqCerradoDTO} or a {@link Page} of {@link EstReqCerradoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstReqCerradoQueryService extends QueryService<EstReqCerrado> {

    private final Logger log = LoggerFactory.getLogger(EstReqCerradoQueryService.class);

    private final EstReqCerradoRepository estReqCerradoRepository;

    private final EstReqCerradoMapper estReqCerradoMapper;

    public EstReqCerradoQueryService(EstReqCerradoRepository estReqCerradoRepository, EstReqCerradoMapper estReqCerradoMapper) {
        this.estReqCerradoRepository = estReqCerradoRepository;
        this.estReqCerradoMapper = estReqCerradoMapper;
    }

    /**
     * Return a {@link List} of {@link EstReqCerradoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstReqCerradoDTO> findByCriteria(EstReqCerradoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstReqCerrado> specification = createSpecification(criteria);
        return estReqCerradoMapper.toDto(estReqCerradoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstReqCerradoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstReqCerradoDTO> findByCriteria(EstReqCerradoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstReqCerrado> specification = createSpecification(criteria);
        return estReqCerradoRepository.findAll(specification, page)
            .map(estReqCerradoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstReqCerradoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstReqCerrado> specification = createSpecification(criteria);
        return estReqCerradoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<EstReqCerrado> createSpecification(EstReqCerradoCriteria criteria) {
        Specification<EstReqCerrado> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstReqCerrado_.id));
            }
            if (criteria.getMotivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotivo(), EstReqCerrado_.motivo));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(EstReqCerrado_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
            if (criteria.getEstatusRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusRequerimientoId(),
                    root -> root.join(EstReqCerrado_.estatusRequerimiento, JoinType.LEFT).get(EstatusRequerimiento_.id)));
            }
        }
        return specification;
    }
}
