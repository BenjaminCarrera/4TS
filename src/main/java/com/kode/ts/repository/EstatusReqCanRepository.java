package com.kode.ts.repository;

import com.kode.ts.domain.EstatusReqCan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusReqCan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusReqCanRepository extends JpaRepository<EstatusReqCan, Long>, JpaSpecificationExecutor<EstatusReqCan> {

}
