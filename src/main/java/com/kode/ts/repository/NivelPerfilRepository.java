package com.kode.ts.repository;

import com.kode.ts.domain.NivelPerfil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NivelPerfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NivelPerfilRepository extends JpaRepository<NivelPerfil, Long>, JpaSpecificationExecutor<NivelPerfil> {

}
