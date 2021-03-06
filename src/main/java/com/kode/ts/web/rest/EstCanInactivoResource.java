package com.kode.ts.web.rest;

import com.kode.ts.service.EstCanInactivoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstCanInactivoDTO;
import com.kode.ts.service.dto.EstCanInactivoCriteria;
import com.kode.ts.service.EstCanInactivoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstCanInactivo}.
 */
@RestController
@RequestMapping("/api")
public class EstCanInactivoResource {

    private final Logger log = LoggerFactory.getLogger(EstCanInactivoResource.class);

    private static final String ENTITY_NAME = "estCanInactivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstCanInactivoService estCanInactivoService;

    private final EstCanInactivoQueryService estCanInactivoQueryService;

    public EstCanInactivoResource(EstCanInactivoService estCanInactivoService, EstCanInactivoQueryService estCanInactivoQueryService) {
        this.estCanInactivoService = estCanInactivoService;
        this.estCanInactivoQueryService = estCanInactivoQueryService;
    }

    /**
     * {@code POST  /est-can-inactivos} : Create a new estCanInactivo.
     *
     * @param estCanInactivoDTO the estCanInactivoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estCanInactivoDTO, or with status {@code 400 (Bad Request)} if the estCanInactivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/est-can-inactivos")
    public ResponseEntity<EstCanInactivoDTO> createEstCanInactivo(@Valid @RequestBody EstCanInactivoDTO estCanInactivoDTO) throws URISyntaxException {
        log.debug("REST request to save EstCanInactivo : {}", estCanInactivoDTO);
        if (estCanInactivoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estCanInactivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstCanInactivoDTO result = estCanInactivoService.save(estCanInactivoDTO);
        return ResponseEntity.created(new URI("/api/est-can-inactivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /est-can-inactivos} : Updates an existing estCanInactivo.
     *
     * @param estCanInactivoDTO the estCanInactivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estCanInactivoDTO,
     * or with status {@code 400 (Bad Request)} if the estCanInactivoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estCanInactivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/est-can-inactivos")
    public ResponseEntity<EstCanInactivoDTO> updateEstCanInactivo(@Valid @RequestBody EstCanInactivoDTO estCanInactivoDTO) throws URISyntaxException {
        log.debug("REST request to update EstCanInactivo : {}", estCanInactivoDTO);
        if (estCanInactivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstCanInactivoDTO result = estCanInactivoService.save(estCanInactivoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estCanInactivoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /est-can-inactivos} : get all the estCanInactivos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estCanInactivos in body.
     */
    @GetMapping("/est-can-inactivos")
    public ResponseEntity<List<EstCanInactivoDTO>> getAllEstCanInactivos(EstCanInactivoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstCanInactivos by criteria: {}", criteria);
        Page<EstCanInactivoDTO> page = estCanInactivoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /est-can-inactivos/count} : count all the estCanInactivos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/est-can-inactivos/count")
    public ResponseEntity<Long> countEstCanInactivos(EstCanInactivoCriteria criteria) {
        log.debug("REST request to count EstCanInactivos by criteria: {}", criteria);
        return ResponseEntity.ok().body(estCanInactivoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /est-can-inactivos/:id} : get the "id" estCanInactivo.
     *
     * @param id the id of the estCanInactivoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estCanInactivoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/est-can-inactivos/{id}")
    public ResponseEntity<EstCanInactivoDTO> getEstCanInactivo(@PathVariable Long id) {
        log.debug("REST request to get EstCanInactivo : {}", id);
        Optional<EstCanInactivoDTO> estCanInactivoDTO = estCanInactivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estCanInactivoDTO);
    }

    /**
     * {@code DELETE  /est-can-inactivos/:id} : delete the "id" estCanInactivo.
     *
     * @param id the id of the estCanInactivoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/est-can-inactivos/{id}")
    public ResponseEntity<Void> deleteEstCanInactivo(@PathVariable Long id) {
        log.debug("REST request to delete EstCanInactivo : {}", id);
        estCanInactivoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
