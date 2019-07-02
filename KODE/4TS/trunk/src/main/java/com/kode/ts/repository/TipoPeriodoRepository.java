package com.kode.ts.repository;

import com.kode.ts.domain.TipoPeriodo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoPeriodo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPeriodoRepository extends JpaRepository<TipoPeriodo, Long>, JpaSpecificationExecutor<TipoPeriodo> {

}
