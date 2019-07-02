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

import com.kode.ts.domain.Tarea;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.TareaRepository;
import com.kode.ts.service.dto.TareaCriteria;
import com.kode.ts.service.dto.TareaDTO;
import com.kode.ts.service.mapper.TareaMapper;

/**
 * Service for executing complex queries for {@link Tarea} entities in the database.
 * The main input is a {@link TareaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TareaDTO} or a {@link Page} of {@link TareaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TareaQueryService extends QueryService<Tarea> {

    private final Logger log = LoggerFactory.getLogger(TareaQueryService.class);

    private final TareaRepository tareaRepository;

    private final TareaMapper tareaMapper;

    public TareaQueryService(TareaRepository tareaRepository, TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
    }

    /**
     * Return a {@link List} of {@link TareaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TareaDTO> findByCriteria(TareaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tarea> specification = createSpecification(criteria);
        return tareaMapper.toDto(tareaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TareaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TareaDTO> findByCriteria(TareaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tarea> specification = createSpecification(criteria);
        return tareaRepository.findAll(specification, page)
            .map(tareaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TareaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tarea> specification = createSpecification(criteria);
        return tareaRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Tarea> createSpecification(TareaCriteria criteria) {
        Specification<Tarea> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Tarea_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Tarea_.descripcion));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Tarea_.titulo));
            }
            if (criteria.getBitacoraId() != null) {
                specification = specification.and(buildSpecification(criteria.getBitacoraId(),
                    root -> root.join(Tarea_.bitacoras, JoinType.LEFT).get(Bitacora_.id)));
            }
            if (criteria.getUsuarioCreadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioCreadorId(),
                    root -> root.join(Tarea_.usuarioCreador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUsuarioEjecutorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioEjecutorId(),
                    root -> root.join(Tarea_.usuarioEjecutor, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(Tarea_.requerimiento, JoinType.LEFT).get(Requerimiento_.id)));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(Tarea_.candidato, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getEstatusTareaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusTareaId(),
                    root -> root.join(Tarea_.estatusTarea, JoinType.LEFT).get(EstatusTarea_.id)));
            }
            if (criteria.getTipoTareaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoTareaId(),
                    root -> root.join(Tarea_.tipoTarea, JoinType.LEFT).get(TipoTarea_.id)));
            }
        }
        return specification;
    }
}
