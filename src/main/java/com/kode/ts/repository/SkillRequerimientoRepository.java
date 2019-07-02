package com.kode.ts.repository;

import com.kode.ts.domain.SkillRequerimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SkillRequerimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRequerimientoRepository extends JpaRepository<SkillRequerimiento, Long>, JpaSpecificationExecutor<SkillRequerimiento> {

}
