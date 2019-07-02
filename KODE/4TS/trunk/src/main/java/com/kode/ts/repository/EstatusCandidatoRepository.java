package com.kode.ts.repository;

import com.kode.ts.domain.EstatusCandidato;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusCandidato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusCandidatoRepository extends JpaRepository<EstatusCandidato, Long>, JpaSpecificationExecutor<EstatusCandidato> {

}
