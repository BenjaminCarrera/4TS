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

import com.kode.ts.domain.FormacionAcademica;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.FormacionAcademicaRepository;
import com.kode.ts.service.dto.FormacionAcademicaCriteria;
import com.kode.ts.service.dto.FormacionAcademicaDTO;
import com.kode.ts.service.mapper.FormacionAcademicaMapper;

/**
 * Service for executing complex queries for {@link FormacionAcademica} entities in the database.
 * The main input is a {@link FormacionAcademicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FormacionAcademicaDTO} or a {@link Page} of {@link FormacionAcademicaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FormacionAcademicaQueryService extends QueryService<FormacionAcademica> {

    private final Logger log = LoggerFactory.getLogger(FormacionAcademicaQueryService.class);

    private final FormacionAcademicaRepository formacionAcademicaRepository;

    private final FormacionAcademicaMapper formacionAcademicaMapper;

    public FormacionAcademicaQueryService(FormacionAcademicaRepository formacionAcademicaRepository, FormacionAcademicaMapper formacionAcademicaMapper) {
        this.formacionAcademicaRepository = formacionAcademicaRepository;
        this.formacionAcademicaMapper = formacionAcademicaMapper;
    }

    /**
     * Return a {@link List} of {@link FormacionAcademicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FormacionAcademicaDTO> findByCriteria(FormacionAcademicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FormacionAcademica> specification = createSpecification(criteria);
        return formacionAcademicaMapper.toDto(formacionAcademicaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FormacionAcademicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FormacionAcademicaDTO> findByCriteria(FormacionAcademicaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FormacionAcademica> specification = createSpecification(criteria);
        return formacionAcademicaRepository.findAll(specification, page)
            .map(formacionAcademicaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FormacionAcademicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FormacionAcademica> specification = createSpecification(criteria);
        return formacionAcademicaRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<FormacionAcademica> createSpecification(FormacionAcademicaCriteria criteria) {
        Specification<FormacionAcademica> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FormacionAcademica_.id));
            }
            if (criteria.getFormacionAcademica() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormacionAcademica(), FormacionAcademica_.formacionAcademica));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(FormacionAcademica_.candidatoes, JoinType.LEFT).get(Candidato_.id)));
            }
        }
        return specification;
    }
}
