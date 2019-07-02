package com.kode.ts.repository;

import com.kode.ts.domain.Colonia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Colonia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Long>, JpaSpecificationExecutor<Colonia> {

}
