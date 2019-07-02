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

import com.kode.ts.domain.EstatusReqCan;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstatusReqCanRepository;
import com.kode.ts.service.dto.EstatusReqCanCriteria;
import com.kode.ts.service.dto.EstatusReqCanDTO;
import com.kode.ts.service.mapper.EstatusReqCanMapper;

/**
 * Service for executing complex queries for {@link EstatusReqCan} entities in the database.
 * The main input is a {@link EstatusReqCanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstatusReqCanDTO} or a {@link Page} of {@link EstatusReqCanDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstatusReqCanQueryService extends QueryService<EstatusReqCan> {

    private final Logger log = LoggerFactory.getLogger(EstatusReqCanQueryService.class);

    private final EstatusReqCanRepository estatusReqCanRepository;

    private final EstatusReqCanMapper estatusReqCanMapper;

    public EstatusReqCanQueryService(EstatusReqCanRepository estatusReqCanRepository, EstatusReqCanMapper estatusReqCanMapper) {
        this.estatusReqCanRepository = estatusReqCanRepository;
        this.estatusReqCanMapper = estatusReqCanMapper;
    }

    /**
     * Return a {@link List} of {@link EstatusReqCanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstatusReqCanDTO> findByCriteria(EstatusReqCanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstatusReqCan> specification = createSpecification(criteria);
        return estatusReqCanMapper.toDto(estatusReqCanRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstatusReqCanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstatusReqCanDTO> findByCriteria(EstatusReqCanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstatusReqCan> specification = createSpecification(criteria);
        return estatusReqCanRepository.findAll(specification, page)
            .map(estatusReqCanMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstatusReqCanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstatusReqCan> specification = createSpecification(criteria);
        return estatusReqCanRepository.count(specification);
    }

    /**
     * Function to convert EstatusReqCanCriteria to a {@link Specification}.
     */
    private Specification<EstatusReqCan> createSpecification(EstatusReqCanCriteria criteria) {
        Specification<EstatusReqCan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstatusReqCan_.id));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstatus(), EstatusReqCan_.estatus));
            }
            if (criteria.getEstatusReqCanRecId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusReqCanRecId(),
                    root -> root.join(EstatusReqCan_.estatusReqCanRecs, JoinType.LEFT).get(EstatusReqCanRec_.id)));
            }
        }
        return specification;
    }
}
