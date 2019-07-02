package com.kode.ts.repository;

import com.kode.ts.domain.EstatusAcademico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusAcademico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusAcademicoRepository extends JpaRepository<EstatusAcademico, Long>, JpaSpecificationExecutor<EstatusAcademico> {

}
