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

import com.kode.ts.domain.EsquemaContratacionKode;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EsquemaContratacionKodeRepository;
import com.kode.ts.service.dto.EsquemaContratacionKodeCriteria;
import com.kode.ts.service.dto.EsquemaContratacionKodeDTO;
import com.kode.ts.service.mapper.EsquemaContratacionKodeMapper;

/**
 * Service for executing complex queries for {@link EsquemaContratacionKode} entities in the database.
 * The main input is a {@link EsquemaContratacionKodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EsquemaContratacionKodeDTO} or a {@link Page} of {@link EsquemaContratacionKodeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EsquemaContratacionKodeQueryService extends QueryService<EsquemaContratacionKode> {

    private final Logger log = LoggerFactory.getLogger(EsquemaContratacionKodeQueryService.class);

    private final EsquemaContratacionKodeRepository esquemaContratacionKodeRepository;

    private final EsquemaContratacionKodeMapper esquemaContratacionKodeMapper;

    public EsquemaContratacionKodeQueryService(EsquemaContratacionKodeRepository esquemaContratacionKodeRepository, EsquemaContratacionKodeMapper esquemaContratacionKodeMapper) {
        this.esquemaContratacionKodeRepository = esquemaContratacionKodeRepository;
        this.esquemaContratacionKodeMapper = esquemaContratacionKodeMapper;
    }

    /**
     * Return a {@link List} of {@link EsquemaContratacionKodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EsquemaContratacionKodeDTO> findByCriteria(EsquemaContratacionKodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EsquemaContratacionKode> specification = createSpecification(criteria);
        return esquemaContratacionKodeMapper.toDto(esquemaContratacionKodeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EsquemaContratacionKodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EsquemaContratacionKodeDTO> findByCriteria(EsquemaContratacionKodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EsquemaContratacionKode> specification = createSpecification(criteria);
        return esquemaContratacionKodeRepository.findAll(specification, page)
            .map(esquemaContratacionKodeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EsquemaContratacionKodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EsquemaContratacionKode> specification = createSpecification(criteria);
        return esquemaContratacionKodeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<EsquemaContratacionKode> createSpecification(EsquemaContratacionKodeCriteria criteria) {
        Specification<EsquemaContratacionKode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EsquemaContratacionKode_.id));
            }
            if (criteria.getEsquema() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEsquema(), EsquemaContratacionKode_.esquema));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(EsquemaContratacionKode_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
        }
        return specification;
    }
}
