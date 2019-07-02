package com.kode.ts.web.rest;

import com.kode.ts.service.EstatusTareaService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstatusTareaDTO;
import com.kode.ts.service.dto.EstatusTareaCriteria;
import com.kode.ts.service.EstatusTareaQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstatusTarea}.
 */
@RestController
@RequestMapping("/api")
public class EstatusTareaResource {

    private final Logger log = LoggerFactory.getLogger(EstatusTareaResource.class);

    private static final String ENTITY_NAME = "estatusTarea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusTareaService estatusTareaService;

    private final EstatusTareaQueryService estatusTareaQueryService;

    public EstatusTareaResource(EstatusTareaService estatusTareaService, EstatusTareaQueryService estatusTareaQueryService) {
        this.estatusTareaService = estatusTareaService;
        this.estatusTareaQueryService = estatusTareaQueryService;
    }

    /**
     * {@code POST  /estatus-tareas} : Create a new estatusTarea.
     *
     * @param estatusTareaDTO the estatusTareaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusTareaDTO, or with status {@code 400 (Bad Request)} if the estatusTarea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-tareas")
    public ResponseEntity<EstatusTareaDTO> createEstatusTarea(@Valid @RequestBody EstatusTareaDTO estatusTareaDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusTarea : {}", estatusTareaDTO);
        if (estatusTareaDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusTarea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusTareaDTO result = estatusTareaService.save(estatusTareaDTO);
        return ResponseEntity.created(new URI("/api/estatus-tareas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatus-tareas} : Updates an existing estatusTarea.
     *
     * @param estatusTareaDTO the estatusTareaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusTareaDTO,
     * or with status {@code 400 (Bad Request)} if the estatusTareaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusTareaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-tareas")
    public ResponseEntity<EstatusTareaDTO> updateEstatusTarea(@Valid @RequestBody EstatusTareaDTO estatusTareaDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusTarea : {}", estatusTareaDTO);
        if (estatusTareaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusTareaDTO result = estatusTareaService.save(estatusTareaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusTareaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estatus-tareas} : get all the estatusTareas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusTareas in body.
     */
    @GetMapping("/estatus-tareas")
    public ResponseEntity<List<EstatusTareaDTO>> getAllEstatusTareas(EstatusTareaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstatusTareas by criteria: {}", criteria);
        Page<EstatusTareaDTO> page = estatusTareaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /estatus-tareas/count} : count all the estatusTareas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/estatus-tareas/count")
    public ResponseEntity<Long> countEstatusTareas(EstatusTareaCriteria criteria) {
        log.debug("REST request to count EstatusTareas by criteria: {}", criteria);
        return ResponseEntity.ok().body(estatusTareaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estatus-tareas/:id} : get the "id" estatusTarea.
     *
     * @param id the id of the estatusTareaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusTareaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-tareas/{id}")
    public ResponseEntity<EstatusTareaDTO> getEstatusTarea(@PathVariable Long id) {
        log.debug("REST request to get EstatusTarea : {}", id);
        Optional<EstatusTareaDTO> estatusTareaDTO = estatusTareaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusTareaDTO);
    }

    /**
     * {@code DELETE  /estatus-tareas/:id} : delete the "id" estatusTarea.
     *
     * @param id the id of the estatusTareaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-tareas/{id}")
    public ResponseEntity<Void> deleteEstatusTarea(@PathVariable Long id) {
        log.debug("REST request to delete EstatusTarea : {}", id);
        estatusTareaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
