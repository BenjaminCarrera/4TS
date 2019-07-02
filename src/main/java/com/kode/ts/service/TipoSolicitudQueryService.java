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

import com.kode.ts.domain.TipoSolicitud;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.TipoSolicitudRepository;
import com.kode.ts.service.dto.TipoSolicitudCriteria;
import com.kode.ts.service.dto.TipoSolicitudDTO;
import com.kode.ts.service.mapper.TipoSolicitudMapper;

/**
 * Service for executing complex queries for {@link TipoSolicitud} entities in the database.
 * The main input is a {@link TipoSolicitudCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoSolicitudDTO} or a {@link Page} of {@link TipoSolicitudDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoSolicitudQueryService extends QueryService<TipoSolicitud> {

    private final Logger log = LoggerFactory.getLogger(TipoSolicitudQueryService.class);

    private final TipoSolicitudRepository tipoSolicitudRepository;

    private final TipoSolicitudMapper tipoSolicitudMapper;

    public TipoSolicitudQueryService(TipoSolicitudRepository tipoSolicitudRepository, TipoSolicitudMapper tipoSolicitudMapper) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
        this.tipoSolicitudMapper = tipoSolicitudMapper;
    }

    /**
     * Return a {@link List} of {@link TipoSolicitudDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoSolicitudDTO> findByCriteria(TipoSolicitudCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoSolicitud> specification = createSpecification(criteria);
        return tipoSolicitudMapper.toDto(tipoSolicitudRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoSolicitudDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoSolicitudDTO> findByCriteria(TipoSolicitudCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoSolicitud> specification = createSpecification(criteria);
        return tipoSolicitudRepository.findAll(specification, page)
            .map(tipoSolicitudMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoSolicitudCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoSolicitud> specification = createSpecification(criteria);
        return tipoSolicitudRepository.count(specification);
    }

    /**
     * Function to convert TipoSolicitudCriteria to a {@link Specification}.
     */
    private Specification<TipoSolicitud> createSpecification(TipoSolicitudCriteria criteria) {
        Specification<TipoSolicitud> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoSolicitud_.id));
            }
            if (criteria.getSolicitud() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSolicitud(), TipoSolicitud_.solicitud));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(TipoSolicitud_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
        }
        return specification;
    }
}
