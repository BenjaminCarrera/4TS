package com.kode.ts.web.rest;

import com.kode.ts.service.RequerimientoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.RequerimientoDTO;
import com.kode.ts.service.dto.RequerimientoCriteria;
import com.kode.ts.service.RequerimientoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.Requerimiento}.
 */
@RestController
@RequestMapping("/api")
public class RequerimientoResource {

    private final Logger log = LoggerFactory.getLogger(RequerimientoResource.class);

    private static final String ENTITY_NAME = "requerimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequerimientoService requerimientoService;

    private final RequerimientoQueryService requerimientoQueryService;

    public RequerimientoResource(RequerimientoService requerimientoService, RequerimientoQueryService requerimientoQueryService) {
        this.requerimientoService = requerimientoService;
        this.requerimientoQueryService = requerimientoQueryService;
    }

    /**
     * {@code POST  /requerimientos} : Create a new requerimiento.
     *
     * @param requerimientoDTO the requerimientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requerimientoDTO, or with status {@code 400 (Bad Request)} if the requerimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requerimientos")
    public ResponseEntity<RequerimientoDTO> createRequerimiento(@Valid @RequestBody RequerimientoDTO requerimientoDTO) throws URISyntaxException {
        log.debug("REST request to save Requerimiento : {}", requerimientoDTO);
        if (requerimientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new requerimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequerimientoDTO result = requerimientoService.save(requerimientoDTO);
        return ResponseEntity.created(new URI("/api/requerimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requerimientos} : Updates an existing requerimiento.
     *
     * @param requerimientoDTO the requerimientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requerimientoDTO,
     * or with status {@code 400 (Bad Request)} if the requerimientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requerimientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requerimientos")
    public ResponseEntity<RequerimientoDTO> updateRequerimiento(@Valid @RequestBody RequerimientoDTO requerimientoDTO) throws URISyntaxException {
        log.debug("REST request to update Requerimiento : {}", requerimientoDTO);
        if (requerimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequerimientoDTO result = requerimientoService.save(requerimientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requerimientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requerimientos} : get all the requerimientos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requerimientos in body.
     */
    @GetMapping("/requerimientos")
    public ResponseEntity<List<RequerimientoDTO>> getAllRequerimientos(RequerimientoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Requerimientos by criteria: {}", criteria);
        Page<RequerimientoDTO> page = requerimientoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /requerimientos/count} : count all the requerimientos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/requerimientos/count")
    public ResponseEntity<Long> countRequerimientos(RequerimientoCriteria criteria) {
        log.debug("REST request to count Requerimientos by criteria: {}", criteria);
        return ResponseEntity.ok().body(requerimientoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /requerimientos/:id} : get the "id" requerimiento.
     *
     * @param id the id of the requerimientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requerimientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requerimientos/{id}")
    public ResponseEntity<RequerimientoDTO> getRequerimiento(@PathVariable Long id) {
        log.debug("REST request to get Requerimiento : {}", id);
        Optional<RequerimientoDTO> requerimientoDTO = requerimientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requerimientoDTO);
    }

    /**
     * {@code DELETE  /requerimientos/:id} : delete the "id" requerimiento.
     *
     * @param id the id of the requerimientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requerimientos/{id}")
    public ResponseEntity<Void> deleteRequerimiento(@PathVariable Long id) {
        log.debug("REST request to delete Requerimiento : {}", id);
        requerimientoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
