package com.kode.ts.repository;

import com.kode.ts.domain.EsquemaContratacionKode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EsquemaContratacionKode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EsquemaContratacionKodeRepository extends JpaRepository<EsquemaContratacionKode, Long>, JpaSpecificationExecutor<EsquemaContratacionKode> {

}
