package com.kode.ts.repository;

import com.kode.ts.domain.ReqCan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReqCan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReqCanRepository extends JpaRepository<ReqCan, Long>, JpaSpecificationExecutor<ReqCan> {

}
