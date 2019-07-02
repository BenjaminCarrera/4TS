package com.kode.ts.repository;

import com.kode.ts.domain.EstatusRequerimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusRequerimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusRequerimientoRepository extends JpaRepository<EstatusRequerimiento, Long>, JpaSpecificationExecutor<EstatusRequerimiento> {

}
