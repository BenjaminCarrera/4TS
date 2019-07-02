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

import com.kode.ts.domain.ReqCan;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.ReqCanRepository;
import com.kode.ts.service.dto.ReqCanCriteria;
import com.kode.ts.service.dto.ReqCanDTO;
import com.kode.ts.service.mapper.ReqCanMapper;

/**
 * Service for executing complex queries for {@link ReqCan} entities in the database.
 * The main input is a {@link ReqCanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReqCanDTO} or a {@link Page} of {@link ReqCanDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReqCanQueryService extends QueryService<ReqCan> {

    private final Logger log = LoggerFactory.getLogger(ReqCanQueryService.class);

    private final ReqCanRepository reqCanRepository;

    private final ReqCanMapper reqCanMapper;

    public ReqCanQueryService(ReqCanRepository reqCanRepository, ReqCanMapper reqCanMapper) {
        this.reqCanRepository = reqCanRepository;
        this.reqCanMapper = reqCanMapper;
    }

    /**
     * Return a {@link List} of {@link ReqCanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReqCanDTO> findByCriteria(ReqCanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReqCan> specification = createSpecification(criteria);
        return reqCanMapper.toDto(reqCanRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReqCanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReqCanDTO> findByCriteria(ReqCanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReqCan> specification = createSpecification(criteria);
        return reqCanRepository.findAll(specification, page)
            .map(reqCanMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReqCanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReqCan> specification = createSpecification(criteria);
        return reqCanRepository.count(specification);
    }

    /**
     * Function to convert ReqCanCriteria to a {@link Specification}.
     */
    private Specification<ReqCan> createSpecification(ReqCanCriteria criteria) {
        Specification<ReqCan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReqCan_.id));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(ReqCan_.candidato, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(ReqCan_.requerimiento, JoinType.LEFT).get(Requerimiento_.id)));
            }
            if (criteria.getEstatusReqCanId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusReqCanId(),
                    root -> root.join(ReqCan_.estatusReqCan, JoinType.LEFT).get(EstatusReqCan_.id)));
            }
            if (criteria.getEstatusReqCanRecId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusReqCanRecId(),
                    root -> root.join(ReqCan_.estatusReqCanRec, JoinType.LEFT).get(EstatusReqCanRec_.id)));
            }
        }
        return specification;
    }
}
