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

import com.kode.ts.domain.NivelPerfil;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.NivelPerfilRepository;
import com.kode.ts.service.dto.NivelPerfilCriteria;
import com.kode.ts.service.dto.NivelPerfilDTO;
import com.kode.ts.service.mapper.NivelPerfilMapper;

/**
 * Service for executing complex queries for {@link NivelPerfil} entities in the database.
 * The main input is a {@link NivelPerfilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NivelPerfilDTO} or a {@link Page} of {@link NivelPerfilDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NivelPerfilQueryService extends QueryService<NivelPerfil> {

    private final Logger log = LoggerFactory.getLogger(NivelPerfilQueryService.class);

    private final NivelPerfilRepository nivelPerfilRepository;

    private final NivelPerfilMapper nivelPerfilMapper;

    public NivelPerfilQueryService(NivelPerfilRepository nivelPerfilRepository, NivelPerfilMapper nivelPerfilMapper) {
        this.nivelPerfilRepository = nivelPerfilRepository;
        this.nivelPerfilMapper = nivelPerfilMapper;
    }

    /**
     * Return a {@link List} of {@link NivelPerfilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NivelPerfilDTO> findByCriteria(NivelPerfilCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NivelPerfil> specification = createSpecification(criteria);
        return nivelPerfilMapper.toDto(nivelPerfilRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NivelPerfilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NivelPerfilDTO> findByCriteria(NivelPerfilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NivelPerfil> specification = createSpecification(criteria);
        return nivelPerfilRepository.findAll(specification, page)
            .map(nivelPerfilMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NivelPerfilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NivelPerfil> specification = createSpecification(criteria);
        return nivelPerfilRepository.count(specification);
    }

    /**
     * Function to convert NivelPerfilCriteria to a {@link Specification}.
     */
    private Specification<NivelPerfil> createSpecification(NivelPerfilCriteria criteria) {
        Specification<NivelPerfil> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NivelPerfil_.id));
            }
            if (criteria.getNivel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNivel(), NivelPerfil_.nivel));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(NivelPerfil_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(NivelPerfil_.requerimientos, JoinType.LEFT).get(Requerimiento_.id)));
            }
        }
        return specification;
    }
}
