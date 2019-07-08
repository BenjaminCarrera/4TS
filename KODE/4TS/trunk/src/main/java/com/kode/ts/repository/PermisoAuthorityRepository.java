package com.kode.ts.repository;

import com.kode.ts.domain.PermisoAuthority;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PermisoAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermisoAuthorityRepository extends JpaRepository<PermisoAuthority, Long>, JpaSpecificationExecutor<PermisoAuthority> {

}
