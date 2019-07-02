package com.kode.ts.web.rest;

import com.kode.ts.service.FuenteReclutamientoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.FuenteReclutamientoDTO;
import com.kode.ts.service.dto.FuenteReclutamientoCriteria;
import com.kode.ts.service.FuenteReclutamientoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.FuenteReclutamiento}.
 */
@RestController
@RequestMapping("/api")
public class FuenteReclutamientoResource {

    private final Logger log = LoggerFactory.getLogger(FuenteReclutamientoResource.class);

    private static final String ENTITY_NAME = "fuenteReclutamiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuenteReclutamientoService fuenteReclutamientoService;

    private final FuenteReclutamientoQueryService fuenteReclutamientoQueryService;

    public FuenteReclutamientoResource(FuenteReclutamientoService fuenteReclutamientoService, FuenteReclutamientoQueryService fuenteReclutamientoQueryService) {
        this.fuenteReclutamientoService = fuenteReclutamientoService;
        this.fuenteReclutamientoQueryService = fuenteReclutamientoQueryService;
    }

    /**
     * {@code POST  /fuente-reclutamientos} : Create a new fuenteReclutamiento.
     *
     * @param fuenteReclutamientoDTO the fuenteReclutamientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuenteReclutamientoDTO, or with status {@code 400 (Bad Request)} if the fuenteReclutamiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fuente-reclutamientos")
    public ResponseEntity<FuenteReclutamientoDTO> createFuenteReclutamiento(@Valid @RequestBody FuenteReclutamientoDTO fuenteReclutamientoDTO) throws URISyntaxException {
        log.debug("REST request to save FuenteReclutamiento : {}", fuenteReclutamientoDTO);
        if (fuenteReclutamientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fuenteReclutamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuenteReclutamientoDTO result = fuenteReclutamientoService.save(fuenteReclutamientoDTO);
        return ResponseEntity.created(new URI("/api/fuente-reclutamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fuente-reclutamientos} : Updates an existing fuenteReclutamiento.
     *
     * @param fuenteReclutamientoDTO the fuenteReclutamientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuenteReclutamientoDTO,
     * or with status {@code 400 (Bad Request)} if the fuenteReclutamientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuenteReclutamientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fuente-reclutamientos")
    public ResponseEntity<FuenteReclutamientoDTO> updateFuenteReclutamiento(@Valid @RequestBody FuenteReclutamientoDTO fuenteReclutamientoDTO) throws URISyntaxException {
        log.debug("REST request to update FuenteReclutamiento : {}", fuenteReclutamientoDTO);
        if (fuenteReclutamientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FuenteReclutamientoDTO result = fuenteReclutamientoService.save(fuenteReclutamientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuenteReclutamientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fuente-reclutamientos} : get all the fuenteReclutamientos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuenteReclutamientos in body.
     */
    @GetMapping("/fuente-reclutamientos")
    public ResponseEntity<List<FuenteReclutamientoDTO>> getAllFuenteReclutamientos(FuenteReclutamientoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get FuenteReclutamientos by criteria: {}", criteria);
        Page<FuenteReclutamientoDTO> page = fuenteReclutamientoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /fuente-reclutamientos/count} : count all the fuenteReclutamientos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/fuente-reclutamientos/count")
    public ResponseEntity<Long> countFuenteReclutamientos(FuenteReclutamientoCriteria criteria) {
        log.debug("REST request to count FuenteReclutamientos by criteria: {}", criteria);
        return ResponseEntity.ok().body(fuenteReclutamientoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fuente-reclutamientos/:id} : get the "id" fuenteReclutamiento.
     *
     * @param id the id of the fuenteReclutamientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuenteReclutamientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fuente-reclutamientos/{id}")
    public ResponseEntity<FuenteReclutamientoDTO> getFuenteReclutamiento(@PathVariable Long id) {
        log.debug("REST request to get FuenteReclutamiento : {}", id);
        Optional<FuenteReclutamientoDTO> fuenteReclutamientoDTO = fuenteReclutamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuenteReclutamientoDTO);
    }

    /**
     * {@code DELETE  /fuente-reclutamientos/:id} : delete the "id" fuenteReclutamiento.
     *
     * @param id the id of the fuenteReclutamientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fuente-reclutamientos/{id}")
    public ResponseEntity<Void> deleteFuenteReclutamiento(@PathVariable Long id) {
        log.debug("REST request to delete FuenteReclutamiento : {}", id);
        fuenteReclutamientoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
