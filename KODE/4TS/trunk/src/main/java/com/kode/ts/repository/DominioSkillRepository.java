package com.kode.ts.repository;

import com.kode.ts.domain.DominioSkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DominioSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DominioSkillRepository extends JpaRepository<DominioSkill, Long>, JpaSpecificationExecutor<DominioSkill> {

}
