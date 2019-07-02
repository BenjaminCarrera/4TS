package com.kode.ts.repository;

import com.kode.ts.domain.Candidato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Candidato entity.
 */
@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long>, JpaSpecificationExecutor<Candidato> {

    @Query("select candidato from Candidato candidato where candidato.usuarioCreador.login = ?#{principal.username}")
    List<Candidato> findByUsuarioCreadorIsCurrentUser();

    @Query("select candidato from Candidato candidato where candidato.usuarioAsignado.login = ?#{principal.username}")
    List<Candidato> findByUsuarioAsignadoIsCurrentUser();

    @Query(value = "select distinct candidato from Candidato candidato left join fetch candidato.cuentaInteres left join fetch candidato.cuentaRechazadas",
        countQuery = "select count(distinct candidato) from Candidato candidato")
    Page<Candidato> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct candidato from Candidato candidato left join fetch candidato.cuentaInteres left join fetch candidato.cuentaRechazadas")
    List<Candidato> findAllWithEagerRelationships();

    @Query("select candidato from Candidato candidato left join fetch candidato.cuentaInteres left join fetch candidato.cuentaRechazadas where candidato.id =:id")
    Optional<Candidato> findOneWithEagerRelationships(@Param("id") Long id);

}
