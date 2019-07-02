package com.kode.ts.repository;

import com.kode.ts.domain.EstatusReqCanRec;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusReqCanRec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusReqCanRecRepository extends JpaRepository<EstatusReqCanRec, Long>, JpaSpecificationExecutor<EstatusReqCanRec> {

}
