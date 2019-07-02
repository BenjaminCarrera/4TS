package com.kode.ts.repository;

import com.kode.ts.domain.TipoIngreso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoIngreso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoIngresoRepository extends JpaRepository<TipoIngreso, Long>, JpaSpecificationExecutor<TipoIngreso> {

}
