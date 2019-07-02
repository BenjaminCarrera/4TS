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

import com.kode.ts.domain.Candidato;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.CandidatoRepository;
import com.kode.ts.service.dto.CandidatoCriteria;
import com.kode.ts.service.dto.CandidatoDTO;
import com.kode.ts.service.mapper.CandidatoMapper;

/**
 * Service for executing complex queries for {@link Candidato} entities in the database.
 * The main input is a {@link CandidatoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CandidatoDTO} or a {@link Page} of {@link CandidatoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CandidatoQueryService extends QueryService<Candidato> {

    private final Logger log = LoggerFactory.getLogger(CandidatoQueryService.class);

    private final CandidatoRepository candidatoRepository;

    private final CandidatoMapper candidatoMapper;

    public CandidatoQueryService(CandidatoRepository candidatoRepository, CandidatoMapper candidatoMapper) {
        this.candidatoRepository = candidatoRepository;
        this.candidatoMapper = candidatoMapper;
    }

    /**
     * Return a {@link List} of {@link CandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CandidatoDTO> findByCriteria(CandidatoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Candidato> specification = createSpecification(criteria);
        return candidatoMapper.toDto(candidatoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CandidatoDTO> findByCriteria(CandidatoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Candidato> specification = createSpecification(criteria);
        return candidatoRepository.findAll(specification, page)
            .map(candidatoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CandidatoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Candidato> specification = createSpecification(criteria);
        return candidatoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Candidato> createSpecification(CandidatoCriteria criteria) {
        Specification<Candidato> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Candidato_.id));
            }
            if (criteria.getAnosExperiencia() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnosExperiencia(), Candidato_.anosExperiencia));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Candidato_.nombre));
            }
            if (criteria.getApellidoPaterno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellidoPaterno(), Candidato_.apellidoPaterno));
            }
            if (criteria.getApellidoMaterno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellidoMaterno(), Candidato_.apellidoMaterno));
            }
            if (criteria.getFechaNacimiento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaNacimiento(), Candidato_.fechaNacimiento));
            }
            if (criteria.getEdad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEdad(), Candidato_.edad));
            }
            if (criteria.getEmailPrincipal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailPrincipal(), Candidato_.emailPrincipal));
            }
            if (criteria.getEmailAdicional() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAdicional(), Candidato_.emailAdicional));
            }
            if (criteria.getEmailAsignacion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAsignacion(), Candidato_.emailAsignacion));
            }
            if (criteria.getEmailKode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailKode(), Candidato_.emailKode));
            }
            if (criteria.getWeb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWeb(), Candidato_.web));
            }
            if (criteria.getTelefonoCasa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonoCasa(), Candidato_.telefonoCasa));
            }
            if (criteria.getTelefonoTrabajo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonoTrabajo(), Candidato_.telefonoTrabajo));
            }
            if (criteria.getTelefonoCelular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonoCelular(), Candidato_.telefonoCelular));
            }
            if (criteria.getTelefonoAdicional() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonoAdicional(), Candidato_.telefonoAdicional));
            }
            if (criteria.getCoorLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoorLat(), Candidato_.coorLat));
            }
            if (criteria.getCoorLong() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoorLong(), Candidato_.coorLong));
            }
            if (criteria.getDirCodigoPostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDirCodigoPostal(), Candidato_.dirCodigoPostal));
            }
            if (criteria.getDirCalle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDirCalle(), Candidato_.dirCalle));
            }
            if (criteria.getNoExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoExt(), Candidato_.noExt));
            }
            if (criteria.getNoInt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoInt(), Candidato_.noInt));
            }
            if (criteria.getSalarioNeto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalarioNeto(), Candidato_.salarioNeto));
            }
            if (criteria.getCostoTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostoTotal(), Candidato_.costoTotal));
            }
            if (criteria.getContatoDuracionMinima() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContatoDuracionMinima(), Candidato_.contatoDuracionMinima));
            }
            if (criteria.getDisponibilidadEntrevistaFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisponibilidadEntrevistaFecha(), Candidato_.disponibilidadEntrevistaFecha));
            }
            if (criteria.getDisponibilidadEntrevistaPeriodo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisponibilidadEntrevistaPeriodo(), Candidato_.disponibilidadEntrevistaPeriodo));
            }
            if (criteria.getDisponibilidadAsignacionFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisponibilidadAsignacionFecha(), Candidato_.disponibilidadAsignacionFecha));
            }
            if (criteria.getDisponibilidadAsignacionPeriodo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisponibilidadAsignacionPeriodo(), Candidato_.disponibilidadAsignacionPeriodo));
            }
            if (criteria.getAntecedenteUltimoEmpleador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAntecedenteUltimoEmpleador(), Candidato_.antecedenteUltimoEmpleador));
            }
            if (criteria.getAntecedenteSalarioNeto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAntecedenteSalarioNeto(), Candidato_.antecedenteSalarioNeto));
            }
            if (criteria.getNotas() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotas(), Candidato_.notas));
            }
            if (criteria.getPorcentajeIngles() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorcentajeIngles(), Candidato_.porcentajeIngles));
            }
            if (criteria.getCurp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurp(), Candidato_.curp));
            }
            if (criteria.getRfc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRfc(), Candidato_.rfc));
            }
            if (criteria.getNss() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNss(), Candidato_.nss));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildSpecification(criteria.getSexo(), Candidato_.sexo));
            }
            if (criteria.getEstadoCivil() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoCivil(), Candidato_.estadoCivil));
            }
            if (criteria.getFechaAlta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAlta(), Candidato_.fechaAlta));
            }
            if (criteria.getFechaUltimoSeguimiento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaUltimoSeguimiento(), Candidato_.fechaUltimoSeguimiento));
            }
            if (criteria.getFoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFoto(), Candidato_.foto));
            }
            if (criteria.getReferenciasLaboralesId() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenciasLaboralesId(),
                    root -> root.join(Candidato_.referenciasLaborales, JoinType.LEFT).get(ReferenciasLaborales_.id)));
            }
            if (criteria.getSkillCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillCandidatoId(),
                    root -> root.join(Candidato_.skillCandidatoes, JoinType.LEFT).get(SkillCandidato_.id)));
            }
            if (criteria.getTareaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTareaId(),
                    root -> root.join(Candidato_.tareas, JoinType.LEFT).get(Tarea_.id)));
            }
            if (criteria.getBitacoraId() != null) {
                specification = specification.and(buildSpecification(criteria.getBitacoraId(),
                    root -> root.join(Candidato_.bitacoras, JoinType.LEFT).get(Bitacora_.id)));
            }
            if (criteria.getDisponibilidadEntrevistaPeriodoTiempoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisponibilidadEntrevistaPeriodoTiempoId(),
                    root -> root.join(Candidato_.disponibilidadEntrevistaPeriodoTiempo, JoinType.LEFT).get(TipoPeriodo_.id)));
            }
            if (criteria.getDisponibilidadAsignacionPeriodoTiempoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisponibilidadAsignacionPeriodoTiempoId(),
                    root -> root.join(Candidato_.disponibilidadAsignacionPeriodoTiempo, JoinType.LEFT).get(TipoPeriodo_.id)));
            }
            if (criteria.getUsuarioCreadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioCreadorId(),
                    root -> root.join(Candidato_.usuarioCreador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUsuarioAsignadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioAsignadoId(),
                    root -> root.join(Candidato_.usuarioAsignado, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getDocumentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentoId(),
                    root -> root.join(Candidato_.documento, JoinType.LEFT).get(Documento_.id)));
            }
            if (criteria.getCuentaInteresId() != null) {
                specification = specification.and(buildSpecification(criteria.getCuentaInteresId(),
                    root -> root.join(Candidato_.cuentaInteres, JoinType.LEFT).get(Cuenta_.id)));
            }
            if (criteria.getCuentaRechazadasId() != null) {
                specification = specification.and(buildSpecification(criteria.getCuentaRechazadasId(),
                    root -> root.join(Candidato_.cuentaRechazadas, JoinType.LEFT).get(Cuenta_.id)));
            }
            if (criteria.getFuenteReclutamientoId() != null) {
                specification = specification.and(buildSpecification(criteria.getFuenteReclutamientoId(),
                    root -> root.join(Candidato_.fuenteReclutamiento, JoinType.LEFT).get(FuenteReclutamiento_.id)));
            }
            if (criteria.getEstatusCandidatoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusCandidatoId(),
                    root -> root.join(Candidato_.estatusCandidato, JoinType.LEFT).get(EstatusCandidato_.id)));
            }
            if (criteria.getPerfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getPerfilId(),
                    root -> root.join(Candidato_.perfil, JoinType.LEFT).get(Perfil_.id)));
            }
            if (criteria.getNivelPerfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getNivelPerfilId(),
                    root -> root.join(Candidato_.nivelPerfil, JoinType.LEFT).get(NivelPerfil_.id)));
            }
            if (criteria.getInstitucionAcademicaId() != null) {
                specification = specification.and(buildSpecification(criteria.getInstitucionAcademicaId(),
                    root -> root.join(Candidato_.institucionAcademica, JoinType.LEFT).get(InstitucionAcademica_.id)));
            }
            if (criteria.getEstatusAcademicoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusAcademicoId(),
                    root -> root.join(Candidato_.estatusAcademico, JoinType.LEFT).get(EstatusAcademico_.id)));
            }
            if (criteria.getEsquemaContratacionKodeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEsquemaContratacionKodeId(),
                    root -> root.join(Candidato_.esquemaContratacionKode, JoinType.LEFT).get(EsquemaContratacionKode_.id)));
            }
            if (criteria.getEstatusLaboralId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatusLaboralId(),
                    root -> root.join(Candidato_.estatusLaboral, JoinType.LEFT).get(EstatusLaboral_.id)));
            }
            if (criteria.getColoniaId() != null) {
                specification = specification.and(buildSpecification(criteria.getColoniaId(),
                    root -> root.join(Candidato_.colonia, JoinType.LEFT).get(Colonia_.id)));
            }
            if (criteria.getAntecedentesEsquemaContratacionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAntecedentesEsquemaContratacionId(),
                    root -> root.join(Candidato_.antecedentesEsquemaContratacion, JoinType.LEFT).get(EsqContRec_.id)));
            }
            if (criteria.getEstudiosId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstudiosId(),
                    root -> root.join(Candidato_.estudios, JoinType.LEFT).get(FormacionAcademica_.id)));
            }
            if (criteria.getEstCanInactivoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstCanInactivoId(),
                    root -> root.join(Candidato_.estCanInactivo, JoinType.LEFT).get(EstCanInactivo_.id)));
            }
        }
        return specification;
    }
}
