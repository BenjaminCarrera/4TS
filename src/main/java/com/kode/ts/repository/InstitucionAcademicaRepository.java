package com.kode.ts.repository;

import com.kode.ts.domain.InstitucionAcademica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InstitucionAcademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitucionAcademicaRepository extends JpaRepository<InstitucionAcademica, Long>, JpaSpecificationExecutor<InstitucionAcademica> {

}
