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

import com.kode.ts.domain.PermisoAuthority;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.PermisoAuthorityRepository;
import com.kode.ts.service.dto.PermisoAuthorityCriteria;
import com.kode.ts.service.dto.PermisoAuthorityDTO;
import com.kode.ts.service.mapper.PermisoAuthorityMapper;

/**
 * Service for executing complex queries for {@link PermisoAuthority} entities in the database.
 * The main input is a {@link PermisoAuthorityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PermisoAuthorityDTO} or a {@link Page} of {@link PermisoAuthorityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PermisoAuthorityQueryService extends QueryService<PermisoAuthority> {

    private final Logger log = LoggerFactory.getLogger(PermisoAuthorityQueryService.class);

    private final PermisoAuthorityRepository permisoAuthorityRepository;

    private final PermisoAuthorityMapper permisoAuthorityMapper;

    public PermisoAuthorityQueryService(PermisoAuthorityRepository permisoAuthorityRepository, PermisoAuthorityMapper permisoAuthorityMapper) {
        this.permisoAuthorityRepository = permisoAuthorityRepository;
        this.permisoAuthorityMapper = permisoAuthorityMapper;
    }

    /**
     * Return a {@link List} of {@link PermisoAuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PermisoAuthorityDTO> findByCriteria(PermisoAuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PermisoAuthority> specification = createSpecification(criteria);
        return permisoAuthorityMapper.toDto(permisoAuthorityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PermisoAuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PermisoAuthorityDTO> findByCriteria(PermisoAuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PermisoAuthority> specification = createSpecification(criteria);
        return permisoAuthorityRepository.findAll(specification, page)
            .map(permisoAuthorityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PermisoAuthorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PermisoAuthority> specification = createSpecification(criteria);
        return permisoAuthorityRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<PermisoAuthority> createSpecification(PermisoAuthorityCriteria criteria) {
        Specification<PermisoAuthority> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PermisoAuthority_.id));
            }
            if (criteria.getAuthority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthority(), PermisoAuthority_.authority));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), PermisoAuthority_.activated));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), PermisoAuthority_.deleted));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), PermisoAuthority_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), PermisoAuthority_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PermisoAuthority_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), PermisoAuthority_.lastModifiedDate));
            }
            if (criteria.getPermisoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPermisoId(),
                    root -> root.join(PermisoAuthority_.permiso, JoinType.LEFT).get(Permiso_.id)));
            }
        }
        return specification;
    }
}
