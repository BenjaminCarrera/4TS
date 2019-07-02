package com.kode.ts.repository;

import com.kode.ts.domain.PrioridadReq;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrioridadReq entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrioridadReqRepository extends JpaRepository<PrioridadReq, Long>, JpaSpecificationExecutor<PrioridadReq> {

}
