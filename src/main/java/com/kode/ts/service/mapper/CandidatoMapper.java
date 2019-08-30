package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.CandidatoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidato} and its DTO {@link CandidatoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoPeriodoMapper.class, UserMapper.class, DocumentoMapper.class, CuentaMapper.class, FuenteReclutamientoMapper.class, EstatusCandidatoMapper.class, PerfilMapper.class, NivelPerfilMapper.class, InstitucionAcademicaMapper.class, EstatusAcademicoMapper.class, EsquemaContratacionKodeMapper.class, EstatusLaboralMapper.class, ColoniaMapper.class, EsqContRecMapper.class, FormacionAcademicaMapper.class, EstCanInactivoMapper.class})
public interface CandidatoMapper extends EntityMapper<CandidatoDTO, Candidato> {

    @Mapping(source = "disponibilidadEntrevistaPeriodoTiempo.id", target = "disponibilidadEntrevistaPeriodoTiempoId")
    @Mapping(source = "disponibilidadEntrevistaPeriodoTiempo.periodo", target = "disponibilidadEntrevistaPeriodoTiempoPeriodo")
    @Mapping(source = "disponibilidadAsignacionPeriodoTiempo.id", target = "disponibilidadAsignacionPeriodoTiempoId")
    @Mapping(source = "disponibilidadAsignacionPeriodoTiempo.periodo", target = "disponibilidadAsignacionPeriodoTiempoPeriodo")
    @Mapping(source = "usuarioCreador.id", target = "usuarioCreadorId")
    @Mapping(source = "usuarioCreador.iniciales", target = "usuarioCreadorFirstName")
    @Mapping(source = "usuarioAsignado.id", target = "usuarioAsignadoId")
    @Mapping(source = "usuarioAsignado.iniciales", target = "usuarioAsignadoFirstName")
    @Mapping(source = "documento.id", target = "documentoId")
    @Mapping(source = "documento.documento", target = "documentoDocumento")
    @Mapping(source = "fuenteReclutamiento.id", target = "fuenteReclutamientoId")
    @Mapping(source = "fuenteReclutamiento.fuente", target = "fuenteReclutamientoFuente")
    @Mapping(source = "estatusCandidato.id", target = "estatusCandidatoId")
    @Mapping(source = "estatusCandidato.estatus", target = "estatusCandidatoEstatus")
    @Mapping(source = "perfil.id", target = "perfilId")
    @Mapping(source = "perfil.perfil", target = "perfilPerfil")
    @Mapping(source = "nivelPerfil.id", target = "nivelPerfilId")
    @Mapping(source = "nivelPerfil.nivel", target = "nivelPerfilNivel")
    @Mapping(source = "institucionAcademica.id", target = "institucionAcademicaId")
    @Mapping(source = "institucionAcademica.institucion", target = "institucionAcademicaInstitucion")
    @Mapping(source = "estatusAcademico.id", target = "estatusAcademicoId")
    @Mapping(source = "estatusAcademico.estatus", target = "estatusAcademicoEstatus")
    @Mapping(source = "esquemaContratacionKode.id", target = "esquemaContratacionKodeId")
    @Mapping(source = "esquemaContratacionKode.esquema", target = "esquemaContratacionKodeEsquema")
    @Mapping(source = "estatusLaboral.id", target = "estatusLaboralId")
    @Mapping(source = "estatusLaboral.estatus", target = "estatusLaboralEstatus")
    @Mapping(source = "colonia.id", target = "coloniaId")
    @Mapping(source = "colonia.colonia", target = "coloniaColonia")
    @Mapping(source = "antecedentesEsquemaContratacion.id", target = "antecedentesEsquemaContratacionId")
    @Mapping(source = "antecedentesEsquemaContratacion.esquema", target = "antecedentesEsquemaContratacionEsquema")
    @Mapping(source = "estudios.id", target = "estudiosId")
    @Mapping(source = "estudios.formacionAcademica", target = "estudiosFormacionAcademica")
    @Mapping(source = "estCanInactivo.id", target = "estCanInactivoId")
    @Mapping(source = "estCanInactivo.motivo", target = "estCanInactivoMotivo")
    CandidatoDTO toDto(Candidato candidato);

    @Mapping(target = "referenciasLaborales", ignore = true)
    @Mapping(target = "removeReferenciasLaborales", ignore = true)
    @Mapping(target = "skillCandidatoes", ignore = true)
    @Mapping(target = "removeSkillCandidato", ignore = true)
    @Mapping(target = "tareas", ignore = true)
    @Mapping(target = "removeTarea", ignore = true)
    @Mapping(target = "bitacoras", ignore = true)
    @Mapping(target = "removeBitacora", ignore = true)
    @Mapping(source = "disponibilidadEntrevistaPeriodoTiempoId", target = "disponibilidadEntrevistaPeriodoTiempo")
    @Mapping(source = "disponibilidadAsignacionPeriodoTiempoId", target = "disponibilidadAsignacionPeriodoTiempo")
    @Mapping(source = "usuarioCreadorId", target = "usuarioCreador")
    @Mapping(source = "usuarioAsignadoId", target = "usuarioAsignado")
    @Mapping(source = "documentoId", target = "documento")
    @Mapping(target = "removeCuentaInteres", ignore = true)
    @Mapping(target = "removeCuentaRechazadas", ignore = true)
    @Mapping(source = "fuenteReclutamientoId", target = "fuenteReclutamiento")
    @Mapping(source = "estatusCandidatoId", target = "estatusCandidato")
    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(source = "nivelPerfilId", target = "nivelPerfil")
    @Mapping(source = "institucionAcademicaId", target = "institucionAcademica")
    @Mapping(source = "estatusAcademicoId", target = "estatusAcademico")
    @Mapping(source = "esquemaContratacionKodeId", target = "esquemaContratacionKode")
    @Mapping(source = "estatusLaboralId", target = "estatusLaboral")
    @Mapping(source = "coloniaId", target = "colonia")
    @Mapping(source = "antecedentesEsquemaContratacionId", target = "antecedentesEsquemaContratacion")
    @Mapping(source = "estudiosId", target = "estudios")
    @Mapping(source = "estCanInactivoId", target = "estCanInactivo")
    Candidato toEntity(CandidatoDTO candidatoDTO);

    default Candidato fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidato candidato = new Candidato();
        candidato.setId(id);
        return candidato;
    }
}
