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

import com.kode.ts.domain.Perfil;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.PerfilRepository;
import com.kode.ts.service.dto.PerfilCriteria;
import com.kode.ts.service.dto.PerfilDTO;
import com.kode.ts.service.mapper.PerfilMapper;

/**
 * Service for executing complex queries for {@link Perfil} entities in the database.
 * The main input is a {@link PerfilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PerfilDTO} or a {@link Page} of {@link PerfilDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerfilQueryService extends QueryService<Perfil> {

    private final Logger log = LoggerFactory.getLogger(PerfilQueryService.class);

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    public PerfilQueryService(PerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    /**
     * Return a {@link List} of {@link PerfilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PerfilDTO> findByCriteria(PerfilCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Perfil> specification = createSpecification(criteria);
        return perfilMapper.toDto(perfilRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PerfilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilDTO> findByCriteria(PerfilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Perfil> specification = createSpecification(criteria);
        return perfilRepository.findAll(specification, page)
            .map(perfilMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerfilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Perfil> specification = createSpecification(criteria);
        return perfilRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Perfil> createSpecification(PerfilCriteria criteria) {
        Specification<Perfil> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Perfil_.id));
            }
            if (criteria.getPerfil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPerfil(), Perfil_.perfil));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(Perfil_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(Perfil_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
        }
        return specification;
    }
}
