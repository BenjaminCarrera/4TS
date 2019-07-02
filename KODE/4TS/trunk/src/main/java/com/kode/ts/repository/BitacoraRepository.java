package com.kode.ts.repository;

import com.kode.ts.domain.Bitacora;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Bitacora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long>, JpaSpecificationExecutor<Bitacora> {

    @Query("select bitacora from Bitacora bitacora where bitacora.usuario.login = ?#{principal.username}")
    List<Bitacora> findByUsuarioIsCurrentUser();

}
