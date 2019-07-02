package com.kode.ts.repository;

import com.kode.ts.domain.TipoTarea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoTarea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoTareaRepository extends JpaRepository<TipoTarea, Long>, JpaSpecificationExecutor<TipoTarea> {

}
