package com.kode.ts.repository;

import com.kode.ts.domain.FormacionAcademica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FormacionAcademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormacionAcademicaRepository extends JpaRepository<FormacionAcademica, Long>, JpaSpecificationExecutor<FormacionAcademica> {

}
