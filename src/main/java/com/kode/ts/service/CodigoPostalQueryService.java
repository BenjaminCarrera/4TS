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

import com.kode.ts.domain.CodigoPostal;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.CodigoPostalRepository;
import com.kode.ts.service.dto.CodigoPostalCriteria;
import com.kode.ts.service.dto.CodigoPostalDTO;
import com.kode.ts.service.mapper.CodigoPostalMapper;

/**
 * Service for executing complex queries for {@link CodigoPostal} entities in the database.
 * The main input is a {@link CodigoPostalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CodigoPostalDTO} or a {@link Page} of {@link CodigoPostalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CodigoPostalQueryService extends QueryService<CodigoPostal> {

    private final Logger log = LoggerFactory.getLogger(CodigoPostalQueryService.class);

    private final CodigoPostalRepository codigoPostalRepository;

    private final CodigoPostalMapper codigoPostalMapper;

    public CodigoPostalQueryService(CodigoPostalRepository codigoPostalRepository, CodigoPostalMapper codigoPostalMapper) {
        this.codigoPostalRepository = codigoPostalRepository;
        this.codigoPostalMapper = codigoPostalMapper;
    }

    /**
     * Return a {@link List} of {@link CodigoPostalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CodigoPostalDTO> findByCriteria(CodigoPostalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CodigoPostal> specification = createSpecification(criteria);
        return codigoPostalMapper.toDto(codigoPostalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CodigoPostalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CodigoPostalDTO> findByCriteria(CodigoPostalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CodigoPostal> specification = createSpecification(criteria);
        return codigoPostalRepository.findAll(specification, page)
            .map(codigoPostalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CodigoPostalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CodigoPostal> specification = createSpecification(criteria);
        return codigoPostalRepository.count(specification);
    }

    /**
     * Function to convert CodigoPostalCriteria to a {@link Specification}.
     */
    private Specification<CodigoPostal> createSpecification(CodigoPostalCriteria criteria) {
        Specification<CodigoPostal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CodigoPostal_.id));
            }
            if (criteria.getCodigoPostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoPostal(), CodigoPostal_.codigoPostal));
            }
            if (criteria.getColoniaId() != null) {
                specification = specification.and(buildSpecification(criteria.getColoniaId(),
                    root -> root.join(CodigoPostal_.colonias, JoinType.LEFT).get(Colonia_.id)));
            }
            if (criteria.getMunicipioId() != null) {
                specification = specification.and(buildSpecification(criteria.getMunicipioId(),
                    root -> root.join(CodigoPostal_.municipios, JoinType.LEFT).get(Municipio_.id)));
            }
        }
        return specification;
    }
}
