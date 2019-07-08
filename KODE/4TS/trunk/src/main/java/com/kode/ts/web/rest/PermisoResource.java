package com.kode.ts.web.rest;

import com.kode.ts.service.PermisoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.PermisoDTO;
import com.kode.ts.service.dto.PermisoCriteria;
import com.kode.ts.service.PermisoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.Permiso}.
 */
@RestController
@RequestMapping("/api")
public class PermisoResource {

    private final Logger log = LoggerFactory.getLogger(PermisoResource.class);

    private static final String ENTITY_NAME = "permiso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermisoService permisoService;

    private final PermisoQueryService permisoQueryService;

    public PermisoResource(PermisoService permisoService, PermisoQueryService permisoQueryService) {
        this.permisoService = permisoService;
        this.permisoQueryService = permisoQueryService;
    }

    /**
     * {@code POST  /permisos} : Create a new permiso.
     *
     * @param permisoDTO the permisoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permisoDTO, or with status {@code 400 (Bad Request)} if the permiso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/permisos")
    public ResponseEntity<PermisoDTO> createPermiso(@Valid @RequestBody PermisoDTO permisoDTO) throws URISyntaxException {
        log.debug("REST request to save Permiso : {}", permisoDTO);
        if (permisoDTO.getId() != null) {
            throw new BadRequestAlertException("A new permiso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PermisoDTO result = permisoService.save(permisoDTO);
        return ResponseEntity.created(new URI("/api/permisos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /permisos} : Updates an existing permiso.
     *
     * @param permisoDTO the permisoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permisoDTO,
     * or with status {@code 400 (Bad Request)} if the permisoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permisoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/permisos")
    public ResponseEntity<PermisoDTO> updatePermiso(@Valid @RequestBody PermisoDTO permisoDTO) throws URISyntaxException {
        log.debug("REST request to update Permiso : {}", permisoDTO);
        if (permisoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PermisoDTO result = permisoService.save(permisoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permisoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /permisos} : get all the permisos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permisos in body.
     */
    @GetMapping("/permisos")
    public ResponseEntity<List<PermisoDTO>> getAllPermisos(PermisoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Permisos by criteria: {}", criteria);
        Page<PermisoDTO> page = permisoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /permisos/count} : count all the permisos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/permisos/count")
    public ResponseEntity<Long> countPermisos(PermisoCriteria criteria) {
        log.debug("REST request to count Permisos by criteria: {}", criteria);
        return ResponseEntity.ok().body(permisoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /permisos/:id} : get the "id" permiso.
     *
     * @param id the id of the permisoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permisoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/permisos/{id}")
    public ResponseEntity<PermisoDTO> getPermiso(@PathVariable Long id) {
        log.debug("REST request to get Permiso : {}", id);
        Optional<PermisoDTO> permisoDTO = permisoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permisoDTO);
    }

    /**
     * {@code DELETE  /permisos/:id} : delete the "id" permiso.
     *
     * @param id the id of the permisoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/permisos/{id}")
    public ResponseEntity<Void> deletePermiso(@PathVariable Long id) {
        log.debug("REST request to delete Permiso : {}", id);
        permisoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
