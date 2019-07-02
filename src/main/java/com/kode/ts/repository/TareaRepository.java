package com.kode.ts.repository;

import com.kode.ts.domain.Tarea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Tarea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long>, JpaSpecificationExecutor<Tarea> {

    @Query("select tarea from Tarea tarea where tarea.usuarioCreador.login = ?#{principal.username}")
    List<Tarea> findByUsuarioCreadorIsCurrentUser();

    @Query("select tarea from Tarea tarea where tarea.usuarioEjecutor.login = ?#{principal.username}")
    List<Tarea> findByUsuarioEjecutorIsCurrentUser();

}
