package com.kode.ts.repository;

import com.kode.ts.domain.Perfil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Perfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>, JpaSpecificationExecutor<Perfil> {

}
