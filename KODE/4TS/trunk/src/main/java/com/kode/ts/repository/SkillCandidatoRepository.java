package com.kode.ts.repository;

import com.kode.ts.domain.SkillCandidato;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SkillCandidato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillCandidatoRepository extends JpaRepository<SkillCandidato, Long>, JpaSpecificationExecutor<SkillCandidato> {

}
