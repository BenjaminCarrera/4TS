package com.kode.ts.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.kode.ts.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.kode.ts.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.kode.ts.domain.User.class.getName());
            createCache(cm, com.kode.ts.domain.Authority.class.getName());
            createCache(cm, com.kode.ts.domain.User.class.getName() + ".authorities");
            createCache(cm, com.kode.ts.domain.Candidato.class.getName());
            createCache(cm, com.kode.ts.domain.Candidato.class.getName() + ".referenciasLaborales");
            createCache(cm, com.kode.ts.domain.Candidato.class.getName() + ".skillCandidatoes");
            createCache(cm, com.kode.ts.domain.Candidato.class.getName() + ".tareas");
            createCache(cm, com.kode.ts.domain.Candidato.class.getName() + ".bitacoras");
            createCache(cm, com.kode.ts.domain.Candidato.class.getName() + ".cuentaInteres");
            createCache(cm, com.kode.ts.domain.Candidato.class.getName() + ".cuentaRechazadas");
            createCache(cm, com.kode.ts.domain.Documento.class.getName());
            createCache(cm, com.kode.ts.domain.FormacionAcademica.class.getName());
            createCache(cm, com.kode.ts.domain.FormacionAcademica.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.Colonia.class.getName());
            createCache(cm, com.kode.ts.domain.Colonia.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.Municipio.class.getName());
            createCache(cm, com.kode.ts.domain.Municipio.class.getName() + ".colonias");
            createCache(cm, com.kode.ts.domain.Municipio.class.getName() + ".codigoPostals");
            createCache(cm, com.kode.ts.domain.Estado.class.getName());
            createCache(cm, com.kode.ts.domain.Estado.class.getName() + ".municipios");
            createCache(cm, com.kode.ts.domain.CodigoPostal.class.getName());
            createCache(cm, com.kode.ts.domain.CodigoPostal.class.getName() + ".colonias");
            createCache(cm, com.kode.ts.domain.CodigoPostal.class.getName() + ".municipios");
            createCache(cm, com.kode.ts.domain.Perfil.class.getName());
            createCache(cm, com.kode.ts.domain.Perfil.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.Perfil.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.InstitucionAcademica.class.getName());
            createCache(cm, com.kode.ts.domain.InstitucionAcademica.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.EstatusCandidato.class.getName());
            createCache(cm, com.kode.ts.domain.EstatusCandidato.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.EstatusCandidato.class.getName() + ".estCanInactivos");
            createCache(cm, com.kode.ts.domain.EstCanInactivo.class.getName());
            createCache(cm, com.kode.ts.domain.EstCanInactivo.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.TipoPeriodo.class.getName());
            createCache(cm, com.kode.ts.domain.TipoPeriodo.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.NivelPerfil.class.getName());
            createCache(cm, com.kode.ts.domain.NivelPerfil.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.NivelPerfil.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.ReferenciasLaborales.class.getName());
            createCache(cm, com.kode.ts.domain.EstatusAcademico.class.getName());
            createCache(cm, com.kode.ts.domain.EstatusAcademico.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.FuenteReclutamiento.class.getName());
            createCache(cm, com.kode.ts.domain.FuenteReclutamiento.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.EsquemaContratacionKode.class.getName());
            createCache(cm, com.kode.ts.domain.EsquemaContratacionKode.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.EsqContRec.class.getName());
            createCache(cm, com.kode.ts.domain.EsqContRec.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.EsqContRec.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.EstatusLaboral.class.getName());
            createCache(cm, com.kode.ts.domain.EstatusLaboral.class.getName() + ".candidatoes");
            createCache(cm, com.kode.ts.domain.Cuenta.class.getName());
            createCache(cm, com.kode.ts.domain.Cuenta.class.getName() + ".candidatoInteres");
            createCache(cm, com.kode.ts.domain.Cuenta.class.getName() + ".candidatoRechazadas");
            createCache(cm, com.kode.ts.domain.Skill.class.getName());
            createCache(cm, com.kode.ts.domain.Skill.class.getName() + ".skillCandidatoes");
            createCache(cm, com.kode.ts.domain.Skill.class.getName() + ".skillRequerimientos");
            createCache(cm, com.kode.ts.domain.TipoSkill.class.getName());
            createCache(cm, com.kode.ts.domain.TipoSkill.class.getName() + ".skillRequerimientos");
            createCache(cm, com.kode.ts.domain.CategoriaSkill.class.getName());
            createCache(cm, com.kode.ts.domain.CategoriaSkill.class.getName() + ".skills");
            createCache(cm, com.kode.ts.domain.SkillCandidato.class.getName());
            createCache(cm, com.kode.ts.domain.DominioSkill.class.getName());
            createCache(cm, com.kode.ts.domain.DominioSkill.class.getName() + ".skillCandidatoes");
            createCache(cm, com.kode.ts.domain.Requerimiento.class.getName());
            createCache(cm, com.kode.ts.domain.Requerimiento.class.getName() + ".skillRequerimientos");
            createCache(cm, com.kode.ts.domain.Requerimiento.class.getName() + ".tareas");
            createCache(cm, com.kode.ts.domain.Requerimiento.class.getName() + ".bitacoras");
            createCache(cm, com.kode.ts.domain.EstatusRequerimiento.class.getName());
            createCache(cm, com.kode.ts.domain.EstatusRequerimiento.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.EstatusRequerimiento.class.getName() + ".estReqCerrados");
            createCache(cm, com.kode.ts.domain.EstReqCerrado.class.getName());
            createCache(cm, com.kode.ts.domain.EstReqCerrado.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.PrioridadReq.class.getName());
            createCache(cm, com.kode.ts.domain.PrioridadReq.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.TipoSolicitud.class.getName());
            createCache(cm, com.kode.ts.domain.TipoSolicitud.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.TipoIngreso.class.getName());
            createCache(cm, com.kode.ts.domain.TipoIngreso.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.BaseTarifa.class.getName());
            createCache(cm, com.kode.ts.domain.BaseTarifa.class.getName() + ".requerimientos");
            createCache(cm, com.kode.ts.domain.SkillRequerimiento.class.getName());
            createCache(cm, com.kode.ts.domain.EstatusReqCan.class.getName());
            createCache(cm, com.kode.ts.domain.EstatusReqCan.class.getName() + ".estatusReqCanRecs");
            createCache(cm, com.kode.ts.domain.EstatusReqCanRec.class.getName());
            createCache(cm, com.kode.ts.domain.Tarea.class.getName());
            createCache(cm, com.kode.ts.domain.Tarea.class.getName() + ".bitacoras");
            createCache(cm, com.kode.ts.domain.EstatusTarea.class.getName());
            createCache(cm, com.kode.ts.domain.EstatusTarea.class.getName() + ".tareas");
            createCache(cm, com.kode.ts.domain.TipoTarea.class.getName());
            createCache(cm, com.kode.ts.domain.TipoTarea.class.getName() + ".tareas");
            createCache(cm, com.kode.ts.domain.Bitacora.class.getName());
            createCache(cm, com.kode.ts.domain.ReqCan.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
