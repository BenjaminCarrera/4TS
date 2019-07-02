package com.kode.ts.repository;

import com.kode.ts.domain.EsqContRec;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EsqContRec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EsqContRecRepository extends JpaRepository<EsqContRec, Long>, JpaSpecificationExecutor<EsqContRec> {

}
