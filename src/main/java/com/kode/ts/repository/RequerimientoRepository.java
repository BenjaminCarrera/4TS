package com.kode.ts.repository;

import com.kode.ts.domain.Requerimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Requerimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequerimientoRepository extends JpaRepository<Requerimiento, Long>, JpaSpecificationExecutor<Requerimiento> {

    @Query("select requerimiento from Requerimiento requerimiento where requerimiento.usuarioCreador.login = ?#{principal.username}")
    List<Requerimiento> findByUsuarioCreadorIsCurrentUser();

    @Query("select requerimiento from Requerimiento requerimiento where requerimiento.usuarioAsignado.login = ?#{principal.username}")
    List<Requerimiento> findByUsuarioAsignadoIsCurrentUser();

}
