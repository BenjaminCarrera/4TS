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

import com.kode.ts.domain.EstCanInactivo;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstCanInactivoRepository;
import com.kode.ts.service.dto.EstCanInactivoCriteria;
import com.kode.ts.service.dto.EstCanInactivoDTO;
import com.kode.ts.service.mapper.EstCanInactivoMapper;

/**
 * Service for executing complex queries for {@link EstCanInactivo} entities in the database.
 * The main input is a {@link EstCanInactivoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstCanInactivoDTO} or a {@link Page} of {@link EstCanInactivoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstCanInactivoQueryService extends QueryService<EstCanInactivo> {

    private final Logger log = LoggerFactory.getLogger(EstCanInactivoQueryService.class);

    private final EstCanInactivoRepository estCanInactivoRepository;

    private final EstCanInactivoMapper estCanInactivoMapper;

    public EstCanInactivoQueryService(EstCanInactivoRepository estCanInactivoRepository, EstCanInactivoMapper estCanInactivoMapper) {
        this.estCanInactivoRepository = estCanInactivoRepository;
        this.estCanInactivoMapper = estCanInactivoMapper;
    }

    /**
     * Return a {@link List} of {@link EstCanInactivoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstCanInactivoDTO> findByCriteria(EstCanInactivoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstCanInactivo> specification = createSpecification(criteria);
        return estCanInactivoMapper.toDto(estCanInactivoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstCanInactivoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstCanInactivoDTO> findByCriteria(EstCanInactivoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstCanInactivo> specification = createSpecification(criteria);
        return estCanInactivoRepository.findAll(specification, page)
            .map(estCanInactivoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstCanInactivoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstCanInactivo> specification = createSpecification(criteria);
        return estCanInactivoRepository.count(specification);
    }

    /**
     * Function to convert EstCanInactivoCriteria to a {@link Specification}.
     */
    private Specification<EstCanInactivo> createSpecification(EstCanInactivoCriteria criteria) {
        Specification<EstCanInactivo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstCanInactivo_.id));
            }
            if (criteria.getMotivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotivo(), EstCanInactivo_.motivo));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(EstCanInactivo_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getEstatusCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusCandidatoId(),
                    root -> root.join(EstCanInactivo_.estatusCandidato, JoinType.LEFT).get(EstatusCandidato_.id)));
            }
        }
        return specification;
    }
}
