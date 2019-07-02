package com.kode.ts.web.rest;

import com.kode.ts.service.TareaService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.TareaDTO;
import com.kode.ts.service.dto.TareaCriteria;
import com.kode.ts.service.TareaQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.Tarea}.
 */
@RestController
@RequestMapping("/api")
public class TareaResource {

    private final Logger log = LoggerFactory.getLogger(TareaResource.class);

    private static final String ENTITY_NAME = "tarea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TareaService tareaService;

    private final TareaQueryService tareaQueryService;

    public TareaResource(TareaService tareaService, TareaQueryService tareaQueryService) {
        this.tareaService = tareaService;
        this.tareaQueryService = tareaQueryService;
    }

    /**
     * {@code POST  /tareas} : Create a new tarea.
     *
     * @param tareaDTO the tareaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tareaDTO, or with status {@code 400 (Bad Request)} if the tarea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tareas")
    public ResponseEntity<TareaDTO> createTarea(@Valid @RequestBody TareaDTO tareaDTO) throws URISyntaxException {
        log.debug("REST request to save Tarea : {}", tareaDTO);
        if (tareaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TareaDTO result = tareaService.save(tareaDTO);
        return ResponseEntity.created(new URI("/api/tareas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tareas} : Updates an existing tarea.
     *
     * @param tareaDTO the tareaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tareaDTO,
     * or with status {@code 400 (Bad Request)} if the tareaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tareaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tareas")
    public ResponseEntity<TareaDTO> updateTarea(@Valid @RequestBody TareaDTO tareaDTO) throws URISyntaxException {
        log.debug("REST request to update Tarea : {}", tareaDTO);
        if (tareaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TareaDTO result = tareaService.save(tareaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tareaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tareas} : get all the tareas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tareas in body.
     */
    @GetMapping("/tareas")
    public ResponseEntity<List<TareaDTO>> getAllTareas(TareaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Tareas by criteria: {}", criteria);
        Page<TareaDTO> page = tareaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tareas/count} : count all the tareas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tareas/count")
    public ResponseEntity<Long> countTareas(TareaCriteria criteria) {
        log.debug("REST request to count Tareas by criteria: {}", criteria);
        return ResponseEntity.ok().body(tareaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tareas/:id} : get the "id" tarea.
     *
     * @param id the id of the tareaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tareaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tareas/{id}")
    public ResponseEntity<TareaDTO> getTarea(@PathVariable Long id) {
        log.debug("REST request to get Tarea : {}", id);
        Optional<TareaDTO> tareaDTO = tareaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tareaDTO);
    }

    /**
     * {@code DELETE  /tareas/:id} : delete the "id" tarea.
     *
     * @param id the id of the tareaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        log.debug("REST request to delete Tarea : {}", id);
        tareaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
