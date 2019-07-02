package com.kode.ts.repository;

import com.kode.ts.domain.CodigoPostal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CodigoPostal entity.
 */
@Repository
public interface CodigoPostalRepository extends JpaRepository<CodigoPostal, Long>, JpaSpecificationExecutor<CodigoPostal> {

    @Query(value = "select distinct codigoPostal from CodigoPostal codigoPostal left join fetch codigoPostal.municipios",
        countQuery = "select count(distinct codigoPostal) from CodigoPostal codigoPostal")
    Page<CodigoPostal> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct codigoPostal from CodigoPostal codigoPostal left join fetch codigoPostal.municipios")
    List<CodigoPostal> findAllWithEagerRelationships();

    @Query("select codigoPostal from CodigoPostal codigoPostal left join fetch codigoPostal.municipios where codigoPostal.id =:id")
    Optional<CodigoPostal> findOneWithEagerRelationships(@Param("id") Long id);

}
