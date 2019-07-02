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

import com.kode.ts.domain.Municipio;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.MunicipioRepository;
import com.kode.ts.service.dto.MunicipioCriteria;
import com.kode.ts.service.dto.MunicipioDTO;
import com.kode.ts.service.mapper.MunicipioMapper;

/**
 * Service for executing complex queries for {@link Municipio} entities in the database.
 * The main input is a {@link MunicipioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MunicipioDTO} or a {@link Page} of {@link MunicipioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MunicipioQueryService extends QueryService<Municipio> {

    private final Logger log = LoggerFactory.getLogger(MunicipioQueryService.class);

    private final MunicipioRepository municipioRepository;

    private final MunicipioMapper municipioMapper;

    public MunicipioQueryService(MunicipioRepository municipioRepository, MunicipioMapper municipioMapper) {
        this.municipioRepository = municipioRepository;
        this.municipioMapper = municipioMapper;
    }

    /**
     * Return a {@link List} of {@link MunicipioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MunicipioDTO> findByCriteria(MunicipioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Municipio> specification = createSpecification(criteria);
        return municipioMapper.toDto(municipioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MunicipioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MunicipioDTO> findByCriteria(MunicipioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Municipio> specification = createSpecification(criteria);
        return municipioRepository.findAll(specification, page)
            .map(municipioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MunicipioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Municipio> specification = createSpecification(criteria);
        return municipioRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Municipio> createSpecification(MunicipioCriteria criteria) {
        Specification<Municipio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Municipio_.id));
            }
            if (criteria.getMunicipio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMunicipio(), Municipio_.municipio));
            }
            if (criteria.getColoniaId() != null) {
                specification = specification.and(buildSpecification(criteria.getColoniaId(),
                    root -> root.join(Municipio_.colonias, JoinType.LEFT).get(Colonia_.id)));
            }
            if (criteria.getCodigoPostalId() != null) {
                specification = specification.and(buildSpecification(criteria.getCodigoPostalId(),
                    root -> root.join(Municipio_.codigoPostals, JoinType.LEFT).get(CodigoPostal_.id)));
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoId(),
                    root -> root.join(Municipio_.estado, JoinType.LEFT).get(Estado_.id)));
            }
        }
        return specification;
    }
}
