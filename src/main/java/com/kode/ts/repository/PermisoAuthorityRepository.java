package com.kode.ts.repository;

import com.kode.ts.domain.PermisoAuthority;
import com.kode.ts.domain.User;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PermisoAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermisoAuthorityRepository extends JpaRepository<PermisoAuthority, Long>, JpaSpecificationExecutor<PermisoAuthority> {
    List<PermisoAuthority> findAllByActivatedIsTrueAndDeletedIsFalseAndAuthority(String authority);
}
