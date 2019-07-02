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

import com.kode.ts.domain.Colonia;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.ColoniaRepository;
import com.kode.ts.service.dto.ColoniaCriteria;
import com.kode.ts.service.dto.ColoniaDTO;
import com.kode.ts.service.mapper.ColoniaMapper;

/**
 * Service for executing complex queries for {@link Colonia} entities in the database.
 * The main input is a {@link ColoniaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ColoniaDTO} or a {@link Page} of {@link ColoniaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ColoniaQueryService extends QueryService<Colonia> {

    private final Logger log = LoggerFactory.getLogger(ColoniaQueryService.class);

    private final ColoniaRepository coloniaRepository;

    private final ColoniaMapper coloniaMapper;

    public ColoniaQueryService(ColoniaRepository coloniaRepository, ColoniaMapper coloniaMapper) {
        this.coloniaRepository = coloniaRepository;
        this.coloniaMapper = coloniaMapper;
    }

    /**
     * Return a {@link List} of {@link ColoniaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ColoniaDTO> findByCriteria(ColoniaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Colonia> specification = createSpecification(criteria);
        return coloniaMapper.toDto(coloniaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ColoniaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ColoniaDTO> findByCriteria(ColoniaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Colonia> specification = createSpecification(criteria);
        return coloniaRepository.findAll(specification, page)
            .map(coloniaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ColoniaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Colonia> specification = createSpecification(criteria);
        return coloniaRepository.count(specification);
    }

    /**
     * Function to convert ColoniaCriteria to a {@link Specification}.
     */
    private Specification<Colonia> createSpecification(ColoniaCriteria criteria) {
        Specification<Colonia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Colonia_.id));
            }
            if (criteria.getColonia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColonia(), Colonia_.colonia));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(Colonia_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getMunicipioId() != null) {
                specification = specification.and(buildSpecification(criteria.getMunicipioId(),
                    root -> root.join(Colonia_.municipio, JoinType.LEFT).get(Municipio_.id)));
            }
            if (criteria.getCodigoPostalId() != null) {
                specification = specification.and(buildSpecification(criteria.getCodigoPostalId(),
                    root -> root.join(Colonia_.codigoPostal, JoinType.LEFT).get(CodigoPostal_.id)));
            }
        }
        return specification;
    }
}
