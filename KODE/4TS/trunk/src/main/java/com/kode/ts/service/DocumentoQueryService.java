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

import com.kode.ts.domain.Documento;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.DocumentoRepository;
import com.kode.ts.service.dto.DocumentoCriteria;
import com.kode.ts.service.dto.DocumentoDTO;
import com.kode.ts.service.mapper.DocumentoMapper;

/**
 * Service for executing complex queries for {@link Documento} entities in the database.
 * The main input is a {@link DocumentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentoDTO} or a {@link Page} of {@link DocumentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentoQueryService extends QueryService<Documento> {

    private final Logger log = LoggerFactory.getLogger(DocumentoQueryService.class);

    private final DocumentoRepository documentoRepository;

    private final DocumentoMapper documentoMapper;

    public DocumentoQueryService(DocumentoRepository documentoRepository, DocumentoMapper documentoMapper) {
        this.documentoRepository = documentoRepository;
        this.documentoMapper = documentoMapper;
    }

    /**
     * Return a {@link List} of {@link DocumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentoDTO> findByCriteria(DocumentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Documento> specification = createSpecification(criteria);
        return documentoMapper.toDto(documentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoDTO> findByCriteria(DocumentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Documento> specification = createSpecification(criteria);
        return documentoRepository.findAll(specification, page)
            .map(documentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Documento> specification = createSpecification(criteria);
        return documentoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Documento> createSpecification(DocumentoCriteria criteria) {
        Specification<Documento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Documento_.id));
            }
            if (criteria.getDocumento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumento(), Documento_.documento));
            }
        }
        return specification;
    }
}
