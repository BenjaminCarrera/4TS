package com.kode.ts.repository;

import com.kode.ts.domain.CodigoPostal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CodigoPostal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodigoPostalRepository extends JpaRepository<CodigoPostal, Long>, JpaSpecificationExecutor<CodigoPostal> {

}
