package com.kode.ts.repository;

import com.kode.ts.domain.TipoSkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoSkillRepository extends JpaRepository<TipoSkill, Long>, JpaSpecificationExecutor<TipoSkill> {

}
