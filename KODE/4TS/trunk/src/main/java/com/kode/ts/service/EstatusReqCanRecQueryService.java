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

import com.kode.ts.domain.EstatusReqCanRec;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.EstatusReqCanRecRepository;
import com.kode.ts.service.dto.EstatusReqCanRecCriteria;
import com.kode.ts.service.dto.EstatusReqCanRecDTO;
import com.kode.ts.service.mapper.EstatusReqCanRecMapper;

/**
 * Service for executing complex queries for {@link EstatusReqCanRec} entities in the database.
 * The main input is a {@link EstatusReqCanRecCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstatusReqCanRecDTO} or a {@link Page} of {@link EstatusReqCanRecDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstatusReqCanRecQueryService extends QueryService<EstatusReqCanRec> {

    private final Logger log = LoggerFactory.getLogger(EstatusReqCanRecQueryService.class);

    private final EstatusReqCanRecRepository estatusReqCanRecRepository;

    private final EstatusReqCanRecMapper estatusReqCanRecMapper;

    public EstatusReqCanRecQueryService(EstatusReqCanRecRepository estatusReqCanRecRepository, EstatusReqCanRecMapper estatusReqCanRecMapper) {
        this.estatusReqCanRecRepository = estatusReqCanRecRepository;
        this.estatusReqCanRecMapper = estatusReqCanRecMapper;
    }

    /**
     * Return a {@link List} of {@link EstatusReqCanRecDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstatusReqCanRecDTO> findByCriteria(EstatusReqCanRecCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstatusReqCanRec> specification = createSpecification(criteria);
        return estatusReqCanRecMapper.toDto(estatusReqCanRecRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstatusReqCanRecDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstatusReqCanRecDTO> findByCriteria(EstatusReqCanRecCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstatusReqCanRec> specification = createSpecification(criteria);
        return estatusReqCanRecRepository.findAll(specification, page)
            .map(estatusReqCanRecMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstatusReqCanRecCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstatusReqCanRec> specification = createSpecification(criteria);
        return estatusReqCanRecRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<EstatusReqCanRec> createSpecification(EstatusReqCanRecCriteria criteria) {
        Specification<EstatusReqCanRec> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EstatusReqCanRec_.id));
            }
            if (criteria.getMotivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotivo(), EstatusReqCanRec_.motivo));
            }
            if (criteria.getEstatusReqCanId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusReqCanId(),
                    root -> root.join(EstatusReqCanRec_.estatusReqCan, JoinType.LEFT).get(EstatusReqCan_.id)));
            }
        }
        return specification;
    }
}
