package com.kode.ts.repository;

import com.kode.ts.domain.EstatusTarea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusTarea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusTareaRepository extends JpaRepository<EstatusTarea, Long>, JpaSpecificationExecutor<EstatusTarea> {

}
