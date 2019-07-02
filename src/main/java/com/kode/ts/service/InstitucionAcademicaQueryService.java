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

import com.kode.ts.domain.InstitucionAcademica;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.InstitucionAcademicaRepository;
import com.kode.ts.service.dto.InstitucionAcademicaCriteria;
import com.kode.ts.service.dto.InstitucionAcademicaDTO;
import com.kode.ts.service.mapper.InstitucionAcademicaMapper;

/**
 * Service for executing complex queries for {@link InstitucionAcademica} entities in the database.
 * The main input is a {@link InstitucionAcademicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InstitucionAcademicaDTO} or a {@link Page} of {@link InstitucionAcademicaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InstitucionAcademicaQueryService extends QueryService<InstitucionAcademica> {

    private final Logger log = LoggerFactory.getLogger(InstitucionAcademicaQueryService.class);

    private final InstitucionAcademicaRepository institucionAcademicaRepository;

    private final InstitucionAcademicaMapper institucionAcademicaMapper;

    public InstitucionAcademicaQueryService(InstitucionAcademicaRepository institucionAcademicaRepository, InstitucionAcademicaMapper institucionAcademicaMapper) {
        this.institucionAcademicaRepository = institucionAcademicaRepository;
        this.institucionAcademicaMapper = institucionAcademicaMapper;
    }

    /**
     * Return a {@link List} of {@link InstitucionAcademicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InstitucionAcademicaDTO> findByCriteria(InstitucionAcademicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InstitucionAcademica> specification = createSpecification(criteria);
        return institucionAcademicaMapper.toDto(institucionAcademicaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InstitucionAcademicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InstitucionAcademicaDTO> findByCriteria(InstitucionAcademicaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InstitucionAcademica> specification = createSpecification(criteria);
        return institucionAcademicaRepository.findAll(specification, page)
            .map(institucionAcademicaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InstitucionAcademicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InstitucionAcademica> specification = createSpecification(criteria);
        return institucionAcademicaRepository.count(specification);
    }

    /**
     * Function to convert InstitucionAcademicaCriteria to a {@link Specification}.
     */
    private Specification<InstitucionAcademica> createSpecification(InstitucionAcademicaCriteria criteria) {
        Specification<InstitucionAcademica> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), InstitucionAcademica_.id));
            }
            if (criteria.getInstitucion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstitucion(), InstitucionAcademica_.institucion));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(InstitucionAcademica_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
        }
        return specification;
    }
}
