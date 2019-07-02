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

import com.kode.ts.domain.ReferenciasLaborales;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.ReferenciasLaboralesRepository;
import com.kode.ts.service.dto.ReferenciasLaboralesCriteria;
import com.kode.ts.service.dto.ReferenciasLaboralesDTO;
import com.kode.ts.service.mapper.ReferenciasLaboralesMapper;

/**
 * Service for executing complex queries for {@link ReferenciasLaborales} entities in the database.
 * The main input is a {@link ReferenciasLaboralesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReferenciasLaboralesDTO} or a {@link Page} of {@link ReferenciasLaboralesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReferenciasLaboralesQueryService extends QueryService<ReferenciasLaborales> {

    private final Logger log = LoggerFactory.getLogger(ReferenciasLaboralesQueryService.class);

    private final ReferenciasLaboralesRepository referenciasLaboralesRepository;

    private final ReferenciasLaboralesMapper referenciasLaboralesMapper;

    public ReferenciasLaboralesQueryService(ReferenciasLaboralesRepository referenciasLaboralesRepository, ReferenciasLaboralesMapper referenciasLaboralesMapper) {
        this.referenciasLaboralesRepository = referenciasLaboralesRepository;
        this.referenciasLaboralesMapper = referenciasLaboralesMapper;
    }

    /**
     * Return a {@link List} of {@link ReferenciasLaboralesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReferenciasLaboralesDTO> findByCriteria(ReferenciasLaboralesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReferenciasLaborales> specification = createSpecification(criteria);
        return referenciasLaboralesMapper.toDto(referenciasLaboralesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReferenciasLaboralesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferenciasLaboralesDTO> findByCriteria(ReferenciasLaboralesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReferenciasLaborales> specification = createSpecification(criteria);
        return referenciasLaboralesRepository.findAll(specification, page)
            .map(referenciasLaboralesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReferenciasLaboralesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReferenciasLaborales> specification = createSpecification(criteria);
        return referenciasLaboralesRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ReferenciasLaborales> createSpecification(ReferenciasLaboralesCriteria criteria) {
        Specification<ReferenciasLaborales> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReferenciasLaborales_.id));
            }
            if (criteria.getEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresa(), ReferenciasLaborales_.empresa));
            }
            if (criteria.getNombreContacto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreContacto(), ReferenciasLaborales_.nombreContacto));
            }
            if (criteria.getPuestoContacto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPuestoContacto(), ReferenciasLaborales_.puestoContacto));
            }
            if (criteria.getEmailContaco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailContaco(), ReferenciasLaborales_.emailContaco));
            }
            if (criteria.getTelefonoContacto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonoContacto(), ReferenciasLaborales_.telefonoContacto));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(ReferenciasLaborales_.candidato, JoinType.LEFT).get(Candidato_.id)));
            }
        }
        return specification;
    }
}
