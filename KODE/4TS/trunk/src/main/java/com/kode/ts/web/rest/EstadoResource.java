package com.kode.ts.web.rest;

import com.kode.ts.service.EstadoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstadoDTO;
import com.kode.ts.service.dto.EstadoCriteria;
import com.kode.ts.service.EstadoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.Estado}.
 */
@RestController
@RequestMapping("/api")
public class EstadoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoResource.class);

    private static final String ENTITY_NAME = "estado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoService estadoService;

    private final EstadoQueryService estadoQueryService;

    public EstadoResource(EstadoService estadoService, EstadoQueryService estadoQueryService) {
        this.estadoService = estadoService;
        this.estadoQueryService = estadoQueryService;
    }

    /**
     * {@code POST  /estados} : Create a new estado.
     *
     * @param estadoDTO the estadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoDTO, or with status {@code 400 (Bad Request)} if the estado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estados")
    public ResponseEntity<EstadoDTO> createEstado(@Valid @RequestBody EstadoDTO estadoDTO) throws URISyntaxException {
        log.debug("REST request to save Estado : {}", estadoDTO);
        if (estadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoDTO result = estadoService.save(estadoDTO);
        return ResponseEntity.created(new URI("/api/estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estados} : Updates an existing estado.
     *
     * @param estadoDTO the estadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoDTO,
     * or with status {@code 400 (Bad Request)} if the estadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estados")
    public ResponseEntity<EstadoDTO> updateEstado(@Valid @RequestBody EstadoDTO estadoDTO) throws URISyntaxException {
        log.debug("REST request to update Estado : {}", estadoDTO);
        if (estadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoDTO result = estadoService.save(estadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estados} : get all the estados.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estados in body.
     */
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoDTO>> getAllEstados(EstadoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Estados by criteria: {}", criteria);
        Page<EstadoDTO> page = estadoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /estados/count} : count all the estados.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/estados/count")
    public ResponseEntity<Long> countEstados(EstadoCriteria criteria) {
        log.debug("REST request to count Estados by criteria: {}", criteria);
        return ResponseEntity.ok().body(estadoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estados/:id} : get the "id" estado.
     *
     * @param id the id of the estadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estados/{id}")
    public ResponseEntity<EstadoDTO> getEstado(@PathVariable Long id) {
        log.debug("REST request to get Estado : {}", id);
        Optional<EstadoDTO> estadoDTO = estadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoDTO);
    }

    /**
     * {@code DELETE  /estados/:id} : delete the "id" estado.
     *
     * @param id the id of the estadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estados/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        log.debug("REST request to delete Estado : {}", id);
        estadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
