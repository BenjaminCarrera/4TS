package com.kode.ts.repository;

import com.kode.ts.domain.FuenteReclutamiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FuenteReclutamiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuenteReclutamientoRepository extends JpaRepository<FuenteReclutamiento, Long>, JpaSpecificationExecutor<FuenteReclutamiento> {

}
