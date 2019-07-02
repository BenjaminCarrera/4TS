package com.kode.ts.repository;

import com.kode.ts.domain.BaseTarifa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BaseTarifa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseTarifaRepository extends JpaRepository<BaseTarifa, Long>, JpaSpecificationExecutor<BaseTarifa> {

}
