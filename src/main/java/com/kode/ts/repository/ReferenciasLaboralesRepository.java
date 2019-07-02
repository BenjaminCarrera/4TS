package com.kode.ts.repository;

import com.kode.ts.domain.ReferenciasLaborales;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReferenciasLaborales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferenciasLaboralesRepository extends JpaRepository<ReferenciasLaborales, Long>, JpaSpecificationExecutor<ReferenciasLaborales> {

}
