package com.kode.ts.repository;

import com.kode.ts.domain.Documento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Documento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long>, JpaSpecificationExecutor<Documento> {

}
