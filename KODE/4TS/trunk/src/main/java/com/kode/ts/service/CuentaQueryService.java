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

import com.kode.ts.domain.Cuenta;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.CuentaRepository;
import com.kode.ts.service.dto.CuentaCriteria;
import com.kode.ts.service.dto.CuentaDTO;
import com.kode.ts.service.mapper.CuentaMapper;

/**
 * Service for executing complex queries for {@link Cuenta} entities in the database.
 * The main input is a {@link CuentaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CuentaDTO} or a {@link Page} of {@link CuentaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CuentaQueryService extends QueryService<Cuenta> {

    private final Logger log = LoggerFactory.getLogger(CuentaQueryService.class);

    private final CuentaRepository cuentaRepository;

    private final CuentaMapper cuentaMapper;

    public CuentaQueryService(CuentaRepository cuentaRepository, CuentaMapper cuentaMapper) {
        this.cuentaRepository = cuentaRepository;
        this.cuentaMapper = cuentaMapper;
    }

    /**
     * Return a {@link List} of {@link CuentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CuentaDTO> findByCriteria(CuentaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cuenta> specification = createSpecification(criteria);
        return cuentaMapper.toDto(cuentaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CuentaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CuentaDTO> findByCriteria(CuentaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cuenta> specification = createSpecification(criteria);
        return cuentaRepository.findAll(specification, page)
            .map(cuentaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CuentaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cuenta> specification = createSpecification(criteria);
        return cuentaRepository.count(specification);
    }

    /**
     * Function to convert CuentaCriteria to a {@link Specification}.
     */
    private Specification<Cuenta> createSpecification(CuentaCriteria criteria) {
        Specification<Cuenta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Cuenta_.id));
            }
            if (criteria.getClave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClave(), Cuenta_.clave));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Cuenta_.nombre));
            }
            if (criteria.getCandidatoInteresId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoInteresId(),
                    root -> root.join(Cuenta_.candidatoInteres, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getCandidatoRechazadasId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoRechazadasId(),
                    root -> root.join(Cuenta_.candidatoRechazadas, JoinType.LEFT).get(Candidato_.id)));
            }
        }
        return specification;
    }
}
