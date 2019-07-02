package com.kode.ts.repository;

import com.kode.ts.domain.EstReqCerrado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstReqCerrado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstReqCerradoRepository extends JpaRepository<EstReqCerrado, Long>, JpaSpecificationExecutor<EstReqCerrado> {

}
