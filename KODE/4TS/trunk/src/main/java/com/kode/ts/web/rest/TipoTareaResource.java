package com.kode.ts.web.rest;

import com.kode.ts.service.TipoTareaService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.TipoTareaDTO;
import com.kode.ts.service.dto.TipoTareaCriteria;
import com.kode.ts.service.TipoTareaQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.TipoTarea}.
 */
@RestController
@RequestMapping("/api")
public class TipoTareaResource {

    private final Logger log = LoggerFactory.getLogger(TipoTareaResource.class);

    private static final String ENTITY_NAME = "tipoTarea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoTareaService tipoTareaService;

    private final TipoTareaQueryService tipoTareaQueryService;

    public TipoTareaResource(TipoTareaService tipoTareaService, TipoTareaQueryService tipoTareaQueryService) {
        this.tipoTareaService = tipoTareaService;
        this.tipoTareaQueryService = tipoTareaQueryService;
    }

    /**
     * {@code POST  /tipo-tareas} : Create a new tipoTarea.
     *
     * @param tipoTareaDTO the tipoTareaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoTareaDTO, or with status {@code 400 (Bad Request)} if the tipoTarea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-tareas")
    public ResponseEntity<TipoTareaDTO> createTipoTarea(@Valid @RequestBody TipoTareaDTO tipoTareaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoTarea : {}", tipoTareaDTO);
        if (tipoTareaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoTarea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoTareaDTO result = tipoTareaService.save(tipoTareaDTO);
        return ResponseEntity.created(new URI("/api/tipo-tareas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-tareas} : Updates an existing tipoTarea.
     *
     * @param tipoTareaDTO the tipoTareaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoTareaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoTareaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoTareaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-tareas")
    public ResponseEntity<TipoTareaDTO> updateTipoTarea(@Valid @RequestBody TipoTareaDTO tipoTareaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoTarea : {}", tipoTareaDTO);
        if (tipoTareaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoTareaDTO result = tipoTareaService.save(tipoTareaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoTareaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-tareas} : get all the tipoTareas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoTareas in body.
     */
    @GetMapping("/tipo-tareas")
    public ResponseEntity<List<TipoTareaDTO>> getAllTipoTareas(TipoTareaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get TipoTareas by criteria: {}", criteria);
        Page<TipoTareaDTO> page = tipoTareaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tipo-tareas/count} : count all the tipoTareas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tipo-tareas/count")
    public ResponseEntity<Long> countTipoTareas(TipoTareaCriteria criteria) {
        log.debug("REST request to count TipoTareas by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoTareaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-tareas/:id} : get the "id" tipoTarea.
     *
     * @param id the id of the tipoTareaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoTareaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-tareas/{id}")
    public ResponseEntity<TipoTareaDTO> getTipoTarea(@PathVariable Long id) {
        log.debug("REST request to get TipoTarea : {}", id);
        Optional<TipoTareaDTO> tipoTareaDTO = tipoTareaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoTareaDTO);
    }

    /**
     * {@code DELETE  /tipo-tareas/:id} : delete the "id" tipoTarea.
     *
     * @param id the id of the tipoTareaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-tareas/{id}")
    public ResponseEntity<Void> deleteTipoTarea(@PathVariable Long id) {
        log.debug("REST request to delete TipoTarea : {}", id);
        tipoTareaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
