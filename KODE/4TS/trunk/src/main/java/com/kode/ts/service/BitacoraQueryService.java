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

import com.kode.ts.domain.Bitacora;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.BitacoraRepository;
import com.kode.ts.service.dto.BitacoraCriteria;
import com.kode.ts.service.dto.BitacoraDTO;
import com.kode.ts.service.mapper.BitacoraMapper;

/**
 * Service for executing complex queries for {@link Bitacora} entities in the database.
 * The main input is a {@link BitacoraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BitacoraDTO} or a {@link Page} of {@link BitacoraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BitacoraQueryService extends QueryService<Bitacora> {

    private final Logger log = LoggerFactory.getLogger(BitacoraQueryService.class);

    private final BitacoraRepository bitacoraRepository;

    private final BitacoraMapper bitacoraMapper;

    public BitacoraQueryService(BitacoraRepository bitacoraRepository, BitacoraMapper bitacoraMapper) {
        this.bitacoraRepository = bitacoraRepository;
        this.bitacoraMapper = bitacoraMapper;
    }

    /**
     * Return a {@link List} of {@link BitacoraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BitacoraDTO> findByCriteria(BitacoraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bitacora> specification = createSpecification(criteria);
        return bitacoraMapper.toDto(bitacoraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BitacoraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BitacoraDTO> findByCriteria(BitacoraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bitacora> specification = createSpecification(criteria);
        return bitacoraRepository.findAll(specification, page)
            .map(bitacoraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BitacoraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bitacora> specification = createSpecification(criteria);
        return bitacoraRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Bitacora> createSpecification(BitacoraCriteria criteria) {
        Specification<Bitacora> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Bitacora_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Bitacora_.fecha));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), Bitacora_.comentario));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(Bitacora_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequerimientoId(),
                    root -> root.join(Bitacora_.requerimiento, JoinType.LEFT).get(Requerimiento_.id)));
            }
            if (criteria.getCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatoId(),
                    root -> root.join(Bitacora_.candidato, JoinType.LEFT).get(Candidato_.id)));
            }
            if (criteria.getTareaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTareaId(),
                    root -> root.join(Bitacora_.tarea, JoinType.LEFT).get(Tarea_.id)));
            }
        }
        return specification;
    }
}
