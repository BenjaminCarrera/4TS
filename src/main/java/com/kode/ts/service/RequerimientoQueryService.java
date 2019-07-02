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

import com.kode.ts.domain.Requerimiento;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.RequerimientoRepository;
import com.kode.ts.service.dto.RequerimientoCriteria;
import com.kode.ts.service.dto.RequerimientoDTO;
import com.kode.ts.service.mapper.RequerimientoMapper;

/**
 * Service for executing complex queries for {@link Requerimiento} entities in the database.
 * The main input is a {@link RequerimientoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RequerimientoDTO} or a {@link Page} of {@link RequerimientoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequerimientoQueryService extends QueryService<Requerimiento> {

    private final Logger log = LoggerFactory.getLogger(RequerimientoQueryService.class);

    private final RequerimientoRepository requerimientoRepository;

    private final RequerimientoMapper requerimientoMapper;

    public RequerimientoQueryService(RequerimientoRepository requerimientoRepository, RequerimientoMapper requerimientoMapper) {
        this.requerimientoRepository = requerimientoRepository;
        this.requerimientoMapper = requerimientoMapper;
    }

    /**
     * Return a {@link List} of {@link RequerimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RequerimientoDTO> findByCriteria(RequerimientoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Requerimiento> specification = createSpecification(criteria);
        return requerimientoMapper.toDto(requerimientoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RequerimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RequerimientoDTO> findByCriteria(RequerimientoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Requerimiento> specification = createSpecification(criteria);
        return requerimientoRepository.findAll(specification, page)
            .map(requerimientoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequerimientoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Requerimiento> specification = createSpecification(criteria);
        return requerimientoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Requerimiento> createSpecification(RequerimientoCriteria criteria) {
        Specification<Requerimiento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Requerimiento_.id));
            }
            if (criteria.getFechaAlda() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAlda(), Requerimiento_.fechaAlda));
            }
            if (criteria.getFechaResolucion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaResolucion(), Requerimiento_.fechaResolucion));
            }
            if (criteria.getRemplazoDe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemplazoDe(), Requerimiento_.remplazoDe));
            }
            if (criteria.getVacantesSolicitadas() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVacantesSolicitadas(), Requerimiento_.vacantesSolicitadas));
            }
            if (criteria.getProyecto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProyecto(), Requerimiento_.proyecto));
            }
            if (criteria.getNombreContacto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreContacto(), Requerimiento_.nombreContacto));
            }
            if (criteria.getTarifaSueldoNet() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifaSueldoNet(), Requerimiento_.tarifaSueldoNet));
            }
            if (criteria.getPrestaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrestaciones(), Requerimiento_.prestaciones));
            }
            if (criteria.getDuracionAsignacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuracionAsignacion(), Requerimiento_.duracionAsignacion));
            }
            if (criteria.getLugarTrabajo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLugarTrabajo(), Requerimiento_.lugarTrabajo));
            }
            if (criteria.getCoorLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoorLat(), Requerimiento_.coorLat));
            }
            if (criteria.getCoorLong() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoorLong(), Requerimiento_.coorLong));
            }
            if (criteria.getHorario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHorario(), Requerimiento_.horario));
            }
            if (criteria.getInformacionExamen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInformacionExamen(), Requerimiento_.informacionExamen));
            }
            if (criteria.getInformacionAdicional() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInformacionAdicional(), Requerimiento_.informacionAdicional));
            }
            if (criteria.getSkillRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillRequerimientoId(),
                    root -> root.join(Requerimiento_.skillRequerimientos, JoinType.LEFT).get(SkillRequerimiento_.id)));
            }
            if (criteria.getTareaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTareaId(),
                    root -> root.join(Requerimiento_.tareas, JoinType.LEFT).get(Tarea_.id)));
            }
            if (criteria.getBitacoraId() != null) {
                specification = specification.and(buildSpecification(criteria.getBitacoraId(),
                    root -> root.join(Requerimiento_.bitacoras, JoinType.LEFT).get(Bitacora_.id)));
            }
            if (criteria.getCuentaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCuentaId(),
                    root -> root.join(Requerimiento_.cuenta, JoinType.LEFT).get(Cuenta_.id)));
            }
            if (criteria.getSubCuentaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubCuentaId(),
                    root -> root.join(Requerimiento_.subCuenta, JoinType.LEFT).get(Cuenta_.id)));
            }
            if (criteria.getUsuarioCreadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioCreadorId(),
                    root -> root.join(Requerimiento_.usuarioCreador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUsuarioAsignadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioAsignadoId(),
                    root -> root.join(Requerimiento_.usuarioAsignado, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEstatusRequerimientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusRequerimientoId(),
                    root -> root.join(Requerimiento_.estatusRequerimiento, JoinType.LEFT).get(EstatusRequerimiento_.id)));
            }
            if (criteria.getPrioridadId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrioridadId(),
                    root -> root.join(Requerimiento_.prioridad, JoinType.LEFT).get(PrioridadReq_.id)));
            }
            if (criteria.getTipoSolicitudId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoSolicitudId(),
                    root -> root.join(Requerimiento_.tipoSolicitud, JoinType.LEFT).get(TipoSolicitud_.id)));
            }
            if (criteria.getTipoIngresoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoIngresoId(),
                    root -> root.join(Requerimiento_.tipoIngreso, JoinType.LEFT).get(TipoIngreso_.id)));
            }
            if (criteria.getBaseTarifaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBaseTarifaId(),
                    root -> root.join(Requerimiento_.baseTarifa, JoinType.LEFT).get(BaseTarifa_.id)));
            }
            if (criteria.getEsquemaContratacionId() != null) {
                specification = specification.and(buildSpecification(criteria.getEsquemaContratacionId(),
                    root -> root.join(Requerimiento_.esquemaContratacion, JoinType.LEFT).get(EsqContRec_.id)));
            }
            if (criteria.getPerfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getPerfilId(),
                    root -> root.join(Requerimiento_.perfil, JoinType.LEFT).get(Perfil_.id)));
            }
            if (criteria.getNivelPerfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getNivelPerfilId(),
                    root -> root.join(Requerimiento_.nivelPerfil, JoinType.LEFT).get(NivelPerfil_.id)));
            }
            if (criteria.getEstatusReqCanId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusReqCanId(),
                    root -> root.join(Requerimiento_.estatusReqCan, JoinType.LEFT).get(EstReqCerrado_.id)));
            }
            if (criteria.getTipoPeriodoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoPeriodoId(),
                    root -> root.join(Requerimiento_.tipoPeriodo, JoinType.LEFT).get(TipoPeriodo_.id)));
            }
        }
        return specification;
    }
}
