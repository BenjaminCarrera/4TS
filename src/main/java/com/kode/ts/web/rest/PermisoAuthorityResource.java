package com.kode.ts.web.rest;

import com.kode.ts.service.PermisoAuthorityService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.PermisoAuthorityDTO;
import com.kode.ts.service.dto.PermisoAuthorityCriteria;
import com.kode.ts.service.PermisoAuthorityQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kode.ts.domain.PermisoAuthority}.
 */
@RestController
@RequestMapping("/api")
public class PermisoAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(PermisoAuthorityResource.class);

    private static final String ENTITY_NAME = "permisoAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermisoAuthorityService permisoAuthorityService;

    private final PermisoAuthorityQueryService permisoAuthorityQueryService;

    public PermisoAuthorityResource(PermisoAuthorityService permisoAuthorityService, PermisoAuthorityQueryService permisoAuthorityQueryService) {
        this.permisoAuthorityService = permisoAuthorityService;
        this.permisoAuthorityQueryService = permisoAuthorityQueryService;
    }

    /**
     * {@code POST  /permiso-authorities} : Create a new permisoAuthority.
     *
     * @param permisoAuthorityDTO the permisoAuthorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permisoAuthorityDTO, or with status {@code 400 (Bad Request)} if the permisoAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/permiso-authorities")
    public ResponseEntity<PermisoAuthorityDTO> createPermisoAuthority(@Valid @RequestBody PermisoAuthorityDTO permisoAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to save PermisoAuthority : {}", permisoAuthorityDTO);
        if (permisoAuthorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new permisoAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PermisoAuthorityDTO result = permisoAuthorityService.save(permisoAuthorityDTO);
        return ResponseEntity.created(new URI("/api/permiso-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /permiso-authorities} : Updates an existing permisoAuthority.
     *
     * @param permisoAuthorityDTO the permisoAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permisoAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the permisoAuthorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permisoAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/permiso-authorities")
    public ResponseEntity<PermisoAuthorityDTO> updatePermisoAuthority(@Valid @RequestBody PermisoAuthorityDTO permisoAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to update PermisoAuthority : {}", permisoAuthorityDTO);
        if (permisoAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PermisoAuthorityDTO result = permisoAuthorityService.save(permisoAuthorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permisoAuthorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /permiso-authorities} : get all the permisoAuthorities.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permisoAuthorities in body.
     */
    @GetMapping("/permiso-authorities")
    public ResponseEntity<List<PermisoAuthorityDTO>> getAllPermisoAuthorities(PermisoAuthorityCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PermisoAuthorities by criteria: {}", criteria);
        Page<PermisoAuthorityDTO> page = permisoAuthorityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /permiso-authorities/count} : count all the permisoAuthorities.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/permiso-authorities/count")
    public ResponseEntity<Long> countPermisoAuthorities(PermisoAuthorityCriteria criteria) {
        log.debug("REST request to count PermisoAuthorities by criteria: {}", criteria);
        return ResponseEntity.ok().body(permisoAuthorityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /permiso-authorities/:id} : get the "id" permisoAuthority.
     *
     * @param id the id of the permisoAuthorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permisoAuthorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/permiso-authorities/{id}")
    public ResponseEntity<PermisoAuthorityDTO> getPermisoAuthority(@PathVariable Long id) {
        log.debug("REST request to get PermisoAuthority : {}", id);
        Optional<PermisoAuthorityDTO> permisoAuthorityDTO = permisoAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permisoAuthorityDTO);
    }

    /**
     * {@code DELETE  /permiso-authorities/:id} : delete the "id" permisoAuthority.
     *
     * @param id the id of the permisoAuthorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/permiso-authorities/{id}")
    public ResponseEntity<Void> deletePermisoAuthority(@PathVariable Long id) {
        log.debug("REST request to delete PermisoAuthority : {}", id);
        permisoAuthorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
