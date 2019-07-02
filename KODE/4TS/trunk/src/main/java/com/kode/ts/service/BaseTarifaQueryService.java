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

import com.kode.ts.domain.BaseTarifa;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.BaseTarifaRepository;
import com.kode.ts.service.dto.BaseTarifaCriteria;
import com.kode.ts.service.dto.BaseTarifaDTO;
import com.kode.ts.service.mapper.BaseTarifaMapper;

/**
 * Service for executing complex queries for {@link BaseTarifa} entities in the database.
 * The main input is a {@link BaseTarifaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BaseTarifaDTO} or a {@link Page} of {@link BaseTarifaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BaseTarifaQueryService extends QueryService<BaseTarifa> {

    private final Logger log = LoggerFactory.getLogger(BaseTarifaQueryService.class);

    private final BaseTarifaRepository baseTarifaRepository;

    private final BaseTarifaMapper baseTarifaMapper;

    public BaseTarifaQueryService(BaseTarifaRepository baseTarifaRepository, BaseTarifaMapper baseTarifaMapper) {
        this.baseTarifaRepository = baseTarifaRepository;
        this.baseTarifaMapper = baseTarifaMapper;
    }

    /**
     * Return a {@link List} of {@link BaseTarifaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BaseTarifaDTO> findByCriteria(BaseTarifaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BaseTarifa> specification = createSpecification(criteria);
        return baseTarifaMapper.toDto(baseTarifaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BaseTarifaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BaseTarifaDTO> findByCriteria(BaseTarifaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BaseTarifa> specification = createSpecification(criteria);
        return baseTarifaRepository.findAll(specification, page)
            .map(baseTarifaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BaseTarifaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BaseTarifa> specification = createSpecification(criteria);
        return baseTarifaRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<BaseTarifa> createSpecification(BaseTarifaCriteria criteria) {
        Specification<BaseTarifa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BaseTarifa_.id));
            }
            if (criteria.getBase() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBase(), BaseTarifa_.base));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(BaseTarifa_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
        }
        return specification;
    }
}
