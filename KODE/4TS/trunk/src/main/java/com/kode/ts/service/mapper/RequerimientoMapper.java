package com.kode.ts.service.mapper;

import com.kode.ts.domain.*;
import com.kode.ts.service.dto.RequerimientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requerimiento} and its DTO {@link RequerimientoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CuentaMapper.class, UserMapper.class, EstatusRequerimientoMapper.class, PrioridadReqMapper.class, TipoSolicitudMapper.class, TipoIngresoMapper.class, BaseTarifaMapper.class, EsqContRecMapper.class, PerfilMapper.class, NivelPerfilMapper.class, EstReqCerradoMapper.class, TipoPeriodoMapper.class})
public interface RequerimientoMapper extends EntityMapper<RequerimientoDTO, Requerimiento> {

    @Mapping(source = "cuenta.id", target = "cuentaId")
    @Mapping(source = "cuenta.clave", target = "cuentaClave")
    @Mapping(source = "subCuenta.id", target = "subCuentaId")
    @Mapping(source = "subCuenta.clave", target = "subCuentaClave")
    @Mapping(source = "usuarioCreador.id", target = "usuarioCreadorId")
    @Mapping(source = "usuarioCreador.iniciales", target = "usuarioCreadorFirstName")
    @Mapping(source = "usuarioAsignado.id", target = "usuarioAsignadoId")
    @Mapping(source = "usuarioAsignado.iniciales", target = "usuarioAsignadoFirstName")
    @Mapping(source = "estatusRequerimiento.id", target = "estatusRequerimientoId")
    @Mapping(source = "estatusRequerimiento.estatus", target = "estatusRequerimientoEstatus")
    @Mapping(source = "prioridad.id", target = "prioridadId")
    @Mapping(source = "prioridad.prioridad", target = "prioridadPrioridad")
    @Mapping(source = "tipoSolicitud.id", target = "tipoSolicitudId")
    @Mapping(source = "tipoSolicitud.solicitud", target = "tipoSolicitudSolicitud")
    @Mapping(source = "tipoIngreso.id", target = "tipoIngresoId")
    @Mapping(source = "tipoIngreso.tipo", target = "tipoIngresoTipo")
    @Mapping(source = "baseTarifa.id", target = "baseTarifaId")
    @Mapping(source = "baseTarifa.base", target = "baseTarifaBase")
    @Mapping(source = "esquemaContratacion.id", target = "esquemaContratacionId")
    @Mapping(source = "esquemaContratacion.esquema", target = "esquemaContratacionEsquema")
    @Mapping(source = "perfil.id", target = "perfilId")
    @Mapping(source = "perfil.perfil", target = "perfilPerfil")
    @Mapping(source = "nivelPerfil.id", target = "nivelPerfilId")
    @Mapping(source = "nivelPerfil.nivel", target = "nivelPerfilNivel")
    @Mapping(source = "estatusReqCan.id", target = "estatusReqCanId")
    @Mapping(source = "estatusReqCan.motivo", target = "estatusReqCanMotivo")
    @Mapping(source = "tipoPeriodo.id", target = "tipoPeriodoId")
    @Mapping(source = "tipoPeriodo.periodo", target = "tipoPeriodoPeriodo")
    RequerimientoDTO toDto(Requerimiento requerimiento);

    @Mapping(target = "skillRequerimientos", ignore = true)
    @Mapping(target = "removeSkillRequerimiento", ignore = true)
    @Mapping(target = "tareas", ignore = true)
    @Mapping(target = "removeTarea", ignore = true)
    @Mapping(target = "bitacoras", ignore = true)
    @Mapping(target = "removeBitacora", ignore = true)
    @Mapping(source = "cuentaId", target = "cuenta")
    @Mapping(source = "subCuentaId", target = "subCuenta")
    @Mapping(source = "usuarioCreadorId", target = "usuarioCreador")
    @Mapping(source = "usuarioAsignadoId", target = "usuarioAsignado")
    @Mapping(source = "estatusRequerimientoId", target = "estatusRequerimiento")
    @Mapping(source = "prioridadId", target = "prioridad")
    @Mapping(source = "tipoSolicitudId", target = "tipoSolicitud")
    @Mapping(source = "tipoIngresoId", target = "tipoIngreso")
    @Mapping(source = "baseTarifaId", target = "baseTarifa")
    @Mapping(source = "esquemaContratacionId", target = "esquemaContratacion")
    @Mapping(source = "perfilId", target = "perfil")
    @Mapping(source = "nivelPerfilId", target = "nivelPerfil")
    @Mapping(source = "estatusReqCanId", target = "estatusReqCan")
    @Mapping(source = "tipoPeriodoId", target = "tipoPeriodo")
    Requerimiento toEntity(RequerimientoDTO requerimientoDTO);

    default Requerimiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setId(id);
        return requerimiento;
    }
}
