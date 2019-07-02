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

import com.kode.ts.domain.EstatusAcademico;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstatusAcademicoRepository;
import com.kode.ts.service.dto.EstatusAcademicoCriteria;
import com.kode.ts.service.dto.EstatusAcademicoDTO;
import com.kode.ts.service.mapper.EstatusAcademicoMapper;

/**
 * Service for executing complex queries for {@link EstatusAcademico} entities in the database.
 * The main input is a {@link EstatusAcademicoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstatusAcademicoDTO} or a {@link Page} of {@link EstatusAcademicoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstatusAcademicoQueryService extends QueryService<EstatusAcademico> {

    private final Logger log = LoggerFactory.getLogger(EstatusAcademicoQueryService.class);

    private final EstatusAcademicoRepository estatusAcademicoRepository;

    private final EstatusAcademicoMapper estatusAcademicoMapper;

    public EstatusAcademicoQueryService(EstatusAcademicoRepository estatusAcademicoRepository, EstatusAcademicoMapper estatusAcademicoMapper) {
        this.estatusAcademicoRepository = estatusAcademicoRepository;
        this.estatusAcademicoMapper = estatusAcademicoMapper;
    }

    /**
     * Return a {@link List} of {@link EstatusAcademicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstatusAcademicoDTO> findByCriteria(EstatusAcademicoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstatusAcademico> specification = createSpecification(criteria);
        return estatusAcademicoMapper.toDto(estatusAcademicoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstatusAcademicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstatusAcademicoDTO> findByCriteria(EstatusAcademicoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstatusAcademico> specification = createSpecification(criteria);
        return estatusAcademicoRepository.findAll(specification, page)
            .map(estatusAcademicoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstatusAcademicoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstatusAcademico> specification = createSpecification(criteria);
        return estatusAcademicoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<EstatusAcademico> createSpecification(EstatusAcademicoCriteria criteria) {
        Specification<EstatusAcademico> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstatusAcademico_.id));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstatus(), EstatusAcademico_.estatus));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(EstatusAcademico_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
        }
        return specification;
    }
}
