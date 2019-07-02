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

import com.kode.ts.domain.EsqContRec;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EsqContRecRepository;
import com.kode.ts.service.dto.EsqContRecCriteria;
import com.kode.ts.service.dto.EsqContRecDTO;
import com.kode.ts.service.mapper.EsqContRecMapper;

/**
 * Service for executing complex queries for {@link EsqContRec} entities in the database.
 * The main input is a {@link EsqContRecCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EsqContRecDTO} or a {@link Page} of {@link EsqContRecDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EsqContRecQueryService extends QueryService<EsqContRec> {

    private final Logger log = LoggerFactory.getLogger(EsqContRecQueryService.class);

    private final EsqContRecRepository esqContRecRepository;

    private final EsqContRecMapper esqContRecMapper;

    public EsqContRecQueryService(EsqContRecRepository esqContRecRepository, EsqContRecMapper esqContRecMapper) {
        this.esqContRecRepository = esqContRecRepository;
        this.esqContRecMapper = esqContRecMapper;
    }

    /**
     * Return a {@link List} of {@link EsqContRecDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EsqContRecDTO> findByCriteria(EsqContRecCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EsqContRec> specification = createSpecification(criteria);
        return esqContRecMapper.toDto(esqContRecRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EsqContRecDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EsqContRecDTO> findByCriteria(EsqContRecCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EsqContRec> specification = createSpecification(criteria);
        return esqContRecRepository.findAll(specification, page)
            .map(esqContRecMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EsqContRecCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EsqContRec> specification = createSpecification(criteria);
        return esqContRecRepository.count(specification);
    }

    /**
     * Function to convert EsqContRecCriteria to a {@link Specification}.
     */
    private Specification<EsqContRec> createSpecification(EsqContRecCriteria criteria) {
        Specification<EsqContRec> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EsqContRec_.id));
            }
            if (criteria.getEsquema() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEsquema(), EsqContRec_.esquema));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(EsqContRec_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(EsqContRec_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
        }
        return specification;
    }
}
