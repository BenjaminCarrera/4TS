package com.kode.ts.repository;

import com.kode.ts.domain.EstatusLaboral;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusLaboral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusLaboralRepository extends JpaRepository<EstatusLaboral, Long>, JpaSpecificationExecutor<EstatusLaboral> {

}
