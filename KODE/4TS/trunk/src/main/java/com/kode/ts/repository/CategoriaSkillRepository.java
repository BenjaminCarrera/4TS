package com.kode.ts.repository;

import com.kode.ts.domain.CategoriaSkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaSkillRepository extends JpaRepository<CategoriaSkill, Long>, JpaSpecificationExecutor<CategoriaSkill> {

}
