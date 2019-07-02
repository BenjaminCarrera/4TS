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

import com.kode.ts.domain.TipoIngreso;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.TipoIngresoRepository;
import com.kode.ts.service.dto.TipoIngresoCriteria;
import com.kode.ts.service.dto.TipoIngresoDTO;
import com.kode.ts.service.mapper.TipoIngresoMapper;

/**
 * Service for executing complex queries for {@link TipoIngreso} entities in the database.
 * The main input is a {@link TipoIngresoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoIngresoDTO} or a {@link Page} of {@link TipoIngresoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoIngresoQueryService extends QueryService<TipoIngreso> {

    private final Logger log = LoggerFactory.getLogger(TipoIngresoQueryService.class);

    private final TipoIngresoRepository tipoIngresoRepository;

    private final TipoIngresoMapper tipoIngresoMapper;

    public TipoIngresoQueryService(TipoIngresoRepository tipoIngresoRepository, TipoIngresoMapper tipoIngresoMapper) {
        this.tipoIngresoRepository = tipoIngresoRepository;
        this.tipoIngresoMapper = tipoIngresoMapper;
    }

    /**
     * Return a {@link List} of {@link TipoIngresoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoIngresoDTO> findByCriteria(TipoIngresoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoIngreso> specification = createSpecification(criteria);
        return tipoIngresoMapper.toDto(tipoIngresoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoIngresoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoIngresoDTO> findByCriteria(TipoIngresoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoIngreso> specification = createSpecification(criteria);
        return tipoIngresoRepository.findAll(specification, page)
            .map(tipoIngresoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoIngresoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoIngreso> specification = createSpecification(criteria);
        return tipoIngresoRepository.count(specification);
    }

    /**
     * Function to convert TipoIngresoCriteria to a {@link Specification}.
     */
    private Specification<TipoIngreso> createSpecification(TipoIngresoCriteria criteria) {
        Specification<TipoIngreso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoIngreso_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), TipoIngreso_.tipo));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(TipoIngreso_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
        }
        return specification;
    }
}
