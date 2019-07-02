package com.kode.ts.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Requerimiento.
 */
@Entity
@Table(name = "requerimiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Requerimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_alda", nullable = false)
    private Instant fechaAlda;

    @Column(name = "fecha_resolucion")
    private Instant fechaResolucion;

    @Size(max = 500)
    @Column(name = "remplazo_de", length = 500)
    private String remplazoDe;

    @Column(name = "vacantes_solicitadas")
    private Integer vacantesSolicitadas;

    @Size(max = 200)
    @Column(name = "proyecto", length = 200)
    private String proyecto;

    @Size(max = 100)
    @Column(name = "nombre_contacto", length = 100)
    private String nombreContacto;

    @Column(name = "tarifa_sueldo_net")
    private Float tarifaSueldoNet;

    @Size(max = 500)
    @Column(name = "prestaciones", length = 500)
    private String prestaciones;

    @Column(name = "duracion_asignacion")
    private Integer duracionAsignacion;

    @Size(max = 500)
    @Column(name = "lugar_trabajo", length = 500)
    private String lugarTrabajo;

    @Column(name = "coor_lat")
    private Float coorLat;

    @Column(name = "coor_long")
    private Float coorLong;

    @Size(max = 300)
    @Column(name = "horario", length = 300)
    private String horario;

    @Size(max = 500)
    @Column(name = "informacion_examen", length = 500)
    private String informacionExamen;

    @Size(max = 1000)
    @Column(name = "informacion_adicional", length = 1000)
    private String informacionAdicional;

    @OneToMany(mappedBy = "idRequerimiento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillRequerimiento> skillRequerimientos = new HashSet<>();

    @OneToMany(mappedBy = "requerimiento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarea> tareas = new HashSet<>();

    @OneToMany(mappedBy = "requerimiento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bitacora> bitacoras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private Cuenta cuenta;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private Cuenta subCuenta;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private User usuarioCreador;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private User usuarioAsignado;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private EstatusRequerimiento estatusRequerimiento;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private PrioridadReq prioridad;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private TipoSolicitud tipoSolicitud;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private TipoIngreso tipoIngreso;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private BaseTarifa baseTarifa;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private EsqContRec esquemaContratacion;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private Perfil perfil;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private NivelPerfil nivelPerfil;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private EstReqCerrado estatusReqCan;

    @ManyToOne
    @JsonIgnoreProperties("requerimientos")
    private TipoPeriodo tipoPeriodo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaAlda() {
        return fechaAlda;
    }

    public Requerimiento fechaAlda(Instant fechaAlda) {
        this.fechaAlda = fechaAlda;
        return this;
    }

    public void setFechaAlda(Instant fechaAlda) {
        this.fechaAlda = fechaAlda;
    }

    public Instant getFechaResolucion() {
        return fechaResolucion;
    }

    public Requerimiento fechaResolucion(Instant fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
        return this;
    }

    public void setFechaResolucion(Instant fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getRemplazoDe() {
        return remplazoDe;
    }

    public Requerimiento remplazoDe(String remplazoDe) {
        this.remplazoDe = remplazoDe;
        return this;
    }

    public void setRemplazoDe(String remplazoDe) {
        this.remplazoDe = remplazoDe;
    }

    public Integer getVacantesSolicitadas() {
        return vacantesSolicitadas;
    }

    public Requerimiento vacantesSolicitadas(Integer vacantesSolicitadas) {
        this.vacantesSolicitadas = vacantesSolicitadas;
        return this;
    }

    public void setVacantesSolicitadas(Integer vacantesSolicitadas) {
        this.vacantesSolicitadas = vacantesSolicitadas;
    }

    public String getProyecto() {
        return proyecto;
    }

    public Requerimiento proyecto(String proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public Requerimiento nombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
        return this;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public Float getTarifaSueldoNet() {
        return tarifaSueldoNet;
    }

    public Requerimiento tarifaSueldoNet(Float tarifaSueldoNet) {
        this.tarifaSueldoNet = tarifaSueldoNet;
        return this;
    }

    public void setTarifaSueldoNet(Float tarifaSueldoNet) {
        this.tarifaSueldoNet = tarifaSueldoNet;
    }

    public String getPrestaciones() {
        return prestaciones;
    }

    public Requerimiento prestaciones(String prestaciones) {
        this.prestaciones = prestaciones;
        return this;
    }

    public void setPrestaciones(String prestaciones) {
        this.prestaciones = prestaciones;
    }

    public Integer getDuracionAsignacion() {
        return duracionAsignacion;
    }

    public Requerimiento duracionAsignacion(Integer duracionAsignacion) {
        this.duracionAsignacion = duracionAsignacion;
        return this;
    }

    public void setDuracionAsignacion(Integer duracionAsignacion) {
        this.duracionAsignacion = duracionAsignacion;
    }

    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    public Requerimiento lugarTrabajo(String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
        return this;
    }

    public void setLugarTrabajo(String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }

    public Float getCoorLat() {
        return coorLat;
    }

    public Requerimiento coorLat(Float coorLat) {
        this.coorLat = coorLat;
        return this;
    }

    public void setCoorLat(Float coorLat) {
        this.coorLat = coorLat;
    }

    public Float getCoorLong() {
        return coorLong;
    }

    public Requerimiento coorLong(Float coorLong) {
        this.coorLong = coorLong;
        return this;
    }

    public void setCoorLong(Float coorLong) {
        this.coorLong = coorLong;
    }

    public String getHorario() {
        return horario;
    }

    public Requerimiento horario(String horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getInformacionExamen() {
        return informacionExamen;
    }

    public Requerimiento informacionExamen(String informacionExamen) {
        this.informacionExamen = informacionExamen;
        return this;
    }

    public void setInformacionExamen(String informacionExamen) {
        this.informacionExamen = informacionExamen;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public Requerimiento informacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
        return this;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public Set<SkillRequerimiento> getSkillRequerimientos() {
        return skillRequerimientos;
    }

    public Requerimiento skillRequerimientos(Set<SkillRequerimiento> skillRequerimientos) {
        this.skillRequerimientos = skillRequerimientos;
        return this;
    }

    public Requerimiento addSkillRequerimiento(SkillRequerimiento skillRequerimiento) {
        this.skillRequerimientos.add(skillRequerimiento);
        skillRequerimiento.setIdRequerimiento(this);
        return this;
    }

    public Requerimiento removeSkillRequerimiento(SkillRequerimiento skillRequerimiento) {
        this.skillRequerimientos.remove(skillRequerimiento);
        skillRequerimiento.setIdRequerimiento(null);
        return this;
    }

    public void setSkillRequerimientos(Set<SkillRequerimiento> skillRequerimientos) {
        this.skillRequerimientos = skillRequerimientos;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public Requerimiento tareas(Set<Tarea> tareas) {
        this.tareas = tareas;
        return this;
    }

    public Requerimiento addTarea(Tarea tarea) {
        this.tareas.add(tarea);
        tarea.setRequerimiento(this);
        return this;
    }

    public Requerimiento removeTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.setRequerimiento(null);
        return this;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Set<Bitacora> getBitacoras() {
        return bitacoras;
    }

    public Requerimiento bitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
        return this;
    }

    public Requerimiento addBitacora(Bitacora bitacora) {
        this.bitacoras.add(bitacora);
        bitacora.setRequerimiento(this);
        return this;
    }

    public Requerimiento removeBitacora(Bitacora bitacora) {
        this.bitacoras.remove(bitacora);
        bitacora.setRequerimiento(null);
        return this;
    }

    public void setBitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public Requerimiento cuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
        return this;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Cuenta getSubCuenta() {
        return subCuenta;
    }

    public Requerimiento subCuenta(Cuenta cuenta) {
        this.subCuenta = cuenta;
        return this;
    }

    public void setSubCuenta(Cuenta cuenta) {
        this.subCuenta = cuenta;
    }

    public User getUsuarioCreador() {
        return usuarioCreador;
    }

    public Requerimiento usuarioCreador(User user) {
        this.usuarioCreador = user;
        return this;
    }

    public void setUsuarioCreador(User user) {
        this.usuarioCreador = user;
    }

    public User getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public Requerimiento usuarioAsignado(User user) {
        this.usuarioAsignado = user;
        return this;
    }

    public void setUsuarioAsignado(User user) {
        this.usuarioAsignado = user;
    }

    public EstatusRequerimiento getEstatusRequerimiento() {
        return estatusRequerimiento;
    }

    public Requerimiento estatusRequerimiento(EstatusRequerimiento estatusRequerimiento) {
        this.estatusRequerimiento = estatusRequerimiento;
        return this;
    }

    public void setEstatusRequerimiento(EstatusRequerimiento estatusRequerimiento) {
        this.estatusRequerimiento = estatusRequerimiento;
    }

    public PrioridadReq getPrioridad() {
        return prioridad;
    }

    public Requerimiento prioridad(PrioridadReq prioridadReq) {
        this.prioridad = prioridadReq;
        return this;
    }

    public void setPrioridad(PrioridadReq prioridadReq) {
        this.prioridad = prioridadReq;
    }

    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public Requerimiento tipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
        return this;
    }

    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public TipoIngreso getTipoIngreso() {
        return tipoIngreso;
    }

    public Requerimiento tipoIngreso(TipoIngreso tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
        return this;
    }

    public void setTipoIngreso(TipoIngreso tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public BaseTarifa getBaseTarifa() {
        return baseTarifa;
    }

    public Requerimiento baseTarifa(BaseTarifa baseTarifa) {
        this.baseTarifa = baseTarifa;
        return this;
    }

    public void setBaseTarifa(BaseTarifa baseTarifa) {
        this.baseTarifa = baseTarifa;
    }

    public EsqContRec getEsquemaContratacion() {
        return esquemaContratacion;
    }

    public Requerimiento esquemaContratacion(EsqContRec esqContRec) {
        this.esquemaContratacion = esqContRec;
        return this;
    }

    public void setEsquemaContratacion(EsqContRec esqContRec) {
        this.esquemaContratacion = esqContRec;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public Requerimiento perfil(Perfil perfil) {
        this.perfil = perfil;
        return this;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public NivelPerfil getNivelPerfil() {
        return nivelPerfil;
    }

    public Requerimiento nivelPerfil(NivelPerfil nivelPerfil) {
        this.nivelPerfil = nivelPerfil;
        return this;
    }

    public void setNivelPerfil(NivelPerfil nivelPerfil) {
        this.nivelPerfil = nivelPerfil;
    }

    public EstReqCerrado getEstatusReqCan() {
        return estatusReqCan;
    }

    public Requerimiento estatusReqCan(EstReqCerrado estReqCerrado) {
        this.estatusReqCan = estReqCerrado;
        return this;
    }

    public void setEstatusReqCan(EstReqCerrado estReqCerrado) {
        this.estatusReqCan = estReqCerrado;
    }

    public TipoPeriodo getTipoPeriodo() {
        return tipoPeriodo;
    }

    public Requerimiento tipoPeriodo(TipoPeriodo tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
        return this;
    }

    public void setTipoPeriodo(TipoPeriodo tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requerimiento)) {
            return false;
        }
        return id != null && id.equals(((Requerimiento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Requerimiento{" +
            "id=" + getId() +
            ", fechaAlda='" + getFechaAlda() + "'" +
            ", fechaResolucion='" + getFechaResolucion() + "'" +
            ", remplazoDe='" + getRemplazoDe() + "'" +
            ", vacantesSolicitadas=" + getVacantesSolicitadas() +
            ", proyecto='" + getProyecto() + "'" +
            ", nombreContacto='" + getNombreContacto() + "'" +
            ", tarifaSueldoNet=" + getTarifaSueldoNet() +
            ", prestaciones='" + getPrestaciones() + "'" +
            ", duracionAsignacion=" + getDuracionAsignacion() +
            ", lugarTrabajo='" + getLugarTrabajo() + "'" +
            ", coorLat=" + getCoorLat() +
            ", coorLong=" + getCoorLong() +
            ", horario='" + getHorario() + "'" +
            ", informacionExamen='" + getInformacionExamen() + "'" +
            ", informacionAdicional='" + getInformacionAdicional() + "'" +
            "}";
    }
}
