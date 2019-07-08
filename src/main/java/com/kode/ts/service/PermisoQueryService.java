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

import com.kode.ts.domain.Permiso;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.PermisoRepository;
import com.kode.ts.service.dto.PermisoCriteria;
import com.kode.ts.service.dto.PermisoDTO;
import com.kode.ts.service.mapper.PermisoMapper;

/**
 * Service for executing complex queries for {@link Permiso} entities in the database.
 * The main input is a {@link PermisoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PermisoDTO} or a {@link Page} of {@link PermisoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PermisoQueryService extends QueryService<Permiso> {

    private final Logger log = LoggerFactory.getLogger(PermisoQueryService.class);

    private final PermisoRepository permisoRepository;

    private final PermisoMapper permisoMapper;

    public PermisoQueryService(PermisoRepository permisoRepository, PermisoMapper permisoMapper) {
        this.permisoRepository = permisoRepository;
        this.permisoMapper = permisoMapper;
    }

    /**
     * Return a {@link List} of {@link PermisoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PermisoDTO> findByCriteria(PermisoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Permiso> specification = createSpecification(criteria);
        return permisoMapper.toDto(permisoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PermisoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PermisoDTO> findByCriteria(PermisoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Permiso> specification = createSpecification(criteria);
        return permisoRepository.findAll(specification, page)
            .map(permisoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PermisoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Permiso> specification = createSpecification(criteria);
        return permisoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Permiso> createSpecification(PermisoCriteria criteria) {
        Specification<Permiso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Permiso_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Permiso_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Permiso_.descripcion));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), Permiso_.activated));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), Permiso_.deleted));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Permiso_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Permiso_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Permiso_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Permiso_.lastModifiedDate));
            }
        }
        return specification;
    }
}
