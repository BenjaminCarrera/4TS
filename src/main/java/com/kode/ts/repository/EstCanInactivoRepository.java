package com.kode.ts.repository;

import com.kode.ts.domain.EstCanInactivo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstCanInactivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstCanInactivoRepository extends JpaRepository<EstCanInactivo, Long>, JpaSpecificationExecutor<EstCanInactivo> {

}
